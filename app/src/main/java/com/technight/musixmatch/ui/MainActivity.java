package com.technight.musixmatch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.technight.musixmatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.searchEventButton) Button searchEventButton;
    @BindView(R.id.bookMarkIcon) TextView bookMarkIcon;
    @BindView(R.id.appNameTextView) TextView appNameTextView;
    @BindView(R.id.appDescription) TextView appDescription;
    @BindView(R.id.Gospel) TextView gospel;
    @BindView(R.id.jazz) TextView jazz;
    @BindView(R.id.hipHop) TextView hipHop;
    @BindView(R.id.reggae) TextView reggae;
    @BindView(R.id.rock) TextView rock;
    @BindView(R.id.pop) TextView pop;
    @BindView(R.id.rhythms) TextView rhythms;
    @BindView(R.id.countryMusic) TextView countryMusic;
    @BindView(R.id.collections) TextView collections;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        getWindow().setFlags(WindowManager.LayoutParams.ANIMATION_CHANGED, WindowManager.LayoutParams.ANIMATION_CHANGED);

        searchEventButton.setOnClickListener(this);
        bookMarkIcon.setOnClickListener(this);

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.blink_anim);
        bookMarkIcon.startAnimation(animation);

        Animation bounceAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
        appNameTextView.startAnimation(bounceAnimation);

        Animation fadeAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_animation);
//        appNameTextView.startAnimation(bounceAnimation);
        gospel.startAnimation(fadeAnimation);
        jazz.startAnimation(fadeAnimation);
        hipHop.startAnimation(fadeAnimation);
        reggae.startAnimation(fadeAnimation);
        rock.startAnimation(fadeAnimation);
        pop.startAnimation(fadeAnimation);
        rhythms.startAnimation(fadeAnimation);
        countryMusic.startAnimation(fadeAnimation);
        collections.startAnimation(fadeAnimation);


        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    getSupportActionBar().setTitle(firebaseUser.getDisplayName());
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_view, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logOut) {
            logOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



    @Override
    public void onClick(View view) {
        if (view == searchEventButton) {
            Intent intent = new Intent(MainActivity.this, EventsListActivity.class);
            startActivity(intent);
        }
        if (view == bookMarkIcon) {
            Intent intent = new Intent(MainActivity.this, BookmarkedEventsList.class);
            startActivity(intent);
        }
    }
}