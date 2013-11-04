
package com.fifa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import com.fifa.Animation.DepthPageTransformer;
import com.fifa.adapter.TestFragmentAdapter;
import com.fifa.constants.Constants;
import com.fifa.db.DbHelper;

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

        mainMenuViewPager.setPageTransformer(true, new DepthPageTransformer());
        // mainMenuViewPager.setPageTransformer(true, new
        // ZoomOutPageTransformer());

        mainMenuFragments.add(fragmentItemsVenu);
        mainMenuFragments.add(fragmentItemsFixture);
        mainMenuFragments.add(fragmentItemsRanking);
        mainMenuFragments.add(fragmentItemsTopPlayers);

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
                .getDrawable(R.drawable.venue));

        fragmentItemsFixture = new MainMenuFragmentItems(MENU2, MainActivity.this.getResources()
                .getDrawable(R.drawable.second));

        fragmentItemsRanking = new MainMenuFragmentItems(MENU3, MainActivity.this.getResources()
                .getDrawable(R.drawable.second));

        fragmentItemsTopPlayers = new MainMenuFragmentItems(MENU4, MainActivity.this.getResources()
                .getDrawable(R.drawable.first));

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
