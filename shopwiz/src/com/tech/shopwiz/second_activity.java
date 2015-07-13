package com.tech.shopwiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class second_activity extends FragmentActivity implements ActionBar.TabListener  {
	 private DrawerLayout mDrawerLayout;
	 private ListView mDrawerList;
	 private ActionBarDrawerToggle mDrawerToggle;
	 private String[] mDrawerTitles;
	 LinearLayout mDrawerLinearLayout;
	
	
	ActionBar action_bar;
    ViewPager viewpager;
    FragmentPageAdapter ft;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String value=null;
		Location loc = null;
		
		
		//setTitle("Details");
		if (extras != null) {
		    value = extras.getString("ButtonClicked");
		    loc=(Location) extras.get("Location");
		}
		setContentView(R.layout.second_page);
		
		mDrawerTitles = getResources().getStringArray(R.array.drawer_contents);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout2);
        mDrawerList = (ListView) findViewById(R.id.left_drawer2);
        mDrawerLinearLayout=(LinearLayout)findViewById(R.id.drawer_lin_layout2);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
       
        ActionBar actionBar = getActionBar();
       
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
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
		
		
		viewpager=(ViewPager) findViewById(R.id.swipe_viewpager);
		ft=new FragmentPageAdapter(getSupportFragmentManager(),loc);
		 action_bar=getActionBar();
		viewpager.setAdapter(ft);
		action_bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab item_tab=action_bar.newTab().setText("Item").setTabListener(this);
		action_bar.addTab(item_tab);
		Tab shop_tab=action_bar.newTab().setText("Shop").setTabListener(this);
		action_bar.addTab(shop_tab);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				action_bar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		 if(value.equalsIgnoreCase("Item")){
			   action_bar.selectTab(item_tab);
			   
		   }
		   else{
			   action_bar.selectTab(shop_tab);
		   }
		
		 Display display = getWindowManager().getDefaultDisplay();
		 Point size = new Point();
		 display.getSize(size);
		 int width = size.x;
		 int height = size.y;
		 //System.out.println("ht="+height);
		 //System.out.println("wd="+width);
		 /*action_bar.setDisplayShowTitleEnabled(false);
	        action_bar.setDisplayUseLogoEnabled(false);
	        action_bar.setDisplayHomeAsUpEnabled(false);
	        action_bar.setDisplayShowCustomEnabled(true);
	        action_bar.setDisplayShowHomeEnabled(false);
	        action_bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	        ViewGroup cView = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_action_bar, null);
	        action_bar.setCustomView(cView);*/
	        getWindow().
	        getDecorView().
	        setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); 
	}

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewpager.setCurrentItem(arg0.getPosition(),true);
		
	}
	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	  private class DrawerItemClickListener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	//TextView tv_name=(TextView)findViewById(R.id.user_name);
	        	//tv_name.setText("Name");
	        	Toast.makeText(second_activity.this,"Clicked on " + mDrawerTitles[position], Toast.LENGTH_SHORT).show();
	        	if(mDrawerTitles[position].equals("Home")){
	        		Intent intent = new Intent(getBaseContext(), heart_activity.class);
	        		startActivity(intent);
	        		//finish();
	        	}
	        	
	        	mDrawerLayout.closeDrawer(mDrawerLinearLayout);
	        	 
	        }
	    }

}
