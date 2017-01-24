package play.android.com.trackthehub.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commit {

    @SerializedName("sha")
    @Expose
    public String sha;
    @SerializedName("author")
    @Expose
    public Author author;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("distinct")
    @Expose
    public Boolean distinct;
    @SerializedName("url")
    @Expose
    public String url;

}
