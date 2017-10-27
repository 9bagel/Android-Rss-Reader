package com.bagel91.devbyrssreader.internet;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bagel91.devbyrssreader.R;
import com.bagel91.devbyrssreader.datebase.Load;
import com.bagel91.devbyrssreader.datebase.Save;
import com.bagel91.devbyrssreader.details.onDescListener;
import com.bagel91.devbyrssreader.titles.onTitlesListener;

public class ParseRss extends AsyncTask<String, Void, String> {

	private ProgressDialog progressDialog;
	private Context context;
	private String pdText, pdWait;

	public ParseRss(Context context, String pdWait, String pdText) {
		this.context = context;
		this.pdText = pdText;
		this.pdWait = pdWait;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		InternetChecker cd = new InternetChecker(context);

		if (!cd.isInternet()) {
			Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG)
					.show();
			return;
		}
		progressDialog = ProgressDialog.show(context, pdWait, pdText, true);
	}

	@Override
	protected String doInBackground(String... params) {
		Document doc = null;

		try {
			ArrayList<String> desc = new ArrayList<>();
			ArrayList<String> titles = new ArrayList<>();

			doc = Jsoup.connect(params[0]).get();
			Elements title = doc.select("title");
			Elements descr = doc.select("description");
			for (Element link : title) {

				String html = link.text().toString();
				html = html.replaceAll("</div>", "\n");
				html = html.replaceAll("\\<.*?>", "");
				html = html.replaceAll("&.*?;", "");

				titles.add(html);
			}
			for (Element link : descr) {

				String html = link.text().toString();
				html = html.replaceAll("</div>", "\n");
				html = html.replaceAll("\\<.*?>", "");
				html = html.replaceAll("&.*?;", "");

				desc.add(html);
			}

			onDescListener.INSTANCE.setDesc(desc);
			onTitlesListener.INSTANCE.setTitles(titles);

		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, R.string.errorHost, Toast.LENGTH_LONG)
					.show();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		new Save(context).SaveData(onTitlesListener.INSTANCE.getTitles(),
				onDescListener.INSTANCE.getDesc());
		progressDialog.dismiss();
		new Load().LoadData(context);
	}
}
