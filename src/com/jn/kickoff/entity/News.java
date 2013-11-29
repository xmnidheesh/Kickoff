
package com.jn.kickoff.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable{

    private String _id;

    private String date;

    private String leauge_name;

    private String news_image_thumb;

    private String section_name;

    private String news_time;

    private String news_detail_link;

    private String news_heading;

    private String news_summury;

    public News() {
        super();
        // TODO Auto-generated constructor stub
    }

    public News(String date, String leauge_name, String news_image_thumb, String section_name,
            String news_time, String news_detail_link, String news_heading, String news_summury) {
        super();
        this.date = date;
        this.leauge_name = leauge_name;
        this.news_image_thumb = news_image_thumb;
        this.section_name = section_name;
        this.news_time = news_time;
        this.news_detail_link = news_detail_link;
        this.news_heading = news_heading;
        this.news_summury = news_summury;
    }

    public News(Parcel in) {
        date = in.readString();
        leauge_name = in.readString();
        news_image_thumb = in.readString();
        section_name = in.readString();
        news_time = in.readString();
        news_detail_link = in.readString();
        news_heading = in.readString();
        news_summury = in.readString();

    }

    /**
     * @return the _id
     */
    public String get_id() {
        return _id;
    }

    /**
     * @param _id the _id to set
     */
    public void set_id(String _id) {
        this._id = _id;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the leauge_name
     */
    public String getLeauge_name() {
        return leauge_name;
    }

    /**
     * @param leauge_name the leauge_name to set
     */
    public void setLeauge_name(String leauge_name) {
        this.leauge_name = leauge_name;
    }

    /**
     * @return the news_image_thumb
     */
    public String getNews_image_thumb() {
        return news_image_thumb;
    }

    /**
     * @param news_image_thumb the news_image_thumb to set
     */
    public void setNews_image_thumb(String news_image_thumb) {
        this.news_image_thumb = news_image_thumb;
    }

    /**
     * @return the section_name
     */
    public String getSection_name() {
        return section_name;
    }

    /**
     * @param section_name the section_name to set
     */
    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    /**
     * @return the news_time
     */
    public String getNews_time() {
        return news_time;
    }

    /**
     * @param news_time the news_time to set
     */
    public void setNews_time(String news_time) {
        this.news_time = news_time;
    }

    /**
     * @return the news_detail_link
     */
    public String getNews_detail_link() {
        return news_detail_link;
    }

    /**
     * @param news_detail_link the news_detail_link to set
     */
    public void setNews_detail_link(String news_detail_link) {
        this.news_detail_link = news_detail_link;
    }

    /**
     * @return the news_heading
     */
    public String getNews_heading() {
        return news_heading;
    }

    /**
     * @param news_heading the news_heading to set
     */
    public void setNews_heading(String news_heading) {
        this.news_heading = news_heading;
    }

    /**
     * @return the news_summury
     */
    public String getNews_summury() {
        return news_summury;
    }

    /**
     * @param news_summury the news_summury to set
     */
    public void setNews_summury(String news_summury) {
        this.news_summury = news_summury;
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub

        dest.writeString(date);
        dest.writeString(leauge_name);
        dest.writeString(news_image_thumb);
        dest.writeString(section_name);
        dest.writeString(news_time);
        dest.writeString(news_detail_link);
        dest.writeString(news_heading);
        dest.writeString(news_summury);

    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

}
