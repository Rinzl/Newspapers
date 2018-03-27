package com.thd.newspapers.utils;

import android.os.AsyncTask;

import com.thd.newspapers.model.News;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tran Hai Dang on 3/21/2018.
 * Email : tranhaidang2320@gmail.com
 */

public class CrawlerAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {
    public interface OnCompleteTask {
        void onComplete(ArrayList<News> newsList);
    }
    private OnCompleteTask onCompleteTask;

    public void setOnCompleteTask(OnCompleteTask onCompleteTask) {
        this.onCompleteTask = onCompleteTask;
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {
        ArrayList<News> newsList = new ArrayList<>();
        try {
            newsList = Crawler.getNews(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        super.onPostExecute(news);
        onCompleteTask.onComplete(news);
    }
}
