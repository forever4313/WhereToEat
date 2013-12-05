package com.example.sqlitedemo;

import com.example.sqlitedemo.bean.Restaurant;
import com.example.sqlitedemo.db.RestaurantTableHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddResActivity extends Activity{
	private EditText editText;
	private Button button;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_res);
		editText = (EditText)findViewById(R.id.res_name);
		button = (Button)findViewById(R.id.add);
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Restaurant.Data c = new Restaurant.Data();
				c.setId("1");//Ϊ���㣬��Զ����1
				c.setName(editText.getText().toString());
				c.setContent("����ľ͵���");				
				RestaurantTableHelper.saveRestaurant(AddResActivity.this, c);
			}
		});
	}
	
}
