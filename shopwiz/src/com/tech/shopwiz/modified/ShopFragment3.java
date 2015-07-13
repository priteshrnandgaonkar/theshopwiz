package com.tech.shopwiz.modified;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

import com.tech.shopwiz.InteractiveArrayAdapter;
import com.tech.shopwiz.MySimpleArrayAdapterShop;
import com.tech.shopwiz.R;
import com.tech.shopwiz.ShopInfo;
import com.tech.shopwiz.ShopFragment.fill_ui;

import android.app.AlertDialog;
import android.app.Dialog;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ShopFragment3 extends Fragment {
	InteractiveArrayAdapter adapter_active_men;
	InteractiveArrayAdapter adapter_active_women;
	InteractiveArrayAdapter adapter_active_kids;
	ListView listView_shop;
	Button filter_butt;
	Location loc;
	Dialog d1;
	AlertDialog.Builder builder;
	ArrayList <ShopInfo> shop_list;
	int visibility_count=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.main_listview_shop2, null);
		visibility_count=0;
		listView_shop=(ListView) rootView.findViewById(R.id.listview_shop);
		filter_butt=(Button) rootView.findViewById(R.id.filter_button_shop);
		loc=new Location("dummyprovider");
		loc.setLatitude((double)19.064146);
		loc.setLongitude((double)72.835472);
		fill_ui runner = new fill_ui();
	    runner.execute();
	  
		return rootView;
		
	}
	 @Override
	    public void setMenuVisibility(final boolean visible) {
	        super.setMenuVisibility(visible);
	       
	       
	        if (visible) {
	        	 visibility_count=visibility_count+1;
	        	 if(visibility_count>1){
	        	  if(getActivity().getActionBar().isShowing()){
	  		    	
	  		    }
	  		    else{
	  		    	getActivity().getActionBar().show();	
	  		    }
	        }
	        }
	        }
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
	}
	public void setLocation(Location _loc){
		this.loc=_loc;
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
	public ArrayList<ShopInfo> prepareData2(String s) {
		// TODO Auto-generated method stub	
		ArrayList<ShopInfo> from_internet_list_prods=new ArrayList<ShopInfo>(); 
		try {
			JSONArray a=new JSONArray(s);
			ShopInfo temp=new ShopInfo(1,"westment","dombivli west",null,"imageURL","landline no","mobile no","website","kurti",19.064146,72.835472);
			
			for(int i=0;i<a.length();++i){
				JSONObject e = a.getJSONObject(i);
				temp=new ShopInfo(e.getInt("id"),e.getString("name"),""
						,"",e.getString("image"),""
						,"",""
						,"",e.getDouble("lattitude"),e.getDouble("longitude"));	
				temp.oneLineAddress=e.getString("onelineaddress");
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
			System.out.println(body);
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
		MySimpleArrayAdapterShopModified adapter=new MySimpleArrayAdapterShopModified(getActivity(),shop_list);
		listView_shop.setAdapter(adapter);
	}
}
