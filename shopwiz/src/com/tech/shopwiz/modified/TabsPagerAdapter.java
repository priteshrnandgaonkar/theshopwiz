package com.tech.shopwiz.modified;
import com.tech.shopwiz.ItemFragmentFinal;
import com.tech.shopwiz.ShopFragment;
import com.tech.shopwiz.modified.ShopFragment2;
import android.location.Location;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
 
public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    public Fragment mFragmentAtPos0;
    private  FragmentManager mFragmentManager;

 @Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
	 super.destroyItem(container, position, object);
     FragmentManager manager = ((Fragment) object).getFragmentManager();
     FragmentTransaction trans = manager.beginTransaction();
     trans.remove((Fragment) object);
     trans.commit();
	}



Location loc;
    public TabsPagerAdapter(FragmentManager fm,Location _loc) {
        super(fm);
        this.mFragmentManager=fm;
        this.loc=_loc;
    }
    @Override 
    public void restoreState(Parcelable state, ClassLoader loader) {
        // do nothing here! no call to super.restoreState(state, loader);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
        	if (mFragmentAtPos0 == null)
             {
                 mFragmentAtPos0 = ItemFragmentFinal.newInstance(new TabsPagerAdapterListener()
                 {
					@Override
					public void onSwitchToNextFragment(String val, String gender, String cat, Location loc) {
						// TODO Auto-generated method stub
						 mFragmentManager.beginTransaction().remove(mFragmentAtPos0).commit();
                         mFragmentAtPos0 = ListviewViaItemSearch.newInstance(val,gender,cat,loc);
                         notifyDataSetChanged();
					}
                 });
             }
             return mFragmentAtPos0;
        	/*ItemFragmentFinal t=new ItemFragmentFinal();
			t.setLocation(loc);
            return t*/
        case 1:
            // Games fragment activity
           // return new GamesFragment();
        	ShopFragment3 s=new ShopFragment3();
        	//ItemFragmentFinal t1=new ItemFragmentFinal();
        	s.setLocation(loc);
            return s;
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof ItemFragmentFinal && mFragmentAtPos0 instanceof ListviewViaItemSearch)
            return POSITION_NONE;
        return POSITION_UNCHANGED;
    }
 
}
