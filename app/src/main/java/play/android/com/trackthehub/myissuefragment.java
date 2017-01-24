package play.android.com.trackthehub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import play.android.com.trackthehub.data.IssueAdapter;
import play.android.com.trackthehub.util.Issues;
import play.android.com.trackthehub.util.Repo;


/**
 * A simple {@link Fragment} subclass.
 */
public class myissuefragment extends Fragment {

    RecyclerView mRepoList;
    ArrayList<Repo> mlist;
    ProgressBar pbar;

    public static final String TAG="error.trackthehub";


    public myissuefragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_my_repos, container, false);
        mRepoList=(RecyclerView)rootview.findViewById(R.id.rvRepoList);
        pbar=(ProgressBar)rootview.findViewById(R.id.pbar_repo_fragment);
        final ArrayList<Issues>mlist=new ArrayList<>();
        final IssueAdapter adapter=new IssueAdapter(mlist,getContext());
        mRepoList.setAdapter(adapter);
        mRepoList.setLayoutManager(new LinearLayoutManager(getContext()));

//        mreciever=new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                String JSON=intent.getStringExtra("jsoneventdata");
//                try {
//                    mlist.addAll(Utils.getissueslist(JSON));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e(TAG, "onReceive: error in parsing json issue fragment");
//                }
//                adapter.notifyDataSetChanged();
//                pbar.setVisibility(View.INVISIBLE);
//
//
//
//            }
//        };
//
//
//
//
//        IntentFilter filter = new IntentFilter("play.android.com.trackthehub.issues");
//        getContext().registerReceiver(mreciever,filter);
//
//






        return rootview;

    }

    @Override
    public void onStart() {
        super.onStart();
//        String username=Utils.getString("username","null",getContext());
//
//        Intent i=new Intent(getContext(),fetchService.class);
//        i.putExtra("code",3);
//        i.putExtra("user",username);
//        i.putExtra("url","https://api.github.com/users/"+username +"/events?oauth_token="+Utils.getString(username+":token","null",getContext()));
//        getContext().startService(i);
    }
}
