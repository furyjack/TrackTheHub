package play.android.com.trackthehub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import play.android.com.trackthehub.util.Utils;


public class MyReposFragment extends Fragment {


    RecyclerView mRepoList;
    ArrayList<String> mlist;

    public MyReposFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview= inflater.inflate(R.layout.fragment_my_repos, container, false);
        mRepoList=(RecyclerView)rootview.findViewById(R.id.rvRepoList);
        mlist= Utils.getdata();
        mRepoList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRepoList.setAdapter(new ListAdapter());




        return  rootview;
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView Title,desc,star,fork,starttoday,lang;



        ListViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.tvTitle);
            desc = (TextView) itemView.findViewById(R.id.tvDesc);
            lang = (TextView) itemView.findViewById(R.id.tvLang);
            star = (TextView) itemView.findViewById(R.id.tvstar);
            fork = (TextView) itemView.findViewById(R.id.tvfork);
            starttoday = (TextView) itemView.findViewById(R.id.tvTodays);
        }
    }

    class ListAdapter extends RecyclerView.Adapter<MyReposFragment.ListViewHolder> {




        @Override
        public MyReposFragment.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View v = inflater.inflate(R.layout.cardviewrepo, parent, false);

            return new MyReposFragment.ListViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyReposFragment.ListViewHolder holder, int position) {

            holder.Title.setText(mlist.get(0));
            holder.desc.setText(mlist.get(1));
            holder.lang.setText(mlist.get(2));
            holder.star.setText(mlist.get(3));
            holder.fork.setText(mlist.get(4));
            holder.starttoday.setText(mlist.get(5)+ " today");

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }





}
