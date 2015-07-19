package com.tech.shopwiz.modified;

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
import com.tech.shopwiz.R;
import com.tech.shopwiz.ShopDescription;
import com.tech.shopwiz.ShopInfo;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MySimpleArrayAdapterShopModified extends BaseAdapter {
	private ArrayList<ShopInfo> shop_lists;
	private Context context;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private ImageLoader imageLoader;
	static class ViewHolder{
		  public TextView shop_1;
		  public TextView shop_1_address;
		  public ImageView img1;
	}
		public MySimpleArrayAdapterShopModified(Context _context ,ArrayList<ShopInfo> _shop_lists) {
			// TODO Auto-generated constructor stub
			this.context=_context;
			this.shop_lists=_shop_lists;
			imageLoader= ImageLoader.getInstance();
			ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
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
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return shop_lists.size();
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

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View rowView = convertView;
			if(rowView==null){
				LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				    rowView = inflater.inflate(R.layout.listview_shop_row_modified,parent,false);
				    ViewHolder viewHolder = new ViewHolder();
				    viewHolder.shop_1=(TextView) rowView.findViewById(R.id.shop_name_shop_page_listview);
				    viewHolder.img1=(ImageView) rowView.findViewById(R.id.shop_img_shop_page_listview);
				    viewHolder.shop_1_address=(TextView) rowView.findViewById(R.id.shop_oneLineAddress_shop_page_listview);
				    
				    rowView.setTag(viewHolder);
				}
			final ViewHolder holder = (ViewHolder) rowView.getTag();
			String body="http://theshopwiz.com/";
			final String imgurl1;
			imgurl1=body+shop_lists.get(position).imageURL;
			holder.shop_1.setText(shop_lists.get(position).shopName);
			holder.shop_1_address.setText(shop_lists.get(position).oneLineAddress);
			
		    ImageLoader.getInstance().displayImage(imgurl1, holder.img1, options, animateFirstListener);
		    holder.img1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, ShopDescriptionModified.class);
					/*Bitmap bmp;
					BitmapDrawable drawable=(BitmapDrawable) holder.img1.getDrawable();
					bmp=drawable.getBitmap(); 
					ByteArrayOutputStream bs = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.JPEG, 50, bs);
					intent.putExtra("ShopDetailsBitmaps",  bs.toByteArray());*/
					intent.putExtra("ShopDetailsId", shop_lists.get(position).id);
					intent.putExtra("ShopOneLineAddress", shop_lists.get(position).oneLineAddress);
					intent.putExtra("StringLatitude", shop_lists.get(position).lat);
					intent.putExtra("StringLongitude", shop_lists.get(position).lon);
					/*intent.putExtra("ShopDetailsImgURL", imgurl1);
					intent.putExtra("ShopDetailsDescription",shop_lists.get(position).description );
					intent.putExtra("ShopDetailsLandline",shop_lists.get(position).landline_no);
					intent.putExtra("ShopDetailsMobile",shop_lists.get(position).mobile_no );
					Location loc_sent=new Location("dummyprovider");//.description
					loc_sent.setLatitude(shop_lists.get(position).lat);
					loc_sent.setLongitude(shop_lists.get(position).lon);
					intent.putExtra("ShopDetailsLocation", loc_sent);
					intent.putExtra("ShopDetailsAddress",shop_lists.get(position).address );
					intent.putExtra("ShopDetailsCategories", shop_lists.get(position).categories);
					
					intent.putExtra("ShopDetailsName", shop_lists.get(position).shopName);
					intent.putExtra("ShopDetailsWebsite", shop_lists.get(position).website);*/
					context.startActivity(intent);
				}
			});
			
			return rowView;
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
