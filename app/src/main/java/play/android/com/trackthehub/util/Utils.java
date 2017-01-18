package play.android.com.trackthehub.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public static String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("myfile");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString+"\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static  void writetoFile(Context c,String string)
    {
        try {
            File file;
        if(!fileExistance(c))
             file = new File(c.getFilesDir(), "myfile");

        FileOutputStream outputStream;

            outputStream = c.openFileOutput("myfile", Context.MODE_PRIVATE);
            outputStream.write((string+'\n').getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean fileExistance(Context c){
        File file = c.getFileStreamPath("myfile");
        return file.exists();
    }


    public static ArrayList<Issues> getissueslist(String json) throws JSONException {

        JSONArray array=new JSONArray(json);
        ArrayList<Issues>list=new ArrayList<>();
        for(int i=0;i<array.length();i++)
        {
           JSONObject OBJ=array.getJSONObject(i);
            String type=OBJ.getString("type");
            if(type.equals("IssuesEvent"))
            {


                JSONObject repo=OBJ.getJSONObject("repo");
                String reponame=repo.getString("name");
                String created=OBJ.getString("created_at").substring(0,10);
                String desc=OBJ.getJSONObject("payload").getJSONObject("issue").getString("title");
                String number=OBJ.getJSONObject("payload").getJSONObject("issue").getString("number");
                String user=OBJ.getJSONObject("payload").getJSONObject("issue").getJSONObject("user").getString("login");

               list.add(new Issues(reponame,desc,number,created,user));
            }



        }

          return list;

    }
}
