package play.android.com.trackthehub;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import play.android.com.trackthehub.data.MyContract;
import play.android.com.trackthehub.data.MyProvider;
import play.android.com.trackthehub.data.RepoAdapter;
import play.android.com.trackthehub.network.fetchService;


public class MyReposFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    RecyclerView mRepoList;
    public static  final int REPO_LOADER=0;
    RepoAdapter adapter;
    TextView tvEmpty;
    SwipeRefreshLayout mRefreshLayout;
    BroadcastReceiver DataUpdateReceiver;
    public MyReposFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview= inflater.inflate(R.layout.fragment_my_repos, container, false);
        mRepoList=(RecyclerView)rootview.findViewById(R.id.rvRepoList);
        mRefreshLayout=(SwipeRefreshLayout)rootview.findViewById(R.id.mSwipeRefreshLayout);
        mRepoList.setLayoutManager(new LinearLayoutManager(getContext()));
        tvEmpty=(TextView)rootview.findViewById(R.id.tv_empty);


        adapter=new RepoAdapter(null,getContext());
        mRepoList.setAdapter(adapter);

        DataUpdateReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                mRefreshLayout.setRefreshing(false);
            }
        };

        IntentFilter intentFilter=new IntentFilter(fetchService.ACTION_DATA_UPDATED);
        getContext().registerReceiver(DataUpdateReceiver,intentFilter);

        final String username=Myapplication.getUser().getLogin();
        String args[]={username};
        Cursor repos = getContext().getContentResolver().query(MyContract.buildrepowithuser(username),
                                                               MyContract.RepoEntry.projection,
                                                               MyProvider.sRepoSettingSelection, args, null);
        if(repos!=null && repos.getCount()==0)
        {
            mRefreshLayout.setRefreshing(true);
            Intent i=new Intent(getContext(), fetchService.class);
            i.setAction(fetchService.ACTION_UPDATE_DATA);
            getContext().startService(i);
            repos.close();
        }

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getContext().getContentResolver().delete(MyContract.buildrepowithuser(username),null,null);
                Intent i=new Intent(getContext(), fetchService.class);
                i.setAction(fetchService.ACTION_UPDATE_DATA);
                getContext().startService(i);

            }
        });



        return  rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(REPO_LOADER, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String usename=Myapplication.getUser().getLogin();
        String[]argument={usename};

        Uri repoRetrieve=MyContract.buildrepowithuser(usename);
        return new CursorLoader(getContext(),repoRetrieve,MyContract.RepoEntry.projection,MyProvider.sRepoSettingSelection,argument,null);


    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapter.swapCursor(data);



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(DataUpdateReceiver);

    }
}
