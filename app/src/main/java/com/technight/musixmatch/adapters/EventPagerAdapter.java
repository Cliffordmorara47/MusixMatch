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

    private List<Event> mEvents;

    public EventPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Event> events) {
        super(fm, behavior);
        mEvents = events;
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Fragment getItem(int position) {
        return EventDetailFragment.newInstance(mEvents.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mEvents.get(position).getName();
    }
}
