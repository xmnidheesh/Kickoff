
package com.jn.kickoff.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jn.kickoff.R;
import com.jn.kickoff.entity.Squard;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class TeamSquardAdapter extends BaseAdapter {

    private final static String TAG = "RankingAdapter";

    List<Squard> squardList;

    private LayoutInflater inflator;

    private Activity activity;

    /**
     * 
     */
    public TeamSquardAdapter(Activity activity, List<Squard> squardList) {

        this.squardList = squardList;
        inflator = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.e(TAG, "" + squardList.size());
        return squardList.size();

    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        NearHospitalHolder historyHolder = null;
        if (convertView == null) {
            historyHolder = new NearHospitalHolder();

            convertView = inflator.inflate(R.layout.squard_list_item, null);

            historyHolder.playerImage = (ImageView)convertView.findViewById(R.id.player_image);
            historyHolder.playerName = (TextView)convertView.findViewById(R.id.player_name);

            convertView.setTag(historyHolder);

        } else {
            historyHolder = (NearHospitalHolder)convertView.getTag();
        }

        if (UtilValidate.isNotEmpty(squardList.get(position).getImage()))

            Picasso.with(activity).load(squardList.get(position).getImage())
                    .placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).fit()
                    .into(historyHolder.playerImage);

        if (UtilValidate.isNotNull(squardList.get(position).getName())){
            historyHolder.playerName.setText(squardList.get(position).getName());
            
            Log.e(TAG, "name " + squardList.get(position).getName());
            
        }

        return convertView;
    }

    static class NearHospitalHolder {

        public ImageView playerImage;

        public TextView playerName;

    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

}
