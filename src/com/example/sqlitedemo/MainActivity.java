package com.example.sqlitedemo;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.sqlitedemo.db.RestaurantTableHelper;

public class MainActivity extends Activity {
	private TextView textView;
	private SensorManager sensorManager;  
    private Vibrator vibrator;  
    private static final int SENSOR_SHAKE = 10;  
    private static final int UPDATE_STR = 11;
    private static final int UPTATE_INTERVAL_TIME = 500;
    private static final String TAG = "TestSensorActivity"; 
    private int count=0;

    private long lastUpdateTime = System.currentTimeMillis();    // �ϴμ��ʱ�� ��һ�γ�ʼ�� Ĭ�ϵ�ǰʱ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		 menu.add(0, 1, 1, "���");
	     menu.add(0, 2, 2, "�༭");
	     return super.onCreateOptionsMenu(menu);	
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		 // TODO Auto-generated method stub
        if(item.getItemId() == 1){
        	Intent intent = new Intent();
        	intent.setClass(this, AddResActivity.class);
        	startActivity(intent);
        }else{
        	
        	Intent intent = new Intent();
        	intent.setClass(this, RestListActivity.class);
        	startActivity(intent);
        }
        return true;
	}

	/** 
     * ������Ӧ���� 
     */  
    private SensorEventListener sensorEventListener = new SensorEventListener() {  
  
        @Override  
        public void onSensorChanged(SensorEvent event) {  
        	// ���ڼ��ʱ��
        	long currentUpdateTime = System.currentTimeMillis();
        	// ���μ���ʱ����
        	long timeInterval = currentUpdateTime - lastUpdateTime;
        	if (timeInterval < UPTATE_INTERVAL_TIME){
        		return;
        	}
        	lastUpdateTime = currentUpdateTime;
            // ��������Ϣ�ı�ʱִ�и÷���  
            float[] values = event.values;  
            float x = values[0]; // x�᷽����������ٶȣ�����Ϊ��  
            float y = values[1]; // y�᷽����������ٶȣ���ǰΪ��  
            float z = values[2]; // z�᷽����������ٶȣ�����Ϊ��  
            Log.i(TAG, "x�᷽����������ٶ�" + x +  "��y�᷽����������ٶ�" + y +  "��z�᷽����������ٶ�" + z);  
            // һ����������������������ٶȴﵽ40�ʹﵽ��ҡ���ֻ���״̬��  
            int medumValue = 14;// ���� i9250��ô�ζ����ᳬ��20��û�취��ֻ����19��  
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
     * ����ִ�� 
     */  
    Handler handler = new Handler() {  
  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch (msg.what) {  
            case SENSOR_SHAKE:  
        		Log.i(TAG, "��⵽ҡ�Σ�ִ�в�����");
        		setWhereToEat();
                break; 
            case UPDATE_STR:
            	if(msg.obj!=null){
            		textView.setText((String)msg.obj);
            	}
            	
            	break;
            	
            }
        }  
  
    };  
    class RunWhereToEat implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				
				java.util.Random r=new java.util.Random();
				count = RestaurantTableHelper.getAllCount(MainActivity.this); 
                if(count>0){ 
                	for(int i=0;i<40;i++){
                		Thread.sleep(50);
                		int id = r.nextInt(count)+1;
                		String res = RestaurantTableHelper.getOneRes(MainActivity.this, id+"").getName();
                		Message msg = new Message();  
    					msg.what = UPDATE_STR;
    					msg.obj = res;
    					handler.sendMessage(msg);
                	}
                }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	

		}
    	
    }
    private void setWhereToEat(){
    	RunWhereToEat runWhere = new RunWhereToEat();
    	Thread th = new Thread(runWhere);
    	th.start();
    	
    }
	@Override  
    protected void onResume() {  
        super.onResume();  
        if (sensorManager != null) {// ע�������  
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);  
            // ��һ��������Listener���ڶ������������ô��������ͣ�����������ֵ��ȡ��������Ϣ��Ƶ��  
        }  
    }  
	@Override  
	protected void onPause() {  
	    super.onPause();  
	    if (sensorManager != null) {// ȡ��������  
	        sensorManager.unregisterListener(sensorEventListener);  
	    }  
	}
	@Override  
	protected void onStop() {  
	    super.onStop();  
	    if (sensorManager != null) {// ȡ��������  
	        sensorManager.unregisterListener(sensorEventListener);  
	    }  
	}  
}
