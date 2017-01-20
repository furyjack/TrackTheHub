package play.android.com.trackthehub;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsFeedActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);


        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setDistanceToTriggerSync(200);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);

        final ArrayList<String>mlist=new ArrayList<>();
        for(int i=0;i<30;i++)
        {
            mlist.add("not loaded");
        }

        class viewholder extends RecyclerView.ViewHolder
        {

          TextView tv;

          public viewholder(View itemView) {
              super(itemView);
              tv=(TextView)itemView.findViewById(android.R.id.text1);

          }
      }

        class Radater extends RecyclerView.Adapter<viewholder>
        {

            @Override
            public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater=getLayoutInflater();
                View root=inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
                return  new viewholder(root);
            }

            @Override
            public void onBindViewHolder(viewholder holder, int position) {

                holder.tv.setText(mlist.get(position));

            }

            @Override
            public int getItemCount() {
                return 30;
            }
        }
            final Radater adapter=new Radater();
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(manager);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                for(int i=0;i<15;i++)
                {
                    mlist.set(i,"loaded:");


                }

                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });





    }

    public void refresh()
    {


    }
}
