package com.technight.musixmatch.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.technight.musixmatch.Constants;
import com.technight.musixmatch.R;
import com.technight.musixmatch.models.Event;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.eventImageView) ImageView eventImage;
    @BindView(R.id.eventNameView) TextView eventName;
    @BindView(R.id.eventCostView) TextView eventCost;
    @BindView(R.id.eventCategoryView) TextView eventCategory;
    @BindView(R.id.eventDetail) TextView eventDetail;
    @BindView(R.id.eventInfo) TextView eventInfo;
    @BindView(R.id.eventAddress) TextView eventAddress;
    @BindView(R.id.bookmarkEventButton) TextView bookmarkEventButton;
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
        eventInfo.setOnClickListener(this);

        bookmarkEventButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == eventInfo) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW);
        }

        if (view == eventAddress) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + event.getLongitude()
                            + "," + event.getLatitude()
                            + "?q=(" + event.getName() + ")"));
            startActivity(mapIntent);
        }

        if (view == bookmarkEventButton) {
            DatabaseReference eventRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_SAVED_EVENT);
            eventRef.push().setValue(event);
            Toast.makeText(getContext(), "Added to List", Toast.LENGTH_SHORT).show();
        }
    }
}