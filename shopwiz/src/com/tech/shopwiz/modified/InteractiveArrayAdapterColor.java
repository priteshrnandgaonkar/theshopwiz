package com.tech.shopwiz.modified;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.tech.shopwiz.Model;
import com.tech.shopwiz.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class InteractiveArrayAdapterColor extends ArrayAdapter<Model> {

	  private List<Model> list;
	  private final Activity context;

	  public InteractiveArrayAdapterColor(Activity context, List<Model> list) {
	    super(context, R.layout.filter_listview_row, list);
	    this.context = context;
	    this.list = list;
	  }
	public List<Model> getListModels(){
		ArrayList<Model> tp=new ArrayList<Model> ();
		for(int i=0;i<list.size();++i){
			if(list.get(i).isSelected()){
				tp.add(list.get(i));
			}
		}
		return tp;
	}
	public List<Model> getWholeListModels(){
		return list;
	}
	public void setWholeListModels(ArrayList<Model> l){
		this.list=l;
	}
	  static class ViewHolder {
	    protected TextView text;
	    protected TextView textcolor;
	    protected CheckBox checkbox;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    View view = null;
	    if (convertView == null) {
	      LayoutInflater inflator = context.getLayoutInflater();
	      view = inflator.inflate(R.layout.filter_color_listview_row, parent,false);
	      final ViewHolder viewHolder = new ViewHolder();
	      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check_color);
	      viewHolder.text = (TextView) view.findViewById(R.id.label_color);
	      viewHolder.textcolor = (TextView) view.findViewById(R.id.color_display_txt);     
	      viewHolder.checkbox
	          .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	            @Override
	            public void onCheckedChanged(CompoundButton buttonView,
	                boolean isChecked) {
	              Model element = (Model) viewHolder.checkbox
	                  .getTag();
	              element.setSelected(buttonView.isChecked());

	            }
	          });
	      view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewHolder.checkbox.setChecked(!viewHolder.checkbox.isChecked());
			}
		});
	      view.setTag(viewHolder);
	      viewHolder.checkbox.setTag(list.get(position));
	    } else {
	      view = convertView;
	      ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
	    }
	    ViewHolder holder = (ViewHolder) view.getTag();
	    
	    
	    holder.text.setTextSize(5 * context.getResources().getDisplayMetrics().density);
	    holder.checkbox.setChecked(list.get(position).isSelected());
	    JSONArray object;
		try {
			object = new JSONArray (list.get(position).getName());
			final float scale = context.getResources().getDisplayMetrics().density;
			GradientDrawable gd = new GradientDrawable();
			 gd.setColor( Color.parseColor(object.getString(1))); // Changes this drawbale to use a single color instead of a gradient
		        gd.setCornerRadius((int)(2* scale + 0.5f));
		        gd.setStroke((int)(2* scale + 0.5f), Color.parseColor("#000000"));
		        holder.textcolor.setBackgroundDrawable(gd);
			
			holder.text.setText(object.getString(0));
			//holder.textcolor.setBackgroundColor(Color.parseColor(object.getString(1)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    return view;
	  }
	} 
