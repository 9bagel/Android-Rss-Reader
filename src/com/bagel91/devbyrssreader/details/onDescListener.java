package com.bagel91.devbyrssreader.details;

import java.util.ArrayList;

public enum onDescListener {
	INSTANCE;
	private ArrayList<String> desc = new ArrayList<>();

	public ArrayList<String> getDesc() {
		return desc;
	}

	public void setDesc(ArrayList<String> desc) {
		this.desc = desc;
	}
}
