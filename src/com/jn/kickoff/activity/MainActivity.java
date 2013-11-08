package com.jn.kickoff.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jn.kickoff.AppRaterManager;
import com.jn.kickoff.FIFA;
import com.jn.kickoff.MainMenuFragmentItems;
import com.jn.kickoff.R;
import com.jn.kickoff.Animation.DepthPageTransformer;
import com.jn.kickoff.adapter.TestFragmentAdapter;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.db.DbHelper;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * @author nidheesh
 */
public class MainActivity extends FragmentActivity implements Constants {

	private static final String TAG = MainActivity.class.getName();

	private TestFragmentAdapter testFragmentAdapter;

	private MainMenuFragmentItems fragmentItemsVenu;

	private MainMenuFragmentItems fragmentItemsFixture;

	private MainMenuFragmentItems fragmentItemsRanking;

	private MainMenuFragmentItems fragmentItemsTopPlayers;

	private MainMenuFragmentItems fragmentItemsNews;

	private ViewPager mainMenuViewPager;

	private List<Fragment> mainMenuFragments = new ArrayList<Fragment>();

	private DbHelper dbHelper;

	private Boolean doubleBackToExitPressedOnce = false;

	private LinearLayout lineartop;

	private PopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initManagers();
		doDataBaseCreation();
		 new PushNotificationThread().start();
		mainMenuViewPager.setPageTransformer(true, new DepthPageTransformer());
		// mainMenuViewPager.setPageTransformer(true, new
		// ZoomOutPageTransformer());

		mainMenuFragments.add(fragmentItemsVenu);
		mainMenuFragments.add(fragmentItemsFixture);
		mainMenuFragments.add(fragmentItemsRanking);
		mainMenuFragments.add(fragmentItemsTopPlayers);
		mainMenuFragments.add(fragmentItemsNews);
		testFragmentAdapter = new TestFragmentAdapter(
				getSupportFragmentManager(), mainMenuFragments);
		mainMenuViewPager.setAdapter(testFragmentAdapter);

	}

	private void initViews() {

		mainMenuViewPager = (ViewPager) findViewById(R.id.pager);
		lineartop = (LinearLayout) findViewById(R.id.lineartop);
	}

	class PushNotificationThread extends Thread {

		public void run() {
			Parse.initialize(MainActivity.this,
					"LmIM3uWcyelvcVjl0m6XoL528glzHpsNNEp7pf9X",
					"fglG1RAhdrCifoDOSwp9fYDUs50hnjY38UqQLX9h");
			PushService.subscribe(MainActivity.this, "Kickoff",
					PushNotificationActivity.class);
			PushService.setDefaultPushCallback(MainActivity.this,
					PushNotificationActivity.class);
			ParseInstallation pi = ParseInstallation.getCurrentInstallation();
			pi.saveEventually();
		}
	}

	private void initManagers() {

		FIFA.setContext(getApplicationContext());
		dbHelper = new DbHelper(this);

		fragmentItemsVenu = new MainMenuFragmentItems(MENU1, MainActivity.this
				.getResources().getDrawable(R.drawable.venue), 0);

		fragmentItemsFixture = new MainMenuFragmentItems(MENU2,
				MainActivity.this.getResources().getDrawable(
						R.drawable.fixtures), 1);

		fragmentItemsRanking = new MainMenuFragmentItems(MENU3,
				MainActivity.this.getResources().getDrawable(
						R.drawable.topplayers), 2);

		fragmentItemsTopPlayers = new MainMenuFragmentItems(MENU4,
				MainActivity.this.getResources().getDrawable(
						R.drawable.topteams), 3);
		fragmentItemsNews = new MainMenuFragmentItems(MENU5, MainActivity.this
				.getResources().getDrawable(R.drawable.news), 4);
		popupWindow = new PopupWindow();

	}

	private void doDataBaseCreation() {

		try {
			dbHelper.createDataBase();
		} catch (IOException e) {

			Log.e(TAG, "Exception occured while trying to create databasse");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
		
		
	
	}
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        switch(item.getItemId())
	        {
	            case R.id.action_settings:
	            	AppRaterManager.showRateDialog(MainActivity.this, null);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }

	/*
	 * @Override public void onBackPressed() { if (doubleBackToExitPressedOnce)
	 * { super.onBackPressed(); return; } this.doubleBackToExitPressedOnce =
	 * true; Toast.makeText(this, "Please click BACK again to exit",
	 * Toast.LENGTH_SHORT).show(); new Handler().postDelayed(new Runnable() {
	 * 
	 * @Override public void run() {
	 * 
	 * doubleBackToExitPressedOnce=false;
	 * 
	 * } }, 2000);
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		showBackandExitPopUp(MainActivity.this);
	}

	private void showBackandExitPopUp(MainActivity mainActivity) {

		LayoutInflater layoutInflater = (LayoutInflater) mainActivity
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(R.layout.backandexit, null);
		TextView textView_exit = (TextView) popupView
				.findViewById(R.id.textView_exit);
		Button button_exit = (Button) popupView
				.findViewById(R.id.cancel_button);
		Button submit_button = (Button) popupView
				.findViewById(R.id.submit_button);

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);

		/**
		 * animation ...
		 */
		popupWindow.setAnimationStyle(R.style.PopUpAnimationtwinslide);

		popupWindow.showAtLocation(lineartop, Gravity.CENTER, 0, 0);

		popupWindow.setFocusable(true);

		popupWindow.update();

		button_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				popupWindow.dismiss();

			}
		});

		submit_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				popupWindow.dismiss();

			}
		});

	}

}
