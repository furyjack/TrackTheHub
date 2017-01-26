package play.android.com.trackthehub.util;


import java.util.List;

import play.android.com.trackthehub.model.*;
import play.android.com.trackthehub.model.Event;
import play.android.com.trackthehub.model.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class RetroFitInterface {

    public interface User
    {

        @GET("/user")
        Call<Owner> getUser(@Header("Authorization")String hash);

        @GET("/users/{username}/events")
        Call<List<play.android.com.trackthehub.model.Event>> getEvents(@Header("Authorization")String hash, @Path("username") String username);

        @GET("/user/repos?affiliation=owner")
        Call<List<Repo>> getRepos(@Header("Authorization")String hash);

        @GET("/users/{username}/received_events")
        Call<List<Event>> getReceivedEvents(@Header("Authorization")String hash, @Path("username") String username);

        @GET("/events")
        Call<List<Event>> getTimelineEvents(@Header("Authorization")String hash);

    }

}
