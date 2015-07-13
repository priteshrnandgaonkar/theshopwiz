package com.tech.shopwiz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


public class InteractiveArrayAdapter extends ArrayAdapter<Model> {

  private List<Model> list;
  private final Activity context;

  public InteractiveArrayAdapter(Activity context, List<Model> list) {
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
    protected CheckBox checkbox;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = null;
    if (convertView == null) {
      LayoutInflater inflator = context.getLayoutInflater();
      view = inflator.inflate(R.layout.filter_listview_row, parent,false);
      final ViewHolder viewHolder = new ViewHolder();
      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
      viewHolder.text = (TextView) view.findViewById(R.id.label);
      
      
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
    holder.text.setText(list.get(position).getName());
    holder.text.setTextSize(5 * context.getResources().getDisplayMetrics().density);
    holder.checkbox.setChecked(list.get(position).isSelected());
    return view;
  }
} 