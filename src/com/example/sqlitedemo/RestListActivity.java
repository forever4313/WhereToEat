package com.example.sqlitedemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.example.sqlitedemo.adapter.ResAdapter;
import com.example.sqlitedemo.bean.Restaurant;
import com.example.sqlitedemo.controller.AnimationController;
import com.example.sqlitedemo.db.RestaurantTableHelper;

public class RestListActivity extends Activity implements OnTouchListener{
	private ListView mListView;
	private List<Restaurant.Data> list = new ArrayList<Restaurant.Data>();
	private ResAdapter resAdapter;
	private static final int UPDATE_LIST = 1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		AnimationController animationController = new AnimationController();
		setContentView(R.layout.res_all);
		mListView = (ListView)findViewById(R.id.res_listView);
		resAdapter = new ResAdapter(this,list,animationController);
		mListView.setAdapter(resAdapter);
		mListView.setDividerHeight(0);
		mListView.setOnTouchListener(this);
		loadData();
	}
	
	/** 
     * ¶¯×÷Ö´ÐÐ 
     */  
    Handler handler = new Handler() {  
  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch (msg.what) {  
           
            case UPDATE_LIST:
            	if(msg.obj!=null){
            		System.out.println("resAdapter");
            		List<Restaurant.Data> list1 = (List<Restaurant.Data>) msg.obj;
            		list.addAll(list1);
            		System.out.println("resAdapter"+list.size());
            		resAdapter.notifyDataSetChanged();
            	}
            	
            	break;
            	
            }
        }  
  
    };  
    private void loadData(){
        new Thread(new Runnable() {
 
            @Override
            public void run() {                              
                Message msg = handler.obtainMessage(UPDATE_LIST);
                msg.obj = RestaurantTableHelper.getRestaurants(RestListActivity.this);;
                msg.sendToTarget();
            }
        }).start();
    }
    


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		float x = 0,y = 0,upx,upy;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {   
            x = event.getX();   
            y = event.getY();  
        }   
        if (event.getAction() == MotionEvent.ACTION_UP) {  
        	
            upx = event.getX();   
            upy = event.getY();
          
            int position1 = ((ListView) v).pointToPosition((int) x, (int) y);   
            int position2 = ((ListView) v).pointToPosition((int) upx,(int) upy);               
            int FirstVisiblePosition = mListView.getFirstVisiblePosition(); 
            System.out.println("---"+position1+"======"+position2);  
            if (position1 == position2 && Math.abs(x - upx) > 10) { 
                View view = ((ListView) v).getChildAt(position1);                   
                if (view == null) {                    
                 view = ((ListView) v).getChildAt(position1 - FirstVisiblePosition);  
                }                   
                removeListItem(view, position1);   
            }   
        }   
		return false;
	}
	
	protected void removeListItem(View rowView, final int positon) {          
        final AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
		alphaAnimation.setDuration(1000);	 
		alphaAnimation.setAnimationListener(new AnimationListener() {   
            public void onAnimationStart(Animation animation) {}   
            public void onAnimationRepeat(Animation animation) {}   
            public void onAnimationEnd(Animation animation) {   
                list.remove(positon);   
                resAdapter.notifyDataSetChanged();   
                animation.reset(); 
            }   
        });   
           
        rowView.startAnimation(alphaAnimation);   
    }  
	
}
