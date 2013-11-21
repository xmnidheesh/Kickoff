package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jn.kickoff.R;
import com.jn.kickoff.adapter.TeamSquardAdapter;
import com.jn.kickoff.entity.PlayerProfile;
import com.jn.kickoff.entity.Squard;
import com.jn.kickoff.manager.CountryManager;
import com.jn.kickoff.utils.ProgressWheel;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class SquardActivity extends FragmentActivity {

	public static final String TAG = SquardActivity.class.getSimpleName();

	private List<Squard> squardList = new ArrayList<Squard>();

	private TeamSquardAdapter squardAdapter;

	private String url;

	private String _id;

	private GridView gridViewSquard;

	private RelativeLayout relativeLayout;

	private CountryManager countryManager;

	private PlayerProfile playerProfile;

	private static RelativeLayout relativeLayoutprogresswheel;

	boolean loadingFinished = true;

	private TextView progressBarDetail_text;

	private static ProgressWheel progressWheel;

	private ProgressBar pbHeaderProgress;

	private PopupWindow popupWindow;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.country_squard);

		initViews();
		initManagers();

		/*relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
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
		progressWheel.spin();*/

		url = getIntent().getStringExtra("url");

		_id = getIntent().getStringExtra("id");

		Log.e(TAG, "_id :" + _id);

		squardList = countryManager.fetchAllPlayersOfACountry(_id);

		if (UtilValidate.isEmpty(squardList)) {

		    Log.e(TAG, "squardList is empty :");
		    
			new SquardScrappingTask().execute(url, _id);

		} else {
		    
		    Log.e(TAG, "squardList is not empty :");

			squardAdapter = new TeamSquardAdapter(SquardActivity.this,
					squardList);

			gridViewSquard.setAdapter(squardAdapter);
		}

		gridViewSquard.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (UtilValidate.isNull(squardList.get(arg2).get_id()))
					squardList = countryManager.fetchAllPlayersOfACountry(_id);

				Log.e(TAG, "squardList p_id :" + squardList.get(arg2).get_id());

				playerProfile = countryManager
						.fetchPlayerProfileData(squardList.get(arg2).get_id());

				if (UtilValidate.isNotNull(playerProfile)) {

					showPlayerProfile(playerProfile);

				} else {

					new PlayerDetailsScrappingTask().execute(
							squardList.get(arg2).getProfileLink(), squardList
									.get(arg2).getProfileImage(), squardList
									.get(arg2).get_id(), squardList.get(arg2)
									.getCountry_id());
				}

			}
		});

	}

	private void initManagers() {

		countryManager = new CountryManager();
	}

	private void initViews() {

		gridViewSquard = (GridView) findViewById(R.id.gridView_team_squard);

		relativeLayout = (RelativeLayout) findViewById(R.id.relative);
		progressWheel = (ProgressWheel) findViewById(R.id.progressBarDetail);
		relativeLayoutprogresswheel = (RelativeLayout) findViewById(R.id.progress_relative_Detail);
		progressBarDetail_text = (TextView) findViewById(R.id.progressBarDetail_text);
		pbHeaderProgress = (ProgressBar) findViewById(R.id.pbHeaderProgress);
	}

	private class SquardScrappingTask extends AsyncTask<String, String, String> {

		private ProgressDialog dialog;

		@Override
		protected String doInBackground(String... params) {

			squardList = countryManager.scrapSquardFromTeamLink(params[0],
					params[1]);

			Log.e(TAG, "Cid :" + params[1]);

			Log.e(TAG, "url :" + params[0]);

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

			/*
			 * dialog = ProgressDialog.show(SquardActivity.this, "",
			 * "Loading. Please wait...", true); dialog.show();
			 */
			/*relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
			progressBarDetail_text.setVisibility(View.VISIBLE);
			pbHeaderProgress.setVisibility(View.VISIBLE);*/
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

			if (UtilValidate.isNotEmpty(squardList)) {

				/*relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
				progressBarDetail_text.setVisibility(View.INVISIBLE);
				pbHeaderProgress.setVisibility(View.INVISIBLE);*/
				
				countryManager.insertIntoPlayers(squardList);
				
				Log.e(TAG, "squardList :" + squardList.size());
				
				squardAdapter = new TeamSquardAdapter(SquardActivity.this,
						squardList);

				gridViewSquard.setAdapter(squardAdapter);

			} else {
				relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
				progressBarDetail_text.setVisibility(View.INVISIBLE);
				pbHeaderProgress.setVisibility(View.INVISIBLE);
				NoInternetpopup();
			}

		}

	}

	private class PlayerDetailsScrappingTask extends
			AsyncTask<String, String, String> {

		private CountryManager countryManager = new CountryManager();

		private PlayerProfile playerProfile;

		private String imageLink;

		private ProgressDialog dialog;

		private String playerId;

		private String countryId;

		@Override
		protected String doInBackground(String... params) {

			playerProfile = countryManager.scrapPlayerprofileDetails(params[0]);

			imageLink = params[1];

			playerId = params[2];

			countryId = params[3];

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

			/*
			 * dialog = ProgressDialog.show(SquardActivity.this, "",
			 * "Loading. Please wait...", true); dialog.show();
			 */
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

			if (UtilValidate.isNotNull(playerProfile)) {

				relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
				progressBarDetail_text.setVisibility(View.INVISIBLE);
				pbHeaderProgress.setVisibility(View.INVISIBLE);

				playerProfile.setImageLink(imageLink);
				playerProfile.setPlayerId(playerId);
				playerProfile.setCountryId(countryId);

				// Insert into table player profile
				countryManager.insertIntoPlayerProfile(playerProfile);

				showPlayerProfile(playerProfile);

			} else {

				Toast.makeText(SquardActivity.this,
						"This service is currently unavailable",
						Toast.LENGTH_SHORT);
			}

		}

	}

	public void showPlayerProfile(PlayerProfile playerProfile) {

		LayoutInflater layoutInflater = (LayoutInflater) this
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(R.layout.profile_page, null);
		ImageView closeBtn = (ImageView) popupView.findViewById(R.id.closeBtn);
		ImageView iconImageView = (ImageView) popupView
				.findViewById(R.id.player_profileimage);
		TextView nameTextView = (TextView) popupView
				.findViewById(R.id.player_name);
		TextView fnameTextView = (TextView) popupView.findViewById(R.id.fname);
		TextView lnameTextView = (TextView) popupView.findViewById(R.id.lname);
		TextView nationalityTextView = (TextView) popupView
				.findViewById(R.id.player_nation);
		TextView dateOfBirthTextView = (TextView) popupView
				.findViewById(R.id.player_dob);
		TextView ageTextView = (TextView) popupView
				.findViewById(R.id.player_age);
		TextView countryTextView = (TextView) popupView
				.findViewById(R.id.player_country);
		TextView placeTextView = (TextView) popupView
				.findViewById(R.id.player_place);
		TextView positionTextView = (TextView) popupView
				.findViewById(R.id.player_position);
		TextView heightTextView = (TextView) popupView
				.findViewById(R.id.player_height);
		TextView weightTextView = (TextView) popupView
				.findViewById(R.id.player_weight);
		TextView footTextView = (TextView) popupView
				.findViewById(R.id.player_foot);

		final PopupWindow popupWindow = new PopupWindow(popupView,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

		/**
		 * animation ...
		 */
		popupWindow.setAnimationStyle(R.style.PopUpAnimationTopToBottom);

		popupWindow.showAtLocation(relativeLayout, Gravity.TOP, 0, 0);

		popupWindow.setFocusable(true);

		popupWindow.update();

		if (UtilValidate.isNotNull(playerProfile.getImageLink()))
			Picasso.with(this).load(playerProfile.getImageLink())
					.placeholder(R.drawable.ic_launcher)
					.error(R.drawable.ic_launcher).fit().into(iconImageView);

		if (UtilValidate.isNotNull(playerProfile.getFirstname()))
			nameTextView.setText(playerProfile.getFirstname());
		if (UtilValidate.isNotNull(playerProfile.getFirstname()))
			fnameTextView.setText(playerProfile.getFirstname());
		if (UtilValidate.isNotNull(playerProfile.getLastname()))
			lnameTextView.setText(playerProfile.getLastname());
		if (UtilValidate.isNotNull(playerProfile.getNationality()))
			nationalityTextView.setText(playerProfile.getNationality());
		if (UtilValidate.isNotNull(playerProfile.getDateOfBirth()))
			dateOfBirthTextView.setText(playerProfile.getDateOfBirth());
		if (UtilValidate.isNotNull(playerProfile.getAge()))
			ageTextView.setText(playerProfile.getAge());
		if (UtilValidate.isNotNull(playerProfile.getCountry()))
			countryTextView.setText(playerProfile.getCountry());
		if (UtilValidate.isNotNull(playerProfile.getPlace()))
			placeTextView.setText(playerProfile.getPlace());
		if (UtilValidate.isNotNull(playerProfile.getPosition()))
			positionTextView.setText(playerProfile.getPosition());
		if (UtilValidate.isNotNull(playerProfile.getHeight()))
			heightTextView.setText(playerProfile.getHeight());
		if (UtilValidate.isNotNull(playerProfile.getWeight()))
			weightTextView.setText(playerProfile.getWeight());
		if (UtilValidate.isNotNull(playerProfile.getFoot()))
			footTextView.setText(playerProfile.getFoot());

		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();

			}
		});

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
		findViewById(R.id.relative).post(new Runnable() {
			public void run() {
				popupWindow.showAtLocation(findViewById(R.id.relative),
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

}
