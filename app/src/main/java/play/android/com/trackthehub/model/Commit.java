package play.android.com.trackthehub.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commit {

    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("author")
    @Expose
    public Author author;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("distinct")
    @Expose
    public Boolean distinct;
    @SerializedName("url")
    @Expose
    public String url;

    public String getMessage() {
        return message;
    }

    public String getSha() {
        return sha;
    }
}
