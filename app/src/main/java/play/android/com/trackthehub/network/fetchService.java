package play.android.com.trackthehub.network;


import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import play.android.com.trackthehub.data.MyContract;
import play.android.com.trackthehub.util.Repo;
import play.android.com.trackthehub.util.Utils;

public class fetchService extends IntentService{


    String URL;
    String user;
    RequestQueue mQueue;
    int code;

    public fetchService() {
        super("fetchService");
    }



    @Override
    protected void onHandleIntent(final Intent intent) {

        mQueue= Volley.newRequestQueue(getApplicationContext());
        URL=intent.getStringExtra("url");
        code=intent.getIntExtra("code",-1);
        user=intent.getStringExtra("user");


        JsonArrayRequest requestA=new JsonArrayRequest(Request.Method.GET, URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

             switch (code)
             {
                 case 1:  //get user repo
                 {

                     ArrayList<Repo>mlist= Utils.getRepoListFromJson(response,user);
                     ContentValues[]values=new ContentValues[mlist.size()];
                     int count=0;
                     for(Repo x:mlist)
                     {
                         ContentValues v=new ContentValues();
                         v.put(MyContract.RepoEntry.COLUMN_TITLE,x.Title);
                         v.put(MyContract.RepoEntry.COLUMN_DESC,x.desc);
                         v.put(MyContract.RepoEntry.COLUMN_LANG,x.Lang);
                         v.put(MyContract.RepoEntry.COLUMN_STARS,x.stars);
                         v.put(MyContract.RepoEntry.COLUMN_FORKS,x.forks);
                         v.put(MyContract.RepoEntry.COLUMN_STODAY,x.startoday);
                         v.put(MyContract.RepoEntry.COLUMN_USER,x.user);
                         values[count]=v;



                         count++;
                     }
                     getContentResolver().bulkInsert(MyContract.buildrepowithuser(user),values);




                 }






             }


               //sendBroadcast();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(fetchService.this, "error occured", Toast.LENGTH_SHORT).show();
                Log.e("error", "onErrorResponse: "+error.getMessage() );

            }
        });


        JsonObjectRequest requesto=new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                switch (code)
                {


                    case 2:
                    {

                        String avatar_url="null";
                        try {

                            avatar_url=response.getString("avatar_url");
                            Utils.SetString("dp_url",avatar_url,getApplicationContext());
                            Utils.SetString("user_fetched","true",getApplicationContext());

                            Intent i=new Intent();
                            i.putExtra("dp_url",avatar_url);
                            i.setAction("play.android.com.trackthehub");
                            sendBroadcast(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }




                }


                //sendBroadcast();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(fetchService.this, "error occured", Toast.LENGTH_SHORT).show();
                Log.e("error", "onErrorResponse: "+error.getMessage() );

            }
        });




if(code==1)
mQueue.add(requestA);
        else
    mQueue.add(requesto);

    }
}
