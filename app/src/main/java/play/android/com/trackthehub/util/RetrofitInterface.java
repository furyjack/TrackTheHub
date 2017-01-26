package play.android.com.trackthehub.util;


import java.util.List;

import play.android.com.trackthehub.model.*;
import play.android.com.trackthehub.model.Event;
import play.android.com.trackthehub.model.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class RetrofitInterface {

    public interface User
    {

        @GET("/user")
        Call<Owner> getuser(@Header("Authorization")String hash);

        @GET("/users/{username}/events")
        Call<List<play.android.com.trackthehub.model.Event>> getevents(@Header("Authorization")String hash, @Path("username") String username);

        @GET("/user/repos?affiliation=owner")
        Call<List<Repo>> getrepos(@Header("Authorization")String hash);

        @GET("/users/{username}/received_events")
        Call<List<Event>> get_received_events(@Header("Authorization")String hash,@Path("username") String username);

        @GET("/events")
        Call<List<Event>> get_timeline_events(@Header("Authorization")String hash);

    }

}
