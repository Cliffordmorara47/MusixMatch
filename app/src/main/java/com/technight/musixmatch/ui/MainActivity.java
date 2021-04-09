package com.technight.musixmatch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technight.musixmatch.Constants;
import com.technight.musixmatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MeetUp";
    @BindView(R.id.location) EditText userLocation;
    @BindView(R.id.searchEventButton) Button searchEventButton;
    @BindView(R.id.bookMarkIcon) TextView bookMarkIcon;
    private DatabaseReference locationReference;
    private ValueEventListener locationReferenceListener;

    private String userAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        locationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.USER_LOCATION_KEY);

         locationReferenceListener = locationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapShot : dataSnapshot.getChildren()) {
                    String location = locationSnapShot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        searchEventButton.setOnClickListener(this);
        bookMarkIcon.setOnClickListener(this);

        bookMarkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookmarkedEventsList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    private void saveLocationToDatabase(String location) {
        locationReference.push().setValue(location);
    }

    @Override
    public void onClick(View view) {
        if (view == searchEventButton) {
            String location = userLocation.getText().toString();
            saveLocationToDatabase(location);
            Intent intent = new Intent(MainActivity.this, EventsListActivity.class);
            intent.putExtra("location", location);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationReference.removeEventListener(locationReferenceListener);
    }
}