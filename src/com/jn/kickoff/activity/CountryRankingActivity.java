
package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

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
import com.jn.kickoff.utils.UtilValidate;

public class CountryRankingActivity extends FragmentActivity implements Constants.Country {

    public static final String TAG = CountryRankingActivity.class.getSimpleName();

    private List<Country> countryList = new ArrayList<Country>();

    private ListView countryRankListView;

    private CountryRankingAdapter countryRankingAdapter;

    private AdView adView;

    private AdRequest adRequest;

    private CountryManager countryManager;
    
    private AnimationSounds animationSounds;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_ranking);

        initViews();
        initManagers();

        countryList = countryManager.fetchAllCountries();

        if (UtilValidate.isEmpty(countryList)) {

            new ScrappingCountriesTask().execute();
        } else {

            countryRankingAdapter = new CountryRankingAdapter(CountryRankingActivity.this, countryList);
            countryRankListView.setAdapter(countryRankingAdapter);

        }

        FrameLayout layout = (FrameLayout)findViewById(R.id.linear);
        layout.addView(adView);

        // get test ads on a physical device.
        adRequest = new AdRequest();

        final TelephonyManager tm = (TelephonyManager)getBaseContext().getSystemService(
                Context.TELEPHONY_SERVICE);
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
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
            	
            	animationSoundBite();

                if (UtilValidate.isNull(countryList.get(pos).get_id()))
                    countryList = countryManager.fetchAllCountries();

                Log.e(TAG, "clicked " + countryList.get(pos).get_id());

                Intent intent = new Intent(CountryRankingActivity.this, SquardActivity.class);
                intent.putExtra("url", countryList.get(pos).getCountryLink());
                intent.putExtra("id", countryList.get(pos).get_id());
                startActivity(intent);

            }
        });
    }

    private void initViews() {

        countryRankListView = (ListView)findViewById(R.id.listview_country_rank);

    }

    private void initManagers() {

        countryManager = new CountryManager();

        adView = new AdView(this, AdSize.SMART_BANNER, Constants.AppConstants.ADDMOB);
        
        animationSounds = new AnimationSounds(CountryRankingActivity.this);

    }
    
   

    private class ScrappingCountriesTask extends AsyncTask<Void, String, String> {

        private ProgressDialog dialog;

        @Override
        protected String doInBackground(Void... params) {

            countryList = countryManager.scrapUrlForCountriesRank(COUNTRY_RANKING_FEED_URL);

            return null;
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = ProgressDialog.show(CountryRankingActivity.this, "", "Loading. Please wait...", true);
            dialog.show();
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (dialog != null)
                dialog.dismiss();

            if (UtilValidate.isNotEmpty(countryList)) {

                countryManager.insertIntoCountries(countryList);

                countryRankingAdapter = new CountryRankingAdapter(CountryRankingActivity.this, countryList);
                countryRankListView.setAdapter(countryRankingAdapter);

            } else {

                Toast.makeText(CountryRankingActivity.this, "This service is currently unavailable",
                        Toast.LENGTH_SHORT);
            }

        }

    }
    
    
    public void animationSoundBite() {

		switch (SettingsPageSharedPreference.getSounds(CountryRankingActivity.this)) {

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
