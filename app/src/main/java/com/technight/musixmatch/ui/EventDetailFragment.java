package com.technight.musixmatch.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technight.musixmatch.R;
import com.technight.musixmatch.models.Event;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailFragment extends Fragment {
    @BindView(R.id.eventImageView) ImageView eventImage;
    @BindView(R.id.eventNameView) TextView eventName;
    @BindView(R.id.eventCostView) TextView eventCost;
    @BindView(R.id.eventCategoryView) TextView eventCategory;
    @BindView(R.id.eventDetail) TextView eventDetail;
    @BindView(R.id.eventInfo) TextView eventInfo;
    @BindView(R.id.eventAddress) TextView eventAddress;
    @BindView(R.id.bookmarkEventButton) TextView bookmarkEvent;
    @BindView(R.id.eventDescription) TextView eventDescription;

    private Event event;

    public EventDetailFragment() {

    }

    public static EventDetailFragment newInstance(Event newEvent) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("newEvent", Parcels.wrap(newEvent));

        eventDetailFragment.setArguments(args);
        return eventDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        event = Parcels.unwrap(getArguments().getParcelable("newEvent"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(event.getImageUrl()).into(eventImage);
        eventName.setText(event.getName());
        eventCost.setText(event.getCost().toString());
        eventCategory.setText(event.getCategory());
        eventDescription.setText(event.getDescription());
        eventAddress.setText(event.getLocation().toString());
        eventInfo.setText(event.getEventSiteUrl());
        eventDetail.setText(event.getAttendingCount());
        return view;
    }

    }