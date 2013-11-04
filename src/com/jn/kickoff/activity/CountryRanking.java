package com.jn.kickoff.activity;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jn.kickoff.R;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.entity.Country;
import com.jn.kickoff.manager.CountryManager;

public class CountryRanking extends FragmentActivity implements Constants.Country{
    
    public static final String TAG = CountryRanking.class.getSimpleName();
    
    private List<Country>countryList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venues_page);

        initViews();
        initManagers();       

        new ScrappingTask().execute();
    }

    private void initViews() {

        
    }

    private void initManagers() {

        
    }
    
    private class ScrappingTask extends AsyncTask<Void, String, String>{
        
        private CountryManager countryManager = new CountryManager();

        @Override
        protected String doInBackground(Void... params) {
            
            countryList = countryManager.scrapUrlForCountriesRank(COUNTRY_RANKING_FEED_URL);
            
            Log.i(TAG,"countryList size :"+countryList.size());
            
            return null;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }
        
        
        
    }
    
}
