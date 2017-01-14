package play.android.com.trackthehub.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MyContract {


    public static final String CONTENT_AUTHORITY="play.android.com.trackthehub";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_REPO = "repo";


    public static final class RepoEntry implements BaseColumns
    {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REPO;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REPO;

       public static final String TABLE_NAME="repos";
        public static final String COLUMN_TITLE="TITLE";
        public static final String COLUMN_DESC="DESC";
        public static final String COLUMN_LANG="LANG";
        public static final String COLUMN_STARS="STARS";
        public static final String COLUMN_FORKS="FORKS";
        public static final String COLUMN_STODAY="STODAY";
        public static final String COLUMN_USER="USER";

        public static final String[] projection={COLUMN_TITLE,COLUMN_DESC,COLUMN_LANG,COLUMN_STARS,COLUMN_FORKS,COLUMN_STODAY,COLUMN_USER};


    }

    public static Uri buildrepowithuser(String username)
    {

        return BASE_CONTENT_URI.buildUpon().appendPath(PATH_REPO).appendPath(username).build();

    }



}
