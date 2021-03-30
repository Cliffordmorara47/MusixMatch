
package com.technight.musixmatch.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.technight.musixmatch.Track;

@Generated("jsonschema2pojo")
public class Body {

    @SerializedName("track_list")
    @Expose
    private List<Track> trackList = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Body() {
    }

    /**
     * 
     * @param trackList
     */
    public Body(List<Track> trackList) {
        super();
        this.trackList = trackList;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

}
