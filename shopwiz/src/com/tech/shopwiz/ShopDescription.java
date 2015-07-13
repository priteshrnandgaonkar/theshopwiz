package com.tech.shopwiz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ShopDescription extends Activity {
	
	
	private Bundle extras;
	 private ShopInfo shop_detail_last;
	 private Bitmap shop_img;
	 Location loc;
	 int no_tabs;
	Dialog d1;	
	ImageView imv;
	AlertDialog.Builder builder;
	ArrayList<ArrayList<ProductInfo>> product_lists;
	TabHost tabs;
	String [] url_data;
	String [] heading;
	MySimpleArrayAdapter adapter_univesal;
	//RelativeLayout rl_anim;
	LinearLayout lin_front;
	ActionBar actionbar;
	  private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	  private ImageLoader imageLoader;
	  private DisplayImageOptions options;
	  String imgurl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_description);
		extras = getIntent().getExtras();
		imv=(ImageView)findViewById(R.id.img_shop_selected);
		lin_front=(LinearLayout) findViewById(R.id.lin_front_shop_description);
		 shop_detail_last=new ShopInfo(1,"westment","dombivli west",null,"imageURL","landline no","mobile no","website",null,19.064146,72.835472);
		shop_img = BitmapFactory.decodeResource(this.getResources(),
	                R.drawable.kurti1);
		product_lists=new ArrayList<ArrayList<ProductInfo>>();		
		loc=new Location("dummyprovider");
		imgurl="dnnt know";
		 if (extras != null) {
	    		
				imgurl=extras.getString("ShopDetailsImgURL");
					loc=(Location) extras.get("ShopDetailsLocation");
				shop_detail_last=new ShopInfo(extras.getInt("ShopDetailsId"),extras.getString("ShopDetailsName")
						,extras.getString("ShopDetailsAddress"),extras.getString("ShopDetailsDescription"),
						null,extras.getString("ShopDetailsLandline"),extras.getString("ShopDetailsMobile")
						,extras.getString("ShopDetailsWebsite"),
						extras.getString("ShopDetailsCategories"),
						loc.getLatitude(),loc.getLongitude());
				imageLoader= ImageLoader.getInstance();
		  		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ShopDescription.this));
		  		options = new DisplayImageOptions.Builder()
		  		.showImageOnLoading(R.drawable.ic_stub)
		  		.showImageForEmptyUri(R.drawable.ic_empty)
		  		.showImageOnFail(R.drawable.ic_error)
		  		.cacheInMemory(true)
		  		.cacheOnDisk(true)
		  		.considerExifParams(true)
		  		//.resetViewBeforeLoading(true)
		  		.displayer(new RoundedBitmapDisplayer(20))
		  		//		.displayer(new FadeInBitmapDisplayer(5000))

		  		.build();
	      }
		 actionbar=getActionBar();
		 actionbar.setTitle("Shop Details");
		 String categories_in_shop=shop_detail_last.categories;
		 String [] cat_split=categories_in_shop.split("\\+");
		 no_tabs=cat_split.length;
		 heading=new String[no_tabs];
		 for(int i=0;i<no_tabs;++i){
			 heading[i]=cat_split[i];
		 }
		 ImageLoader.getInstance().displayImage(imgurl,imv , options, animateFirstListener);
		// imv.setImageBitmap(shop_img);
		 String body="http://theshopwiz.com/app/shopCategories.php?";
		 body=body+"shopId="+Integer.toString(shop_detail_last.id);
		 url_data=new String [no_tabs];
		 for(int j=0;j<no_tabs;++j){
			 url_data[j]=body+"&tableName="+cat_split[j];
		 }
		 tabs=(TabHost)findViewById(R.id.tabhost);
		 tabs.setup();
		    
		   /* TabHost.TabSpec spec=tabs.newTabSpec("buttontab");
		  //  spec.setContent(R.id.buttontab);
		    spec.setIndicator("Button");
		    tabs.addTab(spec);*/
		    //Button btn=(Button)tabs.getCurrentView().findViewById(R.id.buttontab);
		  
			fill_ui rf=new fill_ui();
			rf.execute(url_data);
			}
			private String getJSON(String body) {
				String ret="error";
				try {
				
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
			public ArrayList<ProductInfo> prepareData2(String s) {
				// TODO Auto-generated method stub	
				ArrayList<ProductInfo> from_internet_list_prods=new ArrayList<ProductInfo>(); 
				try {
					JSONArray a=new JSONArray(s);
					//ShopInfo temp=new ShopInfo(1,"westment","dombivli west",null,"imageURL","landline no","mobile no","website","kurti",19.064146,72.835472);
				    
				     Bitmap icon1 = BitmapFactory.decodeResource(this.getResources(),
				                R.drawable.kurti1);
						 ProductInfo temp=new ProductInfo("K1","Westment",null , 700, icon1,"dhikna",100,"");
					for(int i=0;i<a.length();++i){
						JSONObject e = a.getJSONObject(i);
						temp=new ProductInfo("K1","Westment",null , 700, icon1,"dhikna",100,"");
						temp.addProductId(e.getString("ac_prod_id"));
						temp.addDescription(e.getString("Description"));
						temp.dummy_prod_id=e.getString("prod_id");
						temp.addCost(e.getInt("Cost"));
						temp.addShopName(e.getString("Brand"));
						temp.addShopId(e.getString("shop_id"));
						temp.addRating(e.getInt("Rating"));
						//temp.addImageUrl(e.getString("prod_image"));				
						from_internet_list_prods.add(temp);				
					}
					return from_internet_list_prods;
				} catch (JSONException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			
				return new ArrayList<ProductInfo>();	
				}
		public	class fill_ui extends AsyncTask <String,Void,ArrayList<String>>{

				@Override
				protected ArrayList<String> doInBackground(String... arg0) {
					// TODO Auto-generated method stub
					//"http://theshopwiz.com/app/shopCategories.php?"
					ArrayList<String> all_product_json=new ArrayList<String>();
					for(int i=0;i<arg0.length;++i){
						System.out.println(arg0[i]);
						all_product_json.add(getJSON(arg0[i]));
					}
					return all_product_json;
				}

				@Override
				protected void onPostExecute(ArrayList<String> result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					
					if(!result.get(0).equals("error")){
						for(int i=0;i<result.size();++i){
							product_lists.add(prepareData2(result.get(i)));
						}
					d1.dismiss();
					after_load();
					}
					else{
						d1.dismiss();
						//Toast.makeText(getActivity(),"error from the getJson", Toast.LENGTH_SHORT).show();

					}
					
				}

				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					builder=new AlertDialog.Builder (ShopDescription.this);
					//dialog = builder.create();
					d1=new Dialog(ShopDescription.this);
					d1.setTitle("Please wait");
					d1.setCancelable(false);
					d1.show();
					
				}
			}
		public void after_load() {
			// TODO Auto-generated method stub
			  for(int i=0;i<no_tabs;++i){
			    	 TabHost.TabSpec spec2=tabs.newTabSpec(Integer.toString(i));
				        
				        spec2.setContent(new TabHost.TabContentFactory() {
				          public View createTabContent(String tag) {
				        //	View v=getLayoutInflater().inflate(R.layout.dynamic_tab_view, null);
				        	  ArrayList<ProductInfo> tmp_prod_info=new ArrayList<ProductInfo>();
				        	  ListView lv=new ListView(ShopDescription.this);
				        	  tmp_prod_info=product_lists.get(Integer.valueOf(tag));
				        	  String [] cat=product_lists.get(Integer.valueOf(tag)).get(0).getImageUrl().get(0).split("/");
				        	ArrayList<ArrayList<ProductInfo>> arraylist_product_list=new ArrayList<ArrayList<ProductInfo>> ();
				        	if(tmp_prod_info.size()%2==0){
				        		for(int j=0;j<tmp_prod_info.size();){
				        			ArrayList<ProductInfo> tp= new ArrayList<ProductInfo>();
				        			tp.add(tmp_prod_info.get(j));
				        			tp.add(tmp_prod_info.get(j+1));
				        			j=j+2;
				        			arraylist_product_list.add(tp);	
				        					}
				        		
				        	}
				        	else{
				        		for(int j=0;j<tmp_prod_info.size()-1;){
				        			ArrayList<ProductInfo> tp= new ArrayList<ProductInfo>();
				        			tp.add(tmp_prod_info.get(j));
				        			tp.add(tmp_prod_info.get(j+1));
				        			j=j+2;
				        			arraylist_product_list.add(tp);		
				        					}
				        		ArrayList<ProductInfo> tp= new ArrayList<ProductInfo>();
			        			tp.add(tmp_prod_info.get(tmp_prod_info.size()-1));
			        			arraylist_product_list.add(tp);	
				        	}
				        	MySimpleArrayAdapter adapter=new MySimpleArrayAdapter(ShopDescription.this,R.layout.listview_product,arraylist_product_list,new  ArrayList<String>(),cat[2],"shop");
				        	
				        	lv.setAdapter(adapter);
				          
				        	
				        	//TextView tv=new TextView(ShopDescription.this);
				        	//tv.setText(Integer.valueOf(tag));
				        	//return(new AnalogClock(ShopDescription.this));
				        	return lv;
				        	//return(tv);
				          }
				         
			    });
				    String [] head_split=heading[i].split("_");
				    String to_display="";
				    if(head_split[0].equals("k")){
				    	 to_display= to_display+"Kid's" 	;
				    }
				    else if(head_split[0].equals("w")){
				    	to_display= to_display+"Women" ;
				    }
				    else{
				    	to_display= to_display+"Men" ;
				    }
				    to_display=to_display+" ";
				    if(head_split[1].equals("tshirt")){
				    	to_display=to_display+"T-Shirt";	
				    }
				    else if(head_split[1].equals("casualshirt")){
				    	to_display=to_display+"Casual Shirt";
				    }
				    else if(head_split[1].equals("formalshirt")){
				    	to_display=to_display+"Formal Shirt";
				    }
				    else if(head_split[1].equals("jackets")){
				    	to_display=to_display+"Jacket";
				    }
				    else if(head_split[1].equals("chinosandtrousers")){
				    	to_display=to_display+"Chinos And Trousers";
				    }
				    else if(head_split[1].equals("jeans")){
				    	to_display=to_display+"Jeans";
				    }
				    else if(head_split[1].equals("formaltrouser")){
				    	to_display=to_display+"Formal Trouser";
				    }
				    else if(head_split[1].equals("shorts")){
				    	to_display=to_display+"Shorts";
				    }
				    else if(head_split[1].equals("inners")){
				    	to_display=to_display+"Inners";
				    }
				    else if(head_split[1].equals("tracksuits")){
				    	to_display=to_display+"Tracksuits";
				    }
				    else if(head_split[1].equals("swimwear")){
				    	to_display=to_display+"Swimwear";
				    }
				    else if(head_split[1].equals("ethnicwear")){
				    	to_display=to_display+"Ethnic Wear";
				    }
				    else if(head_split[1].equals("sweaters")){
				    	to_display=to_display+"Sweaters";
				    }
				    else if(head_split[1].equals("gloves")){
				    	to_display=to_display+"Gloves";
				    }
				    else if(head_split[1].equals("suit")){
				    	to_display=to_display+"Suit";
				    }
				    else if(head_split[1].equals("kurti")){
				    	to_display=to_display+"Kurti";
				    }
				    else if(head_split[1].equals("inerwearandsleepwear")){
				    	to_display=to_display+"Inners";
				    }
				    
				  
				        spec2.setIndicator(to_display);
				        tabs.addTab(spec2);
			    }
			  //fill_shop_details();
		}
		private void details_clicked(){
			Intent intent = new Intent(ShopDescription.this, ShopDescriptionDetails.class);
			Bitmap bmp;
			BitmapDrawable drawable=(BitmapDrawable) imv.getDrawable();
			bmp=drawable.getBitmap();
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 50, bs);
			intent.putExtra("ShopDetailsBitmaps",  bs.toByteArray());
			intent.putExtra("ShopDetailsDescription",shop_detail_last.description );
			intent.putExtra("ShopDetailsLandline",shop_detail_last.landline_no);
			intent.putExtra("ShopDetailsMobile",shop_detail_last.mobile_no );
			Location loc_sent=new Location("dummyprovider");//.description
			loc_sent.setLatitude(shop_detail_last.lat);
			loc_sent.setLongitude(shop_detail_last.lon);
			intent.putExtra("ShopDetailsLocation", loc_sent);
			intent.putExtra("ShopDetailsAddress",shop_detail_last.address );
			intent.putExtra("ShopDetailsCategories", shop_detail_last.categories);
			intent.putExtra("ShopDetailsId", shop_detail_last.id);
			intent.putExtra("ShopDetailsName", shop_detail_last.shopName);
			intent.putExtra("ShopDetailsWebsite", shop_detail_last.website);
			ShopDescription.this.startActivity(intent);
		}
		
		

		
		@Override
			public boolean onCreateOptionsMenu(Menu menu) {
				// TODO Auto-generated method stub
			 MenuInflater inflater = getMenuInflater();
			    inflater.inflate(R.menu.my_menu, menu);
			    return true;
			}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId()) {
			case R.id.action_details:
				//Toast.makeText(this, "Menu item 1 selected", Toast.LENGTH_SHORT)
			      //.show();
				details_clicked();
			break;
			case R.id.action_map:
			/*	Intent intent = new Intent(ShopDescription.this, MapViewShop.class);
				Location loc_sent=new Location("dummyprovider");//.description
				loc_sent.setLatitude(shop_detail_last.lat);
				loc_sent.setLongitude(shop_detail_last.lon);
				intent.putExtra("ShopDetailsLocation", loc_sent);
				ShopDescription.this.startActivity(intent);
				*/
				/*Geocoder geocoder = new Geocoder(ShopDescription.this, Locale.getDefault());
				StringBuilder result = new StringBuilder();
				try {
					List<Address>addresses = geocoder.getFromLocation(shop_detail_last.lat, shop_detail_last.lon, 1);
					if(addresses.size()>0){
						Address pata=addresses.get(0);
							result.append(pata.getAddressLine(pata.getMaxAddressLineIndex()-2));
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	*/
				
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?f=d&daddr="+shop_detail_last.address));
				ShopDescription.this.startActivity(intent);
			  break;

			default:
			  break;
			}

			return true;
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