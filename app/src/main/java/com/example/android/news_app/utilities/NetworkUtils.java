package com.example.android.news_app.utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Welcome To Future on 6/21/2017.
 */

public class NetworkUtils {
    public static final String TAG ="NetworkUtils";
    private static final String BASE_URL = "https://newsapi.org/v1/articles";

    final static String SRC_PARAM = "source";
    final static String SORT_PARAM = "sortBy";
    final static String NEWSAPIKEY_PARAM = "apiKey";

    private final static String src = "the-next-web";
    private final static String sort = "latest";
    private final static String key = "30c34a91fb6945068d88183293b50e2c";


    public static URL buildUrl() {
       // Log.d("network","begining built url");
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SRC_PARAM, src)
                .appendQueryParameter(SORT_PARAM, sort)
                .appendQueryParameter(NEWSAPIKEY_PARAM, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //Log.v(TAG, " URI " + url);

        return url;
    }

    //so now we are using getResponseFromHttpUrl for getting results from HTTP responses moreover url and contents will fetch url and content from http respectivly;
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
    //here we are parsing object via a json  and to get the data we are using 2 arraylist
    public static ArrayList<newsitem> parseJSON(String json) throws JSONException {

        String source="source";
        final String title="title";
        final String description="description";
        final String url="url";
        final String author="author";
        final String properties="properties";
        final String urlToImage="urlToImage";
        final String publishedAt="publishedAt";
        ArrayList<newsitem> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("items");

        for(int i = 0; i < items.length(); i++){
            JSONObject item = items.getJSONObject(i);
            String title1 = item.getString(title);
            String description1=item.getString("description");
            String url1=item.getString("url");
            String author1=item.getString("author");
            String properties1=item.getString("properties");
            String urlToImage1=item.getString("urlToImage");
            String publishedAt1=item.getString("publishedAt");
           // JSONObject owner = item.getJSONObject("owner");
            //String ownerName = owner.getString("login");
            //String url = item.getString("html_url");
            newsitem repo = new newsitem(source,title1,description1,url1,author1,properties1,urlToImage1,publishedAt1);
            result.add(repo);

        }
        return result;
    }


}
