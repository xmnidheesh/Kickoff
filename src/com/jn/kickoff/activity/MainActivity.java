package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;
import com.jn.kickoff.AnimationSounds;
import com.jn.kickoff.AppRaterManager;
import com.jn.kickoff.MainMenuFragmentItems;
import com.jn.kickoff.R;
import com.jn.kickoff.SettingsPageSharedPreference;
import com.jn.kickoff.Animation.DepthPageTransformer;
import com.jn.kickoff.adapter.TestFragmentAdapter;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.constants.SoundStatus;
import com.jn.kickoff.db.DbHelper;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * @author nidheesh
 */
public class MainActivity extends FragmentActivity implements Constants,
		AdListener {

	private static final String TAG = MainActivity.class.getName();

	private TestFragmentAdapter testFragmentAdapter;

	private MainMenuFragmentItems fragmentItemsVenu;

	private MainMenuFragmentItems fragmentItemsFixture;

	private MainMenuFragmentItems fragmentItemsRanking;

	private MainMenuFragmentItems fragmentItemsTopPlayers;

	private MainMenuFragmentItems fragmentItemsNews;

	private ViewPager mainMenuViewPager;

	private List<Fragment> mainMenuFragments = new ArrayList<Fragment>();

	private DbHelper dbHelper;

	private RelativeLayout lineartop;

	private PopupWindow popupWindow;

	private AnimationSounds animationSounds;

	private InterstitialAd interstitial;

	private MixpanelAPI mixpanel;

	private com.viewpagerindicator.CirclePageIndicator donationSliderPageIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initManagers();
		
		new PushNotificationThread().start();

		// Create ad request

		mainMenuViewPager.setPageTransformer(true, new DepthPageTransformer());
		// mainMenuViewPager.setPageTransformer(true, new
		// ZoomOutPageTransformer());

		mainMenuFragments.add(fragmentItemsVenu);
		mainMenuFragments.add(fragmentItemsFixture);
		mainMenuFragments.add(fragmentItemsRanking);
		mainMenuFragments.add(fragmentItemsTopPlayers);
		mainMenuFragments.add(fragmentItemsNews);
		testFragmentAdapter = new TestFragmentAdapter(
				getSupportFragmentManager(), mainMenuFragments);
		mainMenuViewPager.setAdapter(testFragmentAdapter);
		donationSliderPageIndicator.setViewPager(mainMenuViewPager);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mixpanel.flush();
		super.onDestroy();
	}

	public void animationThankYou() {

		switch (SettingsPageSharedPreference.getSounds(MainActivity.this)) {

		case SOUND_ON:

			animationSounds.thankYouSoundon();

			break;

		case SOUND_OFF:

			animationSounds.thankYouSoundoff();

			break;

		default:
			break;
		}
	}

	private void initViews() {

		mainMenuViewPager = (ViewPager) findViewById(R.id.pager);
		lineartop = (RelativeLayout) findViewById(R.id.lineartop);
		donationSliderPageIndicator = (CirclePageIndicator) findViewById(R.id.donation_image_slider_indicator);
	}

	class PushNotificationThread extends Thread {

		public void run() {
			Parse.initialize(MainActivity.this,
					"LmIM3uWcyelvcVjl0m6XoL528glzHpsNNEp7pf9X",
					"fglG1RAhdrCifoDOSwp9fYDUs50hnjY38UqQLX9h");
			PushService.subscribe(MainActivity.this, "Kickoff",
					PushNotificationActivity.class);
			PushService.setDefaultPushCallback(MainActivity.this,
					PushNotificationActivity.class);
			ParseInstallation pi = ParseInstallation.getCurrentInstallation();
			pi.saveEventually();
		}
	}

	private void initManagers() {

		fragmentItemsVenu = new MainMenuFragmentItems(MENU1, MainActivity.this
				.getResources().getDrawable(R.drawable.venue), 0);

		fragmentItemsFixture = new MainMenuFragmentItems(MENU2,
				MainActivity.this.getResources().getDrawable(
						R.drawable.fixtures), 1);

		fragmentItemsTopPlayers = new MainMenuFragmentItems(MENU3,
				MainActivity.this.getResources().getDrawable(
						R.drawable.topplayers), 2);

		fragmentItemsRanking = new MainMenuFragmentItems(MENU4,
				MainActivity.this.getResources().getDrawable(
						R.drawable.topteams), 3);
		fragmentItemsNews = new MainMenuFragmentItems(MENU5, MainActivity.this
				.getResources().getDrawable(R.drawable.news), 4);
		animationSounds = new AnimationSounds(MainActivity.this);

		// Create the interstitial.
		interstitial = new InterstitialAd(this, Constants.AppConstants.ADDMOB);
		mixpanel = MixpanelAPI.getInstance(this,
				Constants.MixpanelConstants.API_KEY);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_rate:
			AppRaterManager.showRateDialog(MainActivity.this, null);
			return true;
		case R.id.action_about:
			AppRaterManager.showRateDialog(MainActivity.this, null);
			return true;
		case R.id.action_sound:
			CustomtPopUp(MainActivity.this, "ON", "OFF");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		AdRequest adRequest = new AdRequest();

		// Begin loading your interstitial
		interstitial.loadAd(adRequest);

		// Set Ad Listener to use the callbacks below
		interstitial.setAdListener(this);

		showBackandExitPopUp(MainActivity.this);
	}

	private void showBackandExitPopUp(MainActivity mainActivity) {

		LayoutInflater layoutInflater = (LayoutInflater) mainActivity
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(R.layout.backandexit, null);
		TextView textView_exit = (TextView) popupView
				.findViewById(R.id.textView_exit);
		Button button_exit = (Button) popupView
				.findViewById(R.id.cancel_button);
		Button submit_button = (Button) popupView
				.findViewById(R.id.submit_button);

		textView_exit.setText("DO YOU WANT TO EXIT");
		submit_button.setText("YUP");
		button_exit.setText("NOPE");

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);

		/**
		 * animation ...
		 */
		popupWindow.setAnimationStyle(R.style.PopUpAnimationtwinslide);

		popupWindow.showAtLocation(lineartop, Gravity.CENTER, 0, 0);

		popupWindow.setFocusable(true);

		popupWindow.update();

		button_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				popupWindow.dismiss();

			}
		});

		submit_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// get
				SettingsPageSharedPreference.getpositon(MainActivity.this, "User_Uses");
				int value=SettingsPageSharedPreference.getpositon(MainActivity.this, "User_Uses");
				SettingsPageSharedPreference.putlistpostion(MainActivity.this, "User_Uses",value+1);
				Log.e(TAG, "Value"+value);
				
				JSONObject props = new JSONObject();
				try {
					props.put("User_Uses", value);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mixpanel.track("MainActivity", props);
				
				animationThankYou();
				finish();
				popupWindow.dismiss();

			}
		});

	}

	private void CustomtPopUp(MainActivity mainActivity, String on, String off) {

		LayoutInflater layoutInflater = (LayoutInflater) mainActivity
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(R.layout.backandexit, null);
		TextView textView_exit = (TextView) popupView
				.findViewById(R.id.textView_exit);
		Button button_exit = (Button) popupView
				.findViewById(R.id.cancel_button);
		Button submit_button = (Button) popupView
				.findViewById(R.id.submit_button);

		textView_exit.setText("SOUND PROFILE");
		button_exit.setText(off);
		submit_button.setText(on);

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);

		/**
		 * animation ...
		 */
		popupWindow.setAnimationStyle(R.style.PopUpAnimationtwinslide);

		popupWindow.showAtLocation(lineartop, Gravity.CENTER, 0, 0);

		popupWindow.setFocusable(true);

		popupWindow.update();

		submit_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SettingsPageSharedPreference.putSounds(MainActivity.this,
						SoundStatus.SOUND_ON);
				popupWindow.dismiss();

			}
		});

		button_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SettingsPageSharedPreference.putSounds(MainActivity.this,
						SoundStatus.SOUND_OFF);
				popupWindow.dismiss();
			}
		});

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
	public void onReceiveAd(Ad ad) {
		Log.d("OK", "Received ad");
		if (ad == interstitial) {
			interstitial.show();
		}

	}
}
