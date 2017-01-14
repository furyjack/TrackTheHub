package play.android.com.trackthehub.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static play.android.com.trackthehub.data.MyContract.RepoEntry.TABLE_NAME;


public class MyProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int REPO_WITH_USER =102 ;
    static  final int REPO=101;
    private DBhelper mhelper;
    private static final String sRepoSettingSelection =
            MyContract.RepoEntry.TABLE_NAME+
                    "." + MyContract.RepoEntry.COLUMN_USER + " = ? ";

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
        final String Authority=MyContract.CONTENT_AUTHORITY;

        matcher.addURI(Authority,MyContract.PATH_REPO, REPO);
        matcher.addURI(Authority,MyContract.PATH_REPO+"/*",REPO_WITH_USER);



        return matcher;
    }


    @Override
    public boolean onCreate() {
        mhelper=new DBhelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        int code=sUriMatcher.match(uri);
        Cursor retCursor;
        switch (code)
        {
            case REPO_WITH_USER:
            {
                SQLiteDatabase rdb=mhelper.getReadableDatabase();
                String user=uri.getPathSegments().get(2);
                String[] ARGS={user};
                 retCursor=rdb.query(TABLE_NAME,MyContract.RepoEntry.projection,sRepoSettingSelection,ARGS,null,null,null);
               break;


            }
            default:
                throw new UnsupportedOperationException("unexpected query:"+uri);



        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        int code=sUriMatcher.match(uri);

        switch (code)
        {
            case REPO:
                return MyContract.RepoEntry.CONTENT_TYPE;
            case REPO_WITH_USER:
                return  MyContract.RepoEntry.CONTENT_TYPE;


            default:
                 throw new UnsupportedOperationException("Unkown uri:" +uri);

        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mhelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match)
        {
            case REPO_WITH_USER:
            {
                long id=db.insert(MyContract.RepoEntry.TABLE_NAME,null,contentValues);
                returnUri= ContentUris.withAppendedId(MyContract.buildrepowithuser(uri.getPathSegments().get(1)),id);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unkown uri:" +uri);


        }
        getContext().getContentResolver().notifyChange(returnUri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }



}
