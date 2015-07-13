package com.tech.shopwiz;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MySimpleArrayAdapter extends BaseAdapter {
	private final Context context;
	private final String category;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private ImageLoader imageLoader;
	private String flag;
	  private  ArrayList<ArrayList<ProductInfo>> products_list;
	  private ArrayList<String> UnknownFieldNames;
	  static class ViewHolder{
		  public TextView prod_id_2;
		  public TextView prod_id_1;
		  public TextView cost_1;
		  public TextView cost_2;
		  public TextView shop_1;
		  public TextView shop_2;
		  public ImageView img1;
		  public ImageView img2;
		  public LinearLayout l1;
		  public TextView prod_desc_1;
		  public TextView prod_desc_2;
	  }
	 public MySimpleArrayAdapter(Context context, int id,List<ArrayList<ProductInfo>> products_list,ArrayList<String> _UnknownFieldNames,String _category,String _flag) {
		//super(context, R.layout.listview_product, products_list);
		this.products_list=(ArrayList<ArrayList<ProductInfo>>) products_list;
		this.context=context;
		this.UnknownFieldNames=_UnknownFieldNames;
		imageLoader= ImageLoader.getInstance();
		//imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(false)
		// .resetViewBeforeLoading(true)
		.displayer(new RoundedBitmapDisplayer(20))
		//.displayer(new FadeInBitmapDisplayer(5000))
		.build();
		// TODO Auto-generated constructor stub
		this.category=_category;
		this.flag=_flag;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		if(rowView==null){
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    rowView = inflater.inflate(R.layout.listview_product,parent,false);
		    ViewHolder viewHolder = new ViewHolder();
		    viewHolder.l1=(LinearLayout)rowView.findViewById(R.id.second_layoutview);
		   // viewHolder.prod_id_1=(TextView) rowView.findViewById(R.id.product_id_1);
		   // viewHolder.prod_id_2=(TextView) rowView.findViewById(R.id.product_id_2);
		    viewHolder.cost_1=(TextView) rowView.findViewById(R.id.cost_1);
		    viewHolder.cost_2=(TextView) rowView.findViewById(R.id.cost_2);
		    viewHolder.shop_1=(TextView) rowView.findViewById(R.id.shop_1);
		    viewHolder.shop_2=(TextView) rowView.findViewById(R.id.shop_2);
		    viewHolder.img1=(ImageView) rowView.findViewById(R.id.prod1);
		    viewHolder.img2=(ImageView) rowView.findViewById(R.id.prod2);
		    viewHolder.prod_desc_1=(TextView) rowView.findViewById(R.id.one_line_prod_desc_text_1);
		    viewHolder.prod_desc_2=(TextView) rowView.findViewById(R.id.one_line_prod_desc_text_2);
		    rowView.setTag(viewHolder);
		}
		
		/*TextView prod_id_1_text=(TextView)rowView.findViewById(R.id.product_id_1);
		TextView prod_id_2_text=(TextView)rowView.findViewById(R.id.product_id_2);
		TextView cost_1_text=(TextView)rowView.findViewById(R.id.cost_1);
		TextView cost_2_text=(TextView)rowView.findViewById(R.id.cost_2);
		TextView shop_1_text=(TextView)rowView.findViewById(R.id.shop_1);
		TextView shop_2_text=(TextView)rowView.findViewById(R.id.shop_2);
		prod_id_1_text.setText(products_list.get(position).get(0).getProductId());
		prod_id_2_text.setText(products_list.get(position).get(1).getProductId());
		cost_1_text.setText(Integer.toString(products_list.get(position).get(0).getCost()));
		cost_2_text.setText(Integer.toString(products_list.get(position).get(1).getCost()));
		shop_1_text.setText(products_list.get(position).get(0).getShopName());
		shop_2_text.setText(products_list.get(position).get(1).getShopName());
		ImageView im1=(ImageView) rowView.findViewById(R.id.prod1);
		ImageView im2=(ImageView) rowView.findViewById(R.id.prod2);*/
		    
		final ViewHolder holder = (ViewHolder) rowView.getTag();
		String body="";
				//"http://theshopwiz.com/";
		String imgurl1;
		String imgurl2;
		holder.l1.setVisibility(View.VISIBLE);
		
		if(products_list.get(position).size()==1){
			holder.l1.setVisibility(View.INVISIBLE);
			
			imgurl1=body+products_list.get(position).get(0).imageurl.get(0);
		
			
	
			//holder.prod_id_1.setText(products_list.get(position).get(0).getProductId());
		    holder.cost_1.setText("₹ " +Integer.toString(products_list.get(position).get(0).getCost()));
		    holder.cost_1.setTextSize(18);
		    holder.prod_desc_1.setText(products_list.get(position).get(0).description);
		    holder.shop_1.setText(products_list.get(position).get(0).getShopName());
		    System.out.println(imgurl1);
		    ImageLoader.getInstance().displayImage(imgurl1, holder.img1, options, animateFirstListener);  
		    holder.img1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//try {
					Intent intent = new Intent(context, ProductDetailsActivity.class);
					/*Bitmap bmp;
					//=products_list.get(position).get(0).getBitmap();
					BitmapDrawable drawable=(BitmapDrawable) holder.img1.getDrawable();
					bmp=drawable.getBitmap(); 
					ByteArrayOutputStream bs = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.JPEG, 50, bs);
					intent.putExtra("ProductDetailsFrom",  flag);
				intent.putExtra("ProductDetailsBitmaps",  bs.toByteArray());*/
				intent.putExtra("ProductDetailsCategory",  category);
				intent.putExtra("ProductDetailsFrom",  flag);
				intent.putExtra("ProductDetailsDummyId", products_list.get(position).get(0).dummy_prod_id );
				intent.putExtra("ProductDetailsId", products_list.get(position).get(0).getProductId());
				intent.putExtra("ProductDetailsCost", products_list.get(position).get(0).getCost());
				intent.putExtra("ProductDetailsDescription", products_list.get(position).get(0).getDescription());
				intent.putExtra("ProductDetailsShopName", products_list.get(position).get(0).getShopName());
				intent.putExtra("ProductDetailsHashMap", products_list.get(position).get(0).getUnknownFields());				//intent.putStringArrayListExtra("UnknownFieldList", UnknownFieldNames);
				intent.putExtra("ProductDetailsRating", products_list.get(position).get(0).getRating());
				intent.putExtra("ProductDetailsShopAddress", products_list.get(position).get(0).getShopAddress());
				intent.putExtra("ProductDetailsImageURL", products_list.get(position).get(0).getImageUrl());
				intent.putExtra("ProductDetailsList", UnknownFieldNames);
				intent.putStringArrayListExtra("ProductDetailsStyle", products_list.get(position).get(0).style);
				intent.putStringArrayListExtra("ProductDetailsFabric", products_list.get(position).get(0).fabric);
				intent.putStringArrayListExtra("ProductDetailsPattern", products_list.get(position).get(0).pattern);
				intent.putStringArrayListExtra("ProductDetailsSize", products_list.get(position).get(0).size);
				intent.putStringArrayListExtra("ProductDetailsColor", products_list.get(position).get(0).color);
				context.startActivity(intent);	
		
				}
			});
		    //Bitmap bitmap1=products_list.get(position).get(0).getBitmap();
		   // holder.img1.setImageBitmap(bitmap1);
            
		}
		else{
			imgurl1=body+products_list.get(position).get(0).imageurl.get(0);
			imgurl2=body+products_list.get(position).get(1).imageurl.get(0);
		//holder.prod_id_1.setText(products_list.get(position).get(0).getProductId());
		 //   holder.prod_id_2.setText(products_list.get(position).get(1).getProductId());
		    holder.cost_1.setText("₹ "+Integer.toString(products_list.get(position).get(0).getCost()));
		    holder.cost_1.setTextSize(18);
		    holder.prod_desc_1.setText(products_list.get(position).get(0).description);
		    holder.cost_2.setText("₹ "+Integer.toString(products_list.get(position).get(1).getCost()));
		    holder.cost_2.setTextSize(18);
		    holder.shop_1.setText(products_list.get(position).get(0).getShopName());
		    holder.prod_desc_2.setText(products_list.get(position).get(1).description);
		    holder.shop_2.setText(products_list.get(position).get(1).getShopName());
		    ImageLoader.getInstance().displayImage(imgurl1, holder.img1, options, animateFirstListener);
		    holder.img1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//try {
					Intent intent = new Intent(context, ProductDetailsActivity.class);
					/*Bitmap bmp;
					//=products_list.get(position).get(0).getBitmap();
					BitmapDrawable drawable=(BitmapDrawable) holder.img1.getDrawable();
					bmp=drawable.getBitmap(); 
					ByteArrayOutputStream bs = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.JPEG, 50, bs);
					
				intent.putExtra("ProductDetailsBitmaps",  bs.toByteArray());*/
				intent.putExtra("ProductDetailsCategory",  category);
				intent.putExtra("ProductDetailsFrom",  flag);
				intent.putExtra("ProductDetailsDummyId", products_list.get(position).get(0).dummy_prod_id );

				intent.putExtra("ProductDetailsId", products_list.get(position).get(0).getProductId());
				intent.putExtra("ProductDetailsCost", products_list.get(position).get(0).getCost());
				intent.putExtra("ProductDetailsDescription", products_list.get(position).get(0).getDescription());
				intent.putExtra("ProductDetailsShopName", products_list.get(position).get(0).getShopName());
				intent.putExtra("ProductDetailsHashMap", products_list.get(position).get(0).getUnknownFields());				//intent.putStringArrayListExtra("UnknownFieldList", UnknownFieldNames);
				intent.putExtra("ProductDetailsRating", products_list.get(position).get(0).getRating());
				intent.putExtra("ProductDetailsShopAddress", products_list.get(position).get(0).getShopAddress());
				intent.putStringArrayListExtra("ProductDetailsImageURL", products_list.get(position).get(0).imageurl);
				intent.putStringArrayListExtra("ProductDetailsStyle", products_list.get(position).get(0).style);
				intent.putStringArrayListExtra("ProductDetailsFabric", products_list.get(position).get(0).fabric);
				intent.putStringArrayListExtra("ProductDetailsPattern", products_list.get(position).get(0).pattern);
				intent.putStringArrayListExtra("ProductDetailsSize", products_list.get(position).get(0).size);
				intent.putStringArrayListExtra("ProductDetailsColor", products_list.get(position).get(0).color);
				intent.putExtra("ProductDetailsList", UnknownFieldNames);	
				context.startActivity(intent);	
				
				}
			});
		    //Bitmap bitmap1=products_list.get(position).get(0).getBitmap();
		    //holder.img1.setImageBitmap(bitmap1);
		    ImageLoader.getInstance().displayImage(imgurl2, holder.img2, options, animateFirstListener);
holder.img2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//try {
					Intent intent = new Intent(context, ProductDetailsActivity.class);
					/*Bitmap bmp;
					//=products_list.get(position).get(0).getBitmap();
					BitmapDrawable drawable=(BitmapDrawable) holder.img2.getDrawable();
					bmp=drawable.getBitmap(); 
					ByteArrayOutputStream bs = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.JPEG, 50, bs);
				intent.putExtra("ProductDetailsBitmaps",  bs.toByteArray());*/
				intent.putExtra("ProductDetailsCategory",  category);
				intent.putExtra("ProductDetailsFrom",  flag);
				intent.putExtra("ProductDetailsDummyId", products_list.get(position).get(1).dummy_prod_id );

				intent.putExtra("ProductDetailsId", products_list.get(position).get(1).getProductId());
				intent.putExtra("ProductDetailsCost", products_list.get(position).get(1).getCost());
				intent.putExtra("ProductDetailsDescription", products_list.get(position).get(1).getDescription());
				intent.putExtra("ProductDetailsShopName", products_list.get(position).get(1).getShopName());
				intent.putExtra("ProductDetailsHashMap", products_list.get(position).get(1).getUnknownFields());				//intent.putStringArrayListExtra("UnknownFieldList", UnknownFieldNames);
				intent.putExtra("ProductDetailsRating", products_list.get(position).get(1).getRating());
				intent.putExtra("ProductDetailsShopAddress", products_list.get(position).get(1).getShopAddress());
				intent.putExtra("ProductDetailsImageURL", products_list.get(position).get(1).getImageUrl());
				intent.putExtra("ProductDetailsList", UnknownFieldNames);	
				intent.putStringArrayListExtra("ProductDetailsStyle", products_list.get(position).get(1).style);
				intent.putStringArrayListExtra("ProductDetailsFabric", products_list.get(position).get(1).fabric);
				intent.putStringArrayListExtra("ProductDetailsPattern", products_list.get(position).get(1).pattern);
				intent.putStringArrayListExtra("ProductDetailsSize", products_list.get(position).get(1).size);
				intent.putStringArrayListExtra("ProductDetailsColor", products_list.get(position).get(1).color);
				context.startActivity(intent);	
				
				}
			});
		    //Bitmap bitmap2=products_list.get(position).get(1).getBitmap();
		    //holder.img2.setImageBitmap(bitmap2);
		   /* holder.ll.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "main clicked", Toast.LENGTH_SHORT).show();

				}
			});*/
		    }
		return rowView;
		//remaining
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
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return products_list.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
