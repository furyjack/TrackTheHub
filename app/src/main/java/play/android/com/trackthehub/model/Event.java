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
    private Actor actor;
    @SerializedName("repo")
    @Expose
    private Repo repo;
    @SerializedName("payload")
    @Expose
    private Payload payload;
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

    public Payload getPayload() {
        return payload;
    }

    public Actor getActor() {
        return actor;
    }
}
