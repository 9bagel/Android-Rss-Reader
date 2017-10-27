package com.bagel91.devbyrssreader.details;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bagel91.devbyrssreader.R;

public class DetailsFragment extends Fragment {

	public static DetailsFragment newInstance(int pos) {
		DetailsFragment details = new DetailsFragment();
		Bundle args = new Bundle();
		args.putInt("position", pos);
		details.setArguments(args);
		return details;
	}

	public int getPosition() {
		return getArguments().getInt("position", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ArrayList<String> desc = new ArrayList<>();
		desc = onDescListener.INSTANCE.getDesc();
		View v = inflater.inflate(R.layout.details, container, false);
		TextView tv = (TextView) v.findViewById(R.id.tvText);
		tv.setText(desc.get(getPosition()));
		return v;
	}
}
