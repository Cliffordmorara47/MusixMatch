package com.technight.musixmatch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.technight.musixmatch.R;
import com.technight.musixmatch.adapters.EventPagerAdapter;
import com.technight.musixmatch.models.Event;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class
EventDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewEventPage)
    ViewPager viewPager;
    private EventPagerAdapter eventPagerAdapter;
    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        events = Parcels.unwrap(getIntent().getParcelableExtra("events"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        eventPagerAdapter = new EventPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, events);
        viewPager.setAdapter(eventPagerAdapter);
        viewPager.setCurrentItem(startingPosition);
    }
}