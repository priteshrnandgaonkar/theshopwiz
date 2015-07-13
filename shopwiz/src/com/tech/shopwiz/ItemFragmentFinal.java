package com.tech.shopwiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mikhaellopez.circularimageview.R.color;
import com.tech.shopwiz.Home.CustExpListview;
import com.tech.shopwiz.Home.ParentLevel;
import com.tech.shopwiz.Home.SecondLevelAdapter;
import com.tech.shopwiz.modified.ListviewViaItemSearch;
import com.tech.shopwiz.modified.TabsFragment;
import com.tech.shopwiz.modified.TabsPagerAdapterListener;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ItemFragmentFinal extends Fragment{
	ExpandableListView explvlist;
    List<String> listDataHeader;
    static TabsPagerAdapterListener FragmentListener;
    HashMap<String, List<String>> listDataChild; //parent and child map
	HashMap<String, List<String>> listDataMultiHeader;
    HashMap<String,HashMap<String,List<String>>> listMultiDataChild;
    Location loc=null;
	public void setLocation(Location _loc){
		this.loc=_loc;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.main, null);
		Toast.makeText(getActivity(), "itemfragment", Toast.LENGTH_SHORT).show();
		explvlist = (ExpandableListView) rootView.findViewById(R.id.lvExp);
		 prepareListData2();	 
	     //listAdapter = new ExpandableListAdapterModified(getActivity(), listDataHeader, listDataChild);
		 explvlist = (ExpandableListView)rootView.findViewById(R.id.ParentLevel);
	        explvlist.setAdapter(new ParentLevel(getActivity(),listDataHeader,listDataChild,listDataMultiHeader,listMultiDataChild));
		 return rootView;
		 
	}
	 public class ParentLevel extends BaseExpandableListAdapter 
	    {	Context context;
	    	List<String> listDataHeader;
	        HashMap<String, List<String>> listDataChild;
	        HashMap<String, List<String>> listDataMultiHeader;
	        HashMap<String,HashMap<String,List<String>>> listMultiDataChild;
	public ParentLevel(Context _context,List<String> _listDataHeader,HashMap<String, List<String>> _listDataChild,HashMap<String, 
			List<String>>_listDataMultiHeader, HashMap<String,HashMap<String,List<String>>> _listMultiDataChild){
		this.context=_context;
		this.listDataHeader=_listDataHeader;
		this.listDataChild=_listDataChild;
		this.listDataMultiHeader=_listDataMultiHeader;
		this.listMultiDataChild=_listMultiDataChild;
      //  Toast.makeText(this.context, "lat is "+Double.toString(loc.getLatitude())+" "+"lon is "+Double.toString(loc.getLongitude()), Toast.LENGTH_LONG).show();

	}
	  @Override
	  public Object getChild(int arg0, int arg1) 
	  {   
	   return arg1;
	  }

	  @Override
	  public long getChildId(int groupPosition, int childPosition) 
	  {
	   return childPosition;
	  }

	  @Override
	  public View getChildView(int groupPosition, int childPosition,
	    boolean isLastChild, View convertView, ViewGroup parent) 
	  {
	   final String grandparentheader=listDataHeader.get(groupPosition);
	   final String header= listDataMultiHeader.get(listDataHeader.get(groupPosition)).get(childPosition);
	   //final ArrayList<String> grandchildData=new ArrayList<String>();
	   final ArrayList<String> grandchildData=(ArrayList<String>) ((listMultiDataChild.get(listDataHeader.get(groupPosition))).get(header));
	   if (convertView == null) {
	       LayoutInflater infalInflater = (LayoutInflater) context
	               .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	       convertView = infalInflater.inflate(R.layout.expandable_list_item_multi, null);
	   }
	   //ExpandableListView ev=(ExpandableListView) convertView.findViewById(R.id.lvExpMulti);
	   CustExpListview SecondLevelexplv = new CustExpListview(context);
	   SecondLevelexplv.setAdapter(new SecondLevelAdapter(header,grandchildData,grandparentheader));
	   //ev.setAdapter(new SecondLevelAdapter(header,grandchildData));
	   SecondLevelexplv.setOnChildClickListener(new OnChildClickListener() {
		
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			// Toast.makeText(context, "clicked on"+grandchildData.get(childPosition)+"in "+header+"in "+grandparentheader, Toast.LENGTH_LONG).show();
			 /*Intent intent = new Intent(context, ListviewProductsViaItemSearch.class);
				intent.putExtra("Gender and category", grandparentheader+" "+grandchildData.get(childPosition));
				intent.putExtra("Gender", grandparentheader);
				intent.putExtra("Category", grandchildData.get(childPosition));
				intent.putExtra("Location", loc);
				startActivity(intent);
				*/
			FragmentListener.onSwitchToNextFragment(grandparentheader+" "+grandchildData.get(childPosition),grandparentheader,grandchildData.get(childPosition),loc);
				/*Fragment fragment = new ListviewViaItemSearch(grandparentheader+" "+grandchildData.get(childPosition),grandparentheader,"products",loc);
				FragmentManager fragmentManager = getChildFragmentManager();
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				transaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame));
				//transaction.replace(R.id.content_frame, fragment);
				transaction.add(R.id.content_frame, fragment);
				transaction.addToBackStack(null);
				transaction.show(fragment);
				transaction.commit();
				//fragmentManager.executePendingTransactions();*/
			 return true;
		}
	});
	   return SecondLevelexplv;
	  }

	  @Override
	  public int getChildrenCount(int groupPosition) 
	  {   
	   return listDataMultiHeader.get(listDataHeader.get(groupPosition)).size();
	  }

	  @Override
	  public Object getGroup(int groupPosition) 
	  {
	   return listDataHeader.get(groupPosition);
	  }

	  @Override
	  public int getGroupCount() 
	  {   
	   return listDataHeader.size();
	  }

	  @Override
	  public long getGroupId(int groupPosition) 
	  {   
	   return groupPosition;
	  }

	   @Override
	  public View getGroupView(int groupPosition, boolean isExpanded,
	    View convertView, ViewGroup parent) 
	  {
	  // TextView tv = new TextView(Home.this);
	  // tv.setText("->FirstLevel");
	  // tv.setBackgroundColor(Color.BLUE);
	  // tv.setPadding(10, 7, 7, 7); 
		  String headerTitle = (String) getGroup(groupPosition);
	      if (convertView == null) {
	          LayoutInflater infalInflater = (LayoutInflater) context
	                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	          convertView = infalInflater.inflate(R.layout.item_group, null);
	      }

			 LinearLayout lin_layout=(LinearLayout) convertView.findViewById(R.id.group_name);
			 ImageView im=(ImageView) lin_layout.findViewById(R.id.imageView1);
			 WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			 Display display = wm.getDefaultDisplay();
			 Point size = new Point();
			 display.getSize(size);
			 int width = size.x;
			 int height = size.y;
			 //im.getLayoutParams().height=(int)(height/5+50); 
			 if(headerTitle.equals("Men")){
					//lin_layout.setBackgroundResource(R.drawable.menclothing);
				 
				 //im.setImageResource(R.drawable.menclothing);
				 Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
						 R.drawable.nobg_men);
				 int icon_width=icon.getWidth();
				 int icon_height=icon.getHeight();
				 
				 int intended_height=(int)Math.round((width*icon_height)/(icon_width));
				 
				 Bitmap icon_new=Bitmap.createScaledBitmap(icon, width , intended_height ,false);
				 System.out.println(width);
				 System.out.println(intended_height);
				// Bitmap croppedBitmap = Bitmap.createBitmap(icon, 20, 70,width, height/5+50);
				 Bitmap croppedBitmap = Bitmap.createBitmap(icon_new, (int)(width/4) , (int)height/13 ,(int)(0.75*width),(int) height/4);
				 im.setImageBitmap(croppedBitmap);
				// im.setBackgroundColor(color.green);
				 //im.setBackgroundResource(R.drawable.men_1);
			 }
				else if(headerTitle.equals("Women")){
					//lin_layout.setBackgroundResource(R.drawable.femaleclothing);
					//im.setBackgroundResource(R.drawable.women_1);
					//im.setImageResource(R.drawable.ic_launcher);
					Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
							 R.drawable.women);
					int icon_width=icon.getWidth();
					 int icon_height=icon.getHeight();
					 
					 int intended_height=(int)Math.round((width*icon_height)/(icon_width));
					 
					 Bitmap icon_new=Bitmap.createScaledBitmap(icon, width , intended_height ,false);
					 System.out.println(width);
					 System.out.println(intended_height);
					 //Bitmap croppedBitmap = Bitmap.createBitmap(icon, 20, 30,width, height/5+50);
					Bitmap croppedBitmap = Bitmap.createBitmap(icon_new, (int)width/4 , (int)height/17 ,(int)(0.75*width), height/4); 
					im.setImageBitmap(croppedBitmap);
				//	im.setBackgroundColor(color.maroon);

				}
				else{
					//lin_layout.setBackgroundResource(R.drawable.kidsclothing);
					//im.setBackgroundResource(R.drawable.kids_1);
					//im.setImageResource(R.drawable.ic_launcher);
					Log.v("in grp", "working");
					Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
							 R.drawable.nobg_kids);
					int icon_width=icon.getWidth();
					 int icon_height=icon.getHeight();
					 
					 int intended_height=(int)Math.round((width*icon_height)/(icon_width));
					 
					 Bitmap icon_new=Bitmap.createScaledBitmap(icon, width , intended_height ,false);
					 System.out.println(width);
					 System.out.println(intended_height);
					 //Bitmap croppedBitmap = Bitmap.createBitmap(icon, 50, 150,width, height/5+50);
					Bitmap croppedBitmap = Bitmap.createBitmap(icon_new, (int)width/4 , (int)height/6 ,(int)(0.75*width), height/4); 
					im.setImageBitmap(croppedBitmap);
				//	im.setBackgroundColor(color.blue);
				}
	   return convertView;
	  }

	  @Override
	  public boolean hasStableIds() 
	  {
	   return true;
	  }

	  @Override
	  public boolean isChildSelectable(int groupPosition, int childPosition) 
	  {
	   return true;
	  }     
	    }
	    
	    public class CustExpListview extends ExpandableListView
	    {
	  
	  @Override
			public void setOnChildClickListener(
					OnChildClickListener onChildClickListener) {
				// TODO Auto-generated method stub
				super.setOnChildClickListener(onChildClickListener);
			}

	int intGroupPosition, intChildPosition, intGroupid;
	 // Context context;
	  public CustExpListview(Context context) 
	  {
		 // this.context=context;
	   super(context);     
	  }
	    
	  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	  {
		  WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
			 Display display = wm.getDefaultDisplay();
			 Point size = new Point();
			 display.getSize(size);
			 int width = size.x;
			 int height = size.y;
	   widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
	   heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
	   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	  }  
	    }
	    
	    public class SecondLevelAdapter extends BaseExpandableListAdapter
	    { 
	    	ArrayList<String> grandchildData;
	    	String header;
	    	String grandparentheader;
	    	public SecondLevelAdapter(String _header,ArrayList<String> _grandchildData,String _grandparentheader){
	    		this.header=_header;
	    		this.grandchildData=_grandchildData;
	    		this.grandparentheader=_grandparentheader;
	    	}

	  @Override
	  public Object getChild(int groupPosition, int childPosition) 
	  {   
	   return grandchildData.get(childPosition);
	  }

	  @Override
	  public long getChildId(int groupPosition, int childPosition) 
	  {   
	   return childPosition;
	  }

	  @Override
	  public View getChildView(int groupPosition, int childPosition,
	    boolean isLastChild, View convertView, ViewGroup parent) 
	  {
		  if (convertView == null) {
	          LayoutInflater infalInflater = (LayoutInflater) getActivity()
	                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	          convertView = infalInflater.inflate(R.layout.expandable_list_item, null);
	      }
	      	
	      TextView txtListChild = (TextView) convertView
	              .findViewById(R.id.lblListItem);
	   //TextView tv = new TextView(Home.this);
	   //tv.setText(grandchildData.get(childPosition));
	   txtListChild.setText(grandchildData.get(childPosition));
	   //tv.setPadding(15, 5, 5, 5);
	   //tv.setBackgroundColor(Color.YELLOW);
	   //tv.setLayoutParams(new ListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	   return convertView;
	  }

	  @Override
	  public int getChildrenCount(int groupPosition) 
	  {
	   return grandchildData.size();
	  }

	  @Override
	  public Object getGroup(int groupPosition) 
	  {   
	   return header;
	  }

	  @Override
	  public int getGroupCount() 
	  {
	   return 1;
	  }

	  @Override
	  public long getGroupId(int groupPosition) 
	  {
	   return groupPosition;
	  }

	  @Override
	  public View getGroupView(int groupPosition, boolean isExpanded,
	    View convertView, ViewGroup parent) 
	  {
		  if (convertView == null) {
	          LayoutInflater infalInflater = (LayoutInflater) getActivity()
	                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	          convertView = infalInflater.inflate(R.layout.expandable_list_item, null);
	      }
	      	
	      TextView txtListChild = (TextView) convertView
	              .findViewById(R.id.lblListItem);
	   //TextView tv = new TextView(Home.this);
	   //tv.setText(grandchildData.get(childPosition));
	   txtListChild.setText(header);
	   return convertView;
	  }

	  @Override
	  public boolean hasStableIds() {
	   // TODO Auto-generated method stub
	   return true;
	  }

	  @Override
	  public boolean isChildSelectable(int groupPosition, int childPosition) {
	   // TODO Auto-generated method stub
	   return true;
	  }
	     
	    }
	    private void prepareListData2() {
	    	// TODO Auto-generated method stub
	    	listMultiDataChild= new HashMap<String,HashMap<String,List<String>>>();
	    	listDataHeader=new ArrayList<String>();
	    	listDataMultiHeader = new HashMap<String, List<String>>();
	    	listDataHeader.add("Men");
	    	listDataHeader.add("Women");
	    	listDataHeader.add("Kids");
	    	
	    	ArrayList <String> cloth_listMen=new ArrayList<String>();
	    	ArrayList <String> cloth_listWomen=new ArrayList<String>();
	    	ArrayList <String> cloth_listKid=new ArrayList<String>();
	    	
	    	cloth_listMen.add("Topwear");
	    	cloth_listMen.add("Bottomwear");
	    	cloth_listMen.add("Inner & Sleeperwear");
	    	cloth_listMen.add("Sportswear");
	    	cloth_listMen.add("Others");
	    	
	    	cloth_listWomen.add("Topwear");
	    	cloth_listWomen.add("Bottomwear");
	    	cloth_listWomen.add("Ethnic wear");
	    	cloth_listWomen.add("Winter wear");
	    	cloth_listWomen.add("Sportswear");
	    	cloth_listWomen.add("Others");
	    	
	    	cloth_listKid.add("Topwear");
	    	cloth_listKid.add("Bottomwear");
	    	cloth_listKid.add("Inner & Sleeperwear");
	    	cloth_listKid.add("Sportswear");
	    	cloth_listKid.add("Others");
	    	
	    	listDataMultiHeader.put(listDataHeader.get(0), cloth_listMen);
	    	listDataMultiHeader.put(listDataHeader.get(1), cloth_listWomen);
	    	listDataMultiHeader.put(listDataHeader.get(2), cloth_listKid);
	    	
	    	/////Mens data entry
	    	HashMap <String , List<String>> men_tmp=new HashMap<String,List<String>>();
	    	ArrayList<String> grandchild=new ArrayList<String>();
	    	ArrayList<String> grandchild_men_topwear=new ArrayList<String>();
	    	grandchild_men_topwear.add("T-Shirt");
	    	grandchild_men_topwear.add("Casual Shirt");
	    	grandchild_men_topwear.add("Formal Shirt");
	    	grandchild_men_topwear.add("Jackets");
	    	grandchild.add("2");
	    	men_tmp.put("Topwear",grandchild_men_topwear);
	    	ArrayList<String> grandchild_men_bottomwear=new ArrayList<String>();
	    	grandchild_men_bottomwear.add("Chinos and Trousers");
	    	grandchild_men_bottomwear.add("Jeans");
	    	grandchild_men_bottomwear.add("Formal Trouser");
	    	grandchild_men_bottomwear.add("Shorts");
	    	men_tmp.put("Bottomwear",grandchild_men_bottomwear);
	    	ArrayList<String> grandchild_men_innerwear=new ArrayList<String>();
	    	grandchild_men_innerwear.add("Inners");
	    	men_tmp.put("Inner & Sleeperwear",grandchild_men_innerwear);
	    	ArrayList<String> grandchild_men_sportswear=new ArrayList<String>();
	    	grandchild_men_sportswear.add("Tracksuits");
	    	grandchild_men_sportswear.add("Swimwear");
	    	men_tmp.put("Sportswear",grandchild_men_sportswear);
	    	ArrayList<String> grandchild_men_others=new ArrayList<String>();
	    	grandchild_men_others.add("Ethnic Wear");
	    	grandchild_men_others.add("Sweaters");
	    	grandchild_men_others.add("Gloves");
	    	men_tmp.put("Others",grandchild_men_others);
	    	listMultiDataChild.put(listDataHeader.get(0),men_tmp);
	    	
	    	///////////////////////Women data entry
	    	HashMap <String , List<String>> women_tmp=new HashMap<String,List<String>>();
	    	ArrayList<String> grandchild_women_topwear=new ArrayList<String>();
	    	grandchild_women_topwear.add("T-Shirt");
	    	grandchild_women_topwear.add("Casual Shirt");
	    	grandchild_women_topwear.add("Formal Shirt");
	    	grandchild_women_topwear.add("Jackets");
	    	women_tmp.put("Topwear",grandchild_women_topwear);
	    	ArrayList<String> grandchild_women_bottomwear=new ArrayList<String>();
	    	grandchild_women_bottomwear.add("Chinos and Trousers");
	    	grandchild_women_bottomwear.add("Formal Trouser");
	    	grandchild_women_bottomwear.add("Jeans");
	    	grandchild_women_bottomwear.add("Shorts");
	    	women_tmp.put("Bottomwear",grandchild_women_bottomwear);
	    	ArrayList<String> grandchild_women_ethnicwear=new ArrayList<String>();
	    	grandchild_women_ethnicwear.add("Suit");
	    	grandchild_women_ethnicwear.add("Kurti");
	    	women_tmp.put("Ethnic wear",grandchild_women_ethnicwear);
	    	ArrayList<String> grandchild_women_winterwear=new ArrayList<String>();
	    	grandchild_women_winterwear.add("Sweaters");
	    	//grandchild_women_winterwear.add("Winter Gloves");
	    	women_tmp.put("Winter wear",grandchild_women_winterwear);
	    	ArrayList<String> grandchild_women_sportswear=new ArrayList<String>();
	    	grandchild_women_sportswear.add("Swimwear");
	    	grandchild_women_sportswear.add("Tracksuit");
	    	//grandchild_women_sportswear.add("Sports Gloves");
	    	women_tmp.put("Sportswear",grandchild_women_sportswear);
	    	ArrayList<String> grandchild_women_others=new ArrayList<String>();
	    	grandchild_women_others.add("Innerwear and Sleepwear");
	    	grandchild_women_others.add("Gloves");
	    	women_tmp.put("Others",grandchild_women_others);
	    	listMultiDataChild.put(listDataHeader.get(1),women_tmp);
	    	//////////////////////////////////
	    	HashMap <String , List<String>> kid_tmp=new HashMap<String,List<String>>();
	    	ArrayList<String> grandchild_kid_topwear=new ArrayList<String>();
	    	grandchild_kid_topwear.add("T-Shirt");
	    	grandchild_kid_topwear.add("Casual Shirt");
	    	grandchild_kid_topwear.add("Formal Shirt");
	    	grandchild_kid_topwear.add("Jackets");
	    	kid_tmp.put("Topwear",grandchild_kid_topwear);
	    	ArrayList<String> grandchild_kid_bottomwear=new ArrayList<String>();
	    	grandchild_kid_bottomwear.add("Chinos and Trousers");
	    	grandchild_kid_bottomwear.add("Jeans");
	    	grandchild_kid_bottomwear.add("Formal Trouser");
	    	grandchild_kid_bottomwear.add("Shorts");
	    	kid_tmp.put("Bottomwear",grandchild_kid_bottomwear);
	    	ArrayList<String> grandchild_kid_innerwear=new ArrayList<String>();
	    	grandchild_kid_innerwear.add("Inners");
	    	kid_tmp.put("Inner & Sleeperwear",grandchild_kid_innerwear);
	    	ArrayList<String> grandchild_kid_sportswear=new ArrayList<String>();
	    	grandchild_kid_sportswear.add("Tracksuits");
	    	grandchild_kid_sportswear.add("Swimwear");
	    	kid_tmp.put("Sportswear",grandchild_kid_sportswear);
	    	ArrayList<String> grandchild_kid_others=new ArrayList<String>();
	    	grandchild_kid_others.add("Ethnic Wear");
	    	grandchild_kid_others.add("Sweaters");
	    	grandchild_kid_others.add("Gloves");
	    	kid_tmp.put("Others",grandchild_kid_others);
	    	listMultiDataChild.put(listDataHeader.get(2),kid_tmp);
	    	
	    	
	    	/////////////////////////////////
	    	
	    }

		

		public static Fragment newInstance(TabsPagerAdapterListener firstPageFragmentListener) {
			// TODO Auto-generated method stub
			ItemFragmentFinal f=new ItemFragmentFinal();
		FragmentListener=firstPageFragmentListener;
			return f;
		}
	
	
}
