package play.android.com.trackthehub.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABSE_NAME="Mydb";

    public DBhelper(Context context)
    {

        super(context,DATABSE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_REPO_TABLE="CREATE TABLE "+MyContract.RepoEntry.TABLE_NAME+" ("+
                MyContract.RepoEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                MyContract.RepoEntry.COLUMN_TITLE+" TEXT NOT NULL,"+
                MyContract.RepoEntry.COLUMN_DESC+" TEXT,"+
                MyContract.RepoEntry.COLUMN_LANG+" TEXT,"+
                MyContract.RepoEntry.COLUMN_FORKS+" TEXT,"+
                MyContract.RepoEntry.COLUMN_STARS+" TEXT,"+
                MyContract.RepoEntry.COLUMN_STODAY+" TEXT,"+
                MyContract.RepoEntry.COLUMN_USER+" TEXT NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_REPO_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+MyContract.RepoEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
