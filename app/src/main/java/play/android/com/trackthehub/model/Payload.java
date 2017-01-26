package play.android.com.trackthehub.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload {

    @SerializedName("push_id")
    @Expose
    public Integer pushId;
    @SerializedName("size")
    @Expose
    public Integer size;
    @SerializedName("distinct_size")
    @Expose
    public Integer distinctSize;
    @SerializedName("ref")
    @Expose
    public String ref;
    @SerializedName("head")
    @Expose
    public String head;
    @SerializedName("before")
    @Expose
    public String before;
    @SerializedName("commits")
    @Expose
    public List<Commit> commits = null;

    @SerializedName("issue")
    @Expose
    public Issue issue;

    public Issue getIssue() {
        return issue;
    }

    public Commit getCommit()
    {
        if(commits.size()>0)
        return commits.get(0);
        else
         return null;
    }

    public String getRef() {
        return ref;
    }
}
