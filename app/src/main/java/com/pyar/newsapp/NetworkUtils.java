package com.pyar.newsapp;

import android.net.Uri;
import android.util.Log;

/**
 * Created by user1 on 30-Jun-17.
 */

public class NetworkUtils {
    public static final String BASE_URL="https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=";
    public static final String API_KEY="";

    public static String getUrl()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("newsapi.org")
                .appendPath("v1")
                .appendPath("articles")
                .appendQueryParameter("source", "the-next-web")
                .appendQueryParameter("sortBy", "latest")
                .appendQueryParameter("apiKey",API_KEY );
        String myUrl = builder.build().toString();
        Log.d("complete_java_url",""+myUrl);
        return myUrl;
    }
}
