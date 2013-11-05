
package com.fifa.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fifa.R;
import com.fifa.adapter.CountryRankingAdapter;
import com.fifa.constants.Constants;
import com.fifa.entity.Country;
import com.fifa.manager.CountryManager;

public class CountryRanking extends FragmentActivity implements Constants.Country {

    public static final String TAG = CountryRanking.class.getSimpleName();

    private List<Country> countryList = new ArrayList<Country>();

    private ListView countryRankListView;

    private CountryRankingAdapter countryRankingAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_ranking);

        initViews();
        initManagers();

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
