package com.tech.shopwiz.modified;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

public class ExtendedImageView extends ImageView {
	   public ExtendedImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDraw(Canvas canvas) {
	        super.onDraw(canvas);
	        Paint paint = new Paint();
	        paint.setColor(Color.BLACK);
	        paint.setStrokeWidth(3);
	        canvas.drawRect(50, 50, 80, 80, paint);
	    }
}
