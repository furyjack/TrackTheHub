package play.android.com.trackthehub.util;


import java.util.List;

import play.android.com.trackthehub.model.*;
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


    }

}
