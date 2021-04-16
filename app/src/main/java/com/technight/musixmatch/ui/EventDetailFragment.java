package com.technight.musixmatch.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    @BindView(R.id.eventNameView) TextView eventName;
    @BindView(R.id.eventCostView) TextView eventCost;
    @BindView(R.id.eventCategoryView) TextView eventCategory;
    @BindView(R.id.eventDetail) TextView eventDetail;
    @BindView(R.id.eventInfo) TextView eventInfo;
    @BindView(R.id.eventAddress) TextView eventAddress;
    @BindView(R.id.bookmarkEventButton) TextView bookmarkEventButton;
    @BindView(R.id.eventDescription) TextView eventDescription;
    @BindView(R.id.bookMarksIcon) Button bookMarksIcon;

    private Event mEvent;

    public EventDetailFragment() {

    }

    public static EventDetailFragment newInstance(Event event) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("event", Parcels.wrap(event));
        eventDetailFragment.setArguments(args);
        return eventDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mEvent = Parcels.unwrap(getArguments().getParcelable("event"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);
        eventName.setText(mEvent.getName());
        eventCategory.setText(mEvent.getCategory());
        eventDescription.setText(mEvent.getDescription());
        eventInfo.setOnClickListener(this);
        eventAddress.setOnClickListener(this);
        eventDetail.setOnClickListener(this);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        bookMarksIcon.startAnimation(animation);
        Animation topAnim = AnimationUtils.loadAnimation(getContext(), R.anim.top_animation);
        eventName.startAnimation(topAnim);
        eventCategory.startAnimation(topAnim);
        eventDescription.startAnimation(topAnim);
        eventInfo.startAnimation(topAnim);
        eventAddress.startAnimation(topAnim);
        eventDetail.startAnimation(topAnim);


        bookmarkEventButton.setOnClickListener(this);
        bookMarksIcon.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == eventInfo) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mEvent.getEventSiteUrl()));
            startActivity(webIntent);
        }

        if (view == eventAddress) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mEvent.getLongitude()
                            + "," + mEvent.getLatitude()
                            + "?q=(" + mEvent.getName() + ")"));
            startActivity(mapIntent);
        }

        if (view == eventDetail) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mEvent.getEventSiteUrl()));
            startActivity(webIntent);
        }

        if (view == bookmarkEventButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference eventRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_SAVED_EVENT)
                    .child(uid);
            DatabaseReference pushRef = eventRef.push();
            String pushId = pushRef.getKey();
            mEvent.setPushId(pushId);
            pushRef.setValue(mEvent);

            Toast.makeText(getContext(), "Added To Bookmarks", Toast.LENGTH_SHORT).show();
        }
    }
}