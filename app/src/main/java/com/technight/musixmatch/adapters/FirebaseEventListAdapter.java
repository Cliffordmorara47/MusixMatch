package com.technight.musixmatch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.technight.musixmatch.R;
import com.technight.musixmatch.models.Event;
import com.technight.musixmatch.util.ItemTouchHelperAdapter;
import com.technight.musixmatch.util.OnStartDragListener;

public class FirebaseEventListAdapter extends FirebaseRecyclerAdapter<Event, FirebaseEventViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference reference;
    private OnStartDragListener dragListener;
    private Context context1;

    public FirebaseEventListAdapter(FirebaseRecyclerOptions<Event> recyclerOptions,
                                    DatabaseReference databaseReference,
                                    OnStartDragListener onStartDragListener,
                                    Context context) {
        super(recyclerOptions);
        reference = databaseReference.getRef();
        dragListener = onStartDragListener;
        context1 = context;
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseEventViewHolder firebaseEventViewHolder, int position, @NonNull Event event) {
        firebaseEventViewHolder.bindEvent(event);
        firebaseEventViewHolder.eventImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(firebaseEventViewHolder);
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public FirebaseEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list_item_drag, parent, false);
        return new FirebaseEventViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
