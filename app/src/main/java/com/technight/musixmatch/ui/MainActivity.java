package com.technight.musixmatch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.technight.musixmatch.Constants;
import com.technight.musixmatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MeetUp";
    @BindView(R.id.location) EditText userLocation;
    @BindView(R.id.searchEventButton) Button searchEventButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        searchEventButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    private void addLocationToPreferences(String location) {
        editor.putString(Constants.USER_LOCATION_KEY, location).apply();
    }

    @Override
    public void onClick(View view) {
        if (view == searchEventButton) {
            String location = userLocation.getText().toString();
            Intent intent = new Intent(MainActivity.this, EventsListActivity.class);
            intent.putExtra("location", location);
            addLocationToPreferences(location);
            startActivity(intent);
        }
    }
}