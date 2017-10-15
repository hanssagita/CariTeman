package cariteman.hans.rest;

import cariteman.hans.response.DetailEventResponse;
import cariteman.hans.response.EventResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by HansSagita on 14/10/17.
 */

public interface ApiInterface {

    @GET("Event/getAllEvent")
    Call<EventResponse> getAllEvent();

    @GET("Event/detail/{id}")
    Call<DetailEventResponse> getEventById(@Path("id") String id);


}
