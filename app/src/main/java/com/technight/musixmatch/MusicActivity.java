package com.technight.musixmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicActivity extends AppCompatActivity {
    public static final String TAG = MusicActivity.class.getSimpleName();
    @BindView(R.id.listView) ListView mListView;
    @BindView(R.id.lyricsTextView) TextView mLyricsTextView;
//    @BindView(R.id.lv) TextView mLv;

    private String[] songs = new String[] {"Memories", "Quite Miss Home", "Sweet but Psycho", "You are the Reason",
            "EastSide", "TapOut", "Sex Sounds", "Back Door", "7 Summers", "Think about things", "Good News",
            "Little Nokia", "Yo Perreo Sola", "WAP", "People I've Been Sad", "Long Road Home", "Back Door",
            "Move On", "Go Crazy", "Hug the Streets"};

    private String[] artists = new String[] {"Maroon 5", "James Arthur", "Ava Max", "Calcum Scott", "Benny Blanco, " +
            "Khalid & Halsey", "Lil Wayne, Nicki Minaj, Future", "Lil Tjay", "Stray Kids", "Morgan Wallen", "Daoi Freyr",
            "Mac Miller", "Bree Runway", "Bad Bunny, Ivy Queen and Nesi", "Cardi B ft. Megan Thee Stallion", "Christine and the Queens",
            "Oneohtrix Point Never", "Stray Kids", "Lil Tjay", "Chris Brown, Young Thug", "Rick Ross"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        ButterKnife.bind(this);
        mLyricsTextView = (TextView) findViewById(R.id.lyricsTextView);

        MusicListArrayAdapter musicListArrayAdapter = new MusicListArrayAdapter(this, android.R.layout.simple_list_item_1, songs, artists);
        mListView.setAdapter(musicListArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String artist = ((TextView)view).getText().toString();
                Toast.makeText(MusicActivity.this, artist, Toast.LENGTH_LONG).show();
            }
        });
    }


}