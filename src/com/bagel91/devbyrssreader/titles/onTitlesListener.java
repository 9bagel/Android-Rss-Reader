package com.bagel91.devbyrssreader.titles;

import java.util.ArrayList;

public enum onTitlesListener {
	INSTANCE;
	private ArrayList<String> titles = new ArrayList<>();

	public ArrayList<String> getTitles() {
		return titles;
	}

	public void setTitles(ArrayList<String> titles) {
		this.titles = titles;
	}

}
