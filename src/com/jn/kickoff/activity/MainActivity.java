
package com.jn.kickoff.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initManagers();
        doDataBaseCreation();
        
        Parse.initialize(this, "LmIM3uWcyelvcVjl0m6XoL528glzHpsNNEp7pf9X", "fglG1RAhdrCifoDOSwp9fYDUs50hnjY38UqQLX9h");
        PushService.subscribe(this, "Kickoff",PushNotificationActivity .class);
        PushService.setDefaultPushCallback(this, PushNotificationActivity.class);
        ParseInstallation pi = ParseInstallation.getCurrentInstallation();
        pi.saveEventually();
        

        mainMenuViewPager.setPageTransformer(true, new DepthPageTransformer());
        // mainMenuViewPager.setPageTransformer(true, new
        // ZoomOutPageTransformer());

        mainMenuFragments.add(fragmentItemsVenu);
        mainMenuFragments.add(fragmentItemsFixture);
        mainMenuFragments.add(fragmentItemsRanking);
        mainMenuFragments.add(fragmentItemsTopPlayers);
        mainMenuFragments.add(fragmentItemsNews);
        testFragmentAdapter = new TestFragmentAdapter(getSupportFragmentManager(),
                mainMenuFragments);
        mainMenuViewPager.setAdapter(testFragmentAdapter);
        
    }

    private void initViews() {

        mainMenuViewPager = (ViewPager)findViewById(R.id.pager);
    }

    private void initManagers() {

        FIFA.setContext(getApplicationContext());
        dbHelper = new DbHelper(this);

        fragmentItemsVenu = new MainMenuFragmentItems(MENU1, MainActivity.this.getResources()
                .getDrawable(R.drawable.venue),0);
        

        fragmentItemsFixture = new MainMenuFragmentItems(MENU2, MainActivity.this.getResources()
                .getDrawable(R.drawable.fixtures),1);

        fragmentItemsRanking = new MainMenuFragmentItems(MENU3, MainActivity.this.getResources()
                .getDrawable(R.drawable.topplayers),2);

        fragmentItemsTopPlayers = new MainMenuFragmentItems(MENU4, MainActivity.this.getResources()
                .getDrawable(R.drawable.topteams),3);
        fragmentItemsNews = new MainMenuFragmentItems(MENU5, MainActivity.this.getResources()
                .getDrawable(R.drawable.news),4);


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
    
}
