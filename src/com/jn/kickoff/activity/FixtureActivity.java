package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.jn.kickoff.R;
import com.jn.kickoff.adapter.FixtureAdapter;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.holder.Fixture;
import com.jn.kickoff.holder.News;
import com.jn.kickoff.manager.CountryManager;
import com.jn.kickoff.utils.ProgressWheel;

public class FixtureActivity extends Activity {

	private CountryManager countryManager;
	private List<Fixture> fixtureList = new ArrayList<Fixture>();


	private ListView countryRankListView;

	private FixtureAdapter fixtureAdapter;
	private ListView listview_fixture;
	private AdView adView;
	AdRequest adRequest;
	private static ProgressWheel progressWheel;
	private static RelativeLayout relativeLayoutprogresswheel;
	boolean loadingFinished = true;
	private TextView progressBarDetail_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fixtures);

		initViews();
		initManagers();

		relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
		progressBarDetail_text.setVisibility(View.VISIBLE);

		progressWheel.setTextSize(18);
		progressWheel.setBarLength(20);
		progressWheel.setBarWidth(25);
		progressWheel.setRimWidth(50);
		progressWheel.setSpinSpeed(25);
		progressWheel.spin();

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

	new FixtureScrappingTask()
				.execute("http://m.fifa.com/worldcup/preliminaries/matches/fixtures.html");
		
		

	}

	private void initManagers() {
		// TODO Auto-generated method stub
		countryManager = new CountryManager();
		adView = new AdView(this, AdSize.SMART_BANNER,
				Constants.AppConstants.ADDMOB);

	}

	private void initViews() {
		// TODO Auto-generated method stub
		listview_fixture = (ListView) findViewById(R.id.listview_fixture);
		progressWheel = (ProgressWheel) findViewById(R.id.progressBarDetail);
		relativeLayoutprogresswheel = (RelativeLayout) findViewById(R.id.progress_relative_Detail);
		progressBarDetail_text = (TextView) findViewById(R.id.progressBarDetail_text);

	}

	private class FixtureScrappingTask extends
			AsyncTask<String, String, String> {

		private CountryManager countryManager = new CountryManager();

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {

			// TODO Auto-generated method stub
			super.onPostExecute(result);
			relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
			progressBarDetail_text.setVisibility(View.INVISIBLE);
			fixtureAdapter = new FixtureAdapter(FixtureActivity.this,
					fixtureList);
			listview_fixture.setAdapter(fixtureAdapter);

			listview_fixture.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String fulldate = fixtureList.get(arg2).getDate();

					String[] parts = fixtureList.get(arg2).getDate().split(" ");
					String first = parts[0];
					String second = parts[1];

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							FixtureActivity.this);

					// set title
					alertDialogBuilder.setTitle("Date Sheduled on : " + first
							+ " Time : " + second);
					// set dialog message
					alertDialogBuilder.setCancelable(false).setPositiveButton(
							"Ok", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, close
									// current activity
									dialog.cancel();
								}
							});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				}
			});

		}

		@Override
		protected String doInBackground(String... params) {

			loadingFinished = false;
			// SHOW LOADING IF IT ISNT
			// ALREADY
			// VISIBLE
			relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
			progressBarDetail_text.setVisibility(View.VISIBLE);
			fixtureList = countryManager.scrapUrlForFixtures(params[0]);

			return null;
		}

	}
	
	
	
	
	
	
	
	
	

}
