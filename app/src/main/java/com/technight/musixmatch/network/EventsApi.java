package com.technight.musixmatch.network;

import com.technight.musixmatch.models.YelpEventsHandler;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventsApi {
    @GET("/events")
    Call<YelpEventsHandler> getEvents(
            @Query("location") String location
    );
}
