package com.technight.musixmatch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.technight.musixmatch.adapters.FirebaseEventListAdapter;
import com.technight.musixmatch.adapters.FirebaseEventViewHolder;
import com.technight.musixmatch.models.Event;
import com.technight.musixmatch.util.ItemTouchHelperAdapter;
import com.technight.musixmatch.util.OnStartDragListener;
import com.technight.musixmatch.util.SimpleItemTouchHelperCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarkedEventsList extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference eventDatabaseReference;
    private FirebaseEventListAdapter firebaseEventListAdapter;
    private ItemTouchHelper itemTouchHelper;
//    private FirebaseRecyclerAdapter<Event, FirebaseEventViewHolder> firebaseRecyclerAdapter;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.errorTextView) TextView errorTextView;
    @BindView(R.id.loadingTextView) TextView loadingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        ButterKnife.bind(this);

//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = firebaseUser.getUid();
//        eventDatabaseReference = FirebaseDatabase
//                .getInstance()
//                .getReference(Constants.FIREBASE_SAVED_EVENT)
//                .child(uid);
        setUpFirebaseAdapter();
        hideProgressBar();
        showEvents();
    }

    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        eventDatabaseReference = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_SAVED_EVENT)
                .child(uid);

        FirebaseRecyclerOptions<Event> FirebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(eventDatabaseReference, Event.class)
                        .build();

        firebaseEventListAdapter = new FirebaseEventListAdapter(FirebaseRecyclerOptions, eventDatabaseReference, (OnStartDragListener) this, this);;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firebaseEventListAdapter);
        recyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(firebaseEventListAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
                
//        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, FirebaseEventViewHolder>(FirebaseRecyclerOptions) {
//            @Override
//            protected void onBindViewHolder(@NonNull FirebaseEventViewHolder firebaseEventViewHolder, int position, @NonNull Event event) {
//                firebaseEventViewHolder.bindEvent(event);
//            }
//
//            @NonNull
//            @Override
//            public FirebaseEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list_item_drag, parent, false);
//                return new FirebaseEventViewHolder(view);
//            }
//        };
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    private void showEvents() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        loadingTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        loadingTextView.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseEventListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseEventListAdapter != null) {
            firebaseEventListAdapter.stopListening();
        }
    }
}