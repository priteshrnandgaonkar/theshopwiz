package com.tech.shopwiz;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MapViewShop extends  FragmentActivity {
private	GoogleMap googleMap;
private Bundle extras;
Location loc;
Marker shop_marker;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.map_layout);
	       SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
            extras = getIntent().getExtras();
            // Enabling MyLocation button for the Google Map
            googleMap.setMyLocationEnabled(true);
         
            //
            loc=new Location("dummyprovider");
            if(extras != null){
            	 loc=(Location) extras.get("ShopDetailsLocation");
            }
            LatLng shop_loc=new LatLng(loc.getLatitude(),loc.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shop_loc, 14));
            shop_marker=  googleMap.addMarker(new MarkerOptions()
            .position(shop_loc)
            ); 
	    }

}
