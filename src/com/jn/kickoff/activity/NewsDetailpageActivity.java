package com.jn.kickoff.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jn.kickoff.R;
import com.jn.kickoff.utils.UtilValidate;
import com.squareup.picasso.Picasso;

public class NewsDetailpageActivity extends Activity {
	
	private String TAG=NewsDetailpageActivity.class.getName();
	private TextView   textview_heading;
	private TextView   textview_description;
	private TextView   textview_source;
	private TextView   textview_date;
	private ImageView imageview_photo;
	Bundle newsDetailList=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.newsdetail);
	
		initViews();
		initManagers();
		
		newsDetailList = getIntent().getExtras();
		Log.e(TAG, "newsDetailList"+ newsDetailList.getStringArrayList("newsDetailList"));
		
		if (UtilValidate.isNotNull(newsDetailList
				.getStringArrayList("newsDetailList"))) {
			textview_heading.setText(newsDetailList.getStringArrayList("newsDetailList").get(0));
			textview_description.setText(newsDetailList.getStringArrayList("newsDetailList").get(1));
			textview_source.setText(newsDetailList.getStringArrayList("newsDetailList").get(2));
			textview_date.setText(newsDetailList.getStringArrayList("newsDetailList").get(3));
			//imageview_photo.setText(newsDetailList.getStringArrayList("newsDetailList").get(3));
			
			 Picasso.with(this).load(newsDetailList.getStringArrayList("newsDetailList").get(4))
				.placeholder(R.drawable.empty_photo)
				.error(R.drawable.empty_photo).fit()
				.into(imageview_photo);
		}
	}

	private void initManagers() {
		// TODO Auto-generated method stub
	}

	private void initViews() {
		// TODO Auto-generated method stub
		textview_heading=(TextView)findViewById(R.id.textview_heading);
		textview_description=(TextView)findViewById(R.id.textview_description);
		textview_source=(TextView)findViewById(R.id.textview_source);
		textview_date=(TextView)findViewById(R.id.textview_date);
		imageview_photo=(ImageView)findViewById(R.id.imageview_photo);
		
	}

}
