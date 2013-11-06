
package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.jn.kickoff.R;
import com.jn.kickoff.adapter.TeamSquardAdapter;
import com.jn.kickoff.entity.Squard;
import com.jn.kickoff.manager.CountryManager;

public class SquardFragment extends FragmentActivity {

    public static final String TAG = SquardFragment.class.getSimpleName();

    private List<Squard> squardList = new ArrayList<Squard>();

    private TeamSquardAdapter squardAdapter;

    private String url;

    private GridView gridViewSquard;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_squard);

        initViews();
        initManagers();

        url = getIntent().getStringExtra("url");

        new SquardScrappingTask().execute(url);
        
        gridViewSquard.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
           
                
                Log.e(TAG, "squardList :" + squardList.get(arg2).getProfileLink());
            }
        });

    }

    private void initManagers() {
        // TODO Auto-generated method stub
    }

    private void initViews() {

        gridViewSquard = (GridView)findViewById(R.id.gridView_team_squard);

    }

    private class SquardScrappingTask extends AsyncTask<String, String, String> {

        private CountryManager countryManager = new CountryManager();

        @Override
        protected String doInBackground(String... params) {

            squardList = countryManager.scrapSquardFromTeamLink(params[0]);

            Log.e(TAG, "list size :" + squardList.size());

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

            squardAdapter = new TeamSquardAdapter(SquardFragment.this, squardList);

            gridViewSquard.setAdapter(squardAdapter);
        }

    }

}
