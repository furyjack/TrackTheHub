package play.android.com.trackthehub;

import android.content.BroadcastReceiver;
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
import play.android.com.trackthehub.util.RetrofitInterface;
import play.android.com.trackthehub.util.Util_Event;
import play.android.com.trackthehub.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedActivity extends AppCompatActivity {

    private static final String TAG = "error.trackthehub";
    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    ArrayList<Util_Event> mlist;
    BroadcastReceiver mreciever;
    Radater radater;


    void updateui() {
        mlist.clear();
        mSwipeRefreshLayout.setRefreshing(true);
        RetrofitInterface.User userinterface = Myapplication.getRetrofit().create(
                RetrofitInterface.User.class);

        Call<List<play.android.com.trackthehub.model.Event>> geteventscall = userinterface.get_received_events(Utils.getString("authhash",
                "null", this), Myapplication.getUser().getLogin());

        geteventscall.enqueue(new Callback<List<play.android.com.trackthehub.model.Event>>() {
            @Override
            public void onResponse(Call<List<play.android.com.trackthehub.model.Event>> call, Response<List<play.android.com.trackthehub.model.Event>> response) {

                if (response.code() == 200) {
                    List<play.android.com.trackthehub.model.Event> list = response.body();
                    for (play.android.com.trackthehub.model.Event event : list) {
                        if (event.getType().equals(getString(R.string.TypePush))) {
                            Util_Event e = new Util_Event(event.getActor().getLogin(),
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
                            Util_Event e = new Util_Event(event.getActor().getLogin(),
                                    event.getRepo().getName(),
                                    getString(R.string.TypeCreate));
                            mlist.add(e);


                        } else if (event.getType().equals(getString(R.string.TypeFork))) {

                            Util_Event e = new Util_Event(event.getActor().getLogin(),
                                    event.getRepo().getName(),
                                    getString(R.string.TypeFork));

                            e.setBranch(e.user + "/" + e.repo.split("/")[1]);
                            mlist.add(e);


                        }


                    }


                    radater.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);


                } else {
                    Log.d(TAG, "onResponse: newsfeed " + response.code());
                }


            }

            @Override
            public void onFailure(Call<List<play.android.com.trackthehub.model.Event>> call, Throwable t) {

                Toast.makeText(NewsFeedActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: newsfeed");

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

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mlist = new ArrayList<>();
        radater = new NewsFeedActivity.Radater(mlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(radater);

        updateui();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateui();


            }
        });


    }


    class viewholder extends RecyclerView.ViewHolder {

        TextView Name, Desc, event;


        public viewholder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.tvRepo);
            Desc = (TextView) itemView.findViewById(R.id.tvDesc);
            event = (TextView) itemView.findViewById(R.id.tvevent);


        }
    }


    class Radater extends RecyclerView.Adapter<viewholder> {

        ArrayList<Util_Event> mlist;

        public Radater(ArrayList<Util_Event> mlist) {
            this.mlist = mlist;
        }

        @Override
        public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View root = inflater.inflate(R.layout.cardviewissues, parent, false);
            return new viewholder(root);
        }

        @Override
        public void onBindViewHolder(viewholder holder, int position) {

            Util_Event event = mlist.get(position);
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
