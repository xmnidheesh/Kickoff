package com.jn.kickoff.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.jn.kickoff.AnimationSounds;
import com.jn.kickoff.R;
import com.jn.kickoff.SettingsPageSharedPreference;
import com.jn.kickoff.adapter.FixtureAdapter;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.holder.Fixture;
import com.jn.kickoff.manager.CountryManager;
import com.jn.kickoff.utils.ProgressWheel;
import com.jn.kickoff.utils.UtilValidate;

public class FixtureActivity extends Activity {

	private List<Fixture> fixtureList = new ArrayList<Fixture>();

	private FixtureAdapter fixtureAdapter;
	
	private ListView listview_fixture;
	
	private AdView adView;
	
	AdRequest adRequest;
	
	private static ProgressWheel progressWheel;
	
	private static RelativeLayout relativeLayoutprogresswheel;
	
	boolean loadingFinished = true;
	
	private TextView progressBarDetail_text;
	
    private ImageView closeBtn;

    private PopupWindow popupWindow;
    
    private TextView fixture_Venue ;

    private TextView fixture_Date;

    private TextView fixture_time ;
    
    private RelativeLayout relative_fixture_top;
     
    private TableLayout tableLayout1;
    
    private LinearLayout venue_detail_container;
    
    private TextView fixture_teama;
    
    private TextView fixture_teamb;

	private AnimationSounds animationSounds;
	
	private ProgressBar pbHeaderProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fixtures);

		initViews();
		initManagers();

		relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
		progressBarDetail_text.setVisibility(View.VISIBLE);
		pbHeaderProgress.setVisibility(View.VISIBLE);
		
		//pbHeaderProgress.setProgress(100);

		 Animation anim = AnimationUtils.loadAnimation(this, R.anim.blink);
	        anim.setRepeatCount(-1);
	        anim.setRepeatMode(Animation.RESTART);
	        pbHeaderProgress.startAnimation(anim);
	        pbHeaderProgress.setVisibility(View.VISIBLE);
	        pbHeaderProgress.setVisibility(View.VISIBLE);
	        pbHeaderProgress.setProgress(10);
		
		

		FrameLayout layout = (FrameLayout) findViewById(R.id.linear);
		layout.addView(adView);

		// Create an ad request. Check logcat output for the hashed device
		// ID to
		// get test ads on a physical device.
		adRequest = new AdRequest();
		// adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		// adRequest.addTestDevice("C6205A36E35671ED5388B025B0B82698");
		// adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		// adRequest.addTestDevice("0B1CF3FD4AA0118FAB350F160041EFC7");
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = tm.getDeviceId();
		adRequest.addTestDevice(deviceid);

		// Start loading the ad in the background.

		(new Thread() {
			public void run() {
				Looper.prepare();
				adView.loadAd(adRequest);
			}
		}).start();

		adView.loadAd(adRequest);

	new FixtureScrappingTask()
				.execute("http://m.fifa.com/worldcup/preliminaries/matches/fixtures.html");
		
		

	}

	public void animationSoundBite() {

		switch (SettingsPageSharedPreference.getSounds(FixtureActivity.this)) {

		case SOUND_ON:

			animationSounds.whooshSoundon();

			break;

		case SOUND_OFF:

			animationSounds.whooshSoundoff();

			break;

		default:
			break;
		}
	}
	private void initManagers() {
		// TODO Auto-generated method stub
		adView = new AdView(this, AdSize.SMART_BANNER,
				Constants.AppConstants.ADDMOB);
		animationSounds=new AnimationSounds(FixtureActivity.this);

	}

	private void initViews() {
		// TODO Auto-generated method stub
		listview_fixture = (ListView) findViewById(R.id.listview_fixture);
		progressWheel = (ProgressWheel) findViewById(R.id.progressBarDetail);
		relativeLayoutprogresswheel = (RelativeLayout) findViewById(R.id.progress_relative_Detail);
		progressBarDetail_text = (TextView) findViewById(R.id.progressBarDetail_text);
		relative_fixture_top= (RelativeLayout) findViewById(R.id.relative_fixture_top);
		pbHeaderProgress= (ProgressBar) findViewById(R.id.pbHeaderProgress);
	

	}

	private class FixtureScrappingTask extends
			AsyncTask<String, String, String> {

		private CountryManager countryManager = new CountryManager();

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {

			// TODO Auto-generated method stub
			super.onPostExecute(result);
			relativeLayoutprogresswheel.setVisibility(View.INVISIBLE);
			progressBarDetail_text.setVisibility(View.INVISIBLE);
			pbHeaderProgress.setVisibility(View.INVISIBLE);
			
			Log.e("", "in post value of fixtureList"+fixtureList);
			if(UtilValidate.isEmpty(fixtureList))
			{
				 NoInternetpopup();
			}
			else
			{
			
			fixtureAdapter = new FixtureAdapter(FixtureActivity.this,
					fixtureList);
			listview_fixture.setAdapter(fixtureAdapter);

			listview_fixture.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					animationSoundBite();
					String fulldate = fixtureList.get(arg2).getDate();
					String teama = fixtureList.get(arg2).getTeam_a();
					String teamb = fixtureList.get(arg2).getTeam_b();
					String venue = fixtureList.get(arg2).getVenue();

					String[] parts = fixtureList.get(arg2).getDate().split(" ");
					String date = parts[0];
					String time = parts[1];
					
					PopUpFixtureDetails(venue,date,time,teama,teamb);
					
				}
			});

		}
		}

		@Override
		protected String doInBackground(String... params) {

			loadingFinished = false;
			// SHOW LOADING IF IT ISNT
			// ALREADY
			// VISIBLE
			relativeLayoutprogresswheel.setVisibility(View.VISIBLE);
			progressBarDetail_text.setVisibility(View.VISIBLE);
			fixtureList = countryManager.scrapUrlForFixtures(params[0]);

			return null;
		}

	}
	
	
    public void PopUpFixtureDetails(String venue,String date,String time,String teama,String teamb) {

        LayoutInflater layoutInflater = (LayoutInflater)this.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.fixture_details, null);


        fixture_Venue = (TextView)popupView.findViewById(R.id.fixture_Venue);

        fixture_Date = (TextView)popupView.findViewById(R.id.fixture_Date);

        fixture_time = (TextView)popupView.findViewById(R.id.fixture_time);

    	tableLayout1= (TableLayout)popupView. findViewById(R.id.tableLayout1);

        closeBtn = (ImageView)popupView.findViewById(R.id.closeBtn);
        
        fixture_teama = (TextView)popupView.findViewById(R.id.fixture_teama);
        
        fixture_teamb= (TextView)popupView.findViewById(R.id.fixture_teamb);
        
        venue_detail_container= (LinearLayout)popupView.findViewById(R.id.venue_detail_container);

        popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);

        /**
         * animation ...
         */
        popupWindow.setAnimationStyle(R.style.PopUpAnimationtwinslide);

        popupWindow.showAtLocation(relative_fixture_top, Gravity.CENTER, 0, 0);

        popupWindow.setFocusable(true);

        popupWindow.update();

        if (UtilValidate.isNotNull(venue))
        	fixture_Venue.setText(venue);
        if (UtilValidate.isNotNull(date))
        	fixture_Date.setText(date);
        if (UtilValidate.isNotNull(time))
        	fixture_time.setText(time);
        if (UtilValidate.isNotNull(teama))
        	fixture_teama.setText(teama);
        if (UtilValidate.isNotNull(teamb))
        	fixture_teamb.setText(teamb);
        tableLayout1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        
        closeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        
        venue_detail_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        
        

    }
	
	
	
    public void NoInternetpopup() 
	{
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(
				R.layout.advertisement_layout, null);

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);

		/**
		 * animation ...
		 */
		// popupWindow.setAnimationStyle(R.style.PopUpAnimation);
		findViewById(R.id.relative_fixture_top).post(new Runnable() {
			   public void run() {
				   popupWindow.showAtLocation(findViewById(R.id.relative_fixture_top), Gravity.CENTER, 0, 0);
			   }
			});
		popupWindow.setFocusable(true);

		popupWindow.update();

		// set the custom dialog components - text,
		// image and
		// button
		
		TextView textView_nointernet = (TextView) popupView
				.findViewById(R.id.textView_nointernet);
		
	


		Button dialogButtonOk = (Button) popupView.findViewById(R.id.button_ok);
		// if button is clicked, close the custom dialog
		dialogButtonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				popupWindow.dismiss();
				finish();

			}

		});

	}
    	
	
	

}
