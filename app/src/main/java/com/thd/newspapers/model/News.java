package com.thd.newspapers.model;

/**
 * Created by Tran Hai Dang on 3/20/2018.
 * Email : tranhaidang2320@gmail.com
 */

public class News {
    private String headline;
    private String source;
    private String imageUrl;
    private String newsUrl;

    public News(String headline, String source, String imageUrl, String newsUrl) {
        this.headline = headline;
        this.source = source;
        this.imageUrl = imageUrl;
        this.newsUrl = newsUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    @Override
    public String toString() {
        return "News{" +
                "headline='" + headline + '\'' +
                ", source='" + source + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                '}';
    }
}
