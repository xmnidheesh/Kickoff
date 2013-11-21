package com.jn.kickoff.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jn.kickoff.R;
import com.jn.kickoff.holder.Fixture;

public class FixtureAdapter extends BaseAdapter {

    private final static String TAG = "FixtureAdapter";

    List<Fixture> fixtureList;

    private LayoutInflater inflator;

    private Activity activity;

    /**
     * 
     */
    public FixtureAdapter(Activity activity, List<Fixture> fixtureList) {

        this.fixtureList = fixtureList;
        inflator = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fixtureList.size();

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

            convertView = inflator.inflate(R.layout.fixture_list_item, null);

            historyHolder.country_teamb = (TextView)convertView.findViewById(R.id.country_teamb);
            historyHolder.country_teama = (TextView)convertView.findViewById(R.id.country_teama);

            convertView.setTag(historyHolder);

        } else {
            historyHolder = (NearHospitalHolder)convertView.getTag();
        }
        
        historyHolder.country_teama.setText(fixtureList.get(position).getTeam_a());
        historyHolder.country_teamb.setText(fixtureList.get(position).getTeam_b());

        return convertView;
    }

    static class NearHospitalHolder {

        public TextView country_teamb;

        public TextView country_teama;

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
