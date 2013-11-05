package com.jn.kickoff.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jn.kickoff.R;
import com.jn.kickoff.adapter.FixtureAdapter;
import com.jn.kickoff.holder.Fixture;
import com.jn.kickoff.manager.CountryManager;

public class FixtureActivity extends Activity {
	
	private CountryManager countryManager;
	   private List<Fixture> fixtureList=new ArrayList<Fixture>();

	    private ListView countryRankListView;

	    private FixtureAdapter fixtureAdapter;
	    private ListView listview_fixture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fixtures);

		initViews();
		initManagers();
		 new FixtureScrappingTask().execute("http://m.fifa.com/worldcup/preliminaries/matches/fixtures.html");
		//countryManager.scrapUrlForFixtures("http://en.wikipedia.org/wiki/List_of_2010_FIFA_World_Cup_matches");
		
		
		
		
		
	}

	private void initManagers() {
		// TODO Auto-generated method stub
		countryManager=new CountryManager();

	}

	private void initViews() {
		// TODO Auto-generated method stub
		listview_fixture=(ListView)findViewById(R.id.listview_fixture);

	}
	
    private class FixtureScrappingTask extends AsyncTask<String, String, String> {

        private CountryManager countryManager = new CountryManager();

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            fixtureAdapter = new FixtureAdapter(FixtureActivity.this, fixtureList);
            listview_fixture.setAdapter(fixtureAdapter);
            
            
            listview_fixture.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                        long arg3) {
                    // TODO Auto-generated method stub
String fulldate=fixtureList.get(arg2).getDate();

String[] parts = fixtureList.get(arg2).getDate().split(" ");
String first = parts[0];
String second = parts[1];


                	

AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		FixtureActivity.this);

	// set title
alertDialogBuilder.setTitle("Date Sheduled on : "+first+" Time : "+second);
	// set dialog message
	alertDialogBuilder
		.setCancelable(false)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				dialog.cancel();
			}
		  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
});





        }

        @Override
        protected String doInBackground(String... params) {

        	fixtureList = countryManager.scrapUrlForFixtures(params[0]);

            return null;
        }

    }

}
