package com.example.sqlitedemo;

import com.example.sqlitedemo.db.RestaurantTableHelper;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView textView;
	private SensorManager sensorManager;  
    private Vibrator vibrator;  
    private static final int SENSOR_SHAKE = 10;  
    private static final String TAG = "TestSensorActivity"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);  
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);  
        textView = (TextView)findViewById(R.id.text);
		//RestaurantTableHelper.getRestaurants(MainActivity.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		 menu.add(0, 1, 1, "添加");
	     menu.add(0, 2, 2, "编辑");
	     return super.onCreateOptionsMenu(menu);	
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		 // TODO Auto-generated method stub
        if(item.getItemId() == 1){
        	Intent intent = new Intent();
        	intent.setClass(this, AddResActivity.class);
        	startActivity(intent);
        }else{
        	
        	System.out.println("uuuuu");
        }
        return true;
	}
	@Override  
    protected void onResume() {  
        super.onResume();  
        if (sensorManager != null) {// 注册监听器  
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);  
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率  
        }  
    }  
	/** 
     * 重力感应监听 
     */  
    private SensorEventListener sensorEventListener = new SensorEventListener() {  
  
        @Override  
        public void onSensorChanged(SensorEvent event) {  
            // 传感器信息改变时执行该方法  
            float[] values = event.values;  
            float x = values[0]; // x轴方向的重力加速度，向右为正  
            float y = values[1]; // y轴方向的重力加速度，向前为正  
            float z = values[2]; // z轴方向的重力加速度，向上为正  
            Log.i(TAG, "x轴方向的重力加速度" + x +  "；y轴方向的重力加速度" + y +  "；z轴方向的重力加速度" + z);  
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。  
            int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了  
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {  
                vibrator.vibrate(200);  
                Message msg = new Message();  
                msg.what = SENSOR_SHAKE;  
                handler.sendMessage(msg);  
            }  
        }  
  
        @Override  
        public void onAccuracyChanged(Sensor sensor, int accuracy) {  
  
        }  
    };  
  
    /** 
     * 动作执行 
     */  
    Handler handler = new Handler() {  
  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch (msg.what) {  
            case SENSOR_SHAKE:  
            	java.util.Random r=new java.util.Random();
                int count = RestaurantTableHelper.getAllCount(MainActivity.this); 
                System.out.println(count+"-----------------------");
                if(count>0){
                	
                    int id = r.nextInt(count)+1;
                    System.out.println(count+"-----------------------"+id);
                    String res = RestaurantTableHelper.getOneRes(MainActivity.this, id+"").getName();
                    textView.setText(res);
                    Log.i(TAG, "检测到摇晃，执行操作！");
                }
                break;  
            }  
        }  
  
    };  
    
    
}
