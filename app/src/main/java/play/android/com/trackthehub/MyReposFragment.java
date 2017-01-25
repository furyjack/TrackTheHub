package play.android.com.trackthehub;


import android.content.BroadcastReceiver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import play.android.com.trackthehub.data.MyContract;
import play.android.com.trackthehub.data.MyProvider;
import play.android.com.trackthehub.data.RepoAdapter;
import play.android.com.trackthehub.util.Repo;
import play.android.com.trackthehub.util.Utils;


public class MyReposFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    RecyclerView mRepoList;
    ArrayList<Repo> mlist;
    ProgressBar pbar;
    public static  final int REPO_LOADER=0;
 RepoAdapter adapter;
    TextView tvEmpty;
    BroadcastReceiver receiver;
    public MyReposFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview= inflater.inflate(R.layout.fragment_my_repos, container, false);
        mRepoList=(RecyclerView)rootview.findViewById(R.id.rvRepoList);
        pbar=(ProgressBar)rootview.findViewById(R.id.pbar_repo_fragment);
        mRepoList.setLayoutManager(new LinearLayoutManager(getContext()));
        tvEmpty=(TextView)rootview.findViewById(R.id.tv_empty);
        pbar.setVisibility(View.INVISIBLE);


        adapter=new RepoAdapter(null,pbar,getContext());
        mRepoList.setAdapter(adapter);

        String username=Myapplication.getUser().getLogin();
        String args[]={username};
        Cursor repos = getContext().getContentResolver().query(MyContract.buildrepowithuser(username),
                                                               MyContract.RepoEntry.projection,
                                                               MyProvider.sRepoSettingSelection, args, null);
        if(repos!=null && repos.getCount()==0)
        {



        }



        return  rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(REPO_LOADER, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String usename;
        usename= Utils.getString("username","null",getContext());
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

    }
}
