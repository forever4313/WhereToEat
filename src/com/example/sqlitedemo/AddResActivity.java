package com.example.sqlitedemo;

import com.example.sqlitedemo.bean.Restaurant;
import com.example.sqlitedemo.db.RestaurantTableHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddResActivity extends Activity{
	private EditText editText;
	private Button button;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_res);
		editText = (EditText)findViewById(R.id.res_name);
		button = (Button)findViewById(R.id.add);
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Restaurant.Data c = new Restaurant.Data();
				c.setId("1");//为方便，永远都是1
				c.setName(editText.getText().toString());
				c.setContent("想点哪就点哪");				
				RestaurantTableHelper.saveRestaurant(AddResActivity.this, c);
			}
		});
	}
	
}
