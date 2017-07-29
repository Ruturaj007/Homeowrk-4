package com.example.android.news_app.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.news_app.DatabaseHelper;
import com.example.android.news_app.DatabaseUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Welcome To Future on 7/28/2017.
 */

public class RefreshTasks
{
    //here created  a method for have your activity load what's currently in your database into the recyclerview for display and passing a context
    //taking an arraylist and then writing all the data into the database using getWritableDatabase method

    public static final String ACTION_REFRESH = "refresh";


    public static void refreshArticles(Context context) {
        ArrayList<newsitem> result = null;
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();

        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DatabaseUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }
}
