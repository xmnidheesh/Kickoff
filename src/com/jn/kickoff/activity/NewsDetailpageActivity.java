
package com.jn.kickoff.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jn.kickoff.R;
import com.jn.kickoff.adapter.NewsAdapter;
import com.jn.kickoff.entity.News;
import com.jn.kickoff.entity.NewsDetails;
import com.jn.kickoff.manager.NewsManager;
import com.jn.kickoff.utils.ProgressWheel;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class NewsDetailpageActivity extends Activity {

    private String TAG = NewsDetailpageActivity.class.getName();

    private TextView textview_heading;

    private TextView textview_description;

    private TextView textview_source;

    private TextView textview_date;

    private ImageView imageview_photo;

    private static ProgressWheel progressWheel;

    private static RelativeLayout relativeLayoutprogresswheel;

    boolean loadingFinished = true;

    private TextView progressBarDetail_text;

    private ArrayList<News> newsList = new ArrayList<News>();

    Bundle newsDetailList = null;

    private Bundle bundle;

    private int position;

    private NewsManager newsManager;

    private NewsDetails newsDetails;

    private ProgressBar pbHeaderProgress;

    private RelativeLayout relative_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newsdetail);

        initViews();
        initManagers();

        newsList = getIntent().getParcelableArrayListExtra("newsList");
        bundle = getIntent().getExtras();
        position = bundle.getInt("position");

        if (UtilValidate.isNotNull(newsList))
            new ScrappingnewsDetailTask(newsList.get(position).getNews_detail_link()).execute();

        /*if (UtilValidate.isNotNull(newsDetailList.getStringArrayList("newsDetailList"))) {
            textview_heading.setText(newsDetailList.getStringArrayList("newsDetailList").get(0));
            textview_description
                    .setText(newsDetailList.getStringArrayList("newsDetailList").get(1));
            textview_source.setText(newsDetailList.getStringArrayList("newsDetailList").get(2));
            textview_date.setText(newsDetailList.getStringArrayList("newsDetailList").get(3));
            // imageview_photo.setText(newsDetailList.getStringArrayList("newsDetailList").get(3));

            Picasso.with(this).load(newsDetailList.getStringArrayList("newsDetailList").get(4))
                    .placeholder(R.drawable.empty_photo).error(R.drawable.empty_photo).fit()
                    .into(imageview_photo);
        }*/
    }

    private void initManagers() {
        // TODO Auto-generated method stub
        newsManager = new NewsManager();
    }

    private void initViews() {
        // TODO Auto-generated method stub
        textview_heading = (TextView)findViewById(R.id.textview_heading);
        textview_description = (TextView)findViewById(R.id.textview_description);
        textview_source = (TextView)findViewById(R.id.textview_source);
        textview_date = (TextView)findViewById(R.id.textview_date);
        imageview_photo = (ImageView)findViewById(R.id.imageview_photo);

        progressWheel = (ProgressWheel)findViewById(R.id.progressBarDetail);
        relativeLayoutprogresswheel = (RelativeLayout)findViewById(R.id.progress_relative_Detail);
        progressBarDetail_text = (TextView)findViewById(R.id.progressBarDetail_text);
        pbHeaderProgress = (ProgressBar)findViewById(R.id.pbHeaderProgress);
        relative_top = (RelativeLayout)findViewById(R.id.relative_top);

    }

    private class ScrappingnewsDetailTask extends AsyncTask<Void, String, String> {

        private ProgressDialog dialog;

        private String newsLink;

        public ScrappingnewsDetailTask(String news_detail_link) {

            this.newsLink = news_detail_link;
        }

        @Override
        protected String doInBackground(Void... params) {

            newsDetails = newsManager.scrapUrlForNewsDetails(newsLink);

            Log.e(TAG, "newsDetails :" + newsDetails.getNews_image_large());

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
           /* relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
            progressBarDetail_text.setVisibility(View.VISIBLE);
            pbHeaderProgress.setVisibility(View.VISIBLE);*/
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

            Picasso.with(NewsDetailpageActivity.this).load(newsDetails.getNews_image_large())
                    .placeholder(R.drawable.empty_photo).error(R.drawable.empty_photo).fit()
                    .into(imageview_photo);
            
            textview_heading.setText(newsDetails.getHeadin_text());
            
            textview_description.setText(newsDetails.getNews_body_text());
            
            
        }

    }

}
