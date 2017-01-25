package play.android.com.trackthehub.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issue {

    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("created_at")
    @Expose
    public String createdat;
    @SerializedName("closed_at")
    @Expose
    public String closedat;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("number")
    @Expose
    public int number;
    @SerializedName("id")
    @Expose
    public long id;

    public String getState() {
        return state;
    }

    public String getCreatedat() {
        return createdat;
    }

    public String getClosedat() {
        return closedat;
    }

    public String getTitle() {
        return title;
    }

    public int getNumber() {
        return number;
    }

    public long getId() {
        return id;
    }
}
