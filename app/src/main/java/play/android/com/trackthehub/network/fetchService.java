package play.android.com.trackthehub.network;


import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import play.android.com.trackthehub.Myapplication;
import play.android.com.trackthehub.data.MyContract;
import play.android.com.trackthehub.model.Repo;
import play.android.com.trackthehub.util.RetrofitInterface;
import play.android.com.trackthehub.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fetchService extends IntentService{






    public static final String ACTION_DATA_UPDATED="play.android.com.trackthehub.updated";
    public static final String ACTION_UPDATE_DATA="play.android.com.trackthehub.update";
    public static final String TAG="error.trackthehub";

    public fetchService() {
        super("fetchService");
    }



    @Override
    protected void onHandleIntent(final Intent intent) {

        if(intent.getAction().equals(ACTION_UPDATE_DATA))
        {
            String hash= Utils.getString("authhash","null",getApplicationContext());
            RetrofitInterface.User userinterface= Myapplication.getRetrofit().
                                                  create(RetrofitInterface.User.class);

            Call<List<Repo>> getreposcall=userinterface.getrepos(hash);
            getreposcall.enqueue(new Callback<List<Repo>>() {
                @Override
                public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                    if(response.code()==200)
                    {

                        List<Repo> mlist=response.body();
                        ArrayList<ContentValues> values=new ArrayList<>();
                        String username=Myapplication.getUser().getLogin();
                        for(Repo repo:mlist)
                        {

                            ContentValues value=new ContentValues();
                            value.put(MyContract.RepoEntry.COLUMN_TITLE,repo.getName());
                            value.put(MyContract.RepoEntry.COLUMN_DESC,repo.getDescription());
                            value.put(MyContract.RepoEntry.COLUMN_LANG,repo.getLanguage());
                            value.put(MyContract.RepoEntry.COLUMN_STARS,""+repo.getStargazersCount());
                            value.put(MyContract.RepoEntry.COLUMN_FORKS,""+repo.getForksCount());
                            value.put(MyContract.RepoEntry.COLUMN_STODAY,"0");
                            value.put(MyContract.RepoEntry.COLUMN_USER,username);

                            values.add(value);


                        }

                        ContentValues[]array=values.toArray(new ContentValues[values.size()]);
                        getContentResolver().bulkInsert(MyContract.buildrepowithuser(username),array);

                        Intent DataUpdatedIntent=new Intent();
                        DataUpdatedIntent.setAction(ACTION_DATA_UPDATED);
                        sendBroadcast(DataUpdatedIntent);


                    }
                    else
                    {
                        Log.d(TAG, "onResponse: handle intent"+response.code());
                    }

                }

                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {

                    Log.d(TAG, "onFailure: handleintent");
                }
            });



        }



    }
}
