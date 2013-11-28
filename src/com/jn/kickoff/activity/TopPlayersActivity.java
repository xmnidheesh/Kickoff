package com.jn.kickoff.activity;

import java.util.List;
import java.util.regex.Pattern;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.jn.kickoff.R;
import com.jn.kickoff.adapter.PlayerRankingAdapter;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.holder.FifaPlayerDetails;
import com.jn.kickoff.manager.PlayerManager;
import com.jn.kickoff.utils.ProgressWheel;
import com.jn.kickoff.utils.UtilValidate;
import com.jn.kickoff.webservice.AsyncTaskCallBack;
import com.squareup.picasso.Picasso;

public class TopPlayersActivity extends Activity {

	private static final String TAG = TopPlayersActivity.class.getName();

	private AdView adView;

	AdRequest adRequest;

	private PlayerManager playerManager;

	private AsynchTaskCallBack asynchTaskCallBack;

	private static final int REQUEST_CODE = 1;

	private PlayerRankingAdapter rankingAdapter;

	private RelativeLayout relative_top;

	private PopupWindow popupWindow;

	private ListView listview;

	private static ProgressWheel progressWheel;

	private static RelativeLayout relativeLayoutprogresswheel;

	boolean loadingFinished = true;

	private TextView progressBarDetail_text;

	private static final String profileUrl = "http://cdn.content.easports.com/fifa/fltOnlineAssets/C74DDF38-0B11-49b0-B199-2E2A11D1CC13/2014/fut/items/images/players/web/<PICID>.png";

	private static final Pattern profilePicPattern = Pattern.compile("<PICID>");

	private ProgressBar pbHeaderProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topplayers_page);

		initViews();
		initManagers();

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

		playerManager.getAlluserRanking(this, asynchTaskCallBack, REQUEST_CODE);

	}

	private void initViews() {
		listview = (ListView) findViewById(R.id.listview);
		relative_top = (RelativeLayout) findViewById(R.id.relative_top);
		progressWheel = (ProgressWheel) findViewById(R.id.progressBarDetail);
		relativeLayoutprogresswheel = (RelativeLayout) findViewById(R.id.progress_relative_Detail);
		progressBarDetail_text = (TextView) findViewById(R.id.progressBarDetail_text);
		pbHeaderProgress = (ProgressBar) findViewById(R.id.pbHeaderProgress);
	}

	/**
	 * This method will initialize every object instances
	 */
	private void initManagers() {
		// TODO Auto-generated method stub
		adView = new AdView(this, AdSize.SMART_BANNER,
				Constants.AppConstants.ADDMOB);
		playerManager = new PlayerManager();
		asynchTaskCallBack = new AsynchTaskCallBack();

	}

	private class AsynchTaskCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int requestCode) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (UtilValidate.isNotNull(result) && responseCode == REQUEST_CODE) {

				final List<FifaPlayerDetails> playerTempList = (List<FifaPlayerDetails>) result;
				Log.e("size before", "" + playerTempList.size());

				loadingFinished = false;
				// SHOW LOADING IF IT ISNT
				// ALREADY
				// VISIBLE
				relativeLayoutprogresswheel.setVisibility(View.GONE);
				progressBarDetail_text.setVisibility(View.GONE);
				pbHeaderProgress.setVisibility(View.GONE);
				rankingAdapter = new PlayerRankingAdapter(
						TopPlayersActivity.this, playerTempList);
				listview.setAdapter(rankingAdapter);

				listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						String dob = playerTempList.get(position).getDob();
						String fname = playerTempList.get(position)
								.getFirst_name();
						String lname = playerTempList.get(position)
								.getLast_name();
						String commonname = playerTempList.get(position)
								.getCommon_name();
						String height = playerTempList.get(position)
								.getHeight();
						String foot = playerTempList.get(position).getFoot();
						String rating = playerTempList.get(position)
								.getRating();
						String type = playerTempList.get(position).getType();
						String pace = playerTempList.get(position)
								.getAttribute1();
						String shooting = playerTempList.get(position)
								.getAttribute2();
						String passing = playerTempList.get(position)
								.getAttribute3();
						String dribbling = playerTempList.get(position)
								.getAttribute4();
						String defending = playerTempList.get(position)
								.getAttribute5();
						String heading = playerTempList.get(position)
								.getAttribute6();
						String profileimage = playerTempList.get(position)
								.getBase_id();

						popup(dob, fname, lname, commonname, height, foot,
								rating, type, pace, shooting, passing,
								dribbling, defending, heading, profileimage);
					}
				});

			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
		    // TODO Auto-generated method stub
			//Toast.makeText(PeopleOnlineActivity.this, "Respone in activty :"+result, Toast.LENGTH_LONG).show();
			relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
			progressBarDetail_text.setVisibility(View.INVISIBLE);
			pbHeaderProgress.setVisibility(View.INVISIBLE);
			NoInternetpopup();

		
		}

		@Override
		public void onFinish(int responseCode, Object result, int requestCode) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish(int responseCode, Object result,
				boolean isPaginated) {
			// TODO Auto-generated method stub

		}

	}

	public void popup(String dob, String fname, String lname, String common,
			String height, String foot, String rating, String type,
			String pace, String shooting, String passing, String dribbling,
			String defending, String heading, String profileimage) {
		loadingFinished = false;
		// SHOW LOADING IF IT ISNT
		// ALREADY
		// VISIBLE
		relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
		progressBarDetail_text.setVisibility(View.INVISIBLE);
		pbHeaderProgress.setVisibility(View.INVISIBLE);
		listview.setVisibility(View.INVISIBLE);
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(
				R.layout.custom_popup_single_player_details, null);

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);

		/**
		 * animation ...
		 */
		// popupWindow.setAnimationStyle(R.style.PopUpAnimation);

		popupWindow.showAtLocation(relative_top, Gravity.CENTER, 0, 0);

		popupWindow.setFocusable(true);

		popupWindow.update();

		// set the custom dialog components - text,
		// image and
		// button
		TextView textView_dateofbirth = (TextView) popupView
				.findViewById(R.id.textView_dateofbirth);
		TextView textView_firstname = (TextView) popupView
				.findViewById(R.id.textView_firstname);
		TextView textView_lastname = (TextView) popupView
				.findViewById(R.id.textView_lastname);
		TextView textView_commonname = (TextView) popupView
				.findViewById(R.id.textView_commonname);
		TextView textView_height = (TextView) popupView
				.findViewById(R.id.textView_height);
		TextView textView_foot = (TextView) popupView
				.findViewById(R.id.textView_foot);
		TextView textView_rating = (TextView) popupView
				.findViewById(R.id.textView_rating);
		TextView textView_type = (TextView) popupView
				.findViewById(R.id.textView_type);
		TextView textView_pace = (TextView) popupView
				.findViewById(R.id.textView_pace);
		TextView textView_shooting = (TextView) popupView
				.findViewById(R.id.textView_shooting);
		TextView textView_passing = (TextView) popupView
				.findViewById(R.id.textView_passing);
		TextView textView_dribbling = (TextView) popupView
				.findViewById(R.id.textView_dribbling);
		TextView textView_defending = (TextView) popupView
				.findViewById(R.id.textView_defending);
		TextView textView_heading = (TextView) popupView
				.findViewById(R.id.textView_heading);
		ImageView imageview_profileimage = (ImageView) popupView
				.findViewById(R.id.imageview_profileimage);

		textView_dateofbirth.setText(dob);
		textView_firstname.setText(fname);
		textView_lastname.setText(lname);
		textView_commonname.setText(common);
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

		String userPicUrl = profilePicPattern.matcher(profileUrl).replaceAll(
				profileimage);

		Picasso.with(this).load(userPicUrl).placeholder(R.drawable.empty_photo)
				.error(R.drawable.empty_photo).fit()
				.into(imageview_profileimage);

		Button dialogButtonOk = (Button) popupView.findViewById(R.id.button_ok);
		// if button is clicked, close the custom dialog
		dialogButtonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				popupWindow.dismiss();
				listview.setVisibility(View.VISIBLE);

			}

		});

	}
	
	
	
	
	
	
	
	
	public void NoInternetpopup() 
	{
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(
				R.layout.advertisement_layout, null);

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);

		/**
		 * animation ...
		 */
		// popupWindow.setAnimationStyle(R.style.PopUpAnimation);

		findViewById(R.id.relative_top).post(new Runnable() {
			   public void run() {
				   popupWindow.showAtLocation(findViewById(R.id.relative_top), Gravity.CENTER, 0, 0);
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
/*Intent i=new Intent(TopPlayersActivity.this,TopPlayersActivity.class);
startActivity(i);*/

			}

		});

	}

}
