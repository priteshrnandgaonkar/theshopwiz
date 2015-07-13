package com.tech.shopwiz;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class ProductDetailsActivity extends Activity  {
	Dialog d1;	
	AlertDialog.Builder builder;
	private ImageView img;
	   private ScaleGestureDetector SGD;
	   private ProductInfo prod_from_last;
	   private Bundle extras;
	  private String category;
	  private ShopInfo shop_detail_prod;
	  private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	  private ImageLoader imageLoader;
	  private DisplayImageOptions options;
	  TextView shopNameTextView;
	  TextView prodDetailsTextView;
	  TextView styleNameTextView;
	  TextView fabricNameTextView;
	  TextView patternNameTextView;
	  TextView shopNameOnImg;
	  TextView shopAddressOnImg;
	  LinearLayout sizeLinearLayout;
	  LinearLayout colorLinearLayout;
	  TextView costTextView;
	  ArrayList<String> color_list;
	  ArrayList<String> size_list;
	  ArrayList <String> style_list;
	  ArrayList<String> fabric_list;
	  ArrayList<String> pattern_list;
	//  private String flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_description_page_modified);
	    img = (ImageView)findViewById(R.id.prod_img_prod_details_page);
	    shopNameTextView = (TextView) findViewById(R.id.shop_name_prod_details_page);
	    prodDetailsTextView = (TextView) findViewById(R.id.prod_description_prod_details_page);
	    styleNameTextView = (TextView) findViewById(R.id.textview_style_prod_details_page);
	    fabricNameTextView = (TextView) findViewById(R.id.textview_fabric_prod_details_page);
	    patternNameTextView = (TextView) findViewById(R.id.textview_pattern_prod_details_page);
	    costTextView = (TextView) findViewById(R.id.prod_cost_prod_details_page);
	    shopNameOnImg = (TextView) findViewById(R.id.shop_name_prod_details_page_img);
	    shopAddressOnImg=(TextView) findViewById(R.id.shop_address_prod_details_page_img);
	    sizeLinearLayout=(LinearLayout) findViewById(R.id.linear_layout_size_prod_details_page);
	    colorLinearLayout=(LinearLayout) findViewById(R.id.linear_layout_color_prod_details_page);
	    this.getActionBar().hide();
	    extras = getIntent().getExtras();
	    //  flag="shop";
	    
	     prod_from_last=new ProductInfo("K1","Westment",null , 700, null,"dhikna",100,"");
	    
	      if (extras != null) {
	    		/*byte[] byteArray = extras.getByteArray("ProductDetailsBitmaps");
				Bitmap b = BitmapFactory.decodeByteArray(
						byteArray,0,byteArray.length);*/
				//Bitmap bmp=extras.getParcelable("ProductDetailsBitmaps");
					//prod_from_last.addBitmap(b);
					category=extras.getString("ProductDetailsCategory");
					//flag=extras.getString("ProductDetailsFrom");
					  prod_from_last.dummy_prod_id=extras.getString("ProductDetailsDummyId");
			    	  prod_from_last.addCost(extras.getInt("ProductDetailsCost"));
			    	  costTextView.setText("₹ "+prod_from_last.getCost());
			    	  prod_from_last.addProductId(extras.getString("ProductDetailsId"));
			    	  prod_from_last.addDescription(extras.getString("ProductDetailsDescription"));
			    	  prodDetailsTextView.setText(prod_from_last.getDescription());
			    	  prod_from_last.addShopName(extras.getString("ProductDetailsShopName"));
			    	  shopNameOnImg.setText(prod_from_last.shopName);
			    	  shopNameTextView.setText(prod_from_last.getShopName());
			    	  prod_from_last.addRating(extras.getInt("ProductDetailsRating"));
			    	  prod_from_last.addShopAddress(extras.getString("ProductDetailsShopAddress"));
			    	  shopAddressOnImg.setText(prod_from_last.getShopAddress());
			    	  prod_from_last.imageurl=(extras.getStringArrayList("ProductDetailsImageURL"));
			    	  prod_from_last.description = prod_from_last.getDescription();
			    	  prod_from_last.color=extras.getStringArrayList("ProductDetailsColor");
			    	  prod_from_last.size=extras.getStringArrayList("ProductDetailsSize");
			    	  prod_from_last.style=extras.getStringArrayList("ProductDetailsStyle");
			    	  String style_string = "";
			    	  for(int i=0;i<prod_from_last.style.size();++i){
			    		  if(i<prod_from_last.style.size()-1){
			    		  style_string=style_string + prod_from_last.style.get(i)+",";
			    		  }
			    		  else{
			    			  style_string=style_string + prod_from_last.style.get(i);
			    		  }
			    	  }
			    	  styleNameTextView.setText(style_string);
			    	  prod_from_last.fabric=extras.getStringArrayList("ProductDetailsFabric");
			    	  String fabric_string = "";
			    	  for(int i=0;i<prod_from_last.fabric.size();++i){
			    		  if(i<prod_from_last.style.size()-1){
			    		  fabric_string=fabric_string + prod_from_last.fabric.get(i)+",";
			    		  }
			    		  else{
			    			  fabric_string=fabric_string + prod_from_last.fabric.get(i);
			    		  }
			    	  }
			    	  fabricNameTextView.setText(fabric_string);
			    	  prod_from_last.pattern=extras.getStringArrayList("ProductDetailsPattern");
			    	  String pattern_string = "";
			    	  for(int i=0;i<prod_from_last.pattern.size();++i){
			    		  if(i<prod_from_last.pattern.size()-1){
			    		  pattern_string=pattern_string + prod_from_last.pattern.get(i)+",";
			    		  }
			    		  else{
			    			  pattern_string=pattern_string + prod_from_last.pattern.get(i);
			    		  }
			    	  }
			    	  patternNameTextView.setText(pattern_string);
			    	 imageLoader= ImageLoader.getInstance();
			  		//imageLoader.init(ImageLoaderConfiguration.createDefault(context));
			  		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
			  		
			  		options = new DisplayImageOptions.Builder()
			  		.showImageOnLoading(R.drawable.ic_stub)
			  		.showImageForEmptyUri(R.drawable.ic_empty)
			  		.showImageOnFail(R.drawable.ic_error)
			  		.cacheInMemory(true)
			  		.cacheOnDisk(true)
			  		.considerExifParams(true)
			  	//	.resetViewBeforeLoading(true)
			  		.displayer(new RoundedBitmapDisplayer(20))
			  		//.displayer(new FadeInBitmapDisplayer(5000))
			  		.build();
	      }
	      final float scale = this.getResources().getDisplayMetrics().density;
	      int pixels = (int) (40 * scale + 0.5f);
	      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pixels,pixels,1.0f);
	      for(int i=0;i<prod_from_last.size.size();++i){
	    	  TextView tv_size=new TextView (this);
	    	  tv_size.setText(prod_from_last.size.get(i));
	    	  tv_size.setBackgroundResource(R.drawable.rectangle_color);
	    	  tv_size.setGravity(Gravity.CENTER);
	    	    params.setMargins((int)(5 * scale + 0.5f),0,(int)(15 * scale + 0.5f), 0);
	    	    tv_size.setTextSize(16); 
	    	    tv_size.setLayoutParams(params);
	    	    sizeLinearLayout.addView(tv_size);
	      }
	     
			
	      for(int i=0;i<prod_from_last.color.size();++i){
	    	  TextView tv_size=new TextView (this);
	    	  JSONArray object;
	    	  GradientDrawable gd = new GradientDrawable();
			try {
				object = new JSONArray (prod_from_last.color.get(i));
				
			        gd.setColor( Color.parseColor(object.getString(1))); // Changes this drawbale to use a single color instead of a gradient
			        gd.setCornerRadius((int)(2* scale + 0.5f));
			        gd.setStroke((int)(2* scale + 0.5f), Color.parseColor("#000000"));
			        tv_size.setBackgroundDrawable(gd);
		    	  tv_size.setGravity(Gravity.CENTER);
		    	    params.setMargins((int)(5 * scale + 0.5f),0,(int)(15 * scale + 0.5f), 0);
		    	    tv_size.setTextSize(16); 
		    	    tv_size.setLayoutParams(params);
		    	    colorLinearLayout.addView(tv_size);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 // tv_size.setText(prod_from_last.size.get(i));
	    	 
	      }
	      
	      
	      //HashMap<String,String> dummy=new HashMap<String,String>();
	    //  dummy.put("color", "red");
	     // dummy.put("material", "cotton");
	     // dummy.put("HandMade", "True");
	     // dummy.put("Wash","Dry");
	    //  prod_from_last.addUnknownFields(dummy);
	    //  unknownFieldNames=new ArrayList<String>();
	      
	      //unknownFieldNames.add("color");
	      //unknownFieldNames.add("material");
	      //unknownFieldNames.add("HandMade");
	      //unknownFieldNames.add("Wash");
	     // img.setImageBitmap(prod_from_last.getBitmap());
	      ImageLoader.getInstance().displayImage(prod_from_last.getImageUrl().get(0), img, options, animateFirstListener);
	     // makeTable(prod_from_last);
	     
	    img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i1 = new Intent(ProductDetailsActivity.this, PinchZoomActivity.class);
				/*Bitmap bmp;
				BitmapDrawable drawable=(BitmapDrawable) img.getDrawable();
				bmp=drawable.getBitmap(); 
				ByteArrayOutputStream bs = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.JPEG, 50, bs);
				i1.putExtra("BitmapToBeZoomed",  bs.toByteArray());*/
				i1.putExtra("BitmapToBeZoomedURL",prod_from_last.getImageUrl().get(0));
				ProductDetailsActivity.this.startActivity(i1);
			}
		});
	 
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
	      SGD.onTouchEvent(event);
		return true;
	}
	 /* private void makeTable(ProductInfo prod_info) {
		// TODO Auto-generated method stub
		TextView tv_prod=(TextView)detailsTable.findViewById(R.id.product_id_table);
		tv_prod.setText(prod_info.getProductId());
		TextView tv_cost=(TextView)detailsTable.findViewById(R.id.cost_table);
		tv_cost.setText("₹"+Integer.toString(prod_info.getCost()));
		TextView tv_description=(TextView)detailsTable.findViewById(R.id.description_table);
		
		TableRow tr_des=(TableRow) findViewById(R.id.tableRow_Description_prod);
		if(prod_info.getDescription()==null){
			tr_des.setVisibility(View.GONE);	
		}else{
			
			if(prod_info.getDescription().equals("null")){
				tr_des.setVisibility(View.GONE);
			}
			else{
				tv_description.setText(prod_info.getDescription());
			}
		}
		TextView tv_brandName=(TextView)detailsTable.findViewById(R.id.brand_table);
		tv_brandName.setText(prod_info.getShopName());
		
		 HashMap<String,String> prod_hashmap=new HashMap<String,String>();
		prod_hashmap=prod_info.getUnknownFields();
		 TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
	        rowParams.width = TableRow.LayoutParams.MATCH_PARENT;
	        rowParams.height = TableRow.LayoutParams.WRAP_CONTENT;
	        TableRow row;
	
		
	  }*/
	  public class fill_ui extends AsyncTask <Void,Void,String>{

			@Override
			protected String doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				//,"http://theshopwiz.com/app/filterProducts.php?"
				//return getJSON("http://theshopwiz.com/app/tableProducts.php?");
				String url="http://theshopwiz.com/app/shopProducts.php?";
				url=url+"tableName="+category+"&prodId="+prod_from_last.dummy_prod_id;
				System.out.println(url);
			return getJSON(url);
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(!result.equals("error")){
				shop_detail_prod=new ShopInfo(1,"westment","dombivli west",null,"imageURL","landline no","mobile no","website","kurti",19.064146,72.835472);
				shop_detail_prod=prepareData2(result);
				d1.dismiss();
				after_getting_shopInfo(shop_detail_prod);
				}
				else{
					d1.dismiss();
					Toast.makeText(ProductDetailsActivity.this,"error from the getJson", Toast.LENGTH_SHORT).show();

				}
				
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				builder=new AlertDialog.Builder (ProductDetailsActivity.this);
				//dialog = builder.create();
				d1=new Dialog(ProductDetailsActivity.this);
				d1.setTitle("Please wait");
				d1.setCancelable(false);
				d1.show();
				
			}
			

			
			
		}
	  private String getJSON(String body) {
			String ret="error";
			try {
				//body=body+"&"+"lat="+Double.toString(loc.getLatitude())+"&"+"lon="+Double.toString(loc.getLongitude());
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
	  public void after_getting_shopInfo(ShopInfo s) {
		// TODO Auto-generated method stub
		  Intent intent = new Intent(ProductDetailsActivity.this, ShopDescription.class);
		  String imgurl1="http://beta.theshopwiz.com/"+shop_detail_prod.imageURL;
		  
			intent.putExtra("ShopDetailsImgURL", imgurl1);
			intent.putExtra("ShopDetailsDescription",shop_detail_prod.description );
			intent.putExtra("ShopDetailsLandline",shop_detail_prod.landline_no);
			intent.putExtra("ShopDetailsMobile",shop_detail_prod.mobile_no );
			Location loc_sent=new Location("dummyprovider");//.description
			loc_sent.setLatitude(shop_detail_prod.lat);
			loc_sent.setLongitude(shop_detail_prod.lon);
			intent.putExtra("ShopDetailsLocation", loc_sent);
			intent.putExtra("ShopDetailsAddress",shop_detail_prod.address );
			intent.putExtra("ShopDetailsCategories", shop_detail_prod.categories);
			intent.putExtra("ShopDetailsId", shop_detail_prod.id);
			intent.putExtra("ShopDetailsName",shop_detail_prod.shopName);
			intent.putExtra("ShopDetailsWebsite", shop_detail_prod.website);
			ProductDetailsActivity.this.startActivity(intent);
		
	}
	public ShopInfo prepareData2(String s) {
			// TODO Auto-generated method stub	
			 
			try {
				JSONArray a=new JSONArray(s);
				ShopInfo temp=new ShopInfo(1,"westment","dombivli west",null,"imageURL","landline no","mobile no","website","kurti",19.064146,72.835472);
				
				
					JSONObject e = a.getJSONObject(0);
					temp=new ShopInfo(e.getInt("Id"),e.getString("Name"),e.getString("Address")
							,e.getString("Description"),e.getString("Image"),e.getString("landline_no")
							,e.getString("mobile_no"),e.getString("Website")
							,e.getString("Categories"),e.getDouble("Lattitude"),e.getDouble("Longitude"));				
								
			
				return temp;
			} catch (JSONException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		
			return new ShopInfo(1,"westment","dombivli west",null,"imageURL","landline no","mobile no","website","kurti",19.064146,72.835472);
			}
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
					imageView.setImageBitmap(loadedImage);
				}else{
					imageView.setImageBitmap(loadedImage);
				}
			}
		}
	}
	
}

