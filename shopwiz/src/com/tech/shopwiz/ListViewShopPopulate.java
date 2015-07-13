package com.tech.shopwiz;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.tech.shopwiz.ListviewProductsViaItemSearch.fill_ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ListViewShopPopulate extends Activity {
private RelativeLayout rl_front;
private LinearLayout lin_anim;
private Location loc;
private ListView listView_shop;
Dialog d1;	
AlertDialog.Builder builder;
ArrayList <ShopInfo> shop_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_listview_shop);
		listView_shop=(ListView) findViewById(R.id.listview_shop);
		loc=loc=new Location("dummyprovider");
		rl_front=(RelativeLayout) findViewById(R.id.front_layout_shop);
		lin_anim =(LinearLayout) findViewById(R.id.linearlayout_anim_new_shop);
		shop_list=new ArrayList<ShopInfo>();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    loc=(Location) extras.get("Location");
		}
		loc=new Location("dummyprovider");
		loc.setLatitude((double)19.064146);
		loc.setLongitude((double)72.835472);
		fill_ui runner = new fill_ui();
	    runner.execute();
		
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
			//prepareData3(result.get(1));
			d1.dismiss();
			after_load();
			}
			else{
				d1.dismiss();
				Toast.makeText(ListViewShopPopulate.this,"error from the getJson", Toast.LENGTH_SHORT).show();

			}
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			builder=new AlertDialog.Builder (ListViewShopPopulate.this);
			//dialog = builder.create();
			d1=new Dialog(ListViewShopPopulate.this);
			d1.setTitle("Please wait");
			d1.setCancelable(false);
			d1.show();
			
		}
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
		MySimpleArrayAdapterShop adapter=new MySimpleArrayAdapterShop(this,list_shopInfo);
		listView_shop.setAdapter(adapter);
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
}
