package com.amaze.helpme;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{
View v;
TextView un,ua,cn1,cn2,cp1,cp2,md;
SQLiteDatabase db;
Intent i;
String userName,userAddress,careName1,careName2,carePhone1,carePhone2,medical;
Cursor c;
String p1,p2;
private SensorManager senSensorManager;
private Sensor senAccelerometer;
private long lastUpdate=0;
private float last_x,last_y,last_z;
private static final int SHAKE_THRESHOLD=810;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		i=getIntent();
		un=(TextView)findViewById(R.id.userNameM);
		ua=(TextView)findViewById(R.id.userAddrM);
		cn1=(TextView)findViewById(R.id.cname1);
		cn2=(TextView)findViewById(R.id.cname2);
		cp1=(TextView)findViewById(R.id.cmob1);
		cp2=(TextView)findViewById(R.id.cmob2);
		md=(TextView)findViewById(R.id.meddet);
		userName=i.getStringExtra("userName");
		userAddress=i.getStringExtra("userAddress");
		careName1=i.getStringExtra("careName1");
		careName2=i.getStringExtra("careName2");
		carePhone1=i.getStringExtra("carePhone1");
		carePhone2=i.getStringExtra("carePhone2");
		medical=i.getStringExtra("medical");
		senSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		senAccelerometer=senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		senSensorManager.registerListener(this, senAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
		db=openOrCreateDatabase("userdb",Context.MODE_PRIVATE,null);
		db.execSQL("CREATE TABLE IF NOT EXISTS user(name VARCHAR,addr VARCHAR,caren1 VARCHAR,carep1 VARCHAR,caren2 VARCHAR,carep2 VARCHAR,med VARCHAR);");
		db.execSQL("INSERT INTO user VALUES('"+userName+"','"+userAddress+"','"+careName1+"','"+carePhone1+"','"+careName2+"','"+carePhone2+"','"+medical+"');");
		c=db.rawQuery("SELECT * FROM user",null);
		if(c.moveToFirst())
		{
			un.setText(c.getString(0));
			ua.setText(c.getString(1));
			cn1.setText(c.getString(2));
			cn2.setText(c.getString(4));
			cp1.setText(c.getString(3));
			cp2.setText(c.getString(5));
			p1=c.getString(3);
			p2=c.getString(5);
			md.setText(c.getString(6));
		}
	}
	@Override
	protected void onPause()	{
		super.onPause();
	}
	@Override
	protected void onResume()	{
		super.onResume();
		senSensorManager.registerListener(this, senAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
	}
	@Override
	public void onSensorChanged(SensorEvent sensorEvent)
	{
		Sensor mySensor=sensorEvent.sensor;
		if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER)
		{
			float x=sensorEvent.values[0];
			float y=sensorEvent.values[1];
			float z=sensorEvent.values[2];
			long currTime=System.currentTimeMillis();
			if((currTime-lastUpdate)>300)
			{
				long diffTime=(currTime-lastUpdate);
				lastUpdate=currTime;
				float speed=Math.abs(x+y+z-last_x-last_y-last_z)/diffTime*10000;
				if(speed>SHAKE_THRESHOLD)
				{
					Toast.makeText(this,"Emergency Triggered!!!",Toast.LENGTH_LONG).show();
					panic(v);
				}
				last_x=x;
				last_y=y;
				last_z=z;
			}
		}
	}
	@Override
	public void onAccuracyChanged(Sensor sensor,int accuracy)
	{
		
	}
	public void panic(View v)
	{
		Intent i=new Intent(MainActivity.this,AlertActivity.class);
		i.putExtra("no1",p1);
		i.putExtra("no2",p2);
		startActivity(i);
	}
	public void onBackPressed()
	{
		Toast.makeText(this ,"Use Exit Button!",Toast.LENGTH_SHORT).show();
	}
	public void moveToBack(View v)
	{
		moveTaskToBack(true);
	}
	public void exit(View v)
	{
		System.exit(0);
	}
	public void careCall1(View v)
	{
		Intent intent=new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+p1));
		startActivity(intent);
	}
	public void careCall2(View v)
	{
		Intent intent=new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+p2));
		startActivity(intent);
	}
	public void callAmb(View v)
	{
		String a="108";
		Intent intent=new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+a));
		startActivity(intent);
	}
}
