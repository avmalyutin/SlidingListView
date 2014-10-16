package com.example.testmalyutinoleksandr;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleListAdapter  extends ArrayAdapter<String> {
		  private final Context context;
		  private final String[] values;
		  
		  private final int[] counters;
		  
		  private static ArrayList<Drawable> icons;
		  private ArrayList<Drawable> bcgnd;

		  public SimpleListAdapter(Context context, String[] values, int [] counters1) {
		    super(context, R.layout.one_item, values);
		    this.context = context;
		    this.values = values;
		    this.counters = counters1;
		    if(SimpleListAdapter.icons == null)
		    	SimpleListAdapter.icons = DataLoader.getIconsRandomly(context);
		    this.bcgnd = DataLoader.getBackgrounds(context);		    
		  }
		  
		  static class ViewHolder {
		        public ImageView iconImageView;
		        public TextView titleTextView;
		        public TextView counterTextView;
		    }
		  
		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
			  
			  ViewHolder holder;
			  
			  if(convertView == null){
				  
				  LayoutInflater inflater = ((Activity) context).getLayoutInflater();
				  convertView = inflater.inflate(R.layout.one_item, parent, false);
				  
				  holder = new ViewHolder();
				  
				  holder.counterTextView = (TextView) convertView.findViewById(R.id.counterTextView);
				  holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
				  holder.titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
				  
				  convertView.setTag(holder);
				  
			  }
			  else{
				  holder = (ViewHolder) convertView.getTag();
			  }
			  
			  holder.iconImageView.setImageDrawable(icons.get(position));
			  convertView.setBackgroundDrawable(bcgnd.get(position));
			  holder.titleTextView.setText(values[position]);
			  holder.counterTextView.setText(counters[position] + "");
			  
			  return convertView;
		  
		  }
		} 
