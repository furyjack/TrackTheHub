package play.android.com.trackthehub.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import play.android.com.trackthehub.R;


public class RepoAdapter extends RecyclerViewCursorAdapter<RepoAdapter.ListViewHolder> {





    Context c;

    public RepoAdapter(Cursor cursor,Context context) {
        super(cursor);
        c=context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewrepo,parent,false);

        return new ListViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(ListViewHolder holder, Cursor cursor) {

        String title,decs,lang,star,fork,stoday;
        title=cursor.getString(cursor.getColumnIndex(MyContract.RepoEntry.COLUMN_TITLE));
        decs=cursor.getString(cursor.getColumnIndex(MyContract.RepoEntry.COLUMN_DESC));
        decs=(decs==null)?".":decs;
        lang=cursor.getString(cursor.getColumnIndex(MyContract.RepoEntry.COLUMN_LANG));
        star=cursor.getString(cursor.getColumnIndex(MyContract.RepoEntry.COLUMN_STARS));
        fork=cursor.getString(cursor.getColumnIndex(MyContract.RepoEntry.COLUMN_FORKS));
        stoday=cursor.getString(cursor.getColumnIndex(MyContract.RepoEntry.COLUMN_STODAY));

        holder.Title.setText(title);
        holder.desc.setText(decs);
        holder.star.setText(star);
        holder.lang.setText(lang);
        holder.fork.setText(fork);
        holder.starttoday.setText(String.format(c.getString(R.string.starstodayrepo), stoday));
        holder.imstar.setImageResource(R.drawable.ic_star);

    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView Title,desc,star,fork,starttoday,lang;
        ImageView imstar;



        ListViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.tvTitle);
            desc = (TextView) itemView.findViewById(R.id.tvDesc);
            lang = (TextView) itemView.findViewById(R.id.tvLang);
            star = (TextView) itemView.findViewById(R.id.tvstar);
            fork = (TextView) itemView.findViewById(R.id.tvfork);
            starttoday = (TextView) itemView.findViewById(R.id.tvTodays);
            imstar=(ImageView)itemView.findViewById(R.id.Cbstar);
        }
    }





}
