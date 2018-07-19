package com.robinmglsk.newsstories;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsStoryLoader extends AsyncTaskLoader<List<NewsStory>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsStoryLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public NewsStoryLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsStory> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news stories.
        List<NewsStory> newsStories = QueryUtils.fetchNewsStories(mUrl);
        return newsStories;
    }


}
