package com.tech.shopwiz.modified;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.tech.shopwiz.R;

public class SpinnerAdapter extends BaseAdapter {
	private List<String> mItems = new ArrayList<String>();
	Activity c;
	public SpinnerAdapter(Activity _c) {
		// TODO Auto-generated constructor stub
		this.c=_c;
	}
	public void clear() {
		mItems.clear();
	}

	public void addItem(String yourObject) {
		mItems.add(yourObject);
	}
	public void addItems(List<String> yourObjectList) {
		mItems.addAll(yourObjectList);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getDropDownView(int position, View view, ViewGroup parent) {
		if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
			view = c.getLayoutInflater().inflate(R.layout.spinner_item_dropdown, parent, false);
			view.setTag("DROPDOWN");
		}

		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setText(getTitle(position));

		return view;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
			 LayoutInflater inflator = (c).getLayoutInflater();
			view = inflator.inflate(R.layout.
					spinner_item, parent, false);
			view.setTag("NON_DROPDOWN");
		}
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setText(getTitle(position));
		return view;
	}
	private String getTitle(int position) {
		return position >= 0 && position < mItems.size() ? mItems.get(position): "";
	}
}
