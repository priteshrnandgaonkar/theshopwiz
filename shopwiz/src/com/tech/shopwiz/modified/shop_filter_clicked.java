package com.tech.shopwiz.modified;

import java.util.ArrayList;
import java.util.List;

import com.tech.shopwiz.InteractiveArrayAdapter;
import com.tech.shopwiz.Model;
import com.tech.shopwiz.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class shop_filter_clicked extends Activity{
	InteractiveArrayAdapter adapter_active_men;
	InteractiveArrayAdapter adapter_active_women;
	InteractiveArrayAdapter adapter_active_kids;
	ListView listView_filter;
	LinearLayout tv_men;
	LinearLayout tv_women;
	LinearLayout tv_kid;
	String current_clicked;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter_shop_page);
		listView_filter=(ListView) findViewById(R.id.listview_new_filter_shop);
		tv_men=(LinearLayout) findViewById(R.id.men_txtView_shop);
		tv_women=(LinearLayout) findViewById(R.id.women_txtView_shop);
		tv_kid=(LinearLayout) findViewById(R.id.kid_txtView_shop);
		current_clicked="men";
		LinearLayout action_lin_original=(LinearLayout) findViewById(R.id.action_bar_lin_layout);
		 
		 adapter_active_men = new InteractiveArrayAdapter(this,
					getModelMen());
			adapter_active_women = new InteractiveArrayAdapter(this,
					getModelWomen());
			adapter_active_kids = new InteractiveArrayAdapter(this,
					getModelKids());
tv_men.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					current_clicked="men";
					// TODO Auto-generated method stub
					tv_men.setBackgroundColor(Color.parseColor("#232424"));
					tv_women.setBackgroundColor(Color.BLACK);
					tv_kid.setBackgroundColor(Color.BLACK);
					//adapter_active = new InteractiveArrayAdapter(getActivity(),
						//	getModelMen());
					listView_filter.setAdapter(adapter_active_men);
				}
			});
	tv_women.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					current_clicked="women";
					// TODO Auto-generated method stub
					tv_men.setBackgroundColor(Color.BLACK);
					tv_kid.setBackgroundColor(Color.BLACK);
					tv_women.setBackgroundColor(Color.parseColor("#232424"));
				//	adapter_active = new InteractiveArrayAdapter(getActivity(),
					//		getModelWomen());
					listView_filter.setAdapter(adapter_active_women);
				}
			});
	tv_kid.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			current_clicked="kids";
			tv_men.setBackgroundColor(Color.BLACK);
			tv_women.setBackgroundColor(Color.BLACK);
			tv_kid.setBackgroundColor(Color.parseColor("#232424"));
		//	adapter_active = new InteractiveArrayAdapter(getActivity(),
			//		getModelKids());
			listView_filter.setAdapter(adapter_active_kids);
		}
	});
	
	}
	private List<Model> getModelMen(){
		 List<Model> list = new ArrayList<Model>();
		 list.add(get("T-Shirt","Men"));
		 list.add(get("Casual Shirt","Men"));
		 list.add(get("Formal Shirt","Men"));
		 list.add(get("Jackets","Men"));
		 list.add(get("Chinos and Trousers","Men"));
		 list.add(get("Jeans","Men"));
		 list.add(get("Formal Trouser","Men"));
		 list.add(get("Shorts","Men"));
		 list.add(get("Inners","Men"));
		 list.add(get("Tracksuit","Men"));
		 list.add(get("Swimwear","Men"));
		 list.add(get("Ethnic Wear","Men"));
		 list.add(get("Sweaters","Men"));
		 list.add(get("Gloves","Men"));
		return list;	
	}
	private List<Model> getModelKids(){
		 List<Model> list = new ArrayList<Model>();
		 list.add(get("T-Shirt","Kids"));
		 list.add(get("Casual Shirt","Kids"));
		 list.add(get("Formal Shirt","Kids"));
		 list.add(get("Jackets","Kids"));
		 list.add(get("Chinos and Trousers","Kids"));
		 list.add(get("Jeans","Kids"));
		 list.add(get("Formal Trouser","Kids"));
		 list.add(get("Shorts","Kids"));
		 list.add(get("Inners","Kids"));
		 list.add(get("Tracksuit","Kids"));
		 list.add(get("Swimwear","Kids"));
		 list.add(get("Ethnic Wear","Kids"));
		 list.add(get("Sweaters","Kids"));
		 list.add(get("Gloves","Kids"));
		return list;	
	}
	private List<Model> getModelWomen(){
		 List<Model> list = new ArrayList<Model>();
		 list.add(get("T-Shirt","Women"));
		 list.add(get("Casual Shirt","Women"));
		 list.add(get("Formal Shirt","Women"));
		 list.add(get("Jackets","Women"));
		 list.add(get("Chinos and Trousers","Women"));
		 list.add(get("Jeans","Women"));
		 list.add(get("Formal Trouser","Women"));
		 list.add(get("Shorts","Women"));
		 list.add(get("Suit","Women"));
		 list.add(get("Kurti","Women"));
		 list.add(get("Innerwear and Sleepwear","Men"));
		 list.add(get("Tracksuit","Men"));
		 list.add(get("Swimwear","Men"));
		 list.add(get("Sweaters","Men"));
		 list.add(get("Gloves","Men"));
		return list;	
	}
	private Model get(String s,String h) {
	    return new Model(s,h);
	  }
}
