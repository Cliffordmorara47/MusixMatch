package com.technight.musixmatch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technight.musixmatch.Constants;
import com.technight.musixmatch.R;
import com.technight.musixmatch.adapters.FirebaseEventViewHolder;
import com.technight.musixmatch.models.Event;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarkedEventsList extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<Event, FirebaseEventViewHolder> firebaseRecyclerAdapter;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.errorTextView) TextView errorTextView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_SAVED_EVENT)
                .child(uid);
        setUpFirebaseAdapter();
        hideProgressBar();
        showEvents();
    }

    private void setUpFirebaseAdapter() {
        FirebaseRecyclerOptions<Event> FirebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(databaseReference, Event.class)
                        .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, FirebaseEventViewHolder>(FirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseEventViewHolder firebaseEventViewHolder, int position, @NonNull Event event) {
                firebaseEventViewHolder.bindEvent(event);
            }

            @NonNull
            @Override
            public FirebaseEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list, parent, false);
                return new FirebaseEventViewHolder(view);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void showEvents() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }
}