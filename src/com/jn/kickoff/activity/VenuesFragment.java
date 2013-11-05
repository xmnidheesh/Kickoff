
package com.jn.kickoff.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jn.kickoff.FIFA;
import com.jn.kickoff.R;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.holder.Venue;
import com.jn.kickoff.manager.VenueManager;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class VenuesFragment extends Activity {

    private static final String TAG = VenuesFragment.class.getName();

    private List<Venue> venueList;

    private VenueManager venueManager;

    private LinearLayout myGallery;

    private RelativeLayout venueRelative;

    private ImageView venueImageLarge;

    private TextView moreTextView;

    private TextView venueCapacity;

    private TextView venueDes;

    private TextView venueName;

    private TextView venueFullname;

    private TextView venueCity;

    private TextView venueOwner;

    private TextView venueOpened;

    private TextView venueSurface;

    private TextView venueWebsite;

    private ImageView closeBtn;

    private PopupWindow popupWindow;

    private Venue venueSelected;
    
    private AdView adView;
	AdRequest adRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venues_page);

        initViews();
        initManagers();
        
       //showAdvertisementPopUp(this);
        
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
		
		 (new Thread() { public void run() { Looper.prepare();
		 adView.loadAd(adRequest); } }).start();
		 
		adView.loadAd(adRequest);
        


        moreTextView.setText("More...");

        venueList = venueManager.getAllVenuesFromTable();
        
        if (UtilValidate.isNotNull(venueList)) {

            venueSelected = venueList.get(0);

            // Load default image as first venue's image
            Picasso.with(VenuesFragment.this).load(venueList.get(0).getVenue_image())
                    .placeholder(R.drawable.empty_photo) //
                    .error(R.drawable.empty_photo).fit().into(venueImageLarge);

            venueCapacity.setText("Capacity :" + venueList.get(0).getVenue_capacity());
            venueName.setText(venueList.get(0).getVenue_name() + ","
                    + venueList.get(0).getVenue_city());
            venueDes.setText(venueList.get(0).getVenue_des());

            for (Venue _element : venueList) {

                myGallery.addView(insertPhoto(_element));
            }

        }

    }

    /**
     * This method will dynamically creates new views for venues and returns the
     * created view which is added to the horizontal scroll view
     * 
     * @param venue
     * @return View
     */
    private View insertPhoto(final Venue venue) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup vg = (ViewGroup)inflater.inflate(R.layout.gallery_items, null);

        ImageView imageView = (ImageView)vg.findViewById(R.id.venueImage);

        if (UtilValidate.isNotNull(venue.getVenue_image())) {
            Picasso.with(this).load(venue.getVenue_image()).placeholder(R.drawable.empty_photo) //
                    .error(R.drawable.empty_photo).fit().into(imageView);
        }

        imageView.setOnClickListener(new GalleryImageClick(venue));

        moreTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showVenueDetails(venueSelected);

            }
        });

        return vg;
    }

    /**
     * This method will initialize every views
     */
    private void initViews() {
        // TODO Auto-generated method stub
        // venuePager = (ViewPager)findViewById(R.id.pager);

        myGallery = (LinearLayout)findViewById(R.id.horizontalScrollItems);

        venueImageLarge = (ImageView)findViewById(R.id.imageVenueLarge);

        moreTextView = (TextView)findViewById(R.id.more);

        venueCapacity = (TextView)findViewById(R.id.venueCapacity);

        venueDes = (TextView)findViewById(R.id.venueDes);

        venueName = (TextView)findViewById(R.id.venueName);

        venueRelative = (RelativeLayout)findViewById(R.id.venue_relative);

    }

    /**
     * This method will initialize every object instances
     */
    private void initManagers() {
        // TODO Auto-generated method stub
        venueManager = new VenueManager();
        adView = new AdView(this, AdSize.SMART_BANNER, Constants.AppConstants.ADDMOB);

    }

    private class GalleryImageClick implements OnClickListener {

        private Venue venue;

        public GalleryImageClick(Venue venue) {

            this.venue = venue;
        }

        @Override
        public void onClick(View v) {

            if (UtilValidate.isNotNull(venue) && UtilValidate.isNotNull(venue.getVenue_image())) {

                Picasso.with(VenuesFragment.this).load(venue.getVenue_image())
                        .placeholder(R.drawable.empty_photo) //
                        .error(R.drawable.empty_photo).fit().into(venueImageLarge);

                venueCapacity.setText("Capacity :" + venue.getVenue_capacity());
                venueName.setText(venue.getVenue_name() + "," + venue.getVenue_city());
                venueDes.setText(venue.getVenue_des());

                venueSelected = venue;
            }

        }
    }

    /**
     * This method will show a pop up window for the details of selected venue.
     * 
     * @param venue
     */
    public void showVenueDetails(Venue venue) {

        LayoutInflater layoutInflater = (LayoutInflater)this.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.venue_details, null);

        venueFullname = (TextView)popupView.findViewById(R.id.venueFullnameValue);

        venueCity = (TextView)popupView.findViewById(R.id.venueCityValue);

        venueOwner = (TextView)popupView.findViewById(R.id.venueOwnerValue);

        venueOpened = (TextView)popupView.findViewById(R.id.venueOpenedValue);

        venueSurface = (TextView)popupView.findViewById(R.id.venueSurfaceValue);

        venueWebsite = (TextView)popupView.findViewById(R.id.venueWebsiteValue);

        closeBtn = (ImageView)popupView.findViewById(R.id.closeBtn);

        popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);

        /**
         * animation ...
         */
        popupWindow.setAnimationStyle(R.style.PopUpAnimationTopToBottom);

        popupWindow.showAtLocation(venueRelative, Gravity.TOP, 0, 0);

        popupWindow.setFocusable(true);

        popupWindow.update();

        if (UtilValidate.isNotNull(venue.getVenue_fullname()))
            venueFullname.setText(venue.getVenue_fullname());
        if (UtilValidate.isNotNull(venue.getVenue_city()))
            venueCity.setText(venue.getVenue_city());
        if (UtilValidate.isNotNull(venue.getVenue_owner()))
            venueOwner.setText(venue.getVenue_owner());
        if (UtilValidate.isNotNull(venue.getOpened()))
            venueOpened.setText(venue.getOpened());
        if (UtilValidate.isNotNull(venue.getVenue_surface()))
            venueSurface.setText(venue.getVenue_surface());
        if (UtilValidate.isNotNull(venue.getVenue_website()))
            venueWebsite.setText(venue.getVenue_website());

        closeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

    }

    public void showAdvertisementPopUp(Activity activity) {

        LayoutInflater layoutInflater = (LayoutInflater)FIFA.getContext().getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.advertisement_layout, null);
        //a152774de5cc614
 
        
        final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);

        /**
         * animation ...
         */
        popupWindow.setAnimationStyle(R.style.PopUpAnimationBottomToTop);
        
        

		// Add the AdView to the view hierarchy. The view will have no size
		// until the ad is loaded.
		

        findViewById(R.id.venue_relative).post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(venueRelative, Gravity.BOTTOM, 0, 0);
            }
        });

        popupWindow.setFocusable(true);

        popupWindow.update();

       

    }
    
    



}
