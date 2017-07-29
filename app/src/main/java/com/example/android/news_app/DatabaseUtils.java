package com.example.android.news_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.news_app.utilities.newsitem;

import java.util.ArrayList;

import static com.example.android.news_app.Contract.newsitem.COLUMN_AUTHOR;
import static com.example.android.news_app.Contract.newsitem.COLUMN_DESCRIPTION;
import static com.example.android.news_app.Contract.newsitem.COLUMN_PUBLISHED_AT;
import static com.example.android.news_app.Contract.newsitem.COLUMN_SOURCE;
import static com.example.android.news_app.Contract.newsitem.COLUMN_TITLE;
import static com.example.android.news_app.Contract.newsitem.COLUMN_URL;
import static com.example.android.news_app.Contract.newsitem.COLUMN_URL_TO_IMAGE;
import static com.example.android.news_app.Contract.newsitem.TABLE_NAME;

/**
 * Created by Welcome To Future on 7/28/2017.
 */

//this class is for accessing inserting and deleting the data from database in short editing
//getAll for getting all the news items from database
public class DatabaseUtils
{


        public static Cursor getAll(SQLiteDatabase db) {
            Cursor cursor = db.query(
                    TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    COLUMN_PUBLISHED_AT + " DESC"
            );
            return cursor;
        }

        public static void bulkInsert(SQLiteDatabase db, ArrayList<newsitem> NI)
        {
            db.beginTransaction();
            try {
                for (newsitem news : NI) {
                    ContentValues C = new ContentValues();
                    C.put(COLUMN_SOURCE, news.getSource());
                    C.put(COLUMN_AUTHOR, news.getAuthor());
                    C.put(COLUMN_TITLE, news.getTitle());
                    C.put(COLUMN_DESCRIPTION, news.getDescription());
                    C.put(COLUMN_URL, news.getUrl());
                    C.put(COLUMN_URL_TO_IMAGE, news.getUrlToImage());
                    C.put(COLUMN_PUBLISHED_AT, news.getPublishedAt());
                    db.insert(TABLE_NAME, null, C);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
            }
        }
    public static void deleteAll(SQLiteDatabase db)
    {
        db.delete(TABLE_NAME, null, null);
    }
}

