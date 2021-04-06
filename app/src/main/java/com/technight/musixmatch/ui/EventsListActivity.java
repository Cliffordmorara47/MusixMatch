package com.technight.musixmatch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.technight.musixmatch.R;
import com.technight.musixmatch.adapters.EventListAdapter;
import com.technight.musixmatch.models.Event;
import com.technight.musixmatch.models.YelpEventsHandler;
import com.technight.musixmatch.network.EventsApi;
import com.technight.musixmatch.network.EventsClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsListActivity extends AppCompatActivity {
    public static final String TAG = EventsListActivity.class.getSimpleName();
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.errorTextView) TextView errorTextView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    private EventListAdapter eventListAdapter;

    public List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        EventsApi client = EventsClient.getClient();
        Call<YelpEventsHandler> call = client.getEvents(location);

        call.enqueue(new Callback<YelpEventsHandler>() {
            @Override
            public void onResponse(retrofit2.Call<YelpEventsHandler> call, Response<YelpEventsHandler> response) {
                hideProgressBar();

                if (response.isSuccessful()) {
                    events = response.body().getEvents();
                    eventListAdapter = new EventListAdapter(EventsListActivity.this, events);
                    recyclerView .setAdapter(eventListAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EventsListActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);

                    showEvents();
                } else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<YelpEventsHandler> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }
        });
    }
    private void showFailureMessage() {
        errorTextView.setText("Oops, Your Data is Turned Off");
        errorTextView.setVisibility(View.VISIBLE);
    }
    private void showUnsuccessfulMessage() {
        errorTextView.setText("Their Was an Error With the Search \n Please Try Again Later");
        errorTextView.setVisibility(View.VISIBLE);
    }

    private void showEvents() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
    

}