package com.tech.shopwiz;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tech.shopwiz.ListViewShopPopulate.fill_ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopFragment extends Fragment {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case R.id.filter_reset_button_new_shop:
            Toast.makeText(getActivity(), "reset", Toast.LENGTH_LONG).show();
            break;
        default:
            break;
        }
		return super.onOptionsItemSelected(item);
		
		
	}
	private RelativeLayout rl_front;
	private LinearLayout lin_anim;
	private Location loc;
	private ListView listView_shop;
	Dialog d1;	
	AlertDialog.Builder builder;
	ArrayList <ShopInfo> shop_list;
	ArrayList <ShopInfo> unchangeable_shop_list;
	Button filter_butt;
	Button filter_apply_butt;
	Button back_butt;
	Button reset_butt;
	InteractiveArrayAdapter adapter_active_men;
	InteractiveArrayAdapter adapter_active_women;
	InteractiveArrayAdapter adapter_active_kids;
	ListView listView_filter;
	LinearLayout tv_men;
	LinearLayout tv_women;
	LinearLayout tv_kid;
	String current_clicked;
	public void setLocation(Location _loc){
		this.loc=_loc;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.main_listview_shop, null);
		listView_shop=(ListView) rootView.findViewById(R.id.listview_shop);
		listView_filter=(ListView) rootView.findViewById(R.id.listview_new_filter_shop);
		tv_men=(LinearLayout) rootView.findViewById(R.id.men_txtView_shop);
		tv_women=(LinearLayout) rootView.findViewById(R.id.women_txtView_shop);
		tv_kid=(LinearLayout) rootView.findViewById(R.id.kid_txtView_shop);
		current_clicked="men";
		loc=new Location("dummyprovider");
		setHasOptionsMenu(true);
		
		 LinearLayout action_lin_original=(LinearLayout) rootView.findViewById(R.id.action_bar_lin_layout);
		 
		 adapter_active_men = new InteractiveArrayAdapter(getActivity(),
					getModelMen());
			adapter_active_women = new InteractiveArrayAdapter(getActivity(),
					getModelWomen());
			adapter_active_kids = new InteractiveArrayAdapter(getActivity(),
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
			rl_front=(RelativeLayout) rootView.findViewById(R.id.front_layout_shop);
			lin_anim =(LinearLayout) rootView.findViewById(R.id.linearlayout_anim_new_shop);
			//adapter_active = new InteractiveArrayAdapter(getActivity(),
				//	getModelMen());
			LayoutInflater inflater2 = (LayoutInflater) getActivity()
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  View v = inflater2.inflate(R.layout.custom_action_bar_filter,null,false);
			listView_filter.setAdapter(adapter_active_men);
			 LinearLayout action_lin=(LinearLayout)v.findViewById(R.id.action_bar_lin_layout_2);
			filter_butt=(Button) rootView.findViewById(R.id.filter_button_shop);
			filter_apply_butt=(Button) v.findViewById(R.id.filter_applied_button_new_shop);
			back_butt=(Button) v.findViewById(R.id.filter_back_button_new_shop);
			reset_butt=(Button)v.findViewById(R.id.filter_reset_button_new_shop);
			
			reset_butt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "reset_butt_clicked", Toast.LENGTH_SHORT).show();

					adapter_active_men = new InteractiveArrayAdapter(getActivity(),
							getModelMen());
					adapter_active_women = new InteractiveArrayAdapter(getActivity(),
							getModelWomen());
					adapter_active_kids = new InteractiveArrayAdapter(getActivity(),
							getModelKids());
					if(current_clicked.equals("men")){
						listView_filter.setAdapter(adapter_active_men);
					}
					else if(current_clicked.equals("women")){
						listView_filter.setAdapter(adapter_active_women);
					}
					else{
						listView_filter.setAdapter(adapter_active_kids);
					}
				}
			});
			filter_butt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "filter_butt_clicked", Toast.LENGTH_SHORT).show();

					getActivity().getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
					getActivity().getActionBar().setCustomView(R.layout.custom_action_bar_filter);
					 
					lin_anim.setVisibility(View.VISIBLE);
					 TranslateAnimation tanim = new TranslateAnimation(
				                TranslateAnimation.RELATIVE_TO_PARENT, 0,
				                TranslateAnimation.RELATIVE_TO_PARENT, 0,
				                TranslateAnimation.ABSOLUTE,((RelativeLayout)lin_anim.getParent()).getHeight(),
				                TranslateAnimation.RELATIVE_TO_PARENT, 0.0f
				                );
				        tanim.setDuration(400);
				        tanim.setFillAfter(false);
				        tanim.setInterpolator(new DecelerateInterpolator());
				       
				       tanim.setAnimationListener(new AnimationListener(){

							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								rl_front.setVisibility(View.GONE);
								lin_anim.requestFocus();
								//b3.setVisibility(View.GONE);	
							}
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub	
							}
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub	
							}
					 				        }
					        		);
				       lin_anim.setAnimation(tanim);	
				}
				 
			});
			back_butt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "back_butt_clicked", Toast.LENGTH_SHORT).show();
					 getActivity().getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
					 getActivity().getActionBar().setCustomView(R.layout.custom_action_bar);
					rl_front.setVisibility(View.VISIBLE);
					System.out.println("back button clicked");
					TranslateAnimation tanim1 = new TranslateAnimation(
	  		                TranslateAnimation.RELATIVE_TO_PARENT, 0,
	  		                TranslateAnimation.RELATIVE_TO_PARENT, 0,
	  		              TranslateAnimation.ABSOLUTE, 0,
	  		                TranslateAnimation.ABSOLUTE,((RelativeLayout)rl_front.getParent()).getHeight()
	  		              
	  		                );
	  		        tanim1.setDuration(400);
	  		        tanim1.setFillAfter(false);
	  		        tanim1.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							lin_anim.setVisibility(View.GONE);
							rl_front.requestFocus();
							//rl_front.setVisibility(View.VISIBLE);
							System.out.println("anim end");
						}
					});
	  		      tanim1.setInterpolator(new DecelerateInterpolator());
	  		        lin_anim.setAnimation(tanim1);
				}
			});
			filter_apply_butt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 getActivity().getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
					 getActivity().getActionBar().setCustomView(R.layout.custom_action_bar);
					
					ArrayList<Model> selected_men_models=new ArrayList<Model>();
					ArrayList<Model> selected_women_models=new ArrayList<Model>();
					ArrayList<Model> selected_kids_models=new ArrayList<Model>();
					ArrayList<String> selected_strings=new ArrayList<String>();		
					selected_men_models=(ArrayList<Model>) adapter_active_men.getListModels();
					selected_women_models=(ArrayList<Model>) adapter_active_women.getListModels();
					selected_kids_models=(ArrayList<Model>) adapter_active_kids.getListModels();
					for(int i=0;i<selected_men_models.size();++i){
						 String tmp=selected_men_models.get(i).getName();
						 tmp= tmp.toLowerCase();
						 tmp=tmp.replaceAll("\\s+","");
						selected_strings.add("m_"+tmp);
					}
					for(int i=0;i<selected_women_models.size();++i){
						 String tmp=selected_women_models.get(i).getName();
						 tmp= tmp.toLowerCase();
						 tmp=tmp.replaceAll("\\s+","");
						selected_strings.add("w_"+tmp);
					}for(int i=0;i<selected_kids_models.size();++i){
						 String tmp=selected_kids_models.get(i).getName();
						 tmp= tmp.toLowerCase();
						 tmp=tmp.replaceAll("\\s+","");
						selected_strings.add("k_"+tmp);
					}
					if(selected_strings.size()>0){
					shop_list=new ArrayList<ShopInfo>();
					for(int i=0;i<selected_strings.size();++i){
						for(int j=0;j<unchangeable_shop_list.size();++j){
							String cat_all=unchangeable_shop_list.get(j).categories;
							System.out.println(cat_all);
							String [] cat_split=cat_all.split("\\+");
							//ArrayList<String> list_cat = (ArrayList<String>) Arrays.asList(cat_split);
							for(int k=0;k<cat_split.length;++k){
							if(selected_strings.get(i).equals(cat_split[k])){
								shop_list.add(unchangeable_shop_list.get(j));
								break;
							}
							}
						}
					}
					shop_list = new ArrayList<ShopInfo>(new LinkedHashSet<ShopInfo>(shop_list));
				}
					else{
						shop_list=unchangeable_shop_list;
					}
					after_load();
					
					rl_front.setVisibility(View.VISIBLE);
					System.out.println("back button clicked");
					TranslateAnimation tanim1 = new TranslateAnimation(
				                TranslateAnimation.RELATIVE_TO_PARENT, 0,
				                TranslateAnimation.RELATIVE_TO_PARENT, 0,
				              TranslateAnimation.ABSOLUTE, 0,
				                TranslateAnimation.ABSOLUTE,((RelativeLayout)rl_front.getParent()).getHeight()
				              
				                );
				        tanim1.setDuration(400);
				        tanim1.setFillAfter(false);
				        tanim1.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							lin_anim.setVisibility(View.GONE);
							rl_front.requestFocus();
							//rl_front.setVisibility(View.VISIBLE);
							System.out.println("anim end");
						}
					});
				      tanim1.setInterpolator(new DecelerateInterpolator());
				        lin_anim.setAnimation(tanim1);
				}
			});
			shop_list=new ArrayList<ShopInfo>();
			unchangeable_shop_list=new ArrayList<ShopInfo>();
		//changeddddddddddddddddddddddddd
			loc=new Location("dummyprovider");
			loc.setLatitude((double)19.064146);
			loc.setLongitude((double)72.835472);
			fill_ui runner = new fill_ui();
		    runner.execute();
		 return rootView;
	}
	private String getJSON(String body) {
		String ret="error";
		try {
			body=body+"&"+"lat="+Double.toString(loc.getLatitude())+"&"+"lon="+Double.toString(loc.getLongitude());
			HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(body);
	    final HttpParams httpParams = httpclient.getParams();
	  
		//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	//	System.out.println(EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs)));
	    HttpConnectionParams.setConnectionTimeout(httpParams, 150000);
        HttpConnectionParams.setSoTimeout(httpParams, 150000);
        HttpResponse response;
		response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        ret=EntityUtils.toString(entity);
        System.out.println(ret);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return ret;
	}
	catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return ret;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return ret;
	}
		return ret;
		// TODO Auto-generated method stub
		
	}
	public ArrayList<ShopInfo> prepareData2(String s) {
		// TODO Auto-generated method stub	
		ArrayList<ShopInfo> from_internet_list_prods=new ArrayList<ShopInfo>(); 
		try {
			JSONArray a=new JSONArray(s);
			ShopInfo temp=new ShopInfo(1,"westment","dombivli west",null,"imageURL","landline no","mobile no","website","kurti",19.064146,72.835472);
			
			for(int i=0;i<a.length();++i){
				JSONObject e = a.getJSONObject(i);
				temp=new ShopInfo(e.getInt("Id"),e.getString("Name"),e.getString("Address")
						,e.getString("Description"),e.getString("Image"),e.getString("landline_no")
						,e.getString("mobile_no"),e.getString("Website")
						,e.getString("Categories"),e.getDouble("Lattitude"),e.getDouble("Longitude"));				
				from_internet_list_prods.add(temp);				
			}
			return from_internet_list_prods;
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	
		return new ArrayList<ShopInfo>();	
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
	public void after_load(){
		ArrayList<ArrayList<ShopInfo>> list_shopInfo=new ArrayList<ArrayList<ShopInfo>>();
		if(shop_list.size()%2==0){
			for(int i=0;i<shop_list.size();){
				ArrayList<ShopInfo> tmp_shop=new ArrayList<ShopInfo>();
				tmp_shop.add(shop_list.get(i));
				tmp_shop.add(shop_list.get(i+1));
				list_shopInfo.add(tmp_shop);
				i=i+2;
			}
		}
		else{
			for(int i=0;i<shop_list.size()-1;){
				ArrayList<ShopInfo> tmp_shop=new ArrayList<ShopInfo>();
				tmp_shop.add(shop_list.get(i));
				tmp_shop.add(shop_list.get(i+1));
				list_shopInfo.add(tmp_shop);
				i=i+2;
			}
			ArrayList<ShopInfo> tmp_shop=new ArrayList<ShopInfo>();
			tmp_shop.add(shop_list.get(shop_list.size()-1));
			list_shopInfo.add(tmp_shop);
		}
		MySimpleArrayAdapterShop adapter=new MySimpleArrayAdapterShop(getActivity(),list_shopInfo);
		listView_shop.setAdapter(adapter);
	}
	public class fill_ui extends AsyncTask <Void,Void,String>{

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			//,"http://theshopwiz.com/app/filterProducts.php?"
			return getJSON("http://theshopwiz.com/app/tableShops.php?");
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!result.equals("error")){
				shop_list=prepareData2(result);
				unchangeable_shop_list=shop_list;
			//prepareData3(result.get(1));
			d1.dismiss();
			after_load();
			}
			else{
				d1.dismiss();
				Toast.makeText(getActivity(),"error from the getJson", Toast.LENGTH_SHORT).show();

			}
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			builder=new AlertDialog.Builder (getActivity());
			//dialog = builder.create();
			d1=new Dialog(getActivity());
			d1.setTitle("Please wait");
			d1.setCancelable(false);
			d1.show();
			
		}
	
	}
	public void reset_filter_butt_clicked(View v){
		Toast.makeText(getActivity(), "reset_butt_clicked", Toast.LENGTH_SHORT).show();
		adapter_active_men = new InteractiveArrayAdapter(getActivity(),
				getModelMen());
		adapter_active_women = new InteractiveArrayAdapter(getActivity(),
				getModelWomen());
		adapter_active_kids = new InteractiveArrayAdapter(getActivity(),
				getModelKids());
		if(current_clicked.equals("men")){
			listView_filter.setAdapter(adapter_active_men);
		}
		else if(current_clicked.equals("women")){
			listView_filter.setAdapter(adapter_active_women);
		}
		else{
			listView_filter.setAdapter(adapter_active_kids);
		}
	}
	public void back_butt_clicked(View v){
		Toast.makeText(getActivity(), "back_butt_clicked", Toast.LENGTH_SHORT).show();
		 
	}
	
}
