package com.thd.newspapers.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thd.newspapers.model.News;

import java.util.ArrayList;

/**
 * Created by Tran Hai Dang on 5/2/2018.
 * Email : tranhaidang2320@gmail.com
 */

public class SqliteDB extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "newspaper_manager.db";
    private static final String DB_TABLE = "favorite_news";
    private static final String DB_COLUMN_ID = "id";
    private static final String DB_COLUMN_HEADLINE = "headline";
    private static final String DB_COLUMN_IMAGE_SOURCE = "img_source";
    private static final String DB_COLUMN_NEWS_SOURCE = "source";
    private static final String DB_COLUMN_NEWS_URL = "news_url";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + DB_TABLE + "("
            + DB_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + DB_COLUMN_HEADLINE + " TEXT, "
            + DB_COLUMN_IMAGE_SOURCE + " TEXT, "
            + DB_COLUMN_NEWS_SOURCE + " TEXT, "
            + DB_COLUMN_NEWS_URL + " TEXT)";

    public SqliteDB(Context context) {
        super(context,DB_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<News> getAllFavoriteNews() {
        ArrayList<News> newsList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery("select * from " + DB_TABLE, null);
        while (res.moveToNext()) {
            String headline = res.getString(res.getColumnIndex(DB_COLUMN_HEADLINE));
            String imgSource = res.getString(res.getColumnIndex(DB_COLUMN_IMAGE_SOURCE));
            String source = res.getString(res.getColumnIndex(DB_COLUMN_NEWS_SOURCE));
            String newsURL = res.getString(res.getColumnIndex(DB_COLUMN_NEWS_URL));
            News n = new News(headline,source,imgSource,newsURL);
            n.setFav(true);
            newsList.add(n);
        }
        res.close();
        return newsList;
    }
    public void insertToDB(News news) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_COLUMN_HEADLINE, news.getHeadline());
        contentValues.put(DB_COLUMN_IMAGE_SOURCE, news.getImageUrl());
        contentValues.put(DB_COLUMN_NEWS_SOURCE, news.getSource());
        contentValues.put(DB_COLUMN_NEWS_URL, news.getNewsUrl());
        database.insert(DB_TABLE,null, contentValues);
    }
}
