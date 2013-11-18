package com.jn.kickoff.activity;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;
import com.jn.kickoff.R;
import com.jn.kickoff.constants.Constants;

public class PushNotificationActivity extends Activity implements AdListener {

	private TextView textView_notification;
	
	private String alert;

	private AdView adView;

	AdRequest adRequest;
	
	  private InterstitialAd interstitial;

	  private TextView textView_notification_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pushnotification);

		initViews();
		initManagers();

		
		 adRequest = new AdRequest();

	    // Begin loading your interstitial
	    interstitial.loadAd(adRequest);

	    // Set Ad Listener to use the callbacks below
	    interstitial.setAdListener(this);
		
		
		FrameLayout layouttop = (FrameLayout) findViewById(R.id.linear_top);
		layouttop.addView(adView);
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
				textView_notification_text.setText(getUsername());
				textView_notification
						.setText("Click back to go to previous page");
				Uri marketUri = Uri.parse("market://details?id=" + alert);
				Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
				startActivity(marketIntent);
			} else {
				textView_notification_text.setText(getUsername());
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
		 // Create the interstitial.
		 interstitial = new InterstitialAd(this, Constants.AppConstants.ADDMOB);
		// TODO Auto-generated method stub

	}

	private void initViews() {
		textView_notification = (TextView) findViewById(R.id.textView_notification);
		textView_notification_text= (TextView) findViewById(R.id.textView_notification_text);
		// TODO Auto-generated method stub

	}

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveAd(Ad ad) {Log.d("OK", "Received ad");
    if (ad == interstitial)
    {
        interstitial.show();
      }
	}
	
	public String getUsername(){
	    AccountManager manager = AccountManager.get(this); 
	    Account[] accounts = manager.getAccountsByType("com.google"); 
	    List<String> possibleEmails = new LinkedList<String>();

	    for (Account account : accounts) {
	      // TODO: Check possibleEmail against an email regex or treat
	      // account.name as an email address only for certain account.type values.
	      possibleEmails.add(account.name);
	    }

	    if(!possibleEmails.isEmpty() && possibleEmails.get(0) != null){
	        String email = possibleEmails.get(0);
	        String[] parts = email.split("@");
	        if(parts.length > 0 && parts[0] != null)
	            return parts[0];
	        else
	            return null;
	    }else
	        return null;
	}

}
