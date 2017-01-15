package play.android.com.trackthehub.util;


import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public  class Utils {



     static public Repo getdata()
    {

        return new Repo("furyjack/ItsMyGang","its an awesome app:O","Java","320","19","40","furyjack");
    }

  public  static void SetString(String key,String value, Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.apply();


    }

    public static String getString(String key,String value, Context context)
    {

        SharedPreferences sharedPref = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        return sharedPref.getString(key,value);


    }

    public static ArrayList<Repo> getRepoListFromJson(JSONArray data,String username)
    {

        ArrayList<Repo>list=new ArrayList<>();
        for(int i=0;i<data.length();i++)
        {
            try {
                JSONObject obj=data.getJSONObject(i);
                String Title,Desc,lang,stars,forks,stoday,user;

                Title=obj.getString("full_name");
                Desc=obj.getString("description");
                lang=obj.getString("language");
                stars=obj.getString("stargazers_count");
                forks=obj.getString("forks_count");
                stoday="0";
                user=username;

                Repo r=new Repo(Title,Desc,lang,stars,forks,stoday,user);
                list.add(r);



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

return list;



    }
}
