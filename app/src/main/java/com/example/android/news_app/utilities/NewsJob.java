package com.example.android.news_app.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.android.news_app.DatabaseHelper;
import com.example.android.news_app.DatabaseUtils;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.news_app.utilities.RefreshTasks.refreshArticles;

/**
 * Created by Welcome To Future on 7/28/2017.
 */


    public class NewsJob extends JobService {
        AsyncTask mBackgroundTask;

        @Override
        public boolean onStartJob(final JobParameters job) {
            mBackgroundTask = new AsyncTask() {
                @Override
                protected void onPreExecute() {
                    Toast.makeText(NewsJob.this, "News refreshed", Toast.LENGTH_SHORT).show();
                    super.onPreExecute();
                }

                @Override
                protected Object doInBackground(Object[] params) {
                    refreshArticles(NewsJob.this);
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    jobFinished(job, false);
                    super.onPostExecute(o);

                }
            };


            mBackgroundTask.execute();

            return true;
        }

        @Override
        public boolean onStopJob(JobParameters job) {

            if (mBackgroundTask != null) mBackgroundTask.cancel(false);

            return true;
        }


    }


