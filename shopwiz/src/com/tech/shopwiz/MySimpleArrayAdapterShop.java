package com.tech.shopwiz;

import java.io.ByteArrayOutputStream;
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
import com.tech.shopwiz.MySimpleArrayAdapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MySimpleArrayAdapterShop extends BaseAdapter{
private ArrayList<ArrayList<ShopInfo>> shop_lists;
private Context context;
private DisplayImageOptions options;
private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
private ImageLoader imageLoader;
static class ViewHolder{
	  public TextView shop_1;
	  public TextView shop_2;
	  public ImageView img1;
	  public ImageView img2;
	  public LinearLayout lin2;
}
	public MySimpleArrayAdapterShop(Context _context ,ArrayList<ArrayList<ShopInfo>> _shop_lists) {
		// TODO Auto-generated constructor stub
		this.context=_context;
		this.shop_lists=_shop_lists;
		imageLoader= ImageLoader.getInstance();
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
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
			    rowView = inflater.inflate(R.layout.listview_shop_row,parent,false);
			    ViewHolder viewHolder = new ViewHolder();
			    viewHolder.shop_1=(TextView) rowView.findViewById(R.id.shop_1_name);
			    viewHolder.shop_2=(TextView) rowView.findViewById(R.id.shop_2_name);
			    viewHolder.img1=(ImageView) rowView.findViewById(R.id.shop1_image);
			    viewHolder.img2=(ImageView) rowView.findViewById(R.id.shop2_image);
			    viewHolder.lin2=(LinearLayout) rowView.findViewById(R.id.second_layoutview_shop);
			    rowView.setTag(viewHolder);
			}
		final ViewHolder holder = (ViewHolder) rowView.getTag();
		String body="http://theshopwiz.com/";
		final String imgurl1;
		final String imgurl2;
		holder.lin2.setVisibility(View.VISIBLE);
		if(shop_lists.get(position).size()==1){
			 holder.lin2.setVisibility(View.GONE);
			imgurl1=body+shop_lists.get(position).get(0).imageURL;
			holder.shop_1.setText(shop_lists.get(position).get(0).shopName);
		    ImageLoader.getInstance().displayImage(imgurl1, holder.img1, options, animateFirstListener);
		    holder.img1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, ShopDescription.class);
					/*Bitmap bmp;
					BitmapDrawable drawable=(BitmapDrawable) holder.img1.getDrawable();
					bmp=drawable.getBitmap(); 
					ByteArrayOutputStream bs = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.JPEG, 50, bs);
					intent.putExtra("ShopDetailsBitmaps",  bs.toByteArray());*/
					intent.putExtra("ShopDetailsImgURL", imgurl1);
					intent.putExtra("ShopDetailsDescription",shop_lists.get(position).get(0).description );
					intent.putExtra("ShopDetailsLandline",shop_lists.get(position).get(0).landline_no);
					intent.putExtra("ShopDetailsMobile",shop_lists.get(position).get(0).mobile_no );
					Location loc_sent=new Location("dummyprovider");//.description
					loc_sent.setLatitude(shop_lists.get(position).get(0).lat);
					loc_sent.setLongitude(shop_lists.get(position).get(0).lon);
					intent.putExtra("ShopDetailsLocation", loc_sent);
					intent.putExtra("ShopDetailsAddress",shop_lists.get(position).get(0).address );
					intent.putExtra("ShopDetailsCategories", shop_lists.get(position).get(0).categories);
					intent.putExtra("ShopDetailsId", shop_lists.get(position).get(0).id);
					intent.putExtra("ShopDetailsName", shop_lists.get(position).get(0).shopName);
					intent.putExtra("ShopDetailsWebsite", shop_lists.get(position).get(0).website);
					context.startActivity(intent);
				}
			});
		}
		else{
			holder.lin2.setVisibility(View.VISIBLE);
			imgurl1=body+shop_lists.get(position).get(0).imageURL;
			imgurl2=body+shop_lists.get(position).get(1).imageURL;
			holder.shop_1.setText(shop_lists.get(position).get(0).shopName);
			holder.shop_2.setText(shop_lists.get(position).get(1).shopName);
		    ImageLoader.getInstance().displayImage(imgurl1, holder.img1, options, animateFirstListener);
		    ImageLoader.getInstance().displayImage(imgurl2, holder.img2, options, animateFirstListener);
 holder.img1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, ShopDescription.class);
					/*Bitmap bmp;
					BitmapDrawable drawable=(BitmapDrawable) holder.img1.getDrawable();
					bmp=drawable.getBitmap(); 
					ByteArrayOutputStream bs = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.JPEG, 50, bs);
					intent.putExtra("ShopDetailsBitmaps",  bs.toByteArray());*/
					intent.putExtra("ShopDetailsImgURL", imgurl1);
					intent.putExtra("ShopDetailsDescription",shop_lists.get(position).get(0).description );
					intent.putExtra("ShopDetailsLandline",shop_lists.get(position).get(0).landline_no);
					intent.putExtra("ShopDetailsMobile",shop_lists.get(position).get(0).mobile_no );
					Location loc_sent=new Location("dummyprovider");//.description
					loc_sent.setLatitude(shop_lists.get(position).get(0).lat);
					loc_sent.setLongitude(shop_lists.get(position).get(0).lon);
					intent.putExtra("ShopDetailsLocation", loc_sent);
					intent.putExtra("ShopDetailsAddress",shop_lists.get(position).get(0).address );
					intent.putExtra("ShopDetailsCategories", shop_lists.get(position).get(0).categories);
					intent.putExtra("ShopDetailsId", shop_lists.get(position).get(0).id);
					intent.putExtra("ShopDetailsName", shop_lists.get(position).get(0).shopName);
					intent.putExtra("ShopDetailsWebsite", shop_lists.get(position).get(0).website);
					context.startActivity(intent);
				}
			});
 holder.img2.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, ShopDescription.class);
			/*Bitmap bmp;
			BitmapDrawable drawable=(BitmapDrawable) holder.img2.getDrawable();
			bmp=drawable.getBitmap(); 
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 50, bs);
			intent.putExtra("ShopDetailsBitmaps",  bs.toByteArray());*/
			intent.putExtra("ShopDetailsImgURL", imgurl2);
			intent.putExtra("ShopDetailsDescription",shop_lists.get(position).get(1).description );
			intent.putExtra("ShopDetailsLandline",shop_lists.get(position).get(1).landline_no);
			intent.putExtra("ShopDetailsMobile",shop_lists.get(position).get(1).mobile_no );
			Location loc_sent=new Location("dummyprovider");//.description
			loc_sent.setLatitude(shop_lists.get(position).get(1).lat);
			loc_sent.setLongitude(shop_lists.get(position).get(1).lon);
			intent.putExtra("ShopDetailsLocation", loc_sent);
			intent.putExtra("ShopDetailsAddress",shop_lists.get(position).get(1).address );
			intent.putExtra("ShopDetailsCategories", shop_lists.get(position).get(1).categories);
			intent.putExtra("ShopDetailsId", shop_lists.get(position).get(1).id);
			intent.putExtra("ShopDetailsName", shop_lists.get(position).get(1).shopName);
			intent.putExtra("ShopDetailsWebsite", shop_lists.get(position).get(1).website);
			context.startActivity(intent);
		}
	});
		}
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
