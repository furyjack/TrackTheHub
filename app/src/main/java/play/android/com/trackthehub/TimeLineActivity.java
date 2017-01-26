package play.android.com.trackthehub;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import play.android.com.trackthehub.model.Commit;
import play.android.com.trackthehub.util.RetroFitInterface;
import play.android.com.trackthehub.util.UtilEvent;
import play.android.com.trackthehub.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeLineActivity extends AppCompatActivity {


    private static final String TAG = "error.trackthehub";
    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    ArrayList<UtilEvent> mlist;
    RepAdapter RepAdapter;
    TextView TVTITLE;


    void updateUi() {
        mlist.clear();
        mSwipeRefreshLayout.setRefreshing(true);
        RetroFitInterface.User userinterface = MyApplication.getRetroFit().create(
                RetroFitInterface.User.class);

        Call<List<play.android.com.trackthehub.model.Event>> geteventscall = userinterface.getTimelineEvents(Utils.getString("authhash",
                "null", this));

        geteventscall.enqueue(new Callback<List<play.android.com.trackthehub.model.Event>>() {
            @Override
            public void onResponse(Call<List<play.android.com.trackthehub.model.Event>> call, Response<List<play.android.com.trackthehub.model.Event>> response) {

                if (response.code() == 200) {
                    List<play.android.com.trackthehub.model.Event> list = response.body();
                    for (play.android.com.trackthehub.model.Event event : list) {
                        if (event.getType().equals(getString(R.string.TypePush))) {
                            UtilEvent e = new UtilEvent(event.getActor().getLogin(),
                                    event.getRepo().getName(),
                                    getString(R.string.TypePush));

                            Commit c = event.getPayload().getCommit();
                            if (c != null) {
                                e.setCommit(c.getSha().substring(0, 7));
                                e.setCommitMessage(c.getMessage());
                            }
                            e.setBranch(event.getPayload().getRef().split("/")[2]);
                            mlist.add(e);


                        } else if (event.getType().equals(getString(R.string.TypeCreate))) {
                            UtilEvent e = new UtilEvent(event.getActor().getLogin(),
                                    event.getRepo().getName(),
                                    getString(R.string.TypeCreate));
                            mlist.add(e);


                        } else if (event.getType().equals(getString(R.string.TypeFork))) {

                            UtilEvent e = new UtilEvent(event.getActor().getLogin(),
                                    event.getRepo().getName(),
                                    getString(R.string.TypeFork));

                            e.setBranch(e.user + "/" + e.repo.split("/")[1]);
                            mlist.add(e);


                        }


                    }


                    RepAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);


                } else {
                    Log.d(TAG, "onResponse: timeline " + response.code());
                }


            }

            @Override
            public void onFailure(Call<List<play.android.com.trackthehub.model.Event>> call, Throwable t) {

                Toast.makeText(TimeLineActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: timeline");

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setDistanceToTriggerSync(200);
        TVTITLE = (TextView) findViewById(R.id.title);
        TVTITLE.setText(R.string.timeline);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mlist = new ArrayList<>();
        RepAdapter = new RepAdapter(mlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(RepAdapter);
        updateUi();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUi();

            }
        });


    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name, Desc, event;


        ViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.tvRepo);
            Desc = (TextView) itemView.findViewById(R.id.tvDesc);
            event = (TextView) itemView.findViewById(R.id.tvevent);


        }
    }


    class RepAdapter extends RecyclerView.Adapter<ViewHolder> {

        ArrayList<UtilEvent> mlist;

        RepAdapter(ArrayList<UtilEvent> mlist) {
            this.mlist = mlist;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View root = inflater.inflate(R.layout.cardviewissues, parent, false);
            return new ViewHolder(root);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            UtilEvent event = mlist.get(position);
            holder.Name.setText(event.user);
            switch (event.type) {
                case "PushEvent": {
                    holder.Desc.setText(String.format(getString(R.string.timelinebranch), event.getBranch(), event.repo));
                    holder.event.setText(String.format("%s %s", event.getCommit(), event.getCommitMessage()));

                    break;
                }

                case "CreateEvent": {
                    holder.Desc.setText(R.string.descCreateRepo);
                    holder.event.setText(String.format(getString(R.string.timelinerepo), event.repo));

                    break;
                }

                case "ForkEvent": {

                    holder.Desc.setText(String.format(getString(R.string.timelineforked), event.repo));
                    holder.event.setText(String.format(getString(R.string.timelineforkedevent), event.getBranch()));

                    break;
                }


            }

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }


}
