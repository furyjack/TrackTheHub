package play.android.com.trackthehub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import play.android.com.trackthehub.data.IssueAdapter;
import play.android.com.trackthehub.model.Event;
import play.android.com.trackthehub.model.Issue;
import play.android.com.trackthehub.util.Issues;
import play.android.com.trackthehub.util.Repo;
import play.android.com.trackthehub.util.RetrofitInterface;
import play.android.com.trackthehub.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class myissuefragment extends Fragment {

    RecyclerView mRepoList;
    ArrayList<Repo> mlist;
    ProgressBar pbar;

    public static final String TAG = "error.trackthehub";


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

        final View rootview = inflater.inflate(R.layout.fragment_my_repos, container, false);
        mRepoList = (RecyclerView) rootview.findViewById(R.id.rvRepoList);
        pbar = (ProgressBar) rootview.findViewById(R.id.pbar_repo_fragment);
        pbar.setVisibility(View.INVISIBLE);
        final ArrayList<Issues> mlist = new ArrayList<>();
        final IssueAdapter adapter = new IssueAdapter(mlist, getContext());
        mRepoList.setAdapter(adapter);
        mRepoList.setLayoutManager(new LinearLayoutManager(getContext()));

        RetrofitInterface.User userinterface = Myapplication.getRetrofit().create(
                RetrofitInterface.User.class);

        Call<List<Event>> geteventscall = userinterface.getevents(Utils.getString("authhash",
                "null", getContext()), Myapplication.getUser().getLogin());

        geteventscall.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {

                if (response.code() == 200) {

                    List<Event> list = response.body();
                    for (Event x : list) {

                        if (x.getType().equals(getString(R.string.TypeIssues))) {
                            Issue issue = x.getPayload().getIssue();
                            Issues issues = new Issues(x.getRepo().getName(), issue.getTitle(),
                                    "" + issue.getNumber(), issue.getCreatedat(), x.getActor().getLogin());
                            mlist.add(issues);

                        }


                    }
                    adapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getContext(), "wrong", Toast.LENGTH_SHORT).show();

            }
        });


        return rootview;

    }


}
