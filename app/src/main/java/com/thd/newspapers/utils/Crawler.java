package com.thd.newspapers.utils;

import com.thd.newspapers.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tran Hai Dang on 3/20/2018.
 * Email : tranhaidang2320@gma41i1l.com
 */

class Crawler {

    static ArrayList<News> getNews(String category) throws IOException {
        ArrayList<News> newsList = new ArrayList<>();
        String BASE_URL = "https://baomoi.com/";
        Document doc = Jsoup.connect(BASE_URL + category).get();
        Elements elements = doc.select("div.story").not("is-pr");
        int count = 0;
        for (Element e : elements) {
            String url = e.select("a[href]:has(i)").attr("abs:href");
            String headline = e.select("a[href]").attr("title");
            String imgUrl = e.select("img").attr("src");
            String source = e.select("a.source").text();
            News news = new News(headline,source,imgUrl,url);
            if (!news.isNull()) {
                newsList.add(news);
            }
            count++;
            if (count == 20) break;
        }
        return newsList;
    }
}
