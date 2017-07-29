package com.example.android.news_app;

import android.provider.BaseColumns;
// in this class we are defining all the static final values which will represent table column and all the fields respectivly
/**
 * Created by Welcome To Future on 7/28/2017.
 */

public class Contract
{
// linking the results of a provider query to a ListView requires one of the retrieved columns to have the name _ID
    //thats why we are implementing BaseColumns
    public static final class newsitem implements BaseColumns{

    public static final String TABLE_NAME = "news_items";

    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_URL_TO_IMAGE = "urlToImage";
    public static final String COLUMN_PUBLISHED_AT = "published_at";

}
}
