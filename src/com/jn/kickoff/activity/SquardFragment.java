
package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jn.kickoff.R;
import com.jn.kickoff.entity.Squard;
import com.jn.kickoff.manager.CountryManager;

public class SquardFragment extends FragmentActivity {

    public static final String TAG = SquardFragment.class.getSimpleName();
    
    private List<Squard>squardList = new ArrayList<Squard>();
    
    private String url;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_ranking);

        initViews();
        initManagers();
        
        url = getIntent().getStringExtra("url");
        
        Log.e(TAG,"url :"+url);

        new SquardScrappingTask().execute(url);

    }

    private void initManagers() {
        // TODO Auto-generated method stub
    }

    private void initViews() {
        // TODO Auto-generated method stub
      

    }
    
    private class SquardScrappingTask extends AsyncTask<String, String, String> {

        private CountryManager countryManager = new CountryManager();

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

        }

        @Override
        protected String doInBackground(String... params) {

            squardList = countryManager.scrapSquardFromTeamLink(params[0]);

            return null;
        }

    }

}
