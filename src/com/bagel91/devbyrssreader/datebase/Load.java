package com.bagel91.devbyrssreader.datebase;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bagel91.devbyrssreader.details.onDescListener;
import com.bagel91.devbyrssreader.titles.onTitlesListener;

public class Load {

	public boolean LoadData(Context context) {
		DBHelper dbHelper = new DBHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Log.d("Error", "--- Rows in mytable(load): ---");

		Cursor c = db.query("mytable", null, null, null, null, null, null);
		if (c.moveToFirst()) {

			ArrayList<String> titles = new ArrayList<>();
			ArrayList<String> desc = new ArrayList<>();

			int titleColIndex = c.getColumnIndex("title");
			int descColIndex = c.getColumnIndex("desc");
			do {
				titles.add(c.getString(titleColIndex));
				desc.add(c.getString(descColIndex));
			} while (c.moveToNext());

			c.close();
			db.close();

			onDescListener.INSTANCE.setDesc(desc);
			onTitlesListener.INSTANCE.setTitles(titles);
			//TitlesFragment.adapter.notifyDataSetChanged();

			return true;
		} else {
			Log.d("Error", "0 rows");
			c.close();
			db.close();
			return false;
		}
	}
}