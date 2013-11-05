
package com.jn.kickoff.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jn.kickoff.R;
import com.jn.kickoff.entity.Country;

public class CountryRankingAdapter extends BaseAdapter {

    private final static String TAG = "RankingAdapter";

    List<Country> countryList;

    private LayoutInflater inflator;

    private Activity activity;

    /**
     * 
     */
    public CountryRankingAdapter(Activity activity, List<Country> countryList) {

        this.countryList = countryList;
        inflator = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.e(TAG, "" + countryList.size());
        return countryList.size();

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

            convertView = inflator.inflate(R.layout.country_ranking_list_item, null);

            historyHolder.countryRank = (TextView)convertView.findViewById(R.id.country_rank);
            historyHolder.countryName = (TextView)convertView.findViewById(R.id.country_name);
            historyHolder.countryPoints = (TextView)convertView.findViewById(R.id.country_points);

            convertView.setTag(historyHolder);

        } else {
            historyHolder = (NearHospitalHolder)convertView.getTag();
        }
        
        historyHolder.countryRank.setText(countryList.get(position).getRank());
        historyHolder.countryName.setText(countryList.get(position).getName());
        historyHolder.countryPoints.setText(countryList.get(position).getPoints());

        return convertView;
    }

    static class NearHospitalHolder {
        public TextView countryRank;

        public TextView countryName;

        public TextView countryPoints;

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
