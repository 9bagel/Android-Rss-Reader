package com.bagel91.devbyrssreader.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	public DBHelper(Context context) {
		super(context, "myDB", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("Error", "--- onCreate database ---");
		db.execSQL("create table mytable ("
				+ "id integer primary key autoincrement," + "title text,"
				+ "desc text" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
