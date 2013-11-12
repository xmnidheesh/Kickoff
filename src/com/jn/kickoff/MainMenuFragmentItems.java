
package com.jn.kickoff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jn.kickoff.activity.CountryRankingActivity;
import com.jn.kickoff.activity.FixtureActivity;
import com.jn.kickoff.activity.NewsActivity;
import com.jn.kickoff.activity.TopPlayersActivity;
import com.jn.kickoff.activity.VenuesActivity;
import com.jn.kickoff.constants.Constants;

@SuppressLint("ValidFragment")
public class MainMenuFragmentItems extends Fragment implements Constants{

    private static final String TAG = MainMenuFragmentItems.class.getName();

    private String title;

    private Drawable iconImage;

    private ImageView iconImageView;

    private TextView titleTextView;

    private int i;

    @SuppressLint("ValidFragment")
    public MainMenuFragmentItems(String title, Drawable image, int i) {

        this.title = title;

        this.iconImage = image;

        this.i = i;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_menu_items, null);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        initManagers();

        iconImageView.setImageDrawable(iconImage);
        iconImageView.setTag(i);

        titleTextView.setText(title);

        iconImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


            	if (iconImageView.getTag().equals(FRAGMENT1)) {
                    Intent intent = new Intent(getActivity(), VenuesActivity.class);
                    startActivity(intent);

                } else if (iconImageView.getTag().equals(FRAGMENT2)) {

                    Intent intent = new Intent(getActivity(), FixtureActivity.class);
                    startActivity(intent);

                } else if (iconImageView.getTag().equals(FRAGMENT3)) {
                    
                    Intent intent = new Intent(getActivity(), TopPlayersActivity.class);
                    startActivity(intent);

                } else if (iconImageView.getTag().equals(FRAGMENT4)) {

                    Intent intent = new Intent(getActivity(), CountryRankingActivity.class);
                    startActivity(intent);

                } else if (iconImageView.getTag().equals(FRAGMENT5)) {

                    Intent intent = new Intent(getActivity(), NewsActivity.class);
                    startActivity(intent);
                }

            }
        });

        titleTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (titleTextView.getText().equals(MENU1)) {

                    Intent intent = new Intent(getActivity(), VenuesActivity.class);
                    startActivity(intent);

                } else if (titleTextView.getText().equals(MENU2)) {
                    Intent intent = new Intent(getActivity(), FixtureActivity.class);
                    startActivity(intent);

                } else if (titleTextView.getText().equals(MENU4)) {

                    Intent intent = new Intent(getActivity(), CountryRankingActivity.class);
                    startActivity(intent);

                } else if (titleTextView.getText().equals(MENU3)) {

                    Intent intent = new Intent(getActivity(), TopPlayersActivity.class);
                    startActivity(intent);
                }

                else if (titleTextView.getText().equals(MENU5)) {

                    Intent intent = new Intent(getActivity(), NewsActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    private void initManagers() {
        // TODO Auto-generated method stub

    }

    private void initViews() {
        // TODO Auto-generated method stub
        iconImageView = (ImageView)getView().findViewById(R.id.itemImg);

        titleTextView = (TextView)getView().findViewById(R.id.itemTxt);

    }

}
