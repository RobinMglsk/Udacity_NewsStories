package com.robinmglsk.newsstories;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.LoaderManager.LoaderCallbacks;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsStory>> ,SwipeRefreshLayout.OnRefreshListener {

    private static final String URL = "https://content.guardianapis.com/search";
    private static final String API_KEY = "4a229491-771d-4453-9700-8d13a1b41835"; // Needs to be secret... ;) So don't read this github. Only here so the Udacity reviewer doesn't need to register his own key.
    private static final int NEWS_STORY_LOADER_ID = 1;
    private NewsStoryAdapter mAdapter;
    private SwipeRefreshLayout mSwipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetworkConnection();

        mSwipeContainer = findViewById(R.id.swiperefresh);
        mSwipeContainer.setOnRefreshListener(this);

        /** Setup custom adapter */
        ListView newsStoryListView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsStoryAdapter(this, new ArrayList<NewsStory>());
        newsStoryListView.setAdapter(mAdapter);
        newsStoryListView.setEmptyView(findViewById(R.id.empty_list));

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

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_STORY_LOADER_ID, null, this);
        }else{
            findViewById(R.id.loader).setVisibility(View.GONE);
            TextView emptyText = findViewById(R.id.empty_list);
            emptyText.setText(getString(R.string.error_no_internet_connection));
        }
    }

    @Override
    public void onRefresh() {
        getLoaderManager().restartLoader(NEWS_STORY_LOADER_ID , null ,this);
    }



    @Override
    public Loader<List<NewsStory>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String quantity = sharedPrefs.getString(
                getString(R.string.settings_quantity_key),
                getString(R.string.settings_quantity_default));

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value.
        uriBuilder.appendQueryParameter("api-key", API_KEY);
        uriBuilder.appendQueryParameter("page-size", quantity);
        uriBuilder.appendQueryParameter("show-tags", "contributor");

        // Create a new loader for the given URL
        return new NewsStoryLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsStory>> loader, List<NewsStory> newsStories) {
        // Clear the adapter of previous news story data
        mAdapter.clear();

        /**
         * If there is a valid list of {@link NewsStory}s,then add them to the adapter's data set.
         * This will trigger the ListView to update.
         * */

        TextView emptyText = findViewById(R.id.empty_list);
        emptyText.setText(getString(R.string.empty_list));

        findViewById(R.id.loader).setVisibility(View.GONE);

        mSwipeContainer.post(new Runnable() {
            @Override
            public void run() {
                mSwipeContainer.setRefreshing(false);
            }
        });

        if (newsStories != null && !newsStories.isEmpty()) {
            mAdapter.addAll(newsStories);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsStory>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
