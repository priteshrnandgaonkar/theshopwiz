package com.tech.shopwiz.modified;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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
import com.tech.shopwiz.InteractiveArrayAdapter;
import com.tech.shopwiz.Model;
import com.tech.shopwiz.MySimpleArrayAdapter;
import com.tech.shopwiz.ProductInfo;
import com.tech.shopwiz.R;
import com.tech.shopwiz.R.color;
import com.tech.shopwiz.modified.ListviewViaItemSearch.fill_ui;
import com.tech.shopwiz.modified.ListviewViaItemSearch.get_filters;

public class ShopCollectionProducts extends Activity {
	ImageLoader imloader;
	FrameLayout frameLayout;
	HashMap<String,ArrayList<String>> filter_entries;
	Dialog d1;	
	AlertDialog.Builder builder;
	Location loc;
	ProductInfo pi_1;
	ProductInfo pi_2;
	ActionBar actionbar;
	String request_string;
	ArrayList<ArrayList<ProductInfo>> arraylist_product_list;
	ArrayList<ProductInfo> product_list;
	ArrayList<String> style_names;
	ArrayList<String> type_names;
	ArrayList<String> pattern_names;
	ArrayList<String> size_names;
	ArrayList<String> fabric_names;
	ArrayList<String> color_names;
	RelativeLayout rl_front;
	LinearLayout rl_anim;
	LinearLayout lin_anim;
	ListView lv;
	int max_cost;
	int min_cost;
	ListView lv_anim_cost;
	ListView lv_anim_brand;
	InteractiveArrayAdapter adapter_cost;
	InteractiveArrayAdapter adapter_type;
	InteractiveArrayAdapter adapter_style;
	InteractiveArrayAdapter adapter_fabric;
	InteractiveArrayAdapterColor adapter_color;
	InteractiveArrayAdapter adapter_pattern;
	InteractiveArrayAdapter adapter_size;
	String current_view;
	 String category;
	 String gender;
	String value;
	ListView filter_options;
	TableRow shop_img;
	TableRow price_img;
	TableRow pattern_img;
	TableRow style_img;
	TableRow color_img;
	TableRow size_img;
	TableRow fabric_img;
	String current_clicked;
	GestureDetector gestureDetector;
	Button popButton;
	PopupWindow popup;
	private Bundle extras;
	int shop_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		max_cost=0;
		getActionBar().hide();
		min_cost=Integer.MAX_VALUE;
		setContentView(R.layout.main_listview_file_prod);
		style_names=new ArrayList<String>();
		pattern_names=new ArrayList<String>();
		size_names=new ArrayList<String>();
		fabric_names=new ArrayList<String>();
		color_names=new ArrayList<String>();
		lv=(ListView) findViewById(R.id.listview);
		rl_front=(RelativeLayout) findViewById(R.id.front_layout);
		////////////////////changed
		rl_anim =(LinearLayout) findViewById(R.id.relativelayout_anim);
			 	 //lin_anim=(LinearLayout) findViewById(R.id.linearlayout_anim_new);
		shop_img=(TableRow) findViewById(R.id.brand_img_table_row);
		price_img=(TableRow) findViewById(R.id.cost_img_table_row);
		pattern_img=(TableRow) findViewById(R.id.pattern_img_table_row);
		style_img=(TableRow) findViewById(R.id.style_img_table_row);
		color_img=(TableRow) findViewById(R.id.color_img_table_row);
		size_img=(TableRow) findViewById(R.id.size_img_table_row);
		fabric_img=(TableRow) findViewById(R.id.fabric_img_table_row);
		filter_options=(ListView) findViewById(R.id.listview_new_filter_prod);
		frameLayout = (FrameLayout) findViewById(R.id.frame_layout_listview_prod_display);
		frameLayout.getForeground().setAlpha(0);
		extras = getIntent().getExtras();
		if(extras != null){
			//Bitmap bmp=extras.getParcelable("ProductDetailsBitmaps");
			shop_id = extras.getInt("ShopDetailsId");
		}
		popButton = (Button) findViewById(R.id.popup_button);
		popup = new PopupWindow(this);
		
		popButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   LayoutInflater layoutInflater 
				    = (LayoutInflater)ShopCollectionProducts.this
				    .getSystemService(ShopCollectionProducts.this.LAYOUT_INFLATER_SERVICE); 
				View popUpView =layoutInflater.inflate(R.layout.pop_window_custom_view,
			            null); // inflating popup layout
				WindowManager wm = (WindowManager) ShopCollectionProducts.this.getSystemService(ShopCollectionProducts.this.WINDOW_SERVICE);
				Display display = wm.getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int width = size.x;
				int height = size.y;
				frameLayout.getForeground().setAlpha(220);
				   popup = new PopupWindow(popUpView,(int)( width/1.2),
				            LayoutParams.WRAP_CONTENT, true); // Creation of popup
				    popup.setAnimationStyle(android.R.style.Animation_Dialog);
				    popup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
				    ImageView cancelImage = (ImageView) popUpView.findViewById(R.id.cancel_image_popup);
					LinearLayout price_ascending_lin_layout = (LinearLayout) popUpView.findViewById(R.id.price_ascending_lin_layout_popup);
					LinearLayout price_descending_lin_layout =(LinearLayout) popUpView.findViewById(R.id.price_descending_lin_layout_popup);
					LinearLayout popularity_lin_layout =(LinearLayout) popUpView.findViewById(R.id.trending_lin_layout_popup);
					popularity_lin_layout.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							make_products_trending_method();
							popup.dismiss();
							frameLayout.getForeground().setAlpha(0);
						}
					});
					price_ascending_lin_layout.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							make_products_ascending_in_price_method(true);
							popup.dismiss();
							frameLayout.getForeground().setAlpha(0);
						}

						
					});
price_descending_lin_layout.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							make_products_ascending_in_price_method(false);
							popup.dismiss();
							frameLayout.getForeground().setAlpha(0);
						}

						
					});
					cancelImage.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							popup.dismiss();
							frameLayout.getForeground().setAlpha(0);
						}
					});
			}
		});
price_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				current_clicked="cost";
				price_img.setBackgroundResource(color.white);
				shop_img.setBackgroundResource(color.cream);
				pattern_img.setBackgroundResource(color.cream);
				style_img.setBackgroundResource(color.cream);
				color_img.setBackgroundResource(color.cream);
				size_img.setBackgroundResource(color.cream);
				fabric_img.setBackgroundResource(color.cream);
				filter_options.setAdapter(adapter_cost);
			}
		});
		pattern_img.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						current_clicked="pattern";
						price_img.setBackgroundResource(color.cream);
						shop_img.setBackgroundResource(color.cream);
						pattern_img.setBackgroundResource(color.white);
						style_img.setBackgroundResource(color.cream);
						color_img.setBackgroundResource(color.cream);
						size_img.setBackgroundResource(color.cream);
						fabric_img.setBackgroundResource(color.cream);
						filter_options.setAdapter(adapter_pattern);
					}
				});
		style_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				current_clicked="style";
				price_img.setBackgroundResource(color.cream);
				shop_img.setBackgroundResource(color.cream);
				pattern_img.setBackgroundResource(color.cream);
				style_img.setBackgroundResource(color.white);
				color_img.setBackgroundResource(color.cream);
				size_img.setBackgroundResource(color.cream);
				fabric_img.setBackgroundResource(color.cream);
				filter_options.setAdapter(adapter_style);
			}
		});
		size_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				current_clicked="size";
				price_img.setBackgroundResource(color.cream);
				shop_img.setBackgroundResource(color.cream);
				pattern_img.setBackgroundResource(color.cream);
				style_img.setBackgroundResource(color.cream);
				color_img.setBackgroundResource(color.cream);
				size_img.setBackgroundResource(color.white);
				fabric_img.setBackgroundResource(color.cream);
				filter_options.setAdapter(adapter_size);
			}
		});
		fabric_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				current_clicked="fabric";
				price_img.setBackgroundResource(color.cream);
				shop_img.setBackgroundResource(color.cream);
				pattern_img.setBackgroundResource(color.cream);
				style_img.setBackgroundResource(color.cream);
				color_img.setBackgroundResource(color.cream);
				size_img.setBackgroundResource(color.cream);
				fabric_img.setBackgroundResource(color.white);
				filter_options.setAdapter(adapter_fabric);
			}
		});
		color_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				current_clicked="color";
				price_img.setBackgroundResource(color.cream);
				shop_img.setBackgroundResource(color.cream);
				pattern_img.setBackgroundResource(color.cream);
				style_img.setBackgroundResource(color.cream);
				color_img.setBackgroundResource(color.white);
				size_img.setBackgroundResource(color.cream);
				fabric_img.setBackgroundResource(color.cream);
				filter_options.setAdapter(adapter_color);
			}
		});
		
		shop_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				current_clicked="type";
				price_img.setBackgroundResource(color.cream);
				shop_img.setBackgroundResource(color.white);
				pattern_img.setBackgroundResource(color.cream);
				style_img.setBackgroundResource(color.cream);
				color_img.setBackgroundResource(color.cream);
				size_img.setBackgroundResource(color.cream);
				fabric_img.setBackgroundResource(color.cream);
				filter_options.setAdapter(adapter_type);
			}
		});
		loc=new Location("dummyprovider");
		loc.setLatitude((double)19.064146);
		loc.setLongitude((double)72.835472);
		
		pi_1=new ProductInfo("K1","Westment","" , 700, null,"dhikna",100,"");
		pi_2=new ProductInfo("K2","Globus","" , 1700, null,"dhikna",100,"");
		pi_1.addBitmap(null);
		pi_2.addBitmap(null);
	    product_list=new ArrayList<ProductInfo>();
		//product_list.add(pi_1);
		//product_list.add(pi_2);
		arraylist_product_list=new ArrayList<ArrayList<ProductInfo>>();
		//arraylist_product_list.add(product_list);
		//arraylist_product_list.add(product_list);
		//arraylist_product_list.add(product_list);
		
		//product_list=prepareData();
		fill_ui runner = new fill_ui();
	    runner.execute(shop_id);
		
	}
	private String getJSON(String body) {
		String ret="error";
		try {
			System.out.println(body);
			HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(body);
	    final HttpParams httpParams = httpclient.getParams();
		//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	//	System.out.println(EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs)));
	    HttpConnectionParams.setConnectionTimeout(httpParams, 15000000);
        HttpConnectionParams.setSoTimeout(httpParams, 15000000);
        HttpResponse response;
		response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        ret=EntityUtils.toString(entity);
        Log.v("JSON", ret);
        System.out.println(ret);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println(ret);
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
	private void make_products_ascending_in_price_method(boolean var) {
		// TODO Auto-generated method stub
		Collections.sort(product_list,new  CustomComparatorPrice(var));
		apply_popup_changes();
	}
	private void make_products_trending_method() {
		// TODO Auto-generated method stub
		Collections.sort(product_list,new  CustomComparatorTrending());
		apply_popup_changes();
	}
public void	apply_popup_changes(){
	arraylist_product_list=new ArrayList<ArrayList<ProductInfo>>();
	if(product_list.size()%2==0){
		String body="http://theshopwiz.com/";
	for(int j=0;j<product_list.size();){
		ArrayList<ProductInfo> tp= new ArrayList<ProductInfo>();
		tp.add(product_list.get(j));
		tp.add(product_list.get(j+1));
		
		j=j+2;
		arraylist_product_list.add(tp);
		
				}
	}
	else{
		ArrayList<ProductInfo> tp= new ArrayList<ProductInfo>();
		String body="http://theshopwiz.com/";

		for(int j=0;j<product_list.size()-1;){
			tp= new ArrayList<ProductInfo>();
			tp.add(product_list.get(j));
			System.out.println(product_list.get(j).getImageUrl());

			tp.add(product_list.get(j+1));

			j=j+2;
			arraylist_product_list.add(tp);
		
		}	
		tp= new ArrayList<ProductInfo>();
		tp.add(product_list.get(product_list.size()-1));
		arraylist_product_list.add(tp);
		
	}
	Toast.makeText(ShopCollectionProducts.this, " "+product_list.size(), Toast.LENGTH_SHORT).show();
	MySimpleArrayAdapter adapter=new MySimpleArrayAdapter(ShopCollectionProducts.this,R.layout.listview_product,arraylist_product_list,new ArrayList<String>(),request_string,"prod");
	lv.setAdapter(adapter);
	}
	private ArrayList<ProductInfo>  prepareData2(String s) {
		// TODO Auto-generated method stub
		//String s=getJSON();
		Log.i("JSON", s);
		ArrayList<ProductInfo> from_internet_list_prods=new ArrayList<ProductInfo>(); 
		try {
			type_names = new ArrayList<String>();
			style_names = new ArrayList<String>();
			pattern_names = new ArrayList<String>();
			size_names = new ArrayList<String>();
			fabric_names = new ArrayList<String>();
			color_names = new ArrayList<String>();
			JSONArray a=new JSONArray(s);
			ProductInfo temp=new ProductInfo("K1","Westment",null , 700, null,"dhikna",100,"");
			for(int i=0;i<a.length();++i){
				JSONObject e = a.getJSONObject(i);
				temp=new ProductInfo("K1","Westment",null , 700, null,"dhikna",100,"");
				temp.addProductId(e.getString("prod_id"));
				temp.dummy_prod_id=e.getString("prod_id");
				temp.addDescription(e.getString("description"));
				temp.addCost(e.getInt("cost"));
				if(max_cost < temp.getCost() ){
					max_cost = temp.getCost();
				}
				if(temp.getCost() < min_cost) {
					min_cost = temp.getCost();
				}
				temp.addShopName(e.getString("brand"));
				temp.addShopId(Integer.toString(shop_id));
				//temp.addShopAddress(e.getString("onelineaddress"));
				temp.category = (e.getString("category"));
				if(!type_names.contains(e.getString("category"))){
					type_names.add(e.getString("category"));
				}
				temp.popularity = e.getInt("popularity");
				temp.imageurl=new ArrayList<String>();
				temp.style=new ArrayList<String>();
				temp.fabric=new ArrayList<String>();
				temp.pattern=new ArrayList<String>();
				temp.color=new ArrayList<String>();
				temp.size=new ArrayList<String>();
				JSONArray object=new JSONArray (e.getString("prod_image"));
				for(int k=0;k<object.length();++k){
					if(temp.imageurl.contains(object.getString(k))){
					}else{
						temp.imageurl.add(object.getString(k));
					}
				}
				object=new JSONArray (e.getString("style"));
				for(int k=0;k<object.length();++k){
					if(! style_names.contains(object.getString(k))){
						style_names.add(object.getString(k));
					}
					if(temp.style.contains(object.getString(k))){
					}else{
						temp.style.add(object.getString(k));
					}
				}
				object=new JSONArray (e.getString("fabric"));
				for(int k=0;k<object.length();++k){
					if(! fabric_names.contains(object.getString(k))){
						fabric_names.add(object.getString(k));
					}
					if(temp.fabric.contains(object.getString(k))){
					}else{
						temp.fabric.add(object.getString(k));
					}
				}
				 object=new JSONArray (e.getString("pattern"));
				for(int k=0;k<object.length();++k){

					if(! pattern_names.contains(object.getString(k))){
						pattern_names.add(object.getString(k));
					}
					if(temp.pattern.contains(object.getString(k))){
					}else{
						temp.pattern.add(object.getString(k));
					}
				}
				object=new JSONArray (e.getString("color"));
				for(int k=0;k<object.length();++k){
					if(! color_names.contains(object.getString(k))){
						color_names.add(object.getString(k));
					}
					if(temp.color.contains(object.getString(k))){
					}else{
						temp.color.add(object.getString(k));
					}
				}
				object=new JSONArray (e.getString("size"));
				for(int k=0;k<object.length();++k){
					if(! size_names.contains(object.getString(k))){
						size_names.add(object.getString(k));
					}
					if(temp.size.contains(object.getString(k))){
					}else{
						temp.size.add(object.getString(k));
					}
				}
				from_internet_list_prods.add(temp);				
			}
			Toast.makeText(ShopCollectionProducts.this, "in preapredata2="+from_internet_list_prods.size(), Toast.LENGTH_SHORT).show();
			return from_internet_list_prods;
		} catch (JSONException e) {
			Toast.makeText(ShopCollectionProducts.this, "error in preparedata2", Toast.LENGTH_SHORT).show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return new ArrayList<ProductInfo>();
	}
	public class fill_ui extends AsyncTask <Integer,Void,String>{
		
		@Override
		protected String doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			//,"http://theshopwiz.com/app/filterProducts.php?"
			return getJSON("http://theshopwiz.com/app/perShopAllProducts.php?shop_id="+arg0[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!result.equals("error")){
			product_list=prepareData2(result);
			//prepareData3(result.get(1));
			d1.dismiss();
			Toast.makeText(ShopCollectionProducts.this,"received getJson", Toast.LENGTH_SHORT).show();

			after_load();
			}
			else{
				d1.dismiss();
				Toast.makeText(ShopCollectionProducts.this,"error from the getJson", Toast.LENGTH_SHORT).show();

			}
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			builder=new AlertDialog.Builder (ShopCollectionProducts.this);
			//dialog = builder.create();
			d1=new Dialog(ShopCollectionProducts.this);
			d1.setTitle("Please wait");
			d1.setCancelable(false);
			d1.show();
			
		}
	}
	public void after_load() {
		// TODO Auto-generated method stub
		System.out.println("product list size = "+product_list.size());
		if(product_list.size()%2==0){
			String body="http://theshopwiz.com/";
		for(int j=0;j<product_list.size();){
			ArrayList<ProductInfo> tp= new ArrayList<ProductInfo>();
			tp.add(product_list.get(j));
			tp.add(product_list.get(j+1));
			
			j=j+2;
			arraylist_product_list.add(tp);
			
					}
		}
		else{
			ArrayList<ProductInfo> tp= new ArrayList<ProductInfo>();
			String body="http://theshopwiz.com/";

			for(int j=0;j<product_list.size()-1;){
				tp= new ArrayList<ProductInfo>();
				tp.add(product_list.get(j));
				System.out.println(product_list.get(j).getImageUrl());

				tp.add(product_list.get(j+1));

				j=j+2;
				arraylist_product_list.add(tp);
			
			}	
			tp= new ArrayList<ProductInfo>();
			tp.add(product_list.get(product_list.size()-1));
			arraylist_product_list.add(tp);
			
		}
		Toast.makeText(ShopCollectionProducts.this, " "+product_list.size(), Toast.LENGTH_SHORT).show();
		MySimpleArrayAdapter adapter=new MySimpleArrayAdapter(ShopCollectionProducts.this,R.layout.listview_product,arraylist_product_list,new ArrayList<String>(),request_string,"prod");
		lv.setAdapter(adapter);
		Button filter_butt=(Button)findViewById(R.id.filter_button);
		ImageView back_butt=(ImageView)findViewById(R.id.filter_back_button);
		ImageView reset_butt=(ImageView)findViewById(R.id.filter_reset_button);
		ImageView apply_butt=(ImageView)findViewById(R.id.filter_applied_button);
		apply_butt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				current_view="products_page";
				if(arraylist_product_list.size()>0){
					
			ArrayList<Model> selected_cost_models=new ArrayList<Model>();
			selected_cost_models=(ArrayList<Model>) adapter_cost.getListModels();
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
			if( selected_cost_models.size()>0){
			for(int j=0;j<product_list.size();++j){
				int cost_tmp=product_list.get(j).getCost();
				for(int k=0;k<selected_cost_models.size();++k){
					if(cost_tmp<=maxCost_array.get(k)&& cost_tmp>=minCost_array.get(k)){
						after_filter_productInfo_final.add(product_list.get(j));
					}
				}
			}
			}
			else{
				after_filter_productInfo_final=product_list;
			}
			//after_applying_all_filters
			System.out.println("fiters not applied="+after_filter_productInfo_final.size());

			after_filter_productInfo_final=product_list_segregation_after_filters(after_filter_productInfo_final
					,adapter_style.getListModels(),adapter_fabric.getListModels(),adapter_pattern.getListModels(),
					adapter_size.getListModels(),adapter_color.getListModels(),adapter_type.getListModels());
			System.out.println("fiters applied="+after_filter_productInfo_final.size());
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
			MySimpleArrayAdapter adapter2=new MySimpleArrayAdapter(ShopCollectionProducts.this,R.layout.listview_product,after_filter_productInfo_2AtaTime,new ArrayList<String>(),request_string,"prod");
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
						ShopCollectionProducts.this.getActionBar().show();
						
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
			ArrayList<Model> reset_modelStyle=new ArrayList<Model>();
			ArrayList<Model> reset_modelPattern=new ArrayList<Model>();
			ArrayList<Model> reset_modelFabric=new ArrayList<Model>();
			ArrayList<Model> reset_modelColor=new ArrayList<Model>();
			ArrayList<Model> reset_modelSize=new ArrayList<Model>();
			ArrayList<Model> reset_modelType=new ArrayList<Model>();
			reset_modelType=(ArrayList<Model>) getModel_type();
			reset_modelCost=(ArrayList<Model>) getModel_cost();
			reset_modelStyle=(ArrayList<Model>) getModel_style();
			reset_modelPattern= (ArrayList<Model>) getModel_pattern();
			reset_modelFabric=(ArrayList<Model>) getModel_fabric();
			reset_modelColor= (ArrayList<Model>) getModel_color();
			reset_modelSize= (ArrayList<Model>) getModel_size();

			for(int j=0;j<reset_modelCost.size();++j){
				reset_modelCost.get(j).setSelected(false);
			}
			for(int j=0;j<reset_modelStyle.size();++j){
				reset_modelStyle.get(j).setSelected(false);
			}
			for(int j=0;j<reset_modelPattern.size();++j){
				reset_modelPattern.get(j).setSelected(false);
			}
			for(int j=0;j<reset_modelFabric.size();++j){
				reset_modelFabric.get(j).setSelected(false);
			}
			for(int j=0;j<reset_modelColor.size();++j){
				reset_modelColor.get(j).setSelected(false);
			}
			for(int j=0;j<reset_modelSize.size();++j){
				reset_modelSize.get(j).setSelected(false);
			}
			
			adapter_cost = new InteractiveArrayAdapter(ShopCollectionProducts.this,
					reset_modelCost);
			adapter_type = new InteractiveArrayAdapter(ShopCollectionProducts.this,
					reset_modelType);
			adapter_style = new InteractiveArrayAdapter(ShopCollectionProducts.this,
					reset_modelStyle);
			adapter_fabric = new InteractiveArrayAdapter(ShopCollectionProducts.this,
				 reset_modelFabric);
			adapter_pattern = new InteractiveArrayAdapter(ShopCollectionProducts.this,
					reset_modelPattern);
			adapter_size = new InteractiveArrayAdapter(ShopCollectionProducts.this,
				 reset_modelSize);
			adapter_color = new InteractiveArrayAdapterColor(ShopCollectionProducts.this,
					reset_modelColor);
		
			if(current_clicked.equals("type")){
				filter_options.setAdapter(adapter_type);
			}
			else if(current_clicked.equals("style")){
				filter_options.setAdapter(adapter_style);
			}
			else if(current_clicked.equals("fabric")){
				filter_options.setAdapter(adapter_fabric);
			}
			else if(current_clicked.equals("pattern")){
				filter_options.setAdapter(adapter_pattern);
			}
			else if(current_clicked.equals("size")){
				filter_options.setAdapter(adapter_size);
			}
			else if(current_clicked.equals("color")){
				filter_options.setAdapter(adapter_color);
			}
			else{
				filter_options.setAdapter(adapter_cost);
			}
			// lv_anim_cost.setAdapter(adapter_cost);
			 //lv_anim_brand.setAdapter(adapter_brand);
			}
			}
		});
		try
		{
			ShopCollectionProducts.this.getActionBar().getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(ShopCollectionProducts.this.getActionBar(), false);
		}
		catch (Exception exception)
		{
		  // Too bad, the animation will be run ;(
		}
		back_butt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				current_view="products_page";
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
						ShopCollectionProducts.this.getActionBar().show();
						
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
				//getActivity().getActionBar().hide();
				current_view="filter_page";
				System.out.println("filter butt clicked="+current_view);
				 rl_anim.setVisibility(View.VISIBLE);
				  TranslateAnimation tanim2 = new TranslateAnimation(
			                TranslateAnimation.RELATIVE_TO_PARENT, 0,
			                TranslateAnimation.RELATIVE_TO_PARENT, 0,
			                TranslateAnimation.ABSOLUTE,((RelativeLayout)rl_anim.getParent()).getHeight(),
			                TranslateAnimation.RELATIVE_TO_PARENT, 0.0f
			                );
			        tanim2.setDuration(400);
			        tanim2.setFillAfter(false);
			        tanim2.setInterpolator(new DecelerateInterpolator());
			       
			       tanim2.setAnimationListener(new AnimationListener(){

						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							rl_front.setVisibility(View.GONE);
							rl_anim.requestFocus();
							
							
						}
						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub	
						}
						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub	
							ShopCollectionProducts.this.getActionBar().hide();
							
						}
				 				        }
				        		);
			
		   
		       rl_anim.setAnimation(tanim2);
			}
			 
		});
		
		filling_the_filters();
	}
	public void filling_the_filters() {
		// TODO Auto-generated method stub
		 adapter_cost = new InteractiveArrayAdapter(ShopCollectionProducts.this,
			        getModel_cost());
		 adapter_type = new InteractiveArrayAdapter(ShopCollectionProducts.this,
			        getModel_type());
		 adapter_style=new InteractiveArrayAdapter(ShopCollectionProducts.this,
			        getModel_style());
		 adapter_fabric=new InteractiveArrayAdapter(ShopCollectionProducts.this,
			        getModel_fabric());
		adapter_pattern= new InteractiveArrayAdapter(ShopCollectionProducts.this,
			        getModel_pattern());
		adapter_color=new InteractiveArrayAdapterColor(ShopCollectionProducts.this,
		        getModel_color());
		adapter_size=new InteractiveArrayAdapter(ShopCollectionProducts.this,
		        getModel_size());
		filter_options.setAdapter(adapter_style);
		current_clicked="style";
		style_img.setBackgroundResource(R.color.white);
		
		 //changed here
			// lv_anim_cost.setAdapter(adapter_cost);
			 //lv_anim_brand.setAdapter(adapter_brand);
			 
		 
		 //Making dummy data
			 //unknown_field_names.add("Color");
			 //unknown_field_names.add("Material");
			 /*filter_entries.put("Color", tp_string_list);
			 filter_entries.put("Material", tp_string_list);
			 filter_entries.put("Others", tp_string_list);*/
			 
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
			//filling_the_filters();	 
			}
			

			d1.dismiss();
			System.out.println("in post execute");

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			builder=new AlertDialog.Builder (ShopCollectionProducts.this);
			//dialog = builder.create();
			d1=new Dialog(ShopCollectionProducts.this);
			d1.setTitle("Please wait");
			d1.setCancelable(false);
			d1.show();
			
		}	
	}
	private void prepareData3(String s) {
		// TODO Auto-generated method stub
		//String s=getJSON();
		try {
			JSONArray a=new JSONArray(s);
			
			
			for(int i=0;i<a.length()-1;++i){
				JSONObject e = a.getJSONObject(i);
				if(e.has("category")){
				type_names.add(e.getString("category"));
				}
				else if(e.has("Fabric")){
					//JSONArray objecct=new JSONArray(s);
					System.out.println("entered fabric" );
					JSONArray object=new JSONArray(e.getString("Fabric"));
					System.out.println("entered fabric after jsonarray" +object.length());
					for(int k=0;k<object.length();++k){
						System.out.println("entered loop" );
						if(fabric_names.contains(object.getString(k))){
							System.out.println("fabric  contain" +(String) object.get(k) );
						}else{
								System.out.println("fabric do not contain" +(String) object.get(k) );
						fabric_names.add((String) object.get(k));
						}
					}
					//fabric_names.add(e.getString("Fabric"));
				}
				else if(e.has("Style")){
					JSONArray object=new JSONArray (e.getString("Style"));
					for(int k=0;k<object.length();++k){
						if(style_names.contains(object.getString(k))){
						
						}else{
							style_names.add(object.getString(k));
						}
					}
				}
				else if(e.has("Pattern")){
					JSONArray object= new JSONArray (e.getString("Pattern"));
					for(int k=0;k<object.length();++k){
						if(pattern_names.contains(object.getString(k))){
						
						}else{
							pattern_names.add(object.getString(k));
						}
					}
				}
				else if(e.has("Size")){
					JSONArray object= new JSONArray (e.getString("Size"));
					for(int k=0;k<object.length();++k){
						if(size_names.contains(object.getString(k))){
						
						}else{
							size_names.add(object.getString(k));
						}
					}
				}
				else if(e.has("Color")){
					JSONArray object=new JSONArray(e.getString("Color"));
					for(int k=0;k<object.length();++k){
						if(color_names.contains(object.getString(k))){
						
						}else{
							color_names.add(object.getString(k));
							
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
	private List<Model> getModel_type() {
	    List<Model> list = new ArrayList<Model>();
	 for(int i=0;i<type_names.size();++i){
		 list.add(get(type_names.get(i),"type"));
	 }
	    return list;
	  }
	private List<Model> getModel_cost() {
	    List<Model> list = new ArrayList<Model>();
	  
	    int diff=(int)(max_cost-min_cost)/4;
	    list.add(get("₹ "+Integer.toString(min_cost)+" - "+"₹ "+Integer.toString(min_cost+diff),"cost"));
	    list.add(get("₹ "+Integer.toString(min_cost+1+diff)+" - "+"₹ "+Integer.toString(min_cost+(2*diff)),"cost"));
	    list.add(get("₹ "+Integer.toString(min_cost+1+(2*diff))+" - "+"₹ "+Integer.toString(min_cost+(3*diff)),"cost"));
	    list.add(get("₹ "+Integer.toString(min_cost+1+(3*diff))+" - "+"₹ "+Integer.toString(max_cost),"cost"));
	    // Initially select one of the items
	  
	    return list;
	  }
 private List<Model> getModel_style() {
	    List<Model> list = new ArrayList<Model>();
	 for(int i=0;i<style_names.size();++i){
		 list.add(get(style_names.get(i),"Style"));
	 }
	    // Initially select one of the items
	 //   list.get(1).setSelected(true);
	    return list;
	  }
 private List<Model> getModel_fabric() {
	    List<Model> list = new ArrayList<Model>();
	 for(int i=0;i<fabric_names.size();++i){
		 list.add(get(fabric_names.get(i),"Fabric"));
	 }
	    // Initially select one of the items
	 //   list.get(1).setSelected(true);
	    return list;
	  }
 private List<Model> getModel_pattern() {
	    List<Model> list = new ArrayList<Model>();
	 for(int i=0;i<pattern_names.size();++i){
		 list.add(get(pattern_names.get(i),"Pattern"));
	 }
	    // Initially select one of the items
	 //   list.get(1).setSelected(true);
	    return list;
	  }
 private List<Model> getModel_size() {
	    List<Model> list = new ArrayList<Model>();
	 for(int i=0;i<size_names.size();++i){
		 list.add(get(size_names.get(i),"Size"));
	 }
	    // Initially select ogne of the items
	 //   list.get(1).setSelected(true);
	    return list;
	  }
 private List<Model> getModel_color() {
	    List<Model> list = new ArrayList<Model>();
	 for(int i=0;i<color_names.size();++i){
		 list.add(get(color_names.get(i),"Color"));
	 }
	    // Initially select one of the items
	 //   list.get(1).setSelected(true);
	    return list;
	  }
 private Model get(String s,String h) {
	    return new Model(s,h);
	  }
 protected ArrayList<ProductInfo> product_list_segregation_after_filters(ArrayList<ProductInfo>filtered_product_based_on_cost_brand,List<Model> listModel_style,
			List<Model> listModel_fabric, List<Model> listModel_pattern, List<Model> listModel_size, List<Model> listModel_color, List<Model> listModel_type) {
		// TODO Auto-generated method stub
		ArrayList<ProductInfo> final_product_list=new ArrayList<ProductInfo>();
		for(int i=0;i<6;++i){
			if(i==0){
				System.out.println("Style"+listModel_style.size());
				final_product_list=segregating_one_ata_a_time(filtered_product_based_on_cost_brand,listModel_style,"style");
			}
			else if(i==1){
				System.out.println("Fabric"+listModel_fabric.size());
				System.out.println("Fabric initial product_list"+final_product_list.size());
				final_product_list=segregating_one_ata_a_time(final_product_list,listModel_fabric,"fabric");
				System.out.println("Fabric list"+	final_product_list.size());

			}
			else if(i==2){
				System.out.println("Pattern"+listModel_pattern.size());
				final_product_list=segregating_one_ata_a_time(final_product_list,listModel_pattern,"pattern");
			}
			else if(i==3){
				System.out.println("Size"+listModel_size.size());

				final_product_list=segregating_one_ata_a_time(final_product_list,listModel_size,"size");
			}
			else if(i==4){
				System.out.println("Color"+listModel_color.size());

				final_product_list=segregating_one_ata_a_time(final_product_list,listModel_color,"color");
			}
			else if(i==5){
				System.out.println("Type"+listModel_color.size());
				
				final_product_list=segregating_one_ata_a_time(final_product_list,listModel_type,"type");
			}
		}
		return final_product_list;
	}
 protected ArrayList<ProductInfo> segregating_one_ata_a_time(ArrayList<ProductInfo> prod_list, List<Model> model_list ,String tag ){
		ArrayList<ProductInfo> final_intermediate_list=new ArrayList<ProductInfo>();
		if(model_list.size()==0){
		
		return prod_list;
		}
		for(int i=0;i<prod_list.size();++i){
			boolean flag_style=true;
			//ArrayList<Model> tmp=new ArrayList<Model>();
			
			for(int k=0;k<model_list.size();++k){
				flag_style=true;
				if(tag.equals("style")){
					/*tmp=new ArrayList<Model>();
					for(int l=0;l<prod_list.get(i).style.size();++l){
						tmp.add(get(prod_list.get(i).style.get(l),""));
					}*/
					
				if(prod_list.get(i).style.contains(model_list.get(k).getName())){
					flag_style=false;
					break;
				}
				else{
					//flag_style=false;
					//break;
				}
			}
				else if(tag.equals("fabric")){
					if(prod_list.get(i).fabric.contains(model_list.get(k).getName())){
						flag_style=false;
						break;
					}
					else{
						//flag_style=false;
						//break;
					}
				}
				else if(tag.equals("type")){
					Log.i("type",prod_list.get(i).category + " = !=" +model_list.get(k).getName());
					if(prod_list.get(i).category.equals(model_list.get(k).getName())){
						flag_style=false;
						break;
					}
					else{
						//flag_style=false;
						//break;
					}
				}
				
				else if(tag.equals("pattern")){
					if(prod_list.get(i).pattern.contains(model_list.get(k).getName())){
						flag_style=false;
						break;
					}
					else{
						//flag_style=false;
						//break;
					}
				}
				else if(tag.equals("size")){
					if(prod_list.get(i).size.contains(model_list.get(k).getName())){
						flag_style=false;
						break;
					}
					else{
						//flag_style=false;
						//break;
					}
				}
				else if(tag.equals("color")){
					if(prod_list.get(i).color.contains(model_list.get(k).getName())){
						flag_style=false;
						break;
					}
					else{
						//flag_style=false;
						//break;
					}
				}
				
			}
			if(!flag_style){
				final_intermediate_list.add(prod_list.get(i));
			}	
		}
		return final_intermediate_list;
	}
}
