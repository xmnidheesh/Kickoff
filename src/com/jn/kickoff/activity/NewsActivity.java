package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jn.kickoff.R;
import com.jn.kickoff.adapter.NewsAdapter;
import com.jn.kickoff.holder.News;
import com.jn.kickoff.manager.CountryManager;
import com.jn.kickoff.utils.Util;

public class NewsActivity extends Activity {

	private List<News> newsList = new ArrayList<News>();
	private ListView listView_newslist;
	NewsAdapter newsAdapter;
	CountryManager countryManager;

	WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		initViews();
		initManagers();

		new NewsScrappingTask()
				.execute("http://m.fifa.com/newscentre/news/index.html");

	}

	private void initManagers() {
		countryManager = new CountryManager();
		// TODO Auto-generated method stub

	}

	private void initViews() {
		// TODO Auto-generated method stub
		listView_newslist = (ListView) findViewById(R.id.listView_newslist);
		webview= (WebView) findViewById(R.id.webview);

	}

	private class NewsScrappingTask extends AsyncTask<String, String, String> {

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
			newsAdapter = new NewsAdapter(NewsActivity.this, newsList);
			listView_newslist.setAdapter(newsAdapter);

			listView_newslist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					listView_newslist.setVisibility(View.INVISIBLE);
					
					webview.loadUrl("http://m.fifa.com/"+newsList.get(arg2).getDetailednews());
					
				}
			});

		}

		@Override
		protected String doInBackground(String... params) {

			newsList = countryManager.scrapUrlForNews(params[0]);

			return null;
		}

	}

}
