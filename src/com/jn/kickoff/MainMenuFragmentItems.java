package com.jn.kickoff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jn.kickoff.activity.CountryRanking;
import com.jn.kickoff.activity.FixtureActivity;
import com.jn.kickoff.activity.NewsActivity;
import com.jn.kickoff.activity.TopPlayers;
import com.jn.kickoff.activity.VenuesFragment;

@SuppressLint("ValidFragment")
public class MainMenuFragmentItems extends Fragment {

	private String title;

	private Drawable iconImage;

	private ImageView iconImageView;

	private TextView titleTextView;

	@SuppressLint("ValidFragment")
	public MainMenuFragmentItems(String title, Drawable image) {

		this.title = title;

		this.iconImage = image;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_menu_items, null);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initViews();
		initManagers();

		iconImageView.setImageDrawable(iconImage);

		titleTextView.setText(title);
		
		

		titleTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (titleTextView.getText().equals("venues")) {

					/*Intent intent = new Intent(getActivity(),
							VenuesFragment.class);
					startActivity(intent);*/
					Intent intent = new Intent(getActivity(),
							NewsActivity.class);
					startActivity(intent);
					
				} else if (titleTextView.getText().equals("fixtures")) {
					Intent intent = new Intent(getActivity(),
							FixtureActivity.class);
					startActivity(intent);
					
				} else if (titleTextView.getText().equals("TopTeams")) {

					Intent intent = new Intent(getActivity(),
							CountryRanking.class);
					startActivity(intent);

				} else if (titleTextView.getText().equals("TopPlayers")) {

					Intent intent = new Intent(getActivity(), TopPlayers.class);
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
		iconImageView = (ImageView) getView().findViewById(R.id.itemImg);

		titleTextView = (TextView) getView().findViewById(R.id.itemTxt);

	}

}
