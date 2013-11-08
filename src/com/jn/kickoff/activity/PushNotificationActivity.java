package com.jn.kickoff.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.jn.kickoff.R;
import com.jn.kickoff.constants.Constants;

public class PushNotificationActivity extends Activity {

	private TextView textView_notification;
	private String alert;

	private AdView adView;

	AdRequest adRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pushnotification);

		initViews();
		initManagers();

		FrameLayout layouttop = (FrameLayout) findViewById(R.id.linear_top);
		layouttop.addView(adView);
		adRequest = new AdRequest();
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = tm.getDeviceId();
		adRequest.addTestDevice(deviceid);

		// Start loading the ad in the background.

		(new Thread() {
			public void run() {
				Looper.prepare();
				adView.loadAd(adRequest);
			}
		}).start();

		adView.loadAd(adRequest);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String jsonData = extras.getString("com.parse.Data");

		try {
			JSONObject jsonObj = new JSONObject(jsonData);

			alert = jsonObj.getString("alert");
			if (alert.startsWith("com.")) {
				textView_notification
						.setText("Click back to go to previous page");
				Uri marketUri = Uri.parse("market://details?id=" + alert);
				Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
				startActivity(marketIntent);
			} else {
				textView_notification.setText(alert);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initManagers() {
		adView = new AdView(this, AdSize.SMART_BANNER,
				Constants.AppConstants.ADDMOB);
		// TODO Auto-generated method stub

	}

	private void initViews() {
		textView_notification = (TextView) findViewById(R.id.textView_notification);
		// TODO Auto-generated method stub

	}

}
