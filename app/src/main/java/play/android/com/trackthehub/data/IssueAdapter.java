package play.android.com.trackthehub.data;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import play.android.com.trackthehub.R;
import play.android.com.trackthehub.util.Issues;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder>{

    ArrayList<Issues>mlist;
    Context C;

    public IssueAdapter(ArrayList<Issues> mlist,Context context) {
        this.mlist = mlist;
        C=context;
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        return new IssueViewHolder(inflater.inflate(R.layout.cardviewissues,parent,false));
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {


        holder.reponame.setText(mlist.get(position).Reponame);
        holder.desc.setText(mlist.get(position).desc);

        String number=mlist.get(position).number;
        String user=mlist.get(position).user;
        String date=mlist.get(position).date;
        String event=String.format(C.getString(R.string.issueopen),number,date,user);
        holder.event.setText(event);

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder
    {

        TextView reponame,desc,event;



        public IssueViewHolder(View itemView) {
            super(itemView);
            reponame=(TextView)itemView.findViewById(R.id.tvRepo);
            desc=(TextView)itemView.findViewById(R.id.tvDesc);
            event=(TextView)itemView.findViewById(R.id.tvevent);
        }
    }






}
