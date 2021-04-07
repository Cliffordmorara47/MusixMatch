package com.technight.musixmatch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.technight.musixmatch.R;
import com.technight.musixmatch.models.Event;
import com.technight.musixmatch.ui.EventDetailActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    private List<Event> events;
    private Context mContext;

    public EventListAdapter(Context context, List<Event> currentEvents) {
        mContext = context;
        events = currentEvents;
    }

    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list, parent, false);
        EventViewHolder eventViewHolder = new EventViewHolder(view);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(EventListAdapter.EventViewHolder holder, int position) {
        holder.bindEvent(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.eventImageView) ImageView eventImageView;
        @BindView(R.id.eventNameView) TextView eventNameView;
        @BindView(R.id.eventCategoryView) TextView eventCategory;
        @BindView(R.id.eventCostView) TextView eventCostView;
        @BindView(R.id.eventAttendingView) TextView eventAttendingView;
        private Context mContext;

        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindEvent(Event event) {
//            Picasso.get().load(event.getImageUrl()).into(eventImageView);
            eventNameView.setText(event.getName());
            eventCategory.setText("Category: " +event.getCategory());
            eventCostView.setText("Cost: " + event.getCost());
            eventAttendingView.setText("Attending: " + event.getAttendingCount());

        }

        @Override
        public void onClick(View view) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, EventDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("currentEvents", Parcels.wrap(events));
            mContext.startActivity(intent);
        }
    }
}
