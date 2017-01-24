package play.android.com.trackthehub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Author {


    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("name")
    @Expose
    public String name;
}
