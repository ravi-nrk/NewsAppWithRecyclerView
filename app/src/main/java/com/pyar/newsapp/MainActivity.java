package com.pyar.newsapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView responseTextView;
    ProgressDialog progressDialog;
    ArrayList<NewsItem> NewsList;
    RecyclerView newsItemRecyclerView;
    LinearLayoutManager newsItemLayoutManager;
    NewsItemAdapter news_adapter;
  Button taskOne,taskTwo;
    ScrollView task_one_scroll_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       responseTextView = (TextView) findViewById(R.id.tv_response);
        NewsList = new ArrayList<>();
        newsItemRecyclerView = (RecyclerView) findViewById(R.id.rv_news_item_search);
        newsItemLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsItemRecyclerView.setLayoutManager(newsItemLayoutManager);
      /*  taskOne= (Button)findViewById(R.id.btn_task_one);
        taskTwo=(Button)findViewById(R.id.btn_task_two);*/
        task_one_scroll_view=(ScrollView)findViewById(R.id.news_scroll_view);
        task_one_scroll_view.setVisibility(View.GONE);
        newsItemRecyclerView.setVisibility(View.GONE);
        new HttpGetRequest().execute();

 /*       taskOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_one_scroll_view.setVisibility(View.VISIBLE);
               *//* taskOne.setVisibility(View.GONE);
                taskTwo.setVisibility(View.GONE);*//*
                newsItemRecyclerView.setVisibility(View.GONE);
                responseTextView.setVisibility(View.VISIBLE);
            }
        });
        taskTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsItemRecyclerView.setVisibility(View.VISIBLE);
             *//*   taskOne.setVisibility(View.GONE);
                taskTwo.setVisibility(View.GONE);*//*
                task_one_scroll_view.setVisibility(View.GONE);

            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                newsItemRecyclerView.setVisibility(View.VISIBLE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        String data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                //------------------>>
                Log.d("given_url", "" + NetworkUtils.getUrl());
                HttpGet httppost = new HttpGet(NetworkUtils.getUrl());
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                Log.d("my_status", "" + status);

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);

                    Log.d("printing....data", "" + data);
                    try {
                        JSONObject job = new JSONObject(data);
                        JSONArray news_array = job.getJSONArray("articles");
                        for (int i = 0; i < news_array.length(); i++) {
                            JSONObject news_job = news_array.getJSONObject(i);
                            String author = news_job.getString("author");
                            String title = news_job.getString("title");
                            String description = news_job.getString("description");
                            String url = news_job.getString("url");
                            String urlToImage = news_job.getString("urlToImage");
                            String publishedAt = news_job.getString("publishedAt");
                            NewsItem news_item = new NewsItem(author, title, description, url, urlToImage, publishedAt);
                            NewsList.add(news_item);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return data;

                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("my responseee", "" + result);
            progressDialog.dismiss();
            responseTextView.setText(result);
            news_adapter = new NewsItemAdapter(MainActivity.this, NewsList, "news_item");
            newsItemRecyclerView.setAdapter(news_adapter);


        }
    }
}
