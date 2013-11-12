package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.jn.kickoff.R;
import com.jn.kickoff.adapter.NewsAdapter;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.holder.NewsBase;
import com.jn.kickoff.holder.NewsHeadlines;
import com.jn.kickoff.manager.CountryManager;
import com.jn.kickoff.manager.NewsManager;
import com.jn.kickoff.utils.ProgressWheel;
import com.jn.kickoff.utils.UtilValidate;
import com.jn.kickoff.webservice.AsyncTaskCallBack;

public class NewsActivity extends Activity {

	private ListView listView_newslist;
	
	NewsAdapter newsAdapter;
	
	CountryManager countryManager;
	
	private AdView adView;
	
	AdRequest adRequest;
	
	private static ProgressWheel progressWheel;
	
	private static RelativeLayout relativeLayoutprogresswheel;
	
	boolean loadingFinished = true;
	
	private TextView progressBarDetail_text;
	
	private NewsManager newsManager;
	
	private AsynchTaskCallBack asynchTaskCallBack;
	
	private static final int REQUEST_CODE = 1;
	
	private List<NewsHeadlines> newsHeadlinesList = new ArrayList<NewsHeadlines>();
	
	private List<String> newsDetailList;
	
	private ProgressBar pbHeaderProgress;
	
	private PopupWindow popupWindow;

	private RelativeLayout relative_top;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		
		initViews();
		initManagers();
		
		relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
		progressBarDetail_text.setVisibility(View.VISIBLE);
		pbHeaderProgress.setVisibility(View.VISIBLE);

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.blink);
		anim.setRepeatCount(-1);
		anim.setRepeatMode(Animation.RESTART);
		pbHeaderProgress.startAnimation(anim);
		pbHeaderProgress.setProgress(10);

		progressWheel.setTextSize(18);
		progressWheel.setBarLength(20);
		progressWheel.setBarWidth(25);
		progressWheel.setRimWidth(50);
		progressWheel.setSpinSpeed(25);
		progressWheel.spin();

		FrameLayout layout = (FrameLayout) findViewById(R.id.linear);
		layout.addView(adView);

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

		newsManager.getNews(this, asynchTaskCallBack, REQUEST_CODE);

	}

	private void initManagers() {
		countryManager = new CountryManager();
		// TODO Auto-generated method stub
		adView = new AdView(this, AdSize.SMART_BANNER,
				Constants.AppConstants.ADDMOB);
		newsManager = new NewsManager();
		asynchTaskCallBack = new AsynchTaskCallBack();
		

	}

	private void initViews() {
		// TODO Auto-generated method stub
		listView_newslist = (ListView) findViewById(R.id.listView_newslist);
		progressWheel = (ProgressWheel) findViewById(R.id.progressBarDetail);
		relativeLayoutprogresswheel = (RelativeLayout) findViewById(R.id.progress_relative_Detail);
		progressBarDetail_text = (TextView) findViewById(R.id.progressBarDetail_text);
		pbHeaderProgress= (ProgressBar) findViewById(R.id.pbHeaderProgress);
		relative_top= (RelativeLayout) findViewById(R.id.relative_top);
		

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

				NewsBase newsBase = (NewsBase) result;
				if (UtilValidate.isNotNull(newsBase)) {
					if (UtilValidate.isNotEmpty(newsBase.getHeadlines())) {

						newsHeadlinesList.addAll(newsBase.getHeadlines());

						loadingFinished = false;
						// SHOW LOADING IF IT ISNT
						// ALREADY
						// VISIBLE
						relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
						progressBarDetail_text.setVisibility(View.INVISIBLE);
						pbHeaderProgress.setVisibility(View.INVISIBLE);

						newsAdapter = new NewsAdapter(NewsActivity.this,
								newsHeadlinesList);
						listView_newslist.setAdapter(newsAdapter);

						listView_newslist
								.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
										progressBarDetail_text.setVisibility(View.INVISIBLE);
										pbHeaderProgress.setVisibility(View.INVISIBLE);
										newsDetailList=new ArrayList<String>();
										newsDetailList.add(newsHeadlinesList.get(position).getHeadline());
										newsDetailList.add(newsHeadlinesList.get(position).getDescription());
										newsDetailList.add(newsHeadlinesList.get(position).getSource());
										newsDetailList.add(newsHeadlinesList.get(position).getLastModified());
										newsDetailList.add(newsHeadlinesList.get(position).getImages().get(0).getUrl());
										
										 Intent intent=new Intent(NewsActivity.this,NewsDetailpageActivity.class);
										Bundle value= new Bundle();
										value.putStringArrayList("newsDetailList", (ArrayList<String>)newsDetailList);
										intent.putExtras(value);
										startActivity(intent);
										
										

									}
								});

					}
				}
			}
			
		}
		

		@Override
		public void onFinish(int responseCode, String result) {
		    // TODO Auto-generated method stub
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
	public void NoInternetpopup() 
	{
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getApplicationContext().getSystemService(
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

			}

		});

	}
}
