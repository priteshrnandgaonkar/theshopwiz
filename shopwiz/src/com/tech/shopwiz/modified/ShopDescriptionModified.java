package com.tech.shopwiz.modified;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tech.shopwiz.ProductDetailsActivity;
import com.tech.shopwiz.R;
import com.tech.shopwiz.ShopDescription;
import com.tech.shopwiz.ShopInfo;
import com.tech.shopwiz.modified.ListviewViaItemSearch.fill_ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopDescriptionModified extends Activity {
Dialog d1;
ImageView staticMapImage;
ImageView shopImageImageView;
TextView shopNameTextView;
TextView shopOneLineAddressTextView;
TextView shopAddressTextView;
TextView shopGenderTextview;
TextView shopHavingClothesTextView;
String latString;
String lonString;
private DisplayImageOptions options;
private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
private Bundle extras;
AlertDialog.Builder builder;
int shopId;
String oneLineAddressShop;
ShopInfo shopDetails;
LinearLayout viewCollectionLayout;
TextView view_collections_shop;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_description_modified);
		getActionBar().hide();
		staticMapImage = (ImageView) findViewById(R.id.staic_map_img);
		shopImageImageView = (ImageView) findViewById(R.id.shopDisplayImageShopDescription);
		view_collections_shop = (TextView) findViewById(R.id.viewCollectionTextviewShopDescriptionPage);
		shopNameTextView = (TextView) findViewById(R.id.shop_name_shop_details_page_img);
		shopOneLineAddressTextView = (TextView) findViewById(R.id.shop_address_shop_details_page_img);
		shopAddressTextView = (TextView) findViewById(R.id.shopDetailedAddressShopDescriptionPage);
		shopGenderTextview = (TextView) findViewById(R.id.shopGenderShopDescsriptionPage);
		shopHavingClothesTextView = (TextView) findViewById(R.id.shopHavingClothesShopDescriptionPage);
		viewCollectionLayout = (LinearLayout) findViewById(R.id.linear_layout_shop_collection_shop_description);
		extras = getIntent().getExtras();
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(false)
		.cacheOnDisk(false)
		.considerExifParams(true)
		//.resetViewBeforeLoading(true)
		.displayer(new RoundedBitmapDisplayer(20))
		//.displayer(new FadeInBitmapDisplayer(5000))
		.build();
		if(extras != null){
			//Bitmap bmp=extras.getParcelable("ProductDetailsBitmaps");
			shopId = extras.getInt("ShopDetailsId");
			latString = Double.toString(extras.getDouble("StringLatitude"));
			lonString = Double.toString(extras.getDouble("StringLongitude"));
			oneLineAddressShop = extras.getString("ShopOneLineAddress");
		}
		view_collections_shop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ShopDescriptionModified.this, ShopCollectionProducts.class);
				intent.putExtra("ShopDetailsId", shopId);
				ShopDescriptionModified.this.startActivity(intent);
			}
		});
		Log.i("ImageView Height",""+staticMapImage.getHeight());
		String url = "http://maps.google.com/maps/api/staticmap?center=" + latString+ "," + lonString + "&zoom=12&size=150x100&markers=color:red%7C"+latString+","+lonString;
		Log.i("url", url);
		ImageLoader.getInstance().displayImage(url, staticMapImage, options, animateFirstListener);
		fill_ui runner = new fill_ui();
	    runner.execute(shopId);
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
	public ShopInfo prepareData2(String s) {
		// TODO Auto-generated method stub	
		ShopInfo temp=new ShopInfo(1,"westment","dombivli west",null,"imageURL","landline no","mobile no","website","kurti",19.064146,72.835472);
		try {
			JSONObject object = new JSONObject(s);
			
			temp.address = object.getString("address");
			temp.shopName = object.getString("name");
			temp.id = object.getInt("id");
			temp.landline_no = object.getString("landline_no");
			temp.mobile_no = object.getString("mobile_no");
			temp.imageURL = "http://theshopwiz.com/"+object.getString("image");
			JSONArray jsonArrayGenders = new JSONArray(object.getString("genders"));
			temp.gendersList = new ArrayList<String>();
			for(int k=0;k<jsonArrayGenders.length();++k){
				if(temp.gendersList.contains(jsonArrayGenders.getString(k))){
				}else{
					Log.i("type",jsonArrayGenders.getString(k) );
					temp.gendersList.add(jsonArrayGenders.getString(k));
				}
			}
			temp.types = new ArrayList<String>();
			temp.queryNamesForTypes = new ArrayList<String>();
			JSONArray jsonArrayTypes = new JSONArray(object.getString("type"));
			for(int k=0;k<jsonArrayTypes.length();++k){
				JSONArray jsonArray = new JSONArray(jsonArrayTypes.getString(k));
				Log.i("types", jsonArray.getString(0));
				temp.types.add(jsonArray.getString(0));
				temp.queryNamesForTypes.add(jsonArray.getString(1));
			}
			return temp;
		} catch (JSONException e) {
			Log.i("json error", e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	
		return temp;	
		}
	public class fill_ui extends AsyncTask <Integer,Void,String>{

		@Override
		protected String doInBackground(Integer... arg0) {
			return getJSON("http://theshopwiz.com/app/shopDetails.php?shop_id="+arg0[0]) ;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!result.equals("error")){
			shopDetails = prepareData2(result);
			//prepareData3(result.get(1));
			d1.dismiss();
			after_load(shopDetails);
			}
			else{
				d1.dismiss();
				Toast.makeText(ShopDescriptionModified.this,"error from the getJson", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			builder=new AlertDialog.Builder (ShopDescriptionModified.this);
			//dialog = builder.create();
			d1=new Dialog(ShopDescriptionModified.this);
			d1.setTitle("Please wait");
			d1.setCancelable(false);
			d1.show();
		}
	
	}
	private  class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		 final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
					//imageView.setImageBitmap(loadedImage);
					BitmapDrawable ob = new BitmapDrawable(getResources(), loadedImage);
					imageView.setBackground(ob);
				}else{
					imageView.setImageBitmap(loadedImage);
				}
			}
		}
	}
	public void after_load(final ShopInfo shopDetailsArg) {
		// TODO Auto-generated method stub
		staticMapImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?f=d&daddr="+shopDetailsArg.address));
				ShopDescriptionModified.this.startActivity(intent);
			}
		});	
		shopNameTextView.setText(shopDetailsArg.shopName);
		shopAddressTextView.setText(shopDetailsArg.address);
		shopOneLineAddressTextView.setText(oneLineAddressShop);
		String gender =   shopDetailsArg.gendersList.get(0);
		for(int i=0;i< shopDetailsArg.gendersList.size()-1;++i){
			gender =gender+","+shopDetailsArg.gendersList.get(i);
		}
		shopGenderTextview.setText(gender);
		String clothCollection = shopDetailsArg.types.get(0);
		for(int i=1;i< shopDetailsArg.types.size();++i){
			clothCollection = clothCollection+","+shopDetailsArg.types.get(i);
		}
		shopHavingClothesTextView.setText(clothCollection);
		ImageLoader.getInstance().displayImage(shopDetailsArg.imageURL, shopImageImageView, options, animateFirstListener);
	}
}

