package play.android.com.trackthehub.network;


import android.app.IntentService;
import android.content.Intent;

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








        }



    }
}
