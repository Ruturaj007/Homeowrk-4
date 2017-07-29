package com.example.android.news_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Welcome To Future on 7/28/2017.
 */

//when you extends SQLiteOpenHelper class must implement 2 methods 1)onCreate 2)onUpdate
public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsinfo.db";

    private static final String TAG="dbhelper";

    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    SQLiteDatabase db;

    final String TABLE_CREATE = "CREATE TABLE " + Contract.newsitem.TABLE_NAME + " (" +
            Contract.newsitem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Contract.newsitem.COLUMN_SOURCE + " TEXT NOT NULL, " +
            Contract.newsitem.COLUMN_AUTHOR + " TEXT NOT NULL, " +
            Contract.newsitem.COLUMN_TITLE + " TEXT NOT NULL, " +
            Contract.newsitem.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            Contract.newsitem.COLUMN_URL + " TEXT NOT NULL, " +
            Contract.newsitem.COLUMN_URL_TO_IMAGE + " TEXT NOT NULL, " +
            Contract.newsitem.COLUMN_PUBLISHED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            "); ";
    //for creating a database for the first time ..
    public void onCreate(SQLiteDatabase db)
    {
        //this string will create a table contaning id,column source,author,title,description,url;
        db.execSQL(TABLE_CREATE);
        this.db=db;
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If dbversion changes then drop table and create new db.
        String query="DROP TABLE IF EXISTS " +Contract.newsitem.TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

}
