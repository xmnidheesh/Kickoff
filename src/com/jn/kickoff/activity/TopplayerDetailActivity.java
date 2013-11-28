package com.jn.kickoff.activity;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.jn.kickoff.R;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class TopplayerDetailActivity extends Activity {

	TextView textView_dateofbirth;
	TextView textView_firstname;
	TextView textView_lastname;
	TextView textView_commonname;
	TextView textView_height;
	TextView textView_foot;
	TextView textView_rating;
	TextView textView_type;
	TextView textView_pace;
	TextView textView_shooting;
	TextView textView_passing;
	TextView textView_dribbling;
	TextView textView_defending;
	TextView textView_heading;
	ImageView imageview_profileimage;

	String dob;
	String fname;
	String lname;
	String commonname;
	String height;
	String foot;
	String rating;
	String type;
	String pace;
	String shooting;
	String passing;
	String dribbling;
	String defending;
	String heading;
	String profileimage;
	
	private static final String profileUrl = "http://cdn.content.easports.com/fifa/fltOnlineAssets/C74DDF38-0B11-49b0-B199-2E2A11D1CC13/2014/fut/items/images/players/web/<PICID>.png";
	
	private static final Pattern profilePicPattern = Pattern.compile("<PICID>");
	
	private static final String TAG = TopplayerDetailActivity.class.getName();

	private AdView adView;

	AdRequest adRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_popup_single_player_details);

		initViews();
		initManagers();
		
		FrameLayout layout = (FrameLayout) findViewById(R.id.linear);
		layout.addView(adView);

		// Create an ad request. Check logcat output for the hashed device
		// ID to
		// get test ads on a physical device.
		adRequest = new AdRequest();
		// adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		// adRequest.addTestDevice("C6205A36E35671ED5388B025B0B82698");
		// adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		// adRequest.addTestDevice("0B1CF3FD4AA0118FAB350F160041EFC7");
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
		
		

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				dob = null;
				fname = null;
				lname = null;
				commonname = null;
				height = null;
				foot = null;
				rating = null;
				type = null;
				pace = null;
				shooting = null;
				passing = null;
				dribbling = null;
				defending = null;
				heading = null;
				profileimage = null;
			} else {
				dob = extras.getString("dob");
				fname = extras.getString("fname");
				lname = extras.getString("lname");
				commonname = extras.getString("commonname");
				height = extras.getString("height");
				foot = extras.getString("foot");
				rating = extras.getString("rating");
				type = extras.getString("type");
				pace = extras.getString("pace");
				shooting = extras.getString("shooting");
				passing = extras.getString("passing");
				dribbling = extras.getString("dribbling");
				defending = extras.getString("defending");
				heading = extras.getString("heading");
				profileimage = extras.getString("profileimage");
			}
		} else {

			dob = (String) savedInstanceState.getSerializable("dob");
			fname = (String) savedInstanceState.getSerializable("fname");
			lname = (String) savedInstanceState.getSerializable("lname");
			commonname = (String) savedInstanceState
					.getSerializable("commonname");
			height = (String) savedInstanceState.getSerializable("height");
			foot = (String) savedInstanceState.getSerializable("foot");
			rating = (String) savedInstanceState.getSerializable("rating");
			type = (String) savedInstanceState.getSerializable("type");
			pace = (String) savedInstanceState.getSerializable("pace");
			shooting = (String) savedInstanceState.getSerializable("shooting");
			passing = (String) savedInstanceState.getSerializable("passing");
			dribbling = (String) savedInstanceState
					.getSerializable("dribbling");
			defending = (String) savedInstanceState
					.getSerializable("defending");
			heading = (String) savedInstanceState.getSerializable("heading");
			profileimage = (String) savedInstanceState
					.getSerializable("profileimage");
			
			
			  
		}

		textView_dateofbirth.setText(dob);
		textView_firstname.setText(fname);
		textView_lastname.setText(lname);
		textView_commonname.setText(commonname);
		textView_height.setText(height);
		textView_foot.setText(foot);
		textView_rating.setText(rating);
		textView_type.setText(type);
		textView_pace.setText(pace);
		textView_shooting.setText(shooting);
		textView_passing.setText(passing);
		textView_dribbling.setText(dribbling);
		textView_defending.setText(defending);
		textView_heading.setText(heading);
		
		if (UtilValidate.isNotEmpty(profileimage)) {
			String userPicUrl = profilePicPattern.matcher(profileUrl)
					.replaceAll(profileimage);

			 Picasso.with(TopplayerDetailActivity.this).load(userPicUrl)
				.placeholder(R.drawable.ic_launcher)
				.error(R.drawable.ic_launcher).fit()
				.into(imageview_profileimage);
			 Log.e("", "profileimage###########:"+userPicUrl);

		}
		
		
	}

	private void initManagers() {
		adView = new AdView(this, AdSize.SMART_BANNER,
				Constants.AppConstants.ADDMOB);
		// TODO Auto-generated method stub

	}

	private void initViews() {
		// TODO Auto-generated method stub
		textView_dateofbirth = (TextView) findViewById(R.id.textView_dateofbirth);
		textView_firstname = (TextView) findViewById(R.id.textView_firstname);
		textView_lastname = (TextView) findViewById(R.id.textView_lastname);
		textView_commonname = (TextView) findViewById(R.id.textView_commonname);
		textView_height = (TextView) findViewById(R.id.textView_height);
		textView_foot = (TextView) findViewById(R.id.textView_foot);
		textView_rating = (TextView) findViewById(R.id.textView_rating);
		textView_type = (TextView) findViewById(R.id.textView_type);
		textView_pace = (TextView) findViewById(R.id.textView_pace);
		textView_shooting = (TextView) findViewById(R.id.textView_shooting);
		textView_passing = (TextView) findViewById(R.id.textView_passing);
		textView_dribbling = (TextView) findViewById(R.id.textView_dribbling);
		textView_defending = (TextView) findViewById(R.id.textView_defending);
		textView_heading = (TextView) findViewById(R.id.textView_heading);
		imageview_profileimage = (ImageView) findViewById(R.id.imageview_profileimage);

	}

}
