package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
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
import com.jn.kickoff.holder.FifaPlayerDetails;
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
	private List <NewsHeadlines> newsHeadlinesList=new ArrayList<NewsHeadlines>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
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
		
	newsManager.getNews(this,
				asynchTaskCallBack, REQUEST_CODE);
		/*new NewsScrappingTask()
				.execute("http://m.fifa.com/newscentre/news/index.html");*/
		
		
	}

	private void initManagers() {
		countryManager = new CountryManager();
		// TODO Auto-generated method stub
		adView = new AdView(this, AdSize.SMART_BANNER,
				Constants.AppConstants.ADDMOB);
		newsManager=new NewsManager();
		asynchTaskCallBack = new AsynchTaskCallBack();


	}

	private void initViews() {
		// TODO Auto-generated method stub
		listView_newslist = (ListView) findViewById(R.id.listView_newslist);
		progressWheel = (ProgressWheel) findViewById(R.id.progressBarDetail);
		relativeLayoutprogresswheel = (RelativeLayout) findViewById(R.id.progress_relative_Detail);
		progressBarDetail_text = (TextView) findViewById(R.id.progressBarDetail_text);


	}

/*	private class NewsScrappingTask extends AsyncTask<String, String, String> {

		private CountryManager countryManager = new CountryManager();

		
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 
		@Override
		protected void onPostExecute(String result) {

			// TODO Auto-generated method stub
			super.onPostExecute(result);
			relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
			progressBarDetail_text.setVisibility(View.INVISIBLE);
			newsAdapter = new NewsAdapter(NewsActivity.this, newsList);
			listView_newslist.setAdapter(newsAdapter);
			listView_newslist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
				//	webview.loadUrl("http://m.fifa.com/"+newsList.get(arg2).getDetailednews());
					new NewsDetailScrappingTask()
					.execute("http://m.fifa.com/"+newsList.get(arg2).getDetailednews());
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
			newsList = countryManager.scrapUrlForNews(params[0]);

			return null;
		}

	}
	*/
	
/*
	private class NewsDetailScrappingTask extends AsyncTask<String, String, String> {

		private CountryManager countryManager = new CountryManager();

		
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 
		@Override
		protected void onPostExecute(String result) {

			// TODO Auto-generated method stub
			super.onPostExecute(result);
			newsAdapter = new NewsAdapter(NewsActivity.this, newsList);
			listView_newslist.setAdapter(newsAdapter);

			

		}

		@Override
		protected String doInBackground(String... params) {
			
			loadingFinished = false;
			// SHOW LOADING IF IT ISNT
			// ALREADY
			// VISIBLE
			newsList = countryManager.scrapUrlForNewsDetail(params[0]);

			return null;
		}

	}*/
	
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
				 Log.d("",
							"from api newsHolder status :" + newsBase.getStatus());
					if (UtilValidate.isNotNull(newsBase)) {
						if (UtilValidate.isNotEmpty(newsBase.getHeadlines())) {
				 newsHeadlinesList.addAll(newsBase.getHeadlines());

				newsAdapter = new NewsAdapter(NewsActivity.this, newsHeadlinesList);
				listView_newslist.setAdapter(newsAdapter);

				listView_newslist.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						
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
