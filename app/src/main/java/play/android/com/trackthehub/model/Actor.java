package play.android.com.trackthehub.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actor {


        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("login")
        @Expose
        public String login;
        @SerializedName("display_login")
        @Expose
        public String displayLogin;
        @SerializedName("gravatar_id")
        @Expose
        public String gravatarId;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("avatar_url")
        @Expose
        public String avatarUrl;

}
