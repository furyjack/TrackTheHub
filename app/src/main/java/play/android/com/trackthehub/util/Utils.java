package play.android.com.trackthehub.util;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public  class Utils {

      static ArrayList<String> data=new ArrayList<>(5);

     static public ArrayList<String> getdata()
    {
        data.add("furyjack/ItsMyGang");
        data.add("its an awesome app:O");
        data.add("Java");
        data.add("320");
        data.add("19");
        data.add("40");

        return data;
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
