package com.tech.shopwiz;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.os.AsyncTask;
import android.os.StrictMode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tech.shopwiz.R.color;
import com.tech.shopwiz.heart_activity.request_location;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListviewProductsViaItemSearch extends Activity {
//LazyImageLoadAdapter la;I
ImageLoader imloader;
ArrayList<String> unknown_field_names;
HashMap<String,ArrayList<String>> filter_entries;
Dialog d1;	
AlertDialog.Builder builder;
Location loc;
ProductInfo pi_1;
ProductInfo pi_2;
ActionBar actionbar;
String request_string;
ArrayList<ArrayList<ProductInfo>> arraylist_product_list;
ArrayList <String> allImagesUrl ;
ArrayList<ProductInfo> product_list;
ArrayList <String> shop_names;
RelativeLayout rl_front;
RelativeLayout rl_anim;
LinearLayout lin_anim;
ListView lv;
int max_cost;
int min_cost;
ListView lv_anim_cost;
ListView lv_anim_brand;
InteractiveArrayAdapter adapter_cost;
InteractiveArrayAdapter adapter_brand;
String category;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_listview_file_prod);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		shop_names=new ArrayList<String>();
	 lv=(ListView)findViewById(R.id.listview);
		//MySimpleArrayAdapter adapter=
		allImagesUrl=new ArrayList<String>();
		loc=new Location("dummyprovider");
		actionbar=getActionBar();
		 rl_front=(RelativeLayout) findViewById(R.id.front_layout);
	////////////////////changed
		 	 rl_anim =(RelativeLayout) findViewById(R.id.relativelayout_anim);
		 	 //lin_anim=(LinearLayout) findViewById(R.id.linearlayout_anim_new);
		// lv_anim_cost=(ListView) findViewById(R.id.listview_cost);
		 //lv_anim_brand=(ListView) findViewById(R.id.listview_brand);
		///changed 
		Bundle extras = getIntent().getExtras();
		String gender=null;
		category=null;
		String value=null;
		if (extras != null) {
		    value = extras.getString("Gender and category");
		    loc=(Location) extras.get("Location");
		    gender=extras.getString("Gender");
		    category=extras.getString("Category");
		   category= category.toLowerCase();
		   category=category.replaceAll("-", "");
		    category=category.replaceAll("\\s+","");
		    //System.out.println(category);
		}
		loc=new Location("dummyprovider");
		loc.setLatitude((double)19.064146);
		loc.setLongitude((double)72.835472);
		Toast.makeText(this, "lat is "+Double.toString(loc.getLatitude())+" "+"lon is "+Double.toString(loc.getLongitude()), Toast.LENGTH_LONG).show();
		if(gender.equalsIgnoreCase("Men")){
			request_string="m";
			request_string=request_string+"_"+category;
		}
		else if(gender.equalsIgnoreCase("Women")){
			request_string="w";
			request_string=request_string+"_"+category;
		}
		else{
			request_string="k";
			request_string=request_string+"_"+category;
		}
		System.out.println(request_string);
		
		actionbar.setTitle(value);
		
		Bitmap icon1 = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.kurti1);
		Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.suit1);
		pi_1=new ProductInfo("K1","Westment",null , 700, icon1,"dhikna",100,"");
		pi_2=new ProductInfo("K2","Globus",null , 1700, icon2,"dhikna",100,"");
		pi_1.addBitmap(icon1);
		pi_2.addBitmap(icon2);
	    product_list=new ArrayList<ProductInfo>();
		//product_list.add(pi_1);
		//product_list.add(pi_2);
		arraylist_product_list=new ArrayList<ArrayList<ProductInfo>>();
		//arraylist_product_list.add(product_list);
		//arraylist_product_list.add(product_list);
		//arraylist_product_list.add(product_list);
		
		//product_list=prepareData();
		fill_ui runner = new fill_ui();
	    runner.execute();
		
		
		/*LayoutInflater inflater = (LayoutInflater) this
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  View  rowView = inflater.inflate(R.layout.example,null,false);
		  this.setContentView(rowView);*/
	}
	private ArrayList<ProductInfo>  prepareData2(String s) {
		// TODO Auto-generated method stub
		//String s=getJSON();
		ArrayList<String> field_names=new ArrayList<String>();
		ArrayList<String> known_field_names=new ArrayList<String>();
		unknown_field_names=new ArrayList<String>();
		
		ArrayList<ProductInfo> from_internet_list_prods=new ArrayList<ProductInfo>(); 
		Bitmap icon1 = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.kurti1);
		Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.suit1);
		try {
			JSONArray a=new JSONArray(s);
			ProductInfo temp=new ProductInfo("K1","Westment",null , 700, icon2,"dhikna",100,"");
			JSONObject issueObj = a.getJSONObject(0);
			Iterator iterator = issueObj.keys();		
			known_field_names.add("prod_id");
			known_field_names.add("ac_prod_id");
			known_field_names.add("Description");
			known_field_names.add("Cost");
			known_field_names.add("Brand");
			known_field_names.add("shop_id");
			known_field_names.add("Rating");
			known_field_names.add("prod_image");
			
			
			   while(iterator.hasNext()){
			    String key = (String)iterator.next();
			    field_names.add(key);
			    }
			  
			   for(int j=0;j<field_names.size();++j){
				   if(! known_field_names.contains(field_names.get(j))){
					   unknown_field_names.add(field_names.get(j));
				   }
			   }
			for(int i=0;i<a.length();++i){
				JSONObject e = a.getJSONObject(i);
				temp=new ProductInfo("K1","Westment",null , 700, icon2,"dhikna",100,"");
				temp.addProductId(e.getString("ac_prod_id"));
				
				temp.dummy_prod_id=e.getString("prod_id");
				
				temp.addDescription(e.getString("Description"));
				temp.addCost(e.getInt("Cost"));
				temp.addShopName(e.getString("Brand"));
				temp.addShopId(e.getString("shop_id"));
				temp.addRating(e.getInt("Rating"));
				//temp.addImageUrl(e.getString("prod_image"));
				HashMap<String,String> tp=new HashMap<String,String>();
				for(int k=0;k<unknown_field_names.size();++k){
					tp.put(unknown_field_names.get(k),e.getString(unknown_field_names.get(k)));
				}
				temp.addUnknownFields(tp);
				from_internet_list_prods.add(temp);				
			}
			//ArrayList<String> img_urls=generateURL(from_internet_list_prods);
			return from_internet_list_prods;
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	
		return new ArrayList<ProductInfo>();
	}
	private void prepareData3(String s) {
		// TODO Auto-generated method stub
		//String s=getJSON();
		filter_entries=new HashMap<String,ArrayList<String>>();
		try {
			JSONArray a=new JSONArray(s);
			for(int k=0;k<unknown_field_names.size();++k){
				filter_entries.put(unknown_field_names.get(k), new ArrayList<String>());
			}
			for(int i=0;i<a.length()-1;++i){
				JSONObject e = a.getJSONObject(i);
		
		System.out.println("has brand"+!e.has("brand"));
				if(e.has("brand")){
				shop_names.add(e.getString("brand"));
				Log.i("brand", e.getString("brand"));
				}
				else{////changed here///////////////////////////
					for(int k=0;k<unknown_field_names.size();++k){
						if( e.has(unknown_field_names.get(k))){
							filter_entries.get(unknown_field_names.get(k)).add(e.getString(unknown_field_names.get(k)));
							Log.i(unknown_field_names.get(k),e.getString(unknown_field_names.get(k)));
						}
					}
				}
			}
			JSONObject e = a.getJSONObject(a.length()-1);
			max_cost=e.getInt("maxCost");
			min_cost=e.getInt("minCost");
			//ArrayList<String> img_urls=generateURL(from_internet_list_prods);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		}
	@SuppressWarnings("deprecation")
	private String getJSON(String body) {
		String ret="error";
		try {
	
			body=body+"tableName="+request_string+"&"+"lat="+Double.toString(loc.getLatitude())+"&"+"lon="+Double.toString(loc.getLongitude());
			HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(body);
	    final HttpParams httpParams = httpclient.getParams();
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	    nameValuePairs.add(new BasicNameValuePair("tableName",request_string ));
	    nameValuePairs.add(new BasicNameValuePair("lat",Double.toString(loc.getLatitude()) ));
	    nameValuePairs.add(new BasicNameValuePair("lon",Double.toString(loc.getLongitude()) ));
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
	public class get_filters extends AsyncTask <Void,Void,String>{

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			//,"http://theshopwiz.com/app/filterProducts.php?"
			String t=getJSON("http://theshopwiz.com/app/filterProducts.php?");
			
			return t;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(! result.equals("error")){
			prepareData3(result);
			filling_the_filters();	 
			}
			

			d1.dismiss();
			System.out.println("in post execute");

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			builder=new AlertDialog.Builder (ListviewProductsViaItemSearch.this);
			//dialog = builder.create();
			d1=new Dialog(ListviewProductsViaItemSearch.this);
			d1.setTitle("Please wait");
			d1.setCancelable(false);
			d1.show();
			
		}
		

		
		
	}
	public class fill_ui extends AsyncTask <Void,Void,String>{

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			//,"http://theshopwiz.com/app/filterProducts.php?"
			return getJSON("http://theshopwiz.com/app/tableProducts.php?");
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!result.equals("error")){
			product_list=prepareData2(result);
			//prepareData3(result.get(1));
			d1.dismiss();
			after_load();
			}
			else{
				d1.dismiss();
				Toast.makeText(ListviewProductsViaItemSearch.this,"error from the getJson", Toast.LENGTH_SHORT).show();

			}
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			builder=new AlertDialog.Builder (ListviewProductsViaItemSearch.this);
			//dialog = builder.create();
			d1=new Dialog(ListviewProductsViaItemSearch.this);
			d1.setTitle("Please wait");
			d1.setCancelable(false);
			d1.show();
			
		}
		

		
		
	}
	public void after_load() {
		// TODO Auto-generated method stub
		if(product_list.size()%2==0){
			String body="http://beta.theshopwiz.com/";
		for(int j=0;j<product_list.size();){
			ArrayList<ProductInfo> tp= new ArrayList<ProductInfo>();
			tp.add(product_list.get(j));
			allImagesUrl.add(body + product_list.get(j).getImageUrl());
			tp.add(product_list.get(j+1));
			allImagesUrl.add(body + product_list.get(j+1).getImageUrl());
			
			j=j+2;
			arraylist_product_list.add(tp);
			
					}
		}
		else{
			ArrayList<ProductInfo> tp= new ArrayList<ProductInfo>();
			String body="http://beta.theshopwiz.com/";

			for(int j=0;j<product_list.size()-1;){
				tp= new ArrayList<ProductInfo>();
				tp.add(product_list.get(j));
				System.out.println(product_list.get(j).getImageUrl());
				allImagesUrl.add(body + product_list.get(j).getImageUrl());

				tp.add(product_list.get(j+1));
				allImagesUrl.add(body + product_list.get(j+1).getImageUrl());

				j=j+2;
				arraylist_product_list.add(tp);
			
			}	
			tp= new ArrayList<ProductInfo>();
			tp.add(product_list.get(product_list.size()-1));
			allImagesUrl.add(body + product_list.get(product_list.size()-1).getImageUrl());
			arraylist_product_list.add(tp);
			
		}
		
		MySimpleArrayAdapter adapter=new MySimpleArrayAdapter(this,R.layout.listview_product,arraylist_product_list,unknown_field_names,request_string,"prod");
		lv.setAdapter(adapter);
		Button filter_butt=(Button)findViewById(R.id.filter_button);
		Button back_butt=(Button)findViewById(R.id.filter_back_button);
		Button reset_butt=(Button)findViewById(R.id.filter_reset_button);
		Button apply_butt=(Button)findViewById(R.id.filter_applied_button);
		apply_butt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(arraylist_product_list.size()>0){
			ArrayList<Model> selected_cost_models=new ArrayList<Model>();
			ArrayList<Model> selected_brand_models=new ArrayList<Model>();
			selected_cost_models=(ArrayList<Model>) adapter_cost.getListModels();
			selected_brand_models=(ArrayList<Model>) adapter_brand.getListModels();
			ArrayList<ProductInfo> after_filter_productInfo=new ArrayList<ProductInfo>();
			ArrayList<ProductInfo> after_filter_productInfo_final=new ArrayList<ProductInfo>();
			   ArrayList<Integer> minCost_array=new ArrayList<Integer>();
			    ArrayList<Integer> maxCost_array=new ArrayList<Integer>();
			    
			    for(int k=0;k<selected_cost_models.size();++k){
			    	String s_cost=selected_cost_models.get(k).getName();
			    	String [] parts=s_cost.split(" - ");
			    	String tmp_min_cost_string=parts[0].replace("₹ ","");
			    	String tmp_max_cost_string=parts[1].replace("₹ ","");
			    	minCost_array.add(Integer.parseInt(tmp_min_cost_string));
			    	maxCost_array.add(Integer.parseInt(tmp_max_cost_string));
			    }
			    
			if(selected_brand_models.size()>0 && selected_cost_models.size()>0){
			for(int j=0;j<product_list.size();++j){
				Model model_tmp= new Model(product_list.get(j).getShopName(),"brand");
				String tmp=product_list.get(j).getShopName();
				for(int k=0;k<selected_brand_models.size();++k){
					if(selected_brand_models.get(k).getName().equals(tmp)){
						after_filter_productInfo.add(product_list.get(j));
					}
				}
				
			}
			for(int j=0;j<after_filter_productInfo.size();++j){
				int cost_tmp=after_filter_productInfo.get(j).getCost();
				for(int l=0;l<selected_cost_models.size();++l){
				//	after_filter_productInfo_final
				if(cost_tmp<=maxCost_array.get(l)&& cost_tmp>=minCost_array.get(l)){
					after_filter_productInfo_final.add(after_filter_productInfo.get(j));
				}
				}
			}
		 
			}
			else if(selected_brand_models.size()>0 && selected_cost_models.size()==0){
				for(int j=0;j<product_list.size();++j){
					Model model_tmp= new Model(product_list.get(j).getShopName(),"brand");
					String tmp=product_list.get(j).getShopName();
					for(int k=0;k<selected_brand_models.size();++k){
						if(selected_brand_models.get(k).getName().equals(tmp)){
							after_filter_productInfo_final.add(product_list.get(j));
						}
					}
					
				}
			}
			else if(selected_brand_models.size()==0 && selected_cost_models.size()>0){
				for(int j=0;j<product_list.size();++j){
					int cost_tmp=product_list.get(j).getCost();
					for(int l=0;l<selected_cost_models.size();++l){
					//	after_filter_productInfo_final
					if(cost_tmp<=maxCost_array.get(l)&& cost_tmp>=minCost_array.get(l)){
						after_filter_productInfo_final.add(product_list.get(j));
					}
					}
				}
			}
			else{
				after_filter_productInfo_final=product_list;
			}
			ArrayList<ArrayList<ProductInfo>> after_filter_productInfo_2AtaTime=new ArrayList<ArrayList<ProductInfo>>();
			if(after_filter_productInfo_final.size()%2==0){
				for(int p=0;p<after_filter_productInfo_final.size();){
					ArrayList<ProductInfo> tp=new ArrayList<ProductInfo>();
					tp.add(after_filter_productInfo_final.get(p));
					tp.add(after_filter_productInfo_final.get(p+1));
					after_filter_productInfo_2AtaTime.add(tp);
					p=p+2;
				}
			}
			else{
				for(int p=0;p<after_filter_productInfo_final.size()-1;){
					ArrayList<ProductInfo> tp=new ArrayList<ProductInfo>();
					tp.add(after_filter_productInfo_final.get(p));
					tp.add(after_filter_productInfo_final.get(p+1));
					after_filter_productInfo_2AtaTime.add(tp);
					p=p+2;
				}
				ArrayList<ProductInfo> tp=new ArrayList<ProductInfo>();
				tp.add(after_filter_productInfo_final.get(after_filter_productInfo_final.size()-1));
				after_filter_productInfo_2AtaTime.add(tp);
			}
			MySimpleArrayAdapter adapter2=new MySimpleArrayAdapter(ListviewProductsViaItemSearch.this,R.layout.listview_product,after_filter_productInfo_2AtaTime,unknown_field_names,request_string,"prod");
			lv.setAdapter(adapter2);
			
				}
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
						rl_anim.setVisibility(View.GONE);
						rl_front.requestFocus();
						//rl_front.setVisibility(View.VISIBLE);
						System.out.println("anim end");
					}
				});
			      tanim1.setInterpolator(new DecelerateInterpolator());
			        rl_anim.setAnimation(tanim1);	
			}
		});
		reset_butt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			System.out.println("reset button clicked");
			if(arraylist_product_list.size()>0){
			ArrayList<Model> reset_modelCost=new ArrayList<Model>();
			ArrayList<Model> reset_modelBrand=new ArrayList<Model>();
			
			
			
			reset_modelCost=(ArrayList<Model>) getModel_cost();
			reset_modelBrand= (ArrayList<Model>) getModel_brand();
			
			for(int j=0;j<reset_modelCost.size();++j){
				reset_modelCost.get(j).setSelected(false);
			}
			for(int j=0;j<reset_modelBrand.size();++j){
				reset_modelBrand.get(j).setSelected(false);
			}
			adapter_cost = new InteractiveArrayAdapter(ListviewProductsViaItemSearch.this,
					reset_modelCost);
		 adapter_brand = new InteractiveArrayAdapter(ListviewProductsViaItemSearch.this,
				 reset_modelBrand);
			 lv_anim_cost.setAdapter(adapter_cost);
			 lv_anim_brand.setAdapter(adapter_brand);
			}
			}
		});
		
		back_butt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
						rl_anim.setVisibility(View.GONE);
						rl_front.requestFocus();
						//rl_front.setVisibility(View.VISIBLE);
						System.out.println("anim end");
					}
				});
  		      tanim1.setInterpolator(new DecelerateInterpolator());
  		        rl_anim.setAnimation(tanim1);
			}
		});
filter_butt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				rl_anim.setVisibility(View.VISIBLE);
				 TranslateAnimation tanim = new TranslateAnimation(
			                TranslateAnimation.RELATIVE_TO_PARENT, 0,
			                TranslateAnimation.RELATIVE_TO_PARENT, 0,
			                TranslateAnimation.ABSOLUTE,((RelativeLayout)rl_anim.getParent()).getHeight(),
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
							rl_anim.requestFocus();
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
			       rl_anim.setAnimation(tanim);	
			}
			 
		});
		get_filters filter_json=new get_filters();
		filter_json.execute();
	}

	 public void filling_the_filters() {
		// TODO Auto-generated method stub
		 adapter_cost = new InteractiveArrayAdapter(ListviewProductsViaItemSearch.this,
			        getModel_cost());
		 adapter_brand = new InteractiveArrayAdapter(ListviewProductsViaItemSearch.this,
			        getModel_brand());
			 lv_anim_cost.setAdapter(adapter_cost);
			 lv_anim_brand.setAdapter(adapter_brand);
			 //Making dummy data
			 ArrayList<String> tp_string_list=new  ArrayList<String>();
			 tp_string_list.add("RED");
			 tp_string_list.add("Yellow");
			 tp_string_list.add("Green");
			 tp_string_list.add("Orange");
			 tp_string_list.add("Violet");
			 tp_string_list.add("Gray");
			 //unknown_field_names.add("Color");
			 //unknown_field_names.add("Material");
			 filter_entries.put("Color", tp_string_list);
			 filter_entries.put("Material", tp_string_list);
			 filter_entries.put("Others", tp_string_list);
			 
			/* LinearLayout lv_dynamic=(LinearLayout) findViewById(R.id.layout_anim);
			 for(int i=0;i<unknown_field_names.size();++i){
				 
			 }*/
			 
			 /*ScrollView sv=(ScrollView) findViewById(R.id.scroll_view_dynamic);
			 LinearLayout layout_anim_dynamic=new LinearLayout(ListviewProductsViaItemSearch.this);
			// LinearLayout layout_anim_dynamic= (LinearLayout) findViewById(R.id.layout_anim);
			 layout_anim_dynamic.setOrientation(LinearLayout.VERTICAL);
			 layout_anim_dynamic.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			 for(int i=0;i<unknown_field_names.size();++i){
				// layout_anim_dynamic.addView(dynamic_layout);
				 LinearLayout dynamic_layout=new LinearLayout(ListviewProductsViaItemSearch.this);
				 dynamic_layout.setOrientation(LinearLayout.HORIZONTAL);
				 dynamic_layout.setWeightSum(5);
				 TextView v1=new TextView(ListviewProductsViaItemSearch.this);
				 v1.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
				 dynamic_layout.addView(v1);
				 TextView v2=new TextView(ListviewProductsViaItemSearch.this);
				 v2.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
				 dynamic_layout.addView(v2);
				 TextView tv_dynamic=new TextView(ListviewProductsViaItemSearch.this);
				 tv_dynamic.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
				 tv_dynamic.setText(unknown_field_names.get(i));
				 tv_dynamic.setTextColor(Color.WHITE);
				 tv_dynamic.setTypeface(null, Typeface.BOLD);
				 
				 dynamic_layout.addView(tv_dynamic);
				 TextView v3=new TextView(ListviewProductsViaItemSearch.this);
				 v3.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
				 dynamic_layout.addView(v3);
				 TextView v4=new TextView(ListviewProductsViaItemSearch.this);
				 v4.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
				 dynamic_layout.addView(v4);
				// layout_anim_dynamic.addView(dynamic_layout);
				 
				 RelativeLayout rl_dynamic=new RelativeLayout(ListviewProductsViaItemSearch.this);
				 ListView lv_dynamic=new ListView(ListviewProductsViaItemSearch.this);
				 
				 ArrayList<String> list_for_given_header=new ArrayList<String>();
				 list_for_given_header=filter_entries.get(unknown_field_names.get(i));
			
				 ArrayAdapter<Model> adapter_unknown_entry = new InteractiveArrayAdapter(ListviewProductsViaItemSearch.this,
						 getModel_unknown_data(list_for_given_header));
				 lv_dynamic.setAdapter(adapter_unknown_entry);
				 rl_dynamic.addView(lv_dynamic);
				 layout_anim_dynamic.addView(rl_dynamic);
				 }
			 sv.addView(layout_anim_dynamic);*/
	 }
	private List<Model> getModel_unknown_data(
			ArrayList<String> list_for_given_header) {
		// TODO Auto-generated method stub
		 List<Model> list = new ArrayList<Model>();
		 for(int i=0;i< list_for_given_header.size();++i){
			 list.add(get( list_for_given_header.get(i),"unknown"));
			 System.out.println(list_for_given_header.get(i));
		 }
		    // Initially select one of the items
		 //   list.get(1).setSelected(true);
		    return list;
	}
	private List<Model> getModel_cost() {
		    List<Model> list = new ArrayList<Model>();
		    if(shop_names.size()>0){
		    int diff=(int)(max_cost-min_cost)/4;
		    list.add(get("₹ "+Integer.toString(min_cost)+" - "+"₹ "+Integer.toString(min_cost+diff),"cost"));
		    list.add(get("₹ "+Integer.toString(min_cost+1+diff)+" - "+"₹ "+Integer.toString(min_cost+(2*diff)),"cost"));
		    list.add(get("₹ "+Integer.toString(min_cost+1+(2*diff))+" - "+"₹ "+Integer.toString(min_cost+(3*diff)),"cost"));
		    list.add(get("₹ "+Integer.toString(min_cost+1+(3*diff))+" - "+"₹ "+Integer.toString(max_cost),"cost"));
		    // Initially select one of the items
		    }
		    return list;
		  }
	 private List<Model> getModel_brand() {
		    List<Model> list = new ArrayList<Model>();
		 for(int i=0;i<shop_names.size();++i){
			 list.add(get(shop_names.get(i),"brand"));
		 }
		    // Initially select one of the items
		 //   list.get(1).setSelected(true);
		    return list;
		  }
	 private Model get(String s,String h) {
		    return new Model(s,h);
		  }

}
