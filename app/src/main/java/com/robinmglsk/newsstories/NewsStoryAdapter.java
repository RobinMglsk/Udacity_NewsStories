package com.robinmglsk.newsstories;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsStoryAdapter extends ArrayAdapter<NewsStory> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context     The current context. Used to inflate the layout file.
     * @param newsStories A List of NewsStory objects to display in a list
     */
    public NewsStoryAdapter(Activity context, ArrayList<NewsStory> newsStories){
        super(context,0, newsStories);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        /** Check if the existing view is being reused, otherwise inflate the view */
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        /** Get the {@link AndroidFlavor} object located at this position in the list */
        NewsStory currentNewsStory = getItem(position);

        /** Set title */
        String title = currentNewsStory.getTitle();
        TextView titleView = (TextView) listItemView.findViewById(R.id.storyTitle);
        titleView.setText(title);

        /** Set section */
        String section = currentNewsStory.getSectionName();
        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);
        sectionView.setText(section);

        /** Set author */
        String author = currentNewsStory.getAuthor();
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        authorView.setText(author);

        /** Set publish time */
        long publishedAt = currentNewsStory.getPublishedAt();
        TextView publishedAtView = (TextView) listItemView.findViewById(R.id.publishedAt);
        if(publishedAt != 0){
            SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL dd, yyyy HH:mm");
            String dateString = dateFormatter.format(publishedAt);
            publishedAtView.setText(dateString);
        }else{
            publishedAtView.setText("");
        }


        return listItemView;

    }

}
