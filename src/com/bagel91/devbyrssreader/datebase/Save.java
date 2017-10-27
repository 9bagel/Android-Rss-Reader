package com.bagel91.devbyrssreader.datebase;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Save {
	private Context context;

	public Save(Context context) {
		this.context = context;
	}

	public void SaveData(ArrayList<String> titleList, ArrayList<String> descList) {
		DBHelper dbHelper = new DBHelper(context);
		ContentValues cv = new ContentValues();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("mytable", null, null);
		for (int i = 0; i < titleList.size(); ++i) {
			cv.put("title", titleList.get(i));
			cv.put("desc", descList.get(i));
			db.insert("mytable", null, cv);
		}
		Log.d("Error", "--- Rows in mytable(save): ---");
		Cursor c = db.query("mytable", null, null, null, null, null, null);

		if (c.moveToFirst()) {
			c.close();
			db.close();
		} else {
			Log.d("Error", "0 rows");
			c.close();
			db.close();
		}
	}
}
