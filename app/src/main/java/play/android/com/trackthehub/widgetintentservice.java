package play.android.com.trackthehub;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import play.android.com.trackthehub.data.MyContract;
import play.android.com.trackthehub.data.MyProvider;
import play.android.com.trackthehub.util.Utils;


public class widgetintentservice extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                final long identityToken = Binder.clearCallingIdentity();
                String username=Utils.getString("username","null",getApplicationContext());
                if(username.equals("null"))
                {
                    data=null;
                    Binder.restoreCallingIdentity(identityToken);
                    return;
                }
                String args[]={username};
                data = getContentResolver().query(MyContract.buildrepowithuser(username),MyContract.RepoEntry.projection, MyProvider.sRepoSettingSelection,args,null);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        android.R.layout.simple_list_item_1);
                String reponame = data.getString(data.getColumnIndex(MyContract.RepoEntry.COLUMN_TITLE));




                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    setRemoteContentDescription(views, reponame);
                }

                views.setTextViewText(android.R.id.text1, reponame);




                return views;
            }

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, String description) {
                views.setContentDescription(android.R.id.text1, description);
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), android.R.layout.simple_list_item_1);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(data.getColumnIndex(MyContract.RepoEntry._ID));
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
