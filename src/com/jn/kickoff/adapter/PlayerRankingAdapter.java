package com.jn.kickoff.adapter;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jn.kickoff.R;
import com.jn.kickoff.activity.TopPlayersActivity;
import com.jn.kickoff.holder.FifaPlayerDetails;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class PlayerRankingAdapter extends BaseAdapter {

	private final static String TAG = "PlayerRankingAdapter";
	private List<com.jn.kickoff.holder.FifaPlayerDetails> playerTempList = new ArrayList<com.jn.kickoff.holder.FifaPlayerDetails>();
	private LayoutInflater inflator;
	private TopPlayersActivity activity;
	private static final String profileUrl = "http://cdn.content.easports.com/fifa/fltOnlineAssets/C74DDF38-0B11-49b0-B199-2E2A11D1CC13/2014/fut/items/images/players/web/<PICID>.png";
	private static final Pattern profilePicPattern = Pattern.compile("<PICID>");
	private String fname;
	private String lname;
	
	public PlayerRankingAdapter(TopPlayersActivity activity,
			List<FifaPlayerDetails> playerTempList) {

		this.playerTempList = playerTempList;
		inflator = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return playerTempList.size();

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		playerHolder playerHolder = null;
		if (convertView == null) {
			playerHolder = new playerHolder();

			convertView = inflator
					.inflate(R.layout.playerdetailslistitem, null);

			playerHolder.player_firstname = (TextView) convertView
					.findViewById(R.id.player_firstname);
			playerHolder.player_lastname = (TextView) convertView
					.findViewById(R.id.player_lastname);
			playerHolder.player_profileimage = (ImageView) convertView
					.findViewById(R.id.player_profileimage);
			playerHolder.player_type = (TextView) convertView
					.findViewById(R.id.player_type);
			playerHolder.player_dob = (TextView) convertView
					.findViewById(R.id.player_dob);
			

			convertView.setTag(playerHolder);

		} else {
			playerHolder = (playerHolder) convertView.getTag();
		}

		if (UtilValidate.isNotEmpty(playerTempList.get(position)
				.getFirst_name())) {
	
			
			try {
				 fname = URLDecoder.decode(playerTempList.get(position)
						.getFirst_name().toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			
			playerHolder.player_firstname.setText(fname);
		}

		if (UtilValidate.isNotNull(playerTempList.get(position).getLast_name())) {
			
			try {
				lname = URLDecoder.decode(playerTempList.get(position)
						.getLast_name().toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			playerHolder.player_lastname.setText(lname);
		}
		if (UtilValidate.isNotEmpty(playerTempList.get(position).getBase_id())) {
			String userPicUrl = profilePicPattern.matcher(profileUrl)
					.replaceAll(playerTempList.get(position).getBase_id());


			Picasso.with(activity).load(userPicUrl)
					.placeholder(R.drawable.ic_launcher)
					.error(R.drawable.ic_launcher).fit()
					.into(playerHolder.player_profileimage);

		}

		if (UtilValidate.isNotNull(playerTempList.get(position).getType())) {
			
			String output = (playerTempList.get(position)
					.getType().substring(0, 1).toUpperCase() + (playerTempList.get(position)
							.getType().substring(1)));
			playerHolder.player_type.setText(output);
		}
		if (UtilValidate.isNotNull(playerTempList.get(position).getDob())) {
			playerHolder.player_dob.setText(playerTempList.get(position)
					.getDob());
		}

		return convertView;
	}

	static class playerHolder {
		public TextView player_firstname;
		public TextView player_lastname;
		public ImageView player_profileimage;
		public TextView player_type;
		public TextView player_dob;
	}

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

		// ActivityData activityData = dataList.get((Integer) item);
		if (playerTempList.contains(item))
			playerTempList.remove(item);

	}

	public void insert(Object item, int from, int to) {

		FifaPlayerDetails activityData = playerTempList.get((Integer) item);
		playerTempList.remove(from);
		playerTempList.add(to, activityData);
		notifyDataSetChanged();

	}
}
