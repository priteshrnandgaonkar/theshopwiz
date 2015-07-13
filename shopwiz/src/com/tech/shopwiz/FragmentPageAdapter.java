package com.tech.shopwiz;

import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter {
Location loc;
	public FragmentPageAdapter(FragmentManager fm,Location _loc) {
		super(fm);
		this.loc=_loc;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			ItemFragmentFinal t=new ItemFragmentFinal();
			t.setLocation(loc);
			return t;
			
		case 1:
			ShopFragment s=new ShopFragment();
			//s.setLocation(loc);
			return s;
			
		default:
			break;
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
