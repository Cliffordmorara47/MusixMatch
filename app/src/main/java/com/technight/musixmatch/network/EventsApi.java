package com.technight.musixmatch.network;

import com.technight.musixmatch.models.EventsHandler;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventsApi {
    @GET("events")
    Call<EventsHandler> getEvents(
            @Query("location") String location
    );
}
