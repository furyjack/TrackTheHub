package play.android.com.trackthehub.util;


import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginAsyncTask extends AsyncTask<String,Void,String> {
    onPostExcecuteListner mlistener;

    public void setPostListener(onPostExcecuteListner l)
    {
        mlistener=l;
    }

    @SafeVarargs
    @Override
    protected final String doInBackground(String... pairs) {
        try {
            URL url=new URL(pairs[0]);
            String username=pairs[1];
            String password=pairs[2];
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String userCredentials = username+":"+password;
            String basicAuth = "Basic " + new String( Base64.encode(userCredentials.getBytes(),0));
            urlConnection.setRequestProperty ("Authorization", basicAuth);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");

            OutputStream os=urlConnection.getOutputStream();
            JSONArray arr=new JSONArray();
            arr.put("repo");
            arr.put("user");
            arr.put("gist");
            JSONObject obj=new JSONObject();
            obj.put("scopes",arr);
            obj.put("note","TrackTheHub");
            byte[] outputInBytes = obj.toString().getBytes("UTF-8");
            os.write(outputInBytes);
            os.close();
            urlConnection.connect();
            InputStream stream = urlConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            urlConnection.disconnect();
            JSONObject topLevel = new JSONObject(builder.toString());

            return topLevel.getString("token");





        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mlistener.onPostExecute(s);
    }

  public  interface onPostExcecuteListner
    {
        void onPostExecute(String s);
    }

}
