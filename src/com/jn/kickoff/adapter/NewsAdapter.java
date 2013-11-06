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
import com.jn.kickoff.holder.News;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class NewsAdapter extends BaseAdapter {

    private final static String TAG = "NewsAdapter";

    List<News> newsList;

    private LayoutInflater inflator;

    private Activity activity;
    private static final String profileUrl = "http://m.fifa.com<PICID>";
	private static final Pattern profilePicPattern = Pattern.compile("<PICID>");

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

            historyHolder.textView_heading = (TextView)convertView.findViewById(R.id.textView_heading);
            historyHolder.imageView_bg = (ImageView)convertView.findViewById(R.id.imageView_bg);

            convertView.setTag(historyHolder);

        } else {
            historyHolder = (NearHospitalHolder)convertView.getTag();
        }
        
        
        if(UtilValidate.isNotNull(newsList.get(position).getNewsheading()))
        {
     historyHolder.textView_heading.setText(newsList.get(position).getNewsheading());
        }
        
        if(UtilValidate.isNotNull(newsList.get(position).getNewsimage()))
        {
 	String userPicUrl = profilePicPattern.matcher(profileUrl)
			.replaceAll(newsList.get(position).getNewsimage());

     Picasso.with(activity).load(userPicUrl)
		.placeholder(R.drawable.ic_launcher)
		.error(R.drawable.ic_launcher).fit()
		.into(historyHolder.imageView_bg);
        }


        return convertView;
    }

    static class NearHospitalHolder {

        public TextView textView_heading;

        public ImageView imageView_bg;

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
