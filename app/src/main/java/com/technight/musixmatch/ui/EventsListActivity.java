package com.technight.musixmatch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
    public static final String TAG = EventsListActivity.class.getSimpleName();
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.errorTextView) TextView errorTextView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String recentAddress;

    private EventListAdapter eventListAdapter;
    public List<Event> events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        recentAddress = sharedPreferences.getString(Constants.RECENT_LOCATION_KEY, null);

        if (recentAddress != null) {
            displayEvents(recentAddress);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_view, menu);
        ButterKnife.bind(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String location) {
                updateRecentAddress(location);
                displayEvents(location);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void updateRecentAddress(String location){
        editor.putString(Constants.RECENT_LOCATION_KEY, location).apply();
    }

    private void showNoEventsMessage() {
        errorTextView.setText("Their are Currently \n No Events Within This Area");
    }

    private void showFailureMessage() {
        errorTextView.setText("Oops, Your Data is Turned Off");
        errorTextView.setVisibility(View.VISIBLE);
    }
    private void showUnsuccessfulMessage() {
        errorTextView.setText("There are Currently \n No Events Within This Area");
        errorTextView.setVisibility(View.VISIBLE);
    }

    private void showEvents() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
    
    private void displayEvents(String location) {
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

}