package play.android.com.trackthehub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import org.json.JSONException;

import java.util.ArrayList;

import play.android.com.trackthehub.network.fetchService;
import play.android.com.trackthehub.util.Event;
import play.android.com.trackthehub.util.Utils;

public class TimeLineActivity extends AppCompatActivity {


    private static final String TAG ="error.trackthehub" ;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    ArrayList<Event> mlist;
    BroadcastReceiver mreciever;
    Radater radater;
    TextView TVTITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);


        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
     
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setDistanceToTriggerSync(200);
        TVTITLE=(TextView)findViewById(R.id.title) ;
        TVTITLE.setText(R.string.timeline);

        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        mlist=new ArrayList<>();
        radater=new Radater(mlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(radater);
        mSwipeRefreshLayout.setRefreshing(true);

        mreciever=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data=intent.getStringExtra("data");
                try {
                    mlist.addAll(Utils.geteventlist(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onReceive: error in parsing json events");
                }
                radater.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);


            }
        };

        IntentFilter filter=new IntentFilter("play.android.com.trackthehub.timeline");

        this.registerReceiver(mreciever,filter);
        String username=Utils.getString("username","null",this);

        Intent i=new Intent(this,fetchService.class);
        i.putExtra("code",4);
        i.putExtra("user",username);
        i.putExtra("url","https://api.github.com/events" +"?oauth_token="+Utils.getString(username+":token","null",this));
        this.startService(i);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String username=Utils.getString("username","null",TimeLineActivity.this);
                Intent i=new Intent(TimeLineActivity.this,fetchService.class);
                i.putExtra("code",4);
                i.putExtra("user",username);
                i.putExtra("url","https://api.github.com/events/public");
                TimeLineActivity.this.startService(i);




            }
        });











    }


    class viewholder extends RecyclerView.ViewHolder
    {

        TextView Name,Desc,event;


        public viewholder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.tvRepo);
            Desc=(TextView)itemView.findViewById(R.id.tvDesc);
            event=(TextView)itemView.findViewById(R.id.tvevent);


        }
    }


    class Radater extends RecyclerView.Adapter<viewholder>
    {

        ArrayList<Event>mlist;

        public Radater(ArrayList<Event> mlist) {
            this.mlist = mlist;
        }

        @Override
        public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=getLayoutInflater();
            View root=inflater.inflate(R.layout.cardviewissues,parent,false);
            return  new viewholder(root);
        }

        @Override
        public void onBindViewHolder(viewholder holder, int position) {

            Event event=mlist.get(position);
            holder.Name.setText(event.user);
            switch(event.type)
            {
                case "PushEvent":
                {
                    holder.Desc.setText(String.format(getString(R.string.timelinebranch),event.getBranch(),event.repo));
                    holder.event.setText(String.format("%s %s",event.getCommit(),event.getCommitMessage()));

                    break;
                }

                case "CreateEvent":
                {
                    holder.Desc.setText(R.string.descCreateRepo);
                    holder.event.setText(String.format(getString(R.string.timelinerepo),event.repo));

                    break;
                }

                case "ForkEvent":
                {

                    holder.Desc.setText(String.format(getString(R.string.timelineforked),event.repo));
                    holder.event.setText(String.format(getString(R.string.timelineforkedevent),event.getBranch()));

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
