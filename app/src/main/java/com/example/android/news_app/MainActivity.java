package com.example.android.news_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.news_app.utilities.NetworkUtils;
import com.example.android.news_app.utilities.NewsAdapter;
import com.example.android.news_app.utilities.RefreshTasks;
import com.example.android.news_app.utilities.newsitem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.news_app.R.id.nsearch;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object>,NewsAdapter.ItemClickListener {
    static final String TAG = "mainactivity";
    private TextView mNewsTextView;
    private TextView mUrlNewsView;
    private EditText mSearchBoxNewsText;
    private ProgressBar mLoadingIndicator;
    private RecyclerView rv;
    private NewsAdapter adapter;
    private SQLiteDatabase db;
    private Cursor cursor;

    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recyclerview has been updated with database's value;
        rv = (RecyclerView)findViewById(R.id.recyclerview_news);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mNewsTextView = (TextView) findViewById(R.id.ndata);
        mSearchBoxNewsText = (EditText) findViewById(R.id.search);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);
        if(isFirst)
        {
            LoaderManager load =getSupportLoaderManager();
            load.restartLoader(NEWS_LOADER,null,this).forceLoad();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleUtillities.scheduleRefresh(this);

    }
    protected void onStart()
    {
        super.onStart();
        //to write into database
        db=new DatabaseHelper(MainActivity.this)getReadableDatabase();
        cursor= DatabaseUtils.getAll(db);
        adapter =new NewsAdapter(cursor,this);
        rv.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {

            //String s = nsearch.getText().toString();
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    protected void onStop()
    {
        super.onStop();
        db.close();
        cursor.close();
    }

    private void loadNewsData() {

        URL NewsSearchUrl = NetworkUtils.buildUrl();
        mUrlNewsView.setText(NewsSearchUrl.toString());


        new newsinfo("result").execute();
    }
    // this is call back of Async task
    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this){
            @Override
            protected void onStartLoading(){
                super.onStartLoading();
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }
            @Override
            public Void loadInBackground() {
                //here articles gets refreshed
                RefreshTasks.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        //after loading loading indicator gets vanished
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        db=new DatabaseHelper((MainActivity.this).getReadableDatabase());
        cursor = com.example.android.news_app.DatabaseUtils.getAll(db);

        // Reset data in recyclerview
        adapter = new NewsAdapter(cursor, this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.newsitem.COLUMN_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public class newsinfo extends AsyncTask<URL, Void, ArrayList<newsitem>> {


        String query;
        newsinfo(String s) {
            query = s;
        }

        public newsinfo() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<newsitem> doInBackground(URL... params) {
            ArrayList<newsitem> result = null;
            URL url = NetworkUtils.buildUrl();
            Log.d(TAG, "url: " + url.toString());
            try {
                String json=NetworkUtils.getResponseFromHttpUrl(url);

                result = NetworkUtils.parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<newsitem> data) {
            super.onPostExecute(data);
            mLoadingIndicator.setVisibility(View.GONE);
            if (data != null) {
                NewsAdapter adapter = new NewsAdapter(data, new NewsAdapter.ItemClickListener()
                {
                    @Override
                    public void onItemClick(Cursor cursor, int clickedItemIndex) {

                    }

                    @Override
                    public void onItemClick(int clickedItemIndex)
                    {
                        String url = data.get(clickedItemIndex).getUrl();
                        Uri webpage=Uri.parse(url);
                        Intent intent=new Intent(Intent.ACTION_VIEW,webpage);
                        startActivity(intent);
                        Log.d("main activity", String.format("Url %s", url));
                    }
                });
                rv.setAdapter(adapter);

            }
        }

    }
}
