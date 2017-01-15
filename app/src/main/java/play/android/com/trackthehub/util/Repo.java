package play.android.com.trackthehub.util;


public class Repo  {




    public String Title;


    public String desc;


    public String Lang;


    public String stars;


    public String forks;


    public String startoday;


    public String user;

    public Repo(String title, String desc, String lang, String stars, String forks, String startoday, String user) {
       super();

        Title = title;
        this.desc = desc;
        Lang = lang;
        this.stars = stars;
        this.forks = forks;
        this.startoday = startoday;
        this.user = user;
    }

    public Repo() {
super();
    }
}
