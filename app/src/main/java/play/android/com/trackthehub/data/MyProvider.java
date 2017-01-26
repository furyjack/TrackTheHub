package play.android.com.trackthehub.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static play.android.com.trackthehub.data.MyContract.RepoEntry.TABLE_NAME;


public class MyProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int REPO_WITH_USER = 102;
    static final int REPO = 101;
    private DBhelper mHelper;
    public static final String sRepoSettingSelection =
            MyContract.RepoEntry.TABLE_NAME +
                    "." + MyContract.RepoEntry.COLUMN_USER + " = ? ";

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String Authority = MyContract.CONTENT_AUTHORITY;

        matcher.addURI(Authority, MyContract.PATH_REPO, REPO);
        matcher.addURI(Authority, MyContract.PATH_REPO + "/*", REPO_WITH_USER);


        return matcher;
    }


    @Override
    public boolean onCreate() {
        mHelper = new DBhelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {

        int code = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (code) {
            case REPO_WITH_USER: {
                SQLiteDatabase rdb = mHelper.getReadableDatabase();
                String user = uri.getPathSegments().get(1);
                String[] ARGS = {user};
                retCursor = rdb.query(TABLE_NAME, MyContract.RepoEntry.projection, sRepoSettingSelection, ARGS, null, null, null);
                break;


            }
            default:
                throw new UnsupportedOperationException("unexpected query:" + uri);


        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int code = sUriMatcher.match(uri);

        switch (code) {
            case REPO:
                return MyContract.RepoEntry.CONTENT_TYPE;
            case REPO_WITH_USER:
                return MyContract.RepoEntry.CONTENT_TYPE;


            default:
                throw new UnsupportedOperationException("Unkown uri:" + uri);

        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case REPO_WITH_USER: {
                long id = db.insert(MyContract.RepoEntry.TABLE_NAME, null, contentValues);
                returnUri = ContentUris.withAppendedId(MyContract.buildRepoWithUser(uri.getPathSegments().get(1)), id);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unkown uri:" + uri);


        }
        getContext().getContentResolver().notifyChange(returnUri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {

        SQLiteDatabase db = mHelper.getWritableDatabase();
        int code = sUriMatcher.match(uri);
        int rdeleted;

        switch (code) {
            case REPO: {
                if (s == null)
                    s = "1";
                rdeleted = db.delete(MyContract.RepoEntry.TABLE_NAME, s, strings);
                break;

            }
            case REPO_WITH_USER: {

                String username = uri.getPathSegments().get(1);
                String[] args = {username};
                if (s == null) {
                    s = sRepoSettingSelection;
                    strings = args;
                }
                rdeleted = db.delete(MyContract.RepoEntry.TABLE_NAME, s, strings);
                break;

            }
            default:
                throw new UnsupportedOperationException("Unkown uri:" + uri);


        }

        if (rdeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rdeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case REPO_WITH_USER:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MyContract.RepoEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
