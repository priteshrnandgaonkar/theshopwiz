package com.tech.shopwiz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class Home extends Activity 
{
    ExpandableListView explvlist;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataMultiHeader;
    HashMap<String,HashMap<String,List<String>>> listMultiDataChild;
  
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        prepareListData2();
        explvlist = (ExpandableListView)findViewById(R.id.ParentLevel);
        explvlist.setAdapter(new ParentLevel(listDataHeader,listDataChild,listDataMultiHeader,listMultiDataChild));
        
    }
    
    public class ParentLevel extends BaseExpandableListAdapter
    {
    	List<String> listDataHeader;
        HashMap<String, List<String>> listDataChild;
        HashMap<String, List<String>> listDataMultiHeader;
        HashMap<String,HashMap<String,List<String>>> listMultiDataChild;
public ParentLevel(List<String> _listDataHeader,HashMap<String, List<String>> _listDataChild,HashMap<String, 
		List<String>>_listDataMultiHeader, HashMap<String,HashMap<String,List<String>>> _listMultiDataChild){
	this.listDataHeader=_listDataHeader;
	this.listDataChild=_listDataChild;
	this.listDataMultiHeader=_listDataMultiHeader;
	this.listMultiDataChild=_listMultiDataChild;
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
   String header= listDataMultiHeader.get(listDataHeader.get(groupPosition)).get(childPosition);
   ArrayList<String> grandchildData=new ArrayList<String>();
   grandchildData=(ArrayList<String>) ((listMultiDataChild.get(listDataHeader.get(groupPosition))).get(header));
   if (convertView == null) {
       LayoutInflater infalInflater = (LayoutInflater) Home.this
               .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       convertView = infalInflater.inflate(R.layout.expandable_list_item_multi, null);
   }
   //ExpandableListView ev=(ExpandableListView) convertView.findViewById(R.id.lvExpMulti);
   CustExpListview SecondLevelexplv = new CustExpListview(Home.this);
   SecondLevelexplv.setAdapter(new SecondLevelAdapter(header,grandchildData));
   //ev.setAdapter(new SecondLevelAdapter(header,grandchildData));
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
          LayoutInflater infalInflater = (LayoutInflater) Home.this
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          convertView = infalInflater.inflate(R.layout.item_group, null);
      }

		 LinearLayout lin_layout=(LinearLayout) convertView.findViewById(R.id.group_name);
		 ImageView im=(ImageView) lin_layout.findViewById(R.id.imageView1);
		 WindowManager wm = (WindowManager) Home.this.getSystemService(Context.WINDOW_SERVICE);
		 Display display = wm.getDefaultDisplay();
		 Point size = new Point();
		 display.getSize(size);
		 int width = size.x;
		 int height = size.y;
		 //im.getLayoutParams().height=(int)(height/5+50); 
		 if(headerTitle.equals("Men")){
				//lin_layout.setBackgroundResource(R.drawable.menclothing);
			 
			 //im.setImageResource(R.drawable.menclothing);
			 Bitmap icon = BitmapFactory.decodeResource(Home.this.getResources(),
					 R.drawable.men);
			// Bitmap croppedBitmap = Bitmap.createBitmap(icon, 20, 70,width, height/5+50);
			 Bitmap croppedBitmap = Bitmap.createBitmap(icon, (int)width/4 , (int)height/13 ,width, height/4);
			 im.setImageBitmap(croppedBitmap);
			 //im.setBackgroundResource(R.drawable.men_1);
		 }
			else if(headerTitle.equals("Women")){
				//lin_layout.setBackgroundResource(R.drawable.femaleclothing);
				im.setBackgroundResource(R.drawable.women_1);
				//im.setImageResource(R.drawable.ic_launcher);
				Bitmap icon = BitmapFactory.decodeResource(Home.this.getResources(),
						 R.drawable.women);
				 //Bitmap croppedBitmap = Bitmap.createBitmap(icon, 20, 30,width, height/5+50);
				Bitmap croppedBitmap = Bitmap.createBitmap(icon, (int)width/4 , (int)height/17 ,width, height/4); 
				im.setImageBitmap(croppedBitmap);

			}
			else{
				//lin_layout.setBackgroundResource(R.drawable.kidsclothing);
				//im.setBackgroundResource(R.drawable.kids_1);
				//im.setImageResource(R.drawable.ic_launcher);
				Log.v("in grp", "working");
				Bitmap icon = BitmapFactory.decodeResource(Home.this.getResources(),
						 R.drawable.kids);
				 //Bitmap croppedBitmap = Bitmap.createBitmap(icon, 50, 150,width, height/5+50);
				Bitmap croppedBitmap = Bitmap.createBitmap(icon, (int)width/4 , (int)height/6 ,width, height/4); 
				im.setImageBitmap(croppedBitmap);
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
  
  int intGroupPosition, intChildPosition, intGroupid;
  
  public CustExpListview(Context context) 
  {
   super(context);     
  }
    
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
  {
	  WindowManager wm = (WindowManager) Home.this.getSystemService(Context.WINDOW_SERVICE);
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
    	public SecondLevelAdapter(String _header,ArrayList<String> _grandchildData){
    		this.header=_header;
    		this.grandchildData=_grandchildData;
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
          LayoutInflater infalInflater = (LayoutInflater) Home.this
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
          LayoutInflater infalInflater = (LayoutInflater) Home.this
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
    	
    	cloth_listWomen.add("Topwear");
    	cloth_listWomen.add("Bottomwear");
    	cloth_listWomen.add("Ethnic wear");
    	cloth_listWomen.add("Winter wear");
    	cloth_listWomen.add("Sportswear");
    	
    	cloth_listKid.add("boys clothing");
    	cloth_listKid.add("girls clothing");
    	
    	listDataMultiHeader.put(listDataHeader.get(0), cloth_listMen);
    	listDataMultiHeader.put(listDataHeader.get(1), cloth_listWomen);
    	listDataMultiHeader.put(listDataHeader.get(2), cloth_listKid);
    	HashMap <String , List<String>> men_tmp=new HashMap<String,List<String>>();
    	ArrayList<String> grandchild=new ArrayList<String>();
    	grandchild.add("1");
    	grandchild.add("2");
    	men_tmp.put("Topwear",grandchild);
    	men_tmp.put("Bottomwear",grandchild);
    	men_tmp.put("Inner & Sleeperwear",grandchild);
    	men_tmp.put("Sportswear",grandchild);
    	listMultiDataChild.put(listDataHeader.get(0),men_tmp);
    	HashMap <String , List<String>> women_tmp=new HashMap<String,List<String>>();
    	women_tmp.put("Topwear",grandchild);
    	women_tmp.put("Bottomwear",grandchild);
    	women_tmp.put("Ethnic wear",grandchild);
    	women_tmp.put("Winter wear",grandchild);
    	women_tmp.put("Sportswear",grandchild);
    	listMultiDataChild.put(listDataHeader.get(1),women_tmp);
    	HashMap <String , List<String>> kid_tmp=new HashMap<String,List<String>>();
    	kid_tmp.put("boys clothing",grandchild);
    	kid_tmp.put("girls clothing",grandchild);
    	listMultiDataChild.put(listDataHeader.get(2),kid_tmp);    	
    }
}