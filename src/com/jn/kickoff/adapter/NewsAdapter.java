
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
import com.jn.kickoff.entity.News;
import com.jn.kickoff.holder.NewsHeadlines;
import com.jn.kickoff.utils.Util;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class NewsAdapter extends BaseAdapter {

    private final static String TAG = "NewsAdapter";

    List<News> newsList;

    private LayoutInflater inflator;

    private Activity activity;

    /**
     * 
     */
    public NewsAdapter(Activity activity, List<News> newsList) {

        this.newsList = newsList;
        inflator = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.e(TAG, "" + newsList.size());
        return newsList.size();

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

            convertView = inflator.inflate(R.layout.news_list_item, null);

            historyHolder.textView_heading = (TextView)convertView
                    .findViewById(R.id.textView_heading);
            historyHolder.imageView_bg = (ImageView)convertView.findViewById(R.id.imageView_bg);
            historyHolder.textView_desc = (TextView)convertView.findViewById(R.id.textView_desc);

            convertView.setTag(historyHolder);

        } else {
            historyHolder = (NearHospitalHolder)convertView.getTag();
        }

        if (UtilValidate.isNotNull(newsList.get(position).getNews_heading()))
            historyHolder.textView_heading.setText(Util.truncate(newsList.get(position)
                    .getNews_heading(), 25));

        if (UtilValidate.isNotNull(newsList.get(position).getNews_summury()))
            historyHolder.textView_desc.setText(Util.truncate(newsList.get(position)
                    .getNews_summury(), 80));

        if (UtilValidate.isNotNull(newsList.get(position).getNews_image_thumb()))

            Picasso.with(activity).load(newsList.get(position).getNews_image_thumb())
                    .placeholder(R.drawable.empty_photo).error(R.drawable.empty_photo).fit()
                    .into(historyHolder.imageView_bg);

        return convertView;
    }

    static class NearHospitalHolder {

        public TextView textView_heading;

        public ImageView imageView_bg;

        public TextView textView_desc;

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
