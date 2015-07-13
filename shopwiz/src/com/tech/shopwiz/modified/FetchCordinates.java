package com.tech.shopwiz.modified;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;




public class FetchCordinates extends AsyncTask<String, Integer, String> {
    ProgressDialog progDailog = null;
    public AsyncResponse delegate;
    public double lati = 0.0;
    public double longi = 0.0;
    Activity c;
    public LocationManager mLocationManager;
    public VeggsterLocationListener mVeggsterLocationListener;
    public FetchCordinates(AsyncResponse r,Activity _c){
    	this.c=_c;	
    	this.delegate=r;
    	//delegate=(AsyncResponse) _c;
    }
    @Override
    protected void onPreExecute() {
        mVeggsterLocationListener = new VeggsterLocationListener();
        mLocationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0,
                 mVeggsterLocationListener);

        progDailog = new ProgressDialog(c);
        progDailog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                FetchCordinates.this.cancel(true);
            }
        });
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(true);
        progDailog.setCancelable(true);
        progDailog.show();

    }

    @Override
    protected void onCancelled(){
        System.out.println("Cancelled by user!");
       ( progDailog).dismiss();
        mLocationManager.removeUpdates((android.location.LocationListener) mVeggsterLocationListener);
    }

    @Override
    protected void onPostExecute(String result) {
        progDailog.dismiss();
        Location loc_fetch=new Location("dummyprovider");
        loc_fetch.setLatitude(lati);
        loc_fetch.setLongitude(longi);
        delegate.processFinish(loc_fetch);
        Toast.makeText(c,
                "LATITUDE :" + lati + " LONGITUDE :" + longi,
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub

        while (this.lati == 0.0) {

        }
        return null;
    }
    public class VeggsterLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            int lat = (int) location.getLatitude(); // * 1E6);
            int log = (int) location.getLongitude(); // * 1E6);
            int acc = (int) (location.getAccuracy());

            String info = location.getProvider();
            try {

                // LocatorService.myLatitude=location.getLatitude();

                // LocatorService.myLongitude=location.getLongitude();

                lati = location.getLatitude();
                longi = location.getLongitude();

            } catch (Exception e) {
                // progDailog.dismiss();
                // Toast.makeText(getApplicationContext(),"Unable to get Location"
                // , Toast.LENGTH_LONG).show();
            }

        }

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}

 

    }

}