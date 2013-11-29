package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.jn.kickoff.AnimationSounds;
import com.jn.kickoff.R;
import com.jn.kickoff.SettingsPageSharedPreference;
import com.jn.kickoff.adapter.CountryRankingAdapter;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.entity.Country;
import com.jn.kickoff.manager.CountryManager;
import com.jn.kickoff.utils.ProgressWheel;
import com.jn.kickoff.utils.UtilValidate;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class CountryRankingActivity extends FragmentActivity implements
		Constants.Country {

	public static final String TAG = CountryRankingActivity.class
			.getSimpleName();

	private List<Country> countryList = new ArrayList<Country>();

	private ListView countryRankListView;

	private CountryRankingAdapter countryRankingAdapter;

	private AdView adView;

	private AdRequest adRequest;

	private CountryManager countryManager;

	private AnimationSounds animationSounds;

	private PopupWindow popupWindow;

	private static RelativeLayout relativeLayoutprogresswheel;

	boolean loadingFinished = true;

	private TextView progressBarDetail_text;

	private static ProgressWheel progressWheel;

	private ProgressBar pbHeaderProgress;
	
	private MixpanelAPI mixpanel;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.country_ranking);

		initViews();
		initManagers();
		
		
		JSONObject props = new JSONObject();
		try {
			props.put("COUNTRYRANK", TAG);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mixpanel.track("App Usage in FirstActivity", props);

		relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
		progressBarDetail_text.setVisibility(View.VISIBLE);
		pbHeaderProgress.setVisibility(View.VISIBLE);

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.blink);
		anim.setRepeatCount(-1);
		anim.setRepeatMode(Animation.RESTART);
		pbHeaderProgress.startAnimation(anim);
		pbHeaderProgress.setVisibility(View.VISIBLE);
		pbHeaderProgress.setVisibility(View.VISIBLE);
		pbHeaderProgress.setProgress(10);

		progressWheel.setTextSize(18);
		progressWheel.setBarLength(20);
		progressWheel.setBarWidth(25);
		progressWheel.setRimWidth(50);
		progressWheel.setSpinSpeed(25);
		progressWheel.spin();

		countryList = countryManager.fetchAllCountries();
		
		Log.e(TAG, "countryList " + countryList.size());

		if (UtilValidate.isEmpty(countryList) &&
		        countryList.size()==0) {
		    Log.e(TAG, "countryList**************** " + countryList.size());
			new ScrappingCountriesTask().execute();
		} else {

			countryRankingAdapter = new CountryRankingAdapter(
					CountryRankingActivity.this, countryList);
			countryRankListView.setAdapter(countryRankingAdapter);

		}

		FrameLayout layout = (FrameLayout) findViewById(R.id.linear);
		layout.addView(adView);

		// get test ads on a physical device.
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

		countryRankListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				animationSoundBite();

				if (UtilValidate.isNull(countryList.get(pos).get_id()))
					countryList = countryManager.fetchAllCountries();

				Log.e(TAG, "clicked " + countryList.get(pos).get_id());

				Intent intent = new Intent(CountryRankingActivity.this,
						SquardActivity.class);
				intent.putExtra("url", countryList.get(pos).getCountryLink());
				intent.putExtra("id", countryList.get(pos).get_id());
				startActivity(intent);

			}
		});
	}

	private void initViews() {

		countryRankListView = (ListView) findViewById(R.id.listview_country_rank);
		progressWheel = (ProgressWheel) findViewById(R.id.progressBarDetail);
		relativeLayoutprogresswheel = (RelativeLayout) findViewById(R.id.progress_relative_Detail);
		progressBarDetail_text = (TextView) findViewById(R.id.progressBarDetail_text);
		pbHeaderProgress = (ProgressBar) findViewById(R.id.pbHeaderProgress);

	}

	private void initManagers() {

		countryManager = new CountryManager();

		adView = new AdView(this, AdSize.SMART_BANNER,
				Constants.AppConstants.ADDMOB);

		animationSounds = new AnimationSounds(CountryRankingActivity.this);
		mixpanel = MixpanelAPI.getInstance(this,
				Constants.MixpanelConstants.API_KEY);


	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mixpanel.flush();
		super.onDestroy();
	}

	private class ScrappingCountriesTask extends
			AsyncTask<Void, String, String> {

		private ProgressDialog dialog;

		@Override
		protected String doInBackground(Void... params) {

			countryList = countryManager
					.scrapUrlForCountriesRank(COUNTRY_RANKING_FEED_URL);

			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
			progressBarDetail_text.setVisibility(View.VISIBLE);
			pbHeaderProgress.setVisibility(View.VISIBLE);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (dialog != null)
				dialog.dismiss();
			if (UtilValidate.isNotEmpty(countryList)) {
				relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
				progressBarDetail_text.setVisibility(View.INVISIBLE);
				pbHeaderProgress.setVisibility(View.INVISIBLE);

				countryManager.insertIntoCountries(countryList);

				countryRankingAdapter = new CountryRankingAdapter(
						CountryRankingActivity.this, countryList);
				countryRankListView.setAdapter(countryRankingAdapter);

			} else {
				relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
				progressBarDetail_text.setVisibility(View.INVISIBLE);
				pbHeaderProgress.setVisibility(View.INVISIBLE);
				NoInternetpopup();
			}

		}

	}

	public void NoInternetpopup() {
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(R.layout.advertisement_layout,
				null);

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);

		/**
		 * animation ...
		 */
		// popupWindow.setAnimationStyle(R.style.PopUpAnimation);
		findViewById(R.id.relative_top).post(new Runnable() {
			public void run() {
				popupWindow.showAtLocation(findViewById(R.id.relative_top),
						Gravity.CENTER, 0, 0);
			}
		});
		popupWindow.setFocusable(true);

		popupWindow.update();

		// set the custom dialog components - text,
		// image and
		// button

		TextView textView_nointernet = (TextView) popupView
				.findViewById(R.id.textView_nointernet);

		Button dialogButtonOk = (Button) popupView.findViewById(R.id.button_ok);
		// if button is clicked, close the custom dialog
		dialogButtonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				popupWindow.dismiss();
				finish();

			}

		});

	}

	public void animationSoundBite() {

		switch (SettingsPageSharedPreference
				.getSounds(CountryRankingActivity.this)) {

		case SOUND_ON:

			animationSounds.whooshSoundon();

			break;

		case SOUND_OFF:

			animationSounds.whooshSoundoff();

			break;

		default:
			break;
		}

	}
}
