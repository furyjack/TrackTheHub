package play.android.com.trackthehub.util;


public class Repo  {




    public String Title;


    public String desc;


    public String Lang;


    public String stars;


    public String forks;


    public String startoday;


    public Boolean isBookMarked;

    public Repo(String title, String desc, String lang, String stars, String forks, String startoday, Boolean isBookMarked) {
       super();

        Title = title;
        this.desc = desc;
        Lang = lang;
        this.stars = stars;
        this.forks = forks;
        this.startoday = startoday;
        this.isBookMarked = isBookMarked;
    }

    public Repo() {
super();
    }
}
