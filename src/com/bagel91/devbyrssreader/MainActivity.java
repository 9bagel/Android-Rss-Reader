package com.bagel91.devbyrssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bagel91.devbyrssreader.datebase.Load;
import com.bagel91.devbyrssreader.details.DetailsActivity;
import com.bagel91.devbyrssreader.details.DetailsFragment;
import com.bagel91.devbyrssreader.internet.InternetChecker;
import com.bagel91.devbyrssreader.internet.ParseRss;
import com.bagel91.devbyrssreader.titles.TitlesFragment.onItemClickListener;

public class MainActivity extends ActionBarActivity implements
		onItemClickListener {

	private static final String URL = "http://events.dev.by/rss";

	private int position = 0;

	private boolean withDetails = true;

	private Load load = new Load();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (load.LoadData(getApplicationContext()) == false) {
			new ParseRss(this, getResources().getString(R.string.pdWait),
					getResources().getString(R.string.pdText)).execute(URL);
		}

		setContentView(R.layout.main);

		if (savedInstanceState != null)
			position = savedInstanceState.getInt("position");

		withDetails = (findViewById(R.id.cont) != null);

		if (withDetails)
			showDetails(position);
	}

	private void showDetails(int pos) {

		if (withDetails) {
			DetailsFragment details = (DetailsFragment) getSupportFragmentManager()
					.findFragmentById(R.id.cont);

			if (details == null || details.getPosition() != pos) {
				details = DetailsFragment.newInstance(pos);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.cont, details).commit();
			}

		} else {
			startActivity(new Intent(getApplicationContext(),
					DetailsActivity.class).putExtra("position", position));
		}
	}

	@Override
	public void itemClick(int position) {

		this.position = position;

		showDetails(position);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

		outState.putInt("position", position);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemRefresh:
			InternetChecker cd = new InternetChecker(getApplicationContext());
			if (cd.isInternet()) {
				new ParseRss(this, getResources().getString(R.string.pdWait),
						getResources().getString(R.string.pdText)).execute(URL);
			} else {
				Toast.makeText(getApplicationContext(), R.string.noInternet,
						Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}