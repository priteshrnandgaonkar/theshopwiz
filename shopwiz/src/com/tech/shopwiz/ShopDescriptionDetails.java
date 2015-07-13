package com.tech.shopwiz;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class ShopDescriptionDetails extends Activity {
	private Bundle extras;
	private ShopInfo shop_detail_last;
	private Bitmap shop_img;
	Location loc;
	private ImageView img_shop; 
	ActionBar actionbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_details_like_address);
		extras = getIntent().getExtras();
		img_shop=(ImageView) findViewById(R.id.img_shop_selected);
		shop_detail_last=new ShopInfo(1,"westment","dombivli west",null,"imageURL","landline no","mobile no","website",null,19.064146,72.835472);
		shop_img = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.kurti1);
		loc=new Location("dummyprovider");
		if(extras != null){
			byte[] byteArray = extras.getByteArray("ShopDetailsBitmaps");
			Bitmap b = BitmapFactory.decodeByteArray(
					byteArray,0,byteArray.length);
			shop_img=b;
			
			//Bitmap bmp=extras.getParcelable("ProductDetailsBitmaps");
				loc=(Location) extras.get("ShopDetailsLocation");
			shop_detail_last=new ShopInfo(extras.getInt("ShopDetailsId"),extras.getString("ShopDetailsName")
					,extras.getString("ShopDetailsAddress"),extras.getString("ShopDetailsDescription"),
					null,extras.getString("ShopDetailsLandline"),extras.getString("ShopDetailsMobile")
					,extras.getString("ShopDetailsWebsite"),
					extras.getString("ShopDetailsCategories"),
					loc.getLatitude(),loc.getLongitude());
		}
		actionbar=getActionBar();
		actionbar.setTitle(shop_detail_last.shopName);
		img_shop.setImageBitmap(shop_img);
		fill_shop_details();
		
	}

	 private void fill_shop_details() {
			// TODO Auto-generated method stub
			TextView tv_name=(TextView) findViewById(R.id.shop_name_tablerow);
			tv_name.setText(shop_detail_last.shopName);
			TextView tv_address=(TextView) findViewById(R.id.shop_address_tablerow);
			tv_address.setText(shop_detail_last.address);
			TextView tv_landline=(TextView) findViewById(R.id.shop_landline_tablerow);
			TableRow tr_landline=(TableRow)findViewById(R.id.tablerow_landline);
			if(shop_detail_last.landline_no.equals("")){
				//tv_landline.setText("null");
				tr_landline.setVisibility(View.GONE);
				
			}else{
			tv_landline.setText(shop_detail_last.landline_no);
			
			}
			TextView tv_mobile=(TextView) findViewById(R.id.shop_mobile_tablerow);
			TableRow tr_mobile=(TableRow)findViewById(R.id.tablerow_mobile);
		//	tv_mobile.setVisibility(View.VISIBLE);
			if(shop_detail_last.mobile_no.equals("")){
				tv_mobile.setText("");
				tr_mobile.setVisibility(View.GONE);
			}else{
			tv_mobile.setText(shop_detail_last.mobile_no);
			}
			TextView tv_website=(TextView) findViewById(R.id.shop_website_tablerow);
			TableRow tr_website=(TableRow)findViewById(R.id.tablerow_website);
			if(shop_detail_last.website.equals("")){
				tv_website.setText("");
				tr_website.setVisibility(View.GONE);
			}else{
			tv_website.setText(shop_detail_last.website);
			}
			TextView tv_description=(TextView) findViewById(R.id.shop_description_tablerow);
			TableRow tr_description=(TableRow)findViewById(R.id.tablerow_description);
			if(shop_detail_last.description.equals("")){
				tv_description.setText("");
				tr_description.setVisibility(View.GONE);
			}else{
			tv_description.setText(shop_detail_last.description);
			}
	 }

}
