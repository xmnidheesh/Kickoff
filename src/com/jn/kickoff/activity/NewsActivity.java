package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
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

}
