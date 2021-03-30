
package com.technight.musixmatch.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Track {

    @SerializedName("track")
    @Expose
    private String track;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Track() {
    }

    /**
     * 
     * @param track
     */
    public Track(String track) {
        super();
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

}
