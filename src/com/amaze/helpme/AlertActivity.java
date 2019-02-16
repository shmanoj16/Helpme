package com.amaze.helpme;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class AlertActivity extends Activity {
	String no1,no2;
	MediaPlayer mysound;
	Vibrator vibrator;
	String location="Thoraipakkam";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert);
		Intent i=getIntent();
		mysound=MediaPlayer.create(AlertActivity.this, R.raw.click );
		mysound.start();
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(15000);
		no1=i.getStringExtra("no1");
		no2=i.getStringExtra("no2");
		Thread background=new Thread(){
			public void run(){
			try {
				sleep(10*1000);
				SmsManager smsManager=SmsManager.getDefault();
				smsManager.sendTextMessage(no1,null,"I'm in danger!!!"+location,null,null);
				smsManager.sendTextMessage(no2,null,"I'm in danger!!!"+location,null,null);
				Intent intent=new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+no1));
				startActivity(intent);
				finish();
			}catch(Exception e){
			}
			}
			};
			background.start();
		Toast.makeText(this ,"Use cancel Button to cancel alert before 10sec!!!!",Toast.LENGTH_LONG).show();
	}
@Override 
public void onDestroy()
{
	
}
	public void onBackPressed()
	{
		Toast.makeText(this ,"Use cancel Button to cancel alert before 10sec!!!!",Toast.LENGTH_SHORT).show();
	}
	public void alert(View v)
	{
		mysound.stop();
		vibrator.cancel();
		onDestroy();
		System.exit(0);
		
	}
}

