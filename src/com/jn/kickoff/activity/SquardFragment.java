
package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jn.kickoff.R;
import com.jn.kickoff.adapter.TeamSquardAdapter;
import com.jn.kickoff.entity.PlayerProfile;
import com.jn.kickoff.entity.Squard;
import com.jn.kickoff.manager.CountryManager;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class SquardFragment extends FragmentActivity {

    public static final String TAG = SquardFragment.class.getSimpleName();

    private List<Squard> squardList = new ArrayList<Squard>();

    private TeamSquardAdapter squardAdapter;

    private String url;

    private GridView gridViewSquard;

    private RelativeLayout relativeLayout;

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

                new PlayerDetailsScrappingTask().execute(squardList.get(arg2).getProfileLink(),
                        squardList.get(arg2).getImage());
            }
        });

    }

    private void initManagers() {
        // TODO Auto-generated method stub
    }

    private void initViews() {

        gridViewSquard = (GridView)findViewById(R.id.gridView_team_squard);

        relativeLayout = (RelativeLayout)findViewById(R.id.relative);

    }

    private class SquardScrappingTask extends AsyncTask<String, String, String> {

        private CountryManager countryManager = new CountryManager();

        private ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params) {

            squardList = countryManager.scrapSquardFromTeamLink(params[0]);

            Log.e(TAG, "list size :" + squardList.size());

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

            dialog = ProgressDialog.show(SquardFragment.this, "", "Loading. Please wait...", true);
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

            squardAdapter = new TeamSquardAdapter(SquardFragment.this, squardList);

            gridViewSquard.setAdapter(squardAdapter);
        }

    }

    private class PlayerDetailsScrappingTask extends AsyncTask<String, String, String> {

        private CountryManager countryManager = new CountryManager();

        private PlayerProfile playerProfile;

        private String imageLink;

        private ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params) {

            playerProfile = countryManager.scrapPlayerprofileDetails(params[0]);

            imageLink = params[1];

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

            dialog = ProgressDialog.show(SquardFragment.this, "", "Loading. Please wait...", true);
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

            if (UtilValidate.isNotNull(playerProfile)) {

                playerProfile.setImageLink(imageLink);

                showPlayerProfile(playerProfile);

            }

        }

    }

    public void showPlayerProfile(PlayerProfile playerProfile) {

        LayoutInflater layoutInflater = (LayoutInflater)this.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.profile_page, null);
        ImageView closeBtn = (ImageView)popupView.findViewById(R.id.closeBtn);
        ImageView iconImageView = (ImageView)popupView.findViewById(R.id.player_profileimage);
        TextView nameTextView = (TextView)popupView.findViewById(R.id.player_name);
        TextView fnameTextView = (TextView)popupView.findViewById(R.id.fname);
        TextView lnameTextView = (TextView)popupView.findViewById(R.id.lname);
        TextView nationalityTextView = (TextView)popupView.findViewById(R.id.player_nation);
        TextView dateOfBirthTextView = (TextView)popupView.findViewById(R.id.player_dob);
        TextView ageTextView = (TextView)popupView.findViewById(R.id.player_age);
        TextView countryTextView = (TextView)popupView.findViewById(R.id.player_country);
        TextView placeTextView = (TextView)popupView.findViewById(R.id.player_place);
        TextView positionTextView = (TextView)popupView.findViewById(R.id.player_position);
        TextView heightTextView = (TextView)popupView.findViewById(R.id.player_height);
        TextView weightTextView = (TextView)popupView.findViewById(R.id.player_weight);
        TextView footTextView = (TextView)popupView.findViewById(R.id.player_foot);

        final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT, true);

        /**
         * animation ...
         */
        popupWindow.setAnimationStyle(R.style.PopUpAnimationTopToBottom);

        popupWindow.showAtLocation(relativeLayout, Gravity.TOP, 0, 0);

        popupWindow.setFocusable(true);

        popupWindow.update();

        if (UtilValidate.isNotNull(playerProfile.getImageLink()))
            Picasso.with(this).load(playerProfile.getImageLink())
                    .placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).fit()
                    .into(iconImageView);

        if (UtilValidate.isNotNull(playerProfile.getFirstname()))
            nameTextView.setText(playerProfile.getFirstname());

        if (UtilValidate.isNotNull(playerProfile.getFirstname()))
            fnameTextView.setText(playerProfile.getFirstname());
        if (UtilValidate.isNotNull(playerProfile.getLastname()))
            lnameTextView.setText(playerProfile.getLastname());

        if (UtilValidate.isNotNull(playerProfile.getNationality()))
            nationalityTextView.setText(playerProfile.getNationality());
        if (UtilValidate.isNotNull(playerProfile.getDateOfBirth()))
            dateOfBirthTextView.setText(playerProfile.getDateOfBirth());
        if (UtilValidate.isNotNull(playerProfile.getAge()))
            ageTextView.setText(playerProfile.getAge());
        if (UtilValidate.isNotNull(playerProfile.getCountry()))
            countryTextView.setText(playerProfile.getCountry());
        if (UtilValidate.isNotNull(playerProfile.getPlace()))
            placeTextView.setText(playerProfile.getPlace());

        if (UtilValidate.isNotNull(playerProfile.getPosition()))
            positionTextView.setText(playerProfile.getPosition());
        if (UtilValidate.isNotNull(playerProfile.getHeight()))
            heightTextView.setText(playerProfile.getHeight());
        if (UtilValidate.isNotNull(playerProfile.getWeight()))
            weightTextView.setText(playerProfile.getWeight());
        if (UtilValidate.isNotNull(playerProfile.getFoot()))
            footTextView.setText(playerProfile.getFoot());

        closeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

    }

}
