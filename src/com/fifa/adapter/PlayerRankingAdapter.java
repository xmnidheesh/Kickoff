package com.fifa.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fifa.R;
import com.fifa.holder.FifaPlayerDetails;
import com.fifa.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class PlayerRankingAdapter extends BaseAdapter {

	private final static String TAG = "RankingAdapter";
	List<com.fifa.holder.FifaPlayerDetails> playerTempList = new ArrayList<com.fifa.holder.FifaPlayerDetails>();
	private LayoutInflater inflator;
	private Activity activity;
    private static final String profileUrl = "http://cdn.content.easports.com/fifa/fltOnlineAssets/C74DDF38-0B11-49b0-B199-2E2A11D1CC13/2014/fut/items/images/players/web/<PICID>.png";

    private static final Pattern profilePicPattern = Pattern.compile("<PICID>");


	/**
     * 
     */
	public PlayerRankingAdapter(Activity activity,
			List<FifaPlayerDetails> playerTempList) {

		this.playerTempList = playerTempList;
		inflator = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
this.activity=activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.e(TAG, ""+playerTempList.size());
		return playerTempList.size();
		
	

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		NearHospitalHolder historyHolder = null;
		if (convertView == null) {
			historyHolder = new NearHospitalHolder();

			convertView = inflator.inflate(R.layout.playerdetailslistitem,
					null);

			historyHolder.player_firstname = (TextView) convertView
					.findViewById(R.id.player_firstname);
			historyHolder.player_lastname = (TextView) convertView
					.findViewById(R.id.player_lastname);
			historyHolder.player_profileimage = (ImageView) convertView
					.findViewById(R.id.player_profileimage);
			historyHolder.player_type = (TextView) convertView
					.findViewById(R.id.player_type);
			
			
			convertView.setTag(historyHolder);

		} else {
			historyHolder = (NearHospitalHolder) convertView.getTag();
		}

		if (UtilValidate.isNotEmpty(playerTempList.get(position)
				.getFirst_name())) {
			historyHolder.player_firstname.setText(playerTempList.get(position)
					.getFirst_name());
			Log.e(TAG, ""+playerTempList.get(position)
					.getFirst_name());
		}

		if (UtilValidate.isNotNull(playerTempList.get(position)
				.getLast_name())) {
			historyHolder.player_lastname.setText(playerTempList.get(position)
					.getLast_name());
			Log.e(TAG, ""+playerTempList.get(position)
					.getLast_name());
		}
		if (UtilValidate.isNotEmpty(playerTempList.get(position).getBase_id()))
				{
		   String userPicUrl = profilePicPattern.matcher(profileUrl).replaceAll(
				   playerTempList.get(position).getBase_id());


		Log.e("userPicUrl", userPicUrl);
		
		Picasso.with(activity)
		.load(userPicUrl) 
		.placeholder(R.drawable.ic_launcher) 
		.error(R.drawable.ic_launcher).fit() 
		.into(historyHolder.player_profileimage); 
		

				}
		
		if (UtilValidate.isNotNull(playerTempList.get(position)
				.getType())) {
			historyHolder.player_type.setText(playerTempList.get(position)
					.getType());
		}
		
		return convertView;
	}

	static class NearHospitalHolder {
		public TextView player_firstname;
		public TextView player_lastname;
		public ImageView player_profileimage;
		public TextView player_type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public void remove(Object item) {

		Log.i(TAG, "removing item from pos " + (Integer) item);
		Log.e(TAG, "size in remove:" + playerTempList.size());
		// ActivityData activityData = dataList.get((Integer) item);
		if (playerTempList.contains(item))
			playerTempList.remove(item);

	}

	public void insert(Object item, int from, int to) {
		Log.e(TAG, "size in insert:" + playerTempList.size());
		Log.i(TAG, "inserting item to pos " + (Integer) item);

		FifaPlayerDetails activityData = playerTempList.get((Integer) item);
		playerTempList.remove(from);
		playerTempList.add(to, activityData);
		notifyDataSetChanged();

	}
}
