package com.tech.shopwiz.modified;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.tech.shopwiz.R;
import com.tech.shopwiz.heart_activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class home_activity extends ActionBarActivity{
	  FragmentManager fragmentManager;
	 static FragmentTransaction transaction;
	 private DrawerLayout mDrawerLayout;
	 private ListView mDrawerList;
	 private ActionBarDrawerToggle mDrawerToggle;
	 private String[] mDrawerTitles;
	 LinearLayout mDrawerLinearLayout;
	 Intent locatorService = null;
   	 AlertDialog alertDialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_frame_layout);
		
		Fragment fragment = new HomeFragment(this);
		fragmentManager = getFragmentManager();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.content_frame, fragment);
		//transaction.addToBackStack(null);
		transaction.commit();
		fragmentManager.executePendingTransactions();	
		//drawer tamjam
        mDrawerTitles = getResources().getStringArray(R.array.drawer_contents);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLinearLayout=(LinearLayout)findViewById(R.id.drawer_lin_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        LinearLayout action_lin_original=(LinearLayout)findViewById(R.id.action_bar_lin_layout_main_page);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar_main_page);
		final ImageView drawer_img=(ImageView) findViewById(R.id.drawer);
		drawer_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			       boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinearLayout);
	                if(isDrawerOpen){
	                	mDrawerLayout.closeDrawer(mDrawerLinearLayout);
	                       }
	                else{
	                	mDrawerLayout.openDrawer(mDrawerLinearLayout);
	                    }
			}
		});
		
		
	}

    public  class HomeFragment extends Fragment implements AsyncResponse {
        public static final String ARG_PLANET_NUMBER = "planet_number";
        Activity c;
        Location loc;
        String address;
        SpinnerAdapter spinnerAdapter;
        Spinner spinner;
        final ArrayList <String> places;
        Context context;
       
        public HomeFragment(Activity _c) {
            // Empty constructor required for fragment subclasses
        	this.c=_c;
        	this.context=_c;
        	this.spinnerAdapter=null;
        	this.spinner=null;
        	this. places=new ArrayList<String>();
        }
      
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.home_layout, container, false);
           loc=new Location("dummyprovider");
           getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            address="";
            spinnerAdapter = new SpinnerAdapter(c);
            spinner = (Spinner) rootView.findViewById(R.id.spinner1);
           ImageView loc_img=(ImageView)rootView.findViewById(R.id.loc_icon_modified);
           final ArrayList <String> places=new ArrayList<String>();
           spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(address.equals("error") | address.equals("")){
					if(arg2==0){
						loc.setLatitude(19.071769);
						loc.setLongitude(72.833958);
						
					}
					else if(arg2==1){
						loc.setLatitude(19.071769);
						loc.setLongitude(72.833958);
					}
		           }
				else{
					if(arg2==1){
			
						loc.setLatitude(19.071769);
						loc.setLongitude(72.833958);
					}
					else if(arg2==2){
						loc.setLatitude(19.071769);
						loc.setLongitude(72.833958);
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
           loc_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(c, "clicked",
                        Toast.LENGTH_LONG).show();
				 if (!startService(context,c)) {
	                    CreateAlert(c,"Error!", "Service Cannot be started");
	                } else {
	                    Toast.makeText(c, "Service Started",
	                            Toast.LENGTH_LONG).show();
	                   
	                  
	                }
				
			}
		});
         
        
           if(address.equals("error") | address.equals("")){
           }else{
        	   places.add(address);
           }
           places.add("Bandra");
           places.add("Khar");
           spinnerAdapter.addItems(places);
            
            spinner.setAdapter(spinnerAdapter);
            	ImageView prod_img=(ImageView) rootView.findViewById(R.id.prod_img);
            	ImageView shop_img=(ImageView) rootView.findViewById(R.id.shop_img);
            	TextView tv_prod=(TextView) rootView.findViewById(R.id.prod_txt);
            	int ht=prod_img.getHeight();
            	shop_img.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Fragment fragment = new TabsFragment(c,"shop",loc);
						FragmentManager fragmentManager = getFragmentManager();
						FragmentTransaction transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.content_frame, fragment);
						transaction.addToBackStack(null);
						transaction.commit();
						fragmentManager.executePendingTransactions();
						Log.v("back stack entries",fragmentManager.getBackStackEntryCount()+"");
					}
				});
            	prod_img.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Fragment fragment = new TabsFragment(c,"products",loc);
						FragmentManager fragmentManager = getFragmentManager();
						FragmentTransaction transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.content_frame, fragment);
						transaction.addToBackStack(null);
						transaction.commit();
						fragmentManager.executePendingTransactions();
						Log.v("back stack entries",fragmentManager.getBackStackEntryCount()+"");
					}
				});
            	
            return rootView;
        }

		private String getAddress(Activity a,Location loc2) {
			// TODO Auto-generated method stub
			Geocoder geocoder = new Geocoder(a, Locale.getDefault());
			StringBuilder result = new StringBuilder();
			List<Address> addresses;
			try {
				addresses = geocoder.getFromLocation(loc2.getLatitude(), loc2.getLongitude(), 1);
				if(addresses.size()>0){
					Address pata=addresses.get(0);
						result.append(pata.getAddressLine(pata.getMaxAddressLineIndex()-2));
						//result.append(pata.getSubLocality());
						//result.append(",");
						//result.append(pata.getLocality());
						//if(pata.getPostalCode()!=null){
						//result.append(",");
						//result.append(pata.getPostalCode());
						//}
						 Toast.makeText(c,
								 "sent",
					                Toast.LENGTH_LONG).show();
						return result.toString();
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 Toast.makeText(c,
						 "error",
			                Toast.LENGTH_LONG).show();
				return "error";
			}
			return "error";
		}

		@Override
		public void processFinish(Location _loc) {
			// TODO Auto-generated method stub
			address=getAddress(c, _loc);
			places.clear();
			this.loc=_loc;
				 spinnerAdapter.clear();
				  Toast.makeText(c,"+"+ address,
                       Toast.LENGTH_LONG).show();
				 if(address.equals("error") | address.equals("")){
					 Toast.makeText(c, "error",
	                            Toast.LENGTH_LONG).show();
		           }else{
		        	   places.add(address);
		        
		           }
		           places.add("Bandra");
		           places.add("Khar");
		           spinnerAdapter.addItems(places);
		           spinnerAdapter.notifyDataSetChanged();
		           spinner.setAdapter(spinnerAdapter);
		}
		 

		 public  boolean startService(Context v,Activity a) {
		        try {
		            // this.locatorService= new
		            // Intent(FastMainActivity.this,LocatorService.class);
		            // startService(this.locatorService);
		            FetchCordinates fetchCordinates = new FetchCordinates(HomeFragment.this,a);
		          //  fetchCordinates.delegate=(AsyncResponse) a;
		           fetchCordinates.execute();
		            return true;
		        } catch (Exception error) {
		            return false;
		        }

		    }
    }
     
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	//TextView tv_name=(TextView)findViewById(R.id.user_name);
        	//tv_name.setText("Name");
        	Toast.makeText(home_activity.this,"Clicked on " + mDrawerTitles[position], Toast.LENGTH_SHORT).show();
        	mDrawerLayout.closeDrawer(mDrawerLinearLayout);
        	 
        }
    }
    @Override 
    public void onBackPressed() { 
    	Toast.makeText(home_activity.this, Integer.toString(getSupportFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();
    	//getSupportFragmentManager().executePendingTransactions();
    	if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack(); 
        } else { 
            this.finish(); 
        }
    }
  

   
	public static AlertDialog CreateAlert(Activity c,String title, String message) {
        AlertDialog alert = new AlertDialog.Builder(c).create();

        alert.setTitle(title);

        alert.setMessage(message);

        return alert;

    }
	 public boolean stopService() {
	        if (this.locatorService != null) {
	            this.locatorService = null;
	        }
	        return true;
	    }

	   
   
}
