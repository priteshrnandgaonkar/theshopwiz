package com.tech.shopwiz.modified;

import java.util.HashMap;
import java.util.List;

import com.tech.shopwiz.R;

import android.support.v4.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class ProdFragment extends Fragment {
	ExpandableListView explvlist;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild; //parent and child map
	HashMap<String, List<String>> listDataMultiHeader;
    HashMap<String,HashMap<String,List<String>>> listMultiDataChild;
    Location loc=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 View rootView = inflater.inflate(R.layout.main, container, false);
		 
		return rootView;
	}

}
