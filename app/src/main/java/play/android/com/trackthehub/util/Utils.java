package play.android.com.trackthehub.util;


import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public  class Utils {



     static public Repo getdata()
    {

        return new Repo("furyjack/ItsMyGang","its an awesome app:O","Java","320","19","40",false);
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


}
