package com.tech.shopwiz;



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
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class PinchZoomActivity extends Activity {
	private Matrix matrix = new Matrix();
	   private float scale = 1f;
	   private ScaleGestureDetector SGD;
	   private static final String TAG = "Touch";
	   private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		  private ImageLoader imageLoader;
		  private DisplayImageOptions options;
		  private String imagURL;

	// These matrices will be used to move and zoom image
	//Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prod_zoom_in);
		Bundle extras=getIntent().getExtras();
		TouchImageView mimg = (TouchImageView)findViewById(R.id.image_tobe_zoomed);
		
		 if (extras != null) {
			/* byte[] byteArray = extras.getByteArray("BitmapToBeZoomed");
				Bitmap b = BitmapFactory.decodeByteArray(
						byteArray,0,byteArray.length);
				if(b!=null){
				//img.setImageBitmap(b);
				 mimg.setImageBitmap(b);				 
				}*/
			 imagURL=extras.getString("BitmapToBeZoomedURL");
			 
				imageLoader= ImageLoader.getInstance();
		  		//imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		  		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		  		
		  		options = new DisplayImageOptions.Builder()
		  		.showImageOnLoading(R.drawable.ic_stub)
		  		.showImageForEmptyUri(R.drawable.ic_empty)
		  		.showImageOnFail(R.drawable.ic_error)
		  		.cacheInMemory(false)
		  		.cacheOnDisk(false)
		  		.considerExifParams(true)
		  		.resetViewBeforeLoading(true)
		  		.displayer(new RoundedBitmapDisplayer(20))
		  		.build();
		 }
		 
		Button butt=(Button) findViewById(R.id.button1_cancel);
		butt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	
	      ImageLoader.getInstance().displayImage(imagURL, mimg, options, animateFirstListener);

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
