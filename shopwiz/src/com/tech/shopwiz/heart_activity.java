package com.tech.shopwiz;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class heart_activity extends ActionBarActivity {
	 private DrawerLayout mDrawerLayout;
	    private ListView mDrawerList;
	    private ActionBarDrawerToggle mDrawerToggle;
	    private String[] mDrawerTitles;
	    LinearLayout mDrawerLinearLayout;
	
	
	private LocationManager locationManager;
	AlertDialog.Builder builder;
	AlertDialog dialog ;
	Dialog d1;
	StringBuilder current_loc_address;
	List<Address> addresses;
	LocationListener  listener;
	public Location loc;
	String provider;
	AutoCompleteTextView atvPlaces;
	PlacesTask placesTask;
	ParserTask parserTask;
	boolean isNetworkActiveBool;
	 Handler handler;
	Timer    timer;
	Toast toast_one;
	boolean flag;
	@Override
	public void addContentView(View view, LayoutParams params) {
		// TODO Auto-generated method stub
		super.addContentView(view, params);
	}
	/*  @Override
	    public void setTitle(CharSequence title) {
	        mTitle = title;
	      //  getActionBar().setTitle(mTitle);
	    }*/

	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        // Pass any configuration change to the drawer toggls
	        mDrawerToggle.onConfigurationChanged(newConfig);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Pass the event to ActionBarDrawerToggle, if it returns
	        // true, then it has handled the app icon touch event
	        if (mDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	        }
	        // Handle your other action bar items...
	        return super.onOptionsItemSelected(item);
	    }

	  //  @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		//getActionBar().setDisplayShowTitleEnabled(false);
		getWindow().
		  getDecorView().
		  setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); 
		 	mDrawerTitles = getResources().getStringArray(R.array.drawer_contents);
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.left_drawer);
	        mDrawerLinearLayout=(LinearLayout)findViewById(R.id.drawer_lin_layout);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
	        LinearLayout action_lin_original=(LinearLayout)findViewById(R.id.action_bar_lin_layout_main_page);
			 getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			 getSupportActionBar().setCustomView(R.layout.custom_action_bar_main_page);
			 
	        // ActionBarDrawerToggle ties together the the proper interactions
	        // between the sliding drawer and the action bar app icon
	        /*android.support.v7.app.ActionBar action_bar=getSupportActionBar();
	        action_bar.setDisplayShowTitleEnabled(false);
	        action_bar.setDisplayUseLogoEnabled(false);
	        action_bar.setDisplayHomeAsUpEnabled(false);
	        action_bar.setDisplayShowCustomEnabled(true);
	        action_bar.setDisplayShowHomeEnabled(false);
	        action_bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	        ViewGroup cView = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_action_bar, null);
	        action_bar.setCustomView(cView);*/
	        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description for accessibility */
	                R.string.drawer_close  /* "close drawer" description for accessibility */
	                ) {
	            public void onDrawerClosed(View view) {
	                getActionBar().setTitle("TheShopWiz");
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }

	            public void onDrawerOpened(View drawerView) {
	                getActionBar().setTitle("Options");
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);

	       

	        // set a custom shadow that overlays the main content when the drawer opens
	        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	        // set up the drawer's list view with items and click listener
	        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.drawer_list_item, mDrawerTitles));
	        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	        
	/*	int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if(currentapiVersion >= 21){
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(this.getResources().getColor(R.color.burntmaroon));
		}
		*/
		isNetworkActiveBool=false;
		Check_Internet ci=new Check_Internet();
		ci.execute();
		current_loc_address=null;
		 handler = new Handler();
		timer = new Timer();
		timer.schedule(doAsynchronousTask, 0, 20000); 
        addresses=new ArrayList<Address>();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		provider = locationManager.getBestProvider(new Criteria(), false);
		loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		ImageView item_bt=(ImageView) findViewById(R.id.item);
		//Button curr_loc_bt=(Button) findViewById(R.id.your_loc_butt);
		
		/*curr_loc_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(current_loc_address!=null){
				//	flag=true;
				//atvPlaces.setText(current_loc_address);
			//	}else{
					flag=false;
					request_location runner = new request_location();
				    runner.execute();
			//	}
			}
		});*/
		ImageView shop_bt=(ImageView) findViewById(R.id.shop);
		atvPlaces = (AutoCompleteTextView) findViewById(R.id.atv_places);
		
		atvPlaces.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub	
				flag=false;
				if(isNetworkActiveBool){
				placesTask = new PlacesTask();	
				System.out.println("text_change_detected");
				placesTask.execute(s.toString());
				updateLocation(s.toString());
				}
				else{
					if(toast_one!=null){
						toast_one.cancel();
					}
					toast_one=Toast.makeText(heart_activity.this, "Check Internet connection", Toast.LENGTH_SHORT);
					toast_one.show();
				}
				
				
			//loc.setLatitude(g.getLatitudeE6()/1E6);
			//loc.setLongitude(g.getLongitudeE6()/1E6);
			//Toast.makeText(heart_activity.this, "lat is "+Double.toString(loc.getLatitude())+" "+"lon is "+Double.toString(loc.getLongitude()), Toast.LENGTH_LONG).show();
			}
		});	
   
		item_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isNetworkActiveBool){
			//	if(flag){	
			/*Intent intent = new Intent(getBaseContext(), second_activity.class);
			intent.putExtra("ButtonClicked", "Item");
			intent.putExtra("Location",loc);*/
			
			Intent intent = new Intent(getBaseContext(), ItemPageClicked.class);
			//intent.putExtra("ButtonClicked", "Item");
			intent.putExtra("Location",loc);
			
			startActivity(intent);
			//	}
				/*else{
					if(toast_one!=null){
						toast_one.cancel();
					}
					toast_one=Toast.makeText(heart_activity.this, "Enter Location", Toast.LENGTH_SHORT);
					toast_one.show();
				}*/
				}
				else{
					if(toast_one!=null){
						toast_one.cancel();
					}
					toast_one=	Toast.makeText(heart_activity.this, "Check Internet connection",Toast.LENGTH_SHORT);
					toast_one.show();
				}
			}
		});
		shop_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNetworkActiveBool){
				//	if(flag){
				Intent intent = new Intent(getBaseContext(), ShopPageClicked.class);
				//intent.putExtra("ButtonClicked", "Shop");
				intent.putExtra("Location",loc);
				startActivity(intent);
				//	}
					/*else{
						if(toast_one!=null){
							toast_one.cancel();
						}
						toast_one=Toast.makeText(heart_activity.this, "Enter Location", Toast.LENGTH_SHORT);
						toast_one.show();
						
					}*/
				}
				else{
					if(toast_one!=null){
						toast_one.cancel();
					}
					toast_one=Toast.makeText(heart_activity.this, "Check Internet connection", Toast.LENGTH_SHORT);
					toast_one.show();
					
				}
			}
		});
		listener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onLocationChanged(Location location) {
				loc = location;
				Geocoder geocoder = new Geocoder(heart_activity.this, Locale.getDefault());
				flag=true;
				StringBuilder result = new StringBuilder();
				try {
					addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
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
							current_loc_address=result;
							atvPlaces.setText(result);
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		};	
	
	    request_location runner = new request_location();
	    runner.execute();
		
	   

	}
	
protected void updateLocation(String strAddress) {
		// TODO Auto-generated method stub
	//Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		Geocoder coder = new Geocoder(this);
		List<Address> address;

		try {
		    address = coder.getFromLocationName(strAddress,5);
		    if (address == null) {
		       // return null;
		    	flag=false;
		    }
		   
		    Address location = address.get(0);
		  //  location.getLatitude();
		    //location.getLongitude();
		    if(location.getLatitude()!=0){
		    	 flag=true;
		    }
		    loc.setLatitude(location.getLatitude());
		    loc.setLongitude(location.getLongitude());
		 //   p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
		   //                   (int) (location.getLongitude() * 1E6));

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	   // return p1;

	}

public class request_location extends AsyncTask <Void,Void,Void>{

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		locationManager.removeUpdates(listener);
		d1.dismiss();
		if(addresses.size()==0){
			if(toast_one!=null){
				toast_one.cancel();
			}
			toast_one=Toast.makeText(heart_activity.this, "Not getting your Location", Toast.LENGTH_LONG);
			toast_one.show();
		}
		super.onPostExecute(result);
		
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		builder=new AlertDialog.Builder (heart_activity.this);
		//dialog = builder.create();
		d1=new Dialog(heart_activity.this);
		d1.setTitle("Please wait");
		d1.setCancelable(false);
		d1.show();
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000000, 1000, listener);

		
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
/*public static ArrayList<String> autocomplete(String input) {
    ArrayList<String> resultList = null;

    HttpURLConnection conn = null;
    StringBuilder jsonResults = new StringBuilder();
    try {
        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
        sb.append("?key=" + API_KEY);
        sb.append("&components;=country:uk");
        sb.append("&input;=" + URLEncoder.encode(input, "utf8"));

        URL url = new URL(sb.toString());
        conn = (HttpURLConnection) url.openConnection();
        InputStreamReader in = new InputStreamReader(conn.getInputStream());

        // Load the results into a StringBuilder
        int read;
        char[] buff = new char[1024];
        while ((read = in.read(buff)) != -1) {
            jsonResults.append(buff, 0, read);
        }
    } catch (MalformedURLException e) {
        Log.e(LOG_TAG, "Error processing Places API URL", e);
        return resultList;
    } catch (IOException e) {
        Log.e(LOG_TAG, "Error connecting to Places API", e);
        return resultList;
    } finally {
        if (conn != null) {
            conn.disconnect();
        }
    }

    try {
        // Create a JSON object hierarchy from the results
        JSONObject jsonObj = new JSONObject(jsonResults.toString());
        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

        // Extract the Place descriptions from the results
        resultList = new ArrayList<String>(predsJsonArray.length());
        for (int i = 0; i < predsJsonArray.length(); i++) {
            resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
        }
    } catch (JSONException e) {
        Log.e(LOG_TAG, "Cannot process JSON results", e);
    }

    return resultList;
}*/
private String downloadUrl(String strUrl) throws IOException{
    String data = "";
    InputStream iStream = null;
    HttpURLConnection urlConnection = null;
    try{
            URL url = new URL(strUrl);                

            // Creating an http connection to communicate with url 
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url 
            urlConnection.connect();

            // Reading data from url 
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                    sb.append(line);
            }
            
            data = sb.toString();

            br.close();

    }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
    }finally{
            iStream.close();
            urlConnection.disconnect();
    }
    return data;
 }
//Fetches all places from GooglePlaces AutoComplete Web Service
	private class PlacesTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... place) {
			// For storing data from web service
			String data = "";
			
			// Obtain browser key from https://code.google.com/apis/console
			String key = "key=AIzaSyBhYZ2NpXYutenhpoLeiXMbQlaprUBQ61c";
			
			String input="";
			
			try {
				input = "input=" + URLEncoder.encode(place[0], "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}		
			
			
			// place type to be searched
			String types = "types=address";
			
			// Sensor enabled
			String sensor = "sensor=false";			
			
			// Building the parameters to the web service
			String parameters = input+"&"+types+"&"+sensor+"&"+key;
			
			// Output format
			String output = "json";
			
			// Building the url to the web service
			String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;
	
			try{
				// Fetching the data from web service in background
				data = downloadUrl(url);
				System.out.println(data);
			}catch(Exception e){
             Log.d("Background Task",e.toString());
			}
			return data;		
		}
		
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);
			
			// Creating ParserTask
			parserTask = new ParserTask();
			
			// Starting Parsing the JSON string returned by Web Service
			parserTask.execute(result);
		}		
	}
	/** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

    	JSONObject jObject;
    	
		@Override
		protected List<HashMap<String, String>> doInBackground(String... jsonData) {			
			
			List<HashMap<String, String>> places = null;
			
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
            
            try{
            	jObject = new JSONObject(jsonData[0]);
            	
            	// Getting the parsed data as a List construct
            	places = placeJsonParser.parse(jObject);

            }catch(Exception e){
            	Log.d("Exception",e.toString());
            }
            return places;
		}
		
		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {			
			
				String[] from = new String[] { "description"};
				int[] to = new int[] { android.R.id.text1 };
				
				// Creating a SimpleAdapter for the AutoCompleteTextView
				SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);				
				
				// Setting the adapter
				atvPlaces.setAdapter(adapter);
				
		}			
    } 
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    private boolean isActiveNetwork(){
    	
    	if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500); 
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("LOG_TAG_ERROR", "Error checking internet connection", e);
            }
        } else {
            Log.d("LOG_TAG", "No network available!");
        }
        return false;
    }
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	//TextView tv_name=(TextView)findViewById(R.id.user_name);
        	//tv_name.setText("Name");
        	Toast.makeText(heart_activity.this,"Clicked on " + mDrawerTitles[position], Toast.LENGTH_SHORT).show();
        	mDrawerLayout.closeDrawer(mDrawerLinearLayout);
        	 
        }
    }
    private class Check_Internet extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return isActiveNetwork();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			isNetworkActiveBool=result;
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
    	
    }
    TimerTask doAsynchronousTask = new TimerTask() {       
        @Override
        public void run() {
            handler.post(new Runnable() {
                @SuppressWarnings("unchecked")
                public void run() { 
                   try {
                        Check_Internet cv=new Check_Internet();
                        cv.execute();
                       }
                 catch (Exception e) {
                        // TODO Auto-generated catch block
                    }
                }
            });
        }
    };
  /*  public void locationClicked(View v){
    	flag=false;
		request_location runner = new request_location();
	    runner.execute();
    }*/
    @Override
    public boolean onKeyUp (int keyCode, KeyEvent event) { 
        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) { 
       Toast.makeText(heart_activity.this, "keyboard closed", Toast.LENGTH_SHORT).show();
                atvPlaces.clearFocus();
                        return true; 
               
       
        } 
        return super.dispatchKeyEvent(event);
} 
}
