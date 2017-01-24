package play.android.com.trackthehub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    @Expose
     String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("actor")
    @Expose
     Actor actor;
    @SerializedName("repo")
    @Expose
     play.android.com.trackthehub.model.Repo repo;
    @SerializedName("payload")
    @Expose
     Payload payload;
    @SerializedName("public")
    @Expose
     Boolean _public;
    @SerializedName("created_at")
    @Expose
     String createdAt;

    public String getType() {
        return type;
    }

    public Repo getRepo() {
        return repo;
    }
}
