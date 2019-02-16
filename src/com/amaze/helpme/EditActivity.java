package com.amaze.helpme;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
	}

	
	public void save(View v){
		EditText uname,uaddr,cname1,cname2,cno1,cno2,med;
		Intent i=new Intent(EditActivity.this,MainActivity.class);
		uname=(EditText)findViewById(R.id.userNameE);
		uaddr=(EditText)findViewById(R.id.userAddressE);
		cname1=(EditText)findViewById(R.id.cnameE1);
		cno1=(EditText)findViewById(R.id.cPhoneE1);
		cname2=(EditText)findViewById(R.id.cnameE2);
		cno2=(EditText)findViewById(R.id.cPhoneE2);
		med=(EditText)findViewById(R.id.medicalE);
		i.putExtra("userName",uname.getText().toString());
		i.putExtra("userAddress",uaddr.getText().toString());
		i.putExtra("careName1",cname1.getText().toString());
		i.putExtra("careName2",cname2.getText().toString());
		i.putExtra("carePhone1",cno1.getText().toString());
		i.putExtra("carePhone2",cno2.getText().toString());
		i.putExtra("medical",med.getText().toString());
		startActivity(i);
		Toast.makeText(this,"Details Saved!!!",Toast.LENGTH_LONG).show();
		
	}

}
