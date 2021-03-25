package com.technight.musixmatch;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MusicListArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mSongs;
    private String[] mArtists;

    public MusicListArrayAdapter(Context context, int resource, String[] mSongs, String[] mArtists) {
        super(context, resource);
        this.mContext = context;
        this.mSongs = mSongs;
        this.mArtists = mArtists;
    }

    @Override
    public Object getItem(int position) {
        String song = mSongs[position];
        String artist = mArtists[position];
        return String.format("%s \n %s", song, artist);
    }

    @Override
    public int getCount() {
        return mSongs.length;
    }
}
