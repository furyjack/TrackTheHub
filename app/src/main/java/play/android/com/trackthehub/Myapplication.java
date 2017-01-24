package play.android.com.trackthehub;

import android.app.Application;

import com.facebook.stetho.Stetho;

import play.android.com.trackthehub.model.Owner;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Myapplication extends Application {

   static Retrofit retrofit;
   static Owner user;

    @Override
    public void onCreate() {
        super.onCreate();
      
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

// Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

// Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

// Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

// Initialize Stetho with the Initializer
        Stetho.initialize(initializer);


      retrofit=new Retrofit.Builder()
                .baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create())
                .build();


    }


    public static  Retrofit getRetrofit() {
        return retrofit;
    }

    public static Owner getUser() {
        return user;
    }

    public static void setUser(Owner user) {
        Myapplication.user = user;
    }
}
