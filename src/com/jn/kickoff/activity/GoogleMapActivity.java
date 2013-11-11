package com.jn.kickoff.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jn.kickoff.R;

public class GoogleMapActivity extends Activity { // Google Map
    private GoogleMap googleMap;
    
    private TextView satellitTextView;
    
    private TextView mapTextView;
    
    LatLng latlong;
    
    public String TAG=GoogleMapActivity.class.getName();
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);
        
        initViews();
        initManagers();
        
        Intent intent = getIntent();
        String latitude = intent.getExtras().getString("latitude");
        String longitude = intent.getExtras().getString("longitude");
        String name =intent.getExtras().getString("name");
 
        try {
            // Loading map
            initilizeMap();
            
         // latitude and longitude
            double latitude_double = Double.parseDouble(latitude);
            double longitude_double = Double.parseDouble(longitude);
            
            Log.e(TAG, "latitude"+latitude_double);
            Log.e(TAG, "longitude"+longitude_double);
            latlong = new LatLng(latitude_double, longitude_double);
           /* // create marker
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude_double, longitude_double)).title("Kick Off ");
         // ROSE color icon
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));*/
            
            googleMap.addMarker(new MarkerOptions()
			.position(latlong)
			.title("Kick Off")
			.snippet(name)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.close)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong,
				   15));
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            
             
            // adding marker
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        
        
	    satellitTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			    // TODO Auto-generated method stub

			    satellitTextView.setTextColor(Color.parseColor("#000000"));
			    mapTextView.setTextColor(Color.WHITE);
			    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong,
			    		15));
			    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			}
		    });

		    mapTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			    // TODO Auto-generated method stub

			    satellitTextView.setTextColor(Color.WHITE);
			    mapTextView.setTextColor(Color.parseColor("#000000"));

			    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong,
			    		15));
			    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

			}
		    });

        
        
    }
 
    private void initManagers() {
		// TODO Auto-generated method stub
		
	}

	private void initViews() {
		// TODO Auto-generated method stub
		satellitTextView = (TextView) findViewById(R.id.rb_map);
		mapTextView = (TextView) findViewById(R.id.rb_satellite);
	}

	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }}
