package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.jn.kickoff.R;
import com.jn.kickoff.adapter.CountryRankingAdapter;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.entity.Country;
import com.jn.kickoff.manager.CountryManager;

public class CountryRanking extends FragmentActivity implements Constants.Country {

    public static final String TAG = CountryRanking.class.getSimpleName();

    private List<Country> countryList = new ArrayList<Country>();

    private ListView countryRankListView;

    private CountryRankingAdapter countryRankingAdapter;
    
    private AdView adView;
	AdRequest adRequest;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_ranking);

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
		

        new ScrappingTask().execute();

        countryRankListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                Log.e(TAG, "clicked " + pos);

                Intent intent = new Intent(CountryRanking.this,SquardFragment.class);
                intent.putExtra("url",countryList.get(pos).getCountryLink());
                startActivity(intent);

            }
        });
    }

    private void initViews() {

        countryRankListView = (ListView)findViewById(R.id.listview_country_rank);

    }

    private void initManagers() {
    	adView = new AdView(this, AdSize.SMART_BANNER, Constants.AppConstants.ADDMOB);

    }

    private class ScrappingTask extends AsyncTask<Void, String, String> {

        private CountryManager countryManager = new CountryManager();

        @Override
        protected String doInBackground(Void... params) {

            countryList = countryManager.scrapUrlForCountriesRank(COUNTRY_RANKING_FEED_URL);

            return null;
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            countryRankingAdapter = new CountryRankingAdapter(CountryRanking.this, countryList);
            countryRankListView.setAdapter(countryRankingAdapter);
        }

    }

}
