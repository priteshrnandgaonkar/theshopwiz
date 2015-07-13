package com.tech.shopwiz.modified;


import com.tech.shopwiz.FragmentPageAdapter;
import com.tech.shopwiz.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public  class TabsFragment extends Fragment implements TabListener {
   

	Activity c;
    ActionBar action_bar;
    TabsPagerAdapter tabs_adapter;
    Context myContext;
    String selected_cat;
    Location loc;
    ViewPager viewpager;
    public TabsFragment(Activity _c, String _selected_cat,Location _loc) {
        // Empty constructor required for fragment subclasses
    	this.c=_c;
    	this.selected_cat=_selected_cat;
    	this.action_bar=c.getActionBar();
    	this.loc=_loc;
    }

	@SuppressWarnings("deprecation")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.second_page_2, container, false);
    	viewpager=(ViewPager) rootView.findViewById(R.id.swipe_viewpager);
        tabs_adapter=new TabsPagerAdapter(((FragmentActivity) c).getSupportFragmentManager(),loc);
    	viewpager.setAdapter(tabs_adapter);
    	rootView.setFocusableInTouchMode(true);
    	rootView.requestFocus();
    	rootView.setOnKeyListener( new OnKeyListener()
    	{
    	    @Override
    	    public boolean onKey( View v, int keyCode, KeyEvent event )
    	    {
    	        if( keyCode == KeyEvent.KEYCODE_BACK )
    	        {getActivity().getFragmentManager().popBackStack();
    	            return true;
    	        }
    	        return false;
    	    }
    	} );
	
		
		action_bar=c.getActionBar();
        action_bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Toast.makeText(getActivity(), "tabcount="+action_bar.getTabCount(), Toast.LENGTH_SHORT).show();

        if(action_bar.getTabCount()>0){
        	action_bar.removeAllTabs();
        	for(int i=0;i<action_bar.getTabCount();++i){
        		Tab t=action_bar.getTabAt(i);
        		action_bar.removeTab(t);
        	}
        }
        Toast.makeText(getActivity(), "tabcount"+action_bar.getTabCount(), Toast.LENGTH_SHORT).show();
		Tab item_tab=action_bar.newTab().setText("Products").setTabListener(this);
		action_bar.addTab(item_tab);
		// RelativeLayout tabLayout = (RelativeLayout) item_tab.getCustomView();
		 //tabLayout.setBackgroundResource(R.drawable.tabs_selector_red);
		 //item_tab.setCustomView(tabLayout);
		//item_tab.setCustomView(R.layout.activity_main);
		Tab shop_tab=action_bar.newTab().setText("Shops").setTabListener(this);
		action_bar.addTab(shop_tab);
		  if(selected_cat.equals("products")){
	        	action_bar.selectTab(action_bar.getTabAt(0));
	        }
	        else{
	        	action_bar.selectTab(action_bar.getTabAt(1));
	        }
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
		
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        c=(FragmentActivity) activity;
        super.onAttach(activity);
    }
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewpager.setCurrentItem(arg0.getPosition(),true);		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
} 