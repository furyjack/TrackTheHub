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


        public Integer getId() {
                return id;
        }

        public String getLogin() {
                return login;
        }

        public String getDisplayLogin() {
                return displayLogin;
        }

        public String getGravatarId() {
                return gravatarId;
        }

        public String getUrl() {
                return url;
        }

        public String getAvatarUrl() {
                return avatarUrl;
        }
}
