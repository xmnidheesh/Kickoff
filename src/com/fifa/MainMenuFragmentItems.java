package com.fifa;

import java.util.List;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fifa.adapter.PlayerRankingAdapter;
import com.fifa.holder.FifaPlayerDetails;
import com.fifa.manager.PlayerManager;
import com.fifa.utils.UtilValidate;
import com.fifa.webservice.AsyncTaskCallBack;
import com.squareup.picasso.Picasso;

public class MainMenuFragmentItems extends Fragment {

	private String title;

	private Drawable iconImage;

	private ImageView iconImageView;

	private TextView titleTextView;

	private PlayerManager playerManager;

	private static final int REQUEST_CODE = 1;

	private AsynchTaskCallBack asynchTaskCallBack;

	private PlayerRankingAdapter rankingAdapter;

	private ListView listview;

	private PopupWindow popupWindow;

	private RelativeLayout relative_top;
	
	 private static final String profileUrl = "http://cdn.content.easports.com/fifa/fltOnlineAssets/C74DDF38-0B11-49b0-B199-2E2A11D1CC13/2014/fut/items/images/players/web/<PICID>.png";

	    private static final Pattern profilePicPattern = Pattern.compile("<PICID>");

	@SuppressLint("ValidFragment")
	public MainMenuFragmentItems(String title, Drawable image) {

		this.title = title;

		this.iconImage = image;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_menu_items, null);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initViews();
		initManagers();

		iconImageView.setImageDrawable(iconImage);

		titleTextView.setText(title);

		titleTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (titleTextView.getText().equals("Venue")) {

					playerManager.getAlluserRanking(getActivity(),
							asynchTaskCallBack, REQUEST_CODE);
					iconImageView.setVisibility(View.INVISIBLE);
					titleTextView.setVisibility(View.INVISIBLE);
				} 
				else if (titleTextView.getText().equals("Fixture")) {

					playerManager.getAlluserRanking(getActivity(),
							asynchTaskCallBack, REQUEST_CODE);
					iconImageView.setVisibility(View.INVISIBLE);
                    titleTextView.setVisibility(View.INVISIBLE);
				} 
				else if (titleTextView.getText().equals("Ranking")) {

					playerManager.getAlluserRanking(getActivity(),
							asynchTaskCallBack, REQUEST_CODE);
					iconImageView.setVisibility(View.INVISIBLE);
                    titleTextView.setVisibility(View.INVISIBLE);
				} else if (titleTextView.getText().equals("TopPlayers")) {

					playerManager.getAlluserRanking(getActivity(),
							asynchTaskCallBack, REQUEST_CODE);
					iconImageView.setVisibility(View.INVISIBLE);
                    titleTextView.setVisibility(View.INVISIBLE);
				}

			}
		});

	}

	private void initManagers() {
		// TODO Auto-generated method stub
		playerManager = new PlayerManager();
		asynchTaskCallBack = new AsynchTaskCallBack();
		popupWindow = new PopupWindow();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		iconImageView = (ImageView) getView().findViewById(R.id.itemImg);

		titleTextView = (TextView) getView().findViewById(R.id.itemTxt);

		listview = (ListView) getView().findViewById(R.id.listview);
		relative_top = (RelativeLayout) getView().findViewById(
				R.id.relative_top);
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
				rankingAdapter = new PlayerRankingAdapter(getActivity(),
						playerTempList);
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
								dribbling, defending, heading,profileimage);
					}
				});

			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
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
			String defending, String heading,String profileimage) {
		listview.setVisibility(View.INVISIBLE);
		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
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
		ImageView imageview_profileimage=(ImageView) popupView
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

		
		Picasso.with(getActivity())
		.load(userPicUrl) 
		.placeholder(R.drawable.ic_launcher) 
		.error(R.drawable.ic_launcher).fit() 
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

}
