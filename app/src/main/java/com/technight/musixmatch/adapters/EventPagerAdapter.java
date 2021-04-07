package com.technight.musixmatch.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.technight.musixmatch.models.Event;
import com.technight.musixmatch.ui.EventDetailFragment;

import java.util.List;

public class EventPagerAdapter extends FragmentPagerAdapter {

    private List<Event> events;

    public EventPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Event> newEvents) {
        super(fm, behavior);
        events = newEvents;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Fragment getItem(int position) {
        return EventDetailFragment.newInstance(events.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return events.get(position).getName();
    }
}
