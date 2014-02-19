package com.example.sqlitedemo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.bean.Restaurant;
import com.example.sqlitedemo.controller.AnimationController;

public class ResAdapter extends BaseAdapter{
	 private List<Restaurant.Data> List;
	 private LayoutInflater mInflater;
	 private Context mContext;
	 public ResAdapter(){}
	 private AnimationController animationController;
	 
	 public ResAdapter(Context context, List<Restaurant.Data> mData,AnimationController animationController){   
         this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
         this.List = mData;   
         mContext = context;   
         this.animationController = animationController;
     }
	 public ResAdapter(Context context, List<Restaurant.Data> mData){   
         this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
         this.List = mData;   
         mContext = context;   
     }
	 @Override
     public int getCount() {
         return List.size();
     }

     @Override
     public Object getItem(int position) {
         return List.get(position);
     }

     @Override
     public long getItemId(int position) {
         return position;
     }
     public String getWeiBoID(int position){
    	 Restaurant.Data temp = List.get(position);    	  
		 return temp.getId();
   	  
     }
     
     @Override
 	 public View getView(int position, View convertView, ViewGroup parent) {
 		// TODO Auto-generated method stub
 		if(convertView==null){
 			convertView=mInflater.inflate(R.layout.res_item, null);           
 	    }
 		ResHolder rh = new ResHolder();
 		rh.id = (TextView)convertView.findViewById(R.id.res_id); 		
 		rh.name = (TextView)convertView.findViewById(R.id.res_name);
 		Restaurant.Data resD = List.get(position);
 		rh.id.setText(position+1+"");
 		rh.name.setText(resD.getName());
 		long durationMillis = 800, delayMillis = 0;
		animationController.slideFadeIn(convertView, durationMillis, delayMillis);
 		return convertView;
 	}
 	class  ResHolder{
 		private TextView id;
 		private TextView name;
 		
 		
 	}
}
