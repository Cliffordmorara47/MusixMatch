package com.technight.musixmatch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technight.musixmatch.Constants;
import com.technight.musixmatch.R;
import com.technight.musixmatch.models.Event;
import com.technight.musixmatch.ui.EventDetailActivity;
//import com.technight.musixmatch.ui.EventDetailActivity_ViewBinding;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View view;
    Context context;

    public ImageView eventImageView;

    public FirebaseEventViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindEvent(Event event) {
        TextView eventNameView = (TextView) view.findViewById(R.id.eventNameView);
        TextView eventCostView = (TextView) view.findViewById(R.id.eventCostView);
        TextView eventCategoryView = (TextView) view.findViewById(R.id.eventCategoryView);
        eventImageView = (ImageView)  view.findViewById(R.id.eventImageView);
//        TextView eventAddressView = (TextView) view.findViewById(R.id.eventAddress);

        eventNameView.setText(event.getName());
        eventCostView.setText("Cost: " + event.getCost());
        eventCategoryView.setText("Category: " + event.getCategory());
//        eventAddressView.setText(event.getLocation().toString());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Event> events = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_SAVED_EVENT);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    events.add(snapshot.getValue(Event.class));
                }
                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(context, EventDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("events", Parcels.wrap(events));

                context.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
