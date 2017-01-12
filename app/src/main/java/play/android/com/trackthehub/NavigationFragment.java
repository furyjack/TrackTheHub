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


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {


    RecyclerView rvList;
    ArrayList<String> mlist;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_navigation, container, false);
        rvList = (RecyclerView) rootview.findViewById(R.id.drawerList);
        mlist = new ArrayList<>();
        mlist.add("News feed");
        mlist.add("TimeLine");
        rvList.setAdapter(new ListAdapter(mlist));
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));

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
