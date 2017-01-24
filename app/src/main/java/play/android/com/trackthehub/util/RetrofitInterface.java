package play.android.com.trackthehub.util;


import play.android.com.trackthehub.model.Owner;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public class RetrofitInterface {

    public interface User
    {

        @GET("/user")
        Call<Owner> getuser(@Header("Authorization")String hash);


    }

}
