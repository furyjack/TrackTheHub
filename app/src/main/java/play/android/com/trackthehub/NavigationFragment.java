package play.android.com.trackthehub;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import play.android.com.trackthehub.util.Utils;

import static play.android.com.trackthehub.R.id.tv_wel;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {


    RecyclerView rvList;
    ArrayList<String> mlist;
    TextView Tvusername;
    public ImageView ImDp;
    BroadcastReceiver mreciever;


    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_navigation, container, false);
        rvList = (RecyclerView) rootview.findViewById(R.id.drawerList);
        Tvusername=(TextView)rootview.findViewById(tv_wel);
        ImDp=(de.hdodenhof.circleimageview.CircleImageView)rootview.findViewById(R.id.img_pr);
        String user= Utils.getString("username","user",getContext());
        Tvusername.setText(user);
        mlist = new ArrayList<>();
        mlist.add("News feed");
        mlist.add("TimeLine");
        String fetched=Utils.getString("user_fetched","false",getContext());
        if(fetched.equals("true"))
        {
            String av_url=Utils.getString("dp_url","null",getContext());
          Picasso.with(getContext()).load(av_url).into(ImDp);

        }
        rvList.setAdapter(new ListAdapter(mlist));
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mreciever=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String url=intent.getStringExtra("dp_url");
                Picasso.with(getContext()).load(url).into(ImDp);



            }
        };
        IntentFilter filter = new IntentFilter("play.android.com.trackthehub");
        getContext().registerReceiver(mreciever,filter);

        return rootview;
    }



    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView label;

        ListViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
        ArrayList<String> list;

        ListAdapter(ArrayList<String> list) {
            this.list = list;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            return new ListViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {

            holder.label.setText(list.get(position));

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
