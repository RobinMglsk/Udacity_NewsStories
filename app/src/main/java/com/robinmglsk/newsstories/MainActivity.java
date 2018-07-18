package com.robinmglsk.newsstories;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NewsStoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetworkConnection();

        /** Setup custom adapter */
        ListView newsStoryListView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsStoryAdapter(this, new ArrayList<NewsStory>());
        newsStoryListView.setAdapter(mAdapter);
        newsStoryListView.setEmptyView(findViewById(R.id.empty_list));

        mAdapter.add(new NewsStory("Title","qsdsqd","https://www.robinmglsk.com"));


        /**
         * Set an item click listener on the ListView, which sends an intent to a web browser
         * to open a website with more information about the selected news story.
         */
        newsStoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /** Find the current news story that was clicked on */
                NewsStory currentNewsStory = mAdapter.getItem(position);

                /** Convert the String URL into a URI object (to pass into the Intent constructor) */
                Uri newStoryUrl = Uri.parse(currentNewsStory.getUrl());

                /** Create a new intent to view the news story URI */
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newStoryUrl);

                /** Send the intent to launch a new activity */
                startActivity(websiteIntent);
            }
        });
    }

    private void checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {


        }else{
            findViewById(R.id.loader).setVisibility(View.GONE);
            TextView emptyText = findViewById(R.id.empty_list);
            emptyText.setText(getString(R.string.error_no_internet_connection));
        }
    }
}
