package com.thd.newspapers.model;

/**
 * Created by Tran Hai Dang on 3/20/2018.
 * Email : tranhaidang2320@gmail.com
 */

public class News {
    private long id;
    private String headline;
    private String source;
    private String imageUrl;
    private String newsUrl;
    private boolean isFav;

    public News(String headline, String source, String imageUrl, String newsUrl) {
        this.headline = headline;
        this.source = source;
        this.imageUrl = imageUrl;
        this.newsUrl = newsUrl;
        this.isFav = false;
        this.id = -1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
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
    public boolean isNull() {
        return headline.isEmpty() || source.isEmpty() || imageUrl.isEmpty() || newsUrl.isEmpty();
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", headline='" + headline + '\'' +
                ", source='" + source + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                ", isFav=" + isFav +
                '}';
    }
}
