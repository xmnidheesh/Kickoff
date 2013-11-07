package com.jn.kickoff.adapter;

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

import com.jn.kickoff.R;
import com.jn.kickoff.holder.NewsHeadlines;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class NewsAdapter extends BaseAdapter {

    private final static String TAG = "NewsAdapter";

    List<NewsHeadlines> newsList;

    private LayoutInflater inflator;

    private Activity activity;

    /**
     * 
     */
    public NewsAdapter(Activity activity, List<NewsHeadlines> newsList) {

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

            historyHolder.textView_heading = (TextView)convertView.findViewById(R.id.textView_heading);
            historyHolder.imageView_bg = (ImageView)convertView.findViewById(R.id.imageView_bg);
            historyHolder. textView_desc= (TextView)convertView.findViewById(R.id.textView_desc);

            convertView.setTag(historyHolder);

        } else {
            historyHolder = (NearHospitalHolder)convertView.getTag();
        }
        
        
        if(UtilValidate.isNotNull(newsList.get(position).getHeadline()))
        {
     historyHolder.textView_heading.setText(newsList.get(position).getHeadline());
        }
        if(UtilValidate.isNotNull(newsList.get(position).getDescription()))
        {
     historyHolder.textView_desc.setText(newsList.get(position).getDescription());
        }
        if(UtilValidate.isNotEmpty(newsList.get(position).getImages()))
        {
        	 if(UtilValidate.isNotNull(newsList.get(position).getImages()))
        	 {
        	//historyHolder.textView_heading.setText(newsList.get(position).getHeadline());
        	String userPicUrl = newsList.get(position).getImages().get(0).getUrl();

     Picasso.with(activity).load(userPicUrl)
		.placeholder(R.drawable.ic_launcher)
		.error(R.drawable.ic_launcher).fit()
		.into(historyHolder.imageView_bg);
        }
    }


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
