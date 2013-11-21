
package com.jn.kickoff.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ProgressBar;

import com.jn.kickoff.FIFA;
import com.jn.kickoff.R;
import com.jn.kickoff.db.DbHelper;
import com.jn.kickoff.utils.AsyncTaskResult;

public class SplashScreenActivity extends Activity {

    private static final String TAG = SplashScreenActivity.class.getName();

    private ProgressBar progressBar;

    private int myProgress;

    private DbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        initViews();
        initManagers();

        loadData();

    }

    private void initViews() {

        progressBar = (ProgressBar)findViewById(R.id.ProgressBar);

    }

    private void initManagers() {

        FIFA.setContext(getApplicationContext());

        dbHelper = DbHelper.getInstance();

    }

    private void loadData() {
        // TODO Auto-generated method stub
        myProgress += 10;
        progressBar.setProgress(myProgress);

        new BackgroundAsyncTask().execute();
    }

    public class BackgroundAsyncTask extends AsyncTask<Void, Integer, AsyncTaskResult<String>> {

        @Override
        protected void onPreExecute() {

            myProgress += 10;
        }

        @Override
        protected AsyncTaskResult<String> doInBackground(Void... params) {

            try {
                dbHelper.createDataBase();

                while (myProgress <= 100) {

                    myProgress++;
                    publishProgress(myProgress);
                    SystemClock.sleep(10);

                }
            } catch (IOException e) {

                Log.e(TAG, "Exception occured while trying to create databasse");
            }

            return null;

        }

        @Override
        protected void onPostExecute(AsyncTaskResult<String> result) {

            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            progressBar.setProgress(values[0]);
        }

    }

}
