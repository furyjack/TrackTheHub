package play.android.com.trackthehub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import play.android.com.trackthehub.data.IssueAdapter;
import play.android.com.trackthehub.model.Event;
import play.android.com.trackthehub.model.Issue;
import play.android.com.trackthehub.util.Issues;
import play.android.com.trackthehub.util.RetroFitInterface;
import play.android.com.trackthehub.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyIssueFragment extends Fragment {

    RecyclerView mRepoList;
    ArrayList<Issues> mlist;
    IssueAdapter adapter;

    SwipeRefreshLayout mRefreshLayout;
    public static final String TAG = "error.trackthehub";


    public MyIssueFragment() {
        // Required empty public constructor
    }


    public void updateUi() {
        mRefreshLayout.setRefreshing(true);
        RetroFitInterface.User userinterface = MyApplication.getRetroFit().create(
                RetroFitInterface.User.class);

        Call<List<Event>> geteventscall = userinterface.getEvents(Utils.getString("authhash",
                "null", getContext()), MyApplication.getUser().getLogin());

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
                    mRefreshLayout.setRefreshing(false);


                } else {
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error1, Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_my_repos, container, false);
        mRepoList = (RecyclerView) rootview.findViewById(R.id.rvRepoList);
        mRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.mSwipeRefreshLayout);
        mlist = new ArrayList<>();
        adapter = new IssueAdapter(mlist, getContext());
        mRepoList.setAdapter(adapter);
        mRepoList.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUi();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUi();
            }
        });


        return rootview;

    }


}
