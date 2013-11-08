package com.jn.kickoff.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.jn.kickoff.R;

public class PushNotificationActivity extends Activity {

	private TextView textView_notification;
	private String alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pushnotification);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String jsonData = extras.getString("com.parse.Data");

		textView_notification = (TextView) findViewById(R.id.textView_notification);
		
		 try {
			JSONObject jsonObj = new JSONObject(jsonData);
			
			alert= jsonObj.getString("alert");
			if(alert.startsWith("com."))
			{
				textView_notification.setText("Click back to go to previous page");
				Uri marketUri = Uri.parse("market://details?id=" + alert);
				Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
				startActivity(marketIntent);
			}
			else
			{
				textView_notification.setText(alert);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 
	}

}
