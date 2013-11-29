
package com.jn.kickoff.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsDetails{

    private String headin_text;

    private String news_body_text;

    private String news_image_large;

    private String section_name;

    private String news_time;
    
    

    /**
     * @return the headin_text
     */
    public String getHeadin_text() {
        return headin_text;
    }

    /**
     * @param headin_text the headin_text to set
     */
    public void setHeadin_text(String headin_text) {
        this.headin_text = headin_text;
    }

    /**
     * @return the news_image_large
     */
    public String getNews_image_large() {
        return news_image_large;
    }

    /**
     * @param news_image_large the news_image_large to set
     */
    public void setNews_image_large(String news_image_large) {
        this.news_image_large = news_image_large;
    }

    /**
     * @return the news_body_text
     */
    public String getNews_body_text() {
        return news_body_text;
    }

    /**
     * @param news_body_text the news_body_text to set
     */
    public void setNews_body_text(String news_body_text) {
        this.news_body_text = news_body_text;
    }

    
}
