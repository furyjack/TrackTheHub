package play.android.com.trackthehub.util;


 class UtilRepo {




    private String Title;


    private String desc;


    private String Lang;


    private String stars;


    private String forks;


    private String startoday;


    private String user;

    public UtilRepo(String title, String desc, String lang, String stars, String forks, String startoday, String user) {
       super();

        Title = title;
        this.desc = desc;
        Lang = lang;
        this.stars = stars;
        this.forks = forks;
        this.startoday = startoday;
        this.user = user;
    }

    public UtilRepo() {
super();
    }
}
