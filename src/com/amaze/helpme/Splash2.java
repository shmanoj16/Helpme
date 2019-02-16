package com.amaze.helpme;



import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;

public class Splash2 extends Activity {
SQLiteDatabase db;
Cursor c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		db=openOrCreateDatabase("jff",Context.MODE_PRIVATE,null);
		db.execSQL("CREATE TABLE IF NOT EXISTS jf(sno VARCHAR)");
		c=db.rawQuery("SELECT * FROM jf",null);
		Thread background=new Thread(){
		public void run(){
		try {
			sleep(2*1000);
			if(c.getCount()==0){
			Intent i=new Intent(getBaseContext(),EditActivity.class);
			startActivity(i);
			db.execSQL("INSERT INTO jf VALUES('1');");
			}
			else{
				Intent i=new Intent(getBaseContext(),MainActivity.class);
				startActivity(i);
				}
			finish();
		}catch(Exception e){
		}
		}
		};
		background.start();
	}

}
