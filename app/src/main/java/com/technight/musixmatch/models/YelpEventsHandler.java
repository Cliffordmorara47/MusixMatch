
package com.technight.musixmatch.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;
import org.parceler.Parcel;

@Generated("jsonschema2pojo")
public class YelpEventsHandler {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("events")
    @Expose
    private List<Event> events = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public YelpEventsHandler() {
    }

    /**
     * 
     * @param total
     * @param events
     */
    public YelpEventsHandler(Integer total, List<Event> events) {
        super();
        this.total = total;
        this.events = events;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
