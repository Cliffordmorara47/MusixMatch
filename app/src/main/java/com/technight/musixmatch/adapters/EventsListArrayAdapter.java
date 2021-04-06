package com.technight.musixmatch.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class EventsListArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mCategories;
    private String[] mEvents;

    public EventsListArrayAdapter(Context context, int resource, String[] mEvents, String[] mCategories) {
        super(context, resource);
        this.mContext = context;
        this.mEvents = mEvents;
        this.mCategories = mCategories;
    }

    @Override
    public Object getItem(int position) {
        String event = mEvents[position];
        String eventCategory = mCategories[position];

        return String.format("%s  \n %s", event, eventCategory);
    }

    @Override
    public int getCount() {
        return mEvents.length;
    }
}
