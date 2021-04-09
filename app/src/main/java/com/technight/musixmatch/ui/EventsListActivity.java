package com.technight.musixmatch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technight.musixmatch.Constants;
import com.technight.musixmatch.R;
import com.technight.musixmatch.adapters.EventListAdapter;
import com.technight.musixmatch.models.Event;
import com.technight.musixmatch.models.EventsHandler;
import com.technight.musixmatch.network.EventsApi;
import com.technight.musixmatch.network.EventsClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsListActivity extends AppCompatActivity {
    public static final String TAG = "Events";
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
        Call<EventsHandler> call = client.getEvents(location);

        call.enqueue(new Callback<EventsHandler>() {
            @Override
            public void onResponse(retrofit2.Call<EventsHandler> call, Response<EventsHandler> response) {
                hideProgressBar();

                if (response.isSuccessful()) {
                    events = response.body().getEvents();
                    eventListAdapter = new EventListAdapter(EventsListActivity.this, events);
                    recyclerView .setAdapter(eventListAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EventsListActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);

                    showEvents();
                    if (events == null) {
                        showNoEventsMessage();
                    }
                } else {
                    showUnsuccessfulMessage();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<EventsHandler> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }
        });
    }

    private void showNoEventsMessage() {
        errorTextView.setText("Their are Currently \n No Events Within This Area");
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