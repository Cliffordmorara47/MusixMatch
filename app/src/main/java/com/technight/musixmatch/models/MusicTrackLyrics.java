
package com.technight.musixmatch.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MusicTrackLyrics {

    @SerializedName("message")
    @Expose
    private Message message;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MusicTrackLyrics() {
    }

    /**
     * 
     * @param message
     */
    public MusicTrackLyrics(Message message) {
        super();
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
