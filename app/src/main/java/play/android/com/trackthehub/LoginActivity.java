package play.android.com.trackthehub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import play.android.com.trackthehub.model.Owner;
import play.android.com.trackthehub.util.RetrofitInterface;
import play.android.com.trackthehub.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    ProgressBar pbar;
    FloatingActionButton btnlogin;

    public void login(View v) throws ExecutionException, InterruptedException {
        pbar.setVisibility(View.VISIBLE);
        btnlogin.setVisibility(View.INVISIBLE);

        final String username=etUsername.getText().toString();
        final String password=etPassword.getText().toString();
        final String basicAuth = "Basic " + Base64.encodeToString(("furyjack:HARBHAJAn1996").getBytes(), Base64.NO_WRAP);

        RetrofitInterface.User userinterface=Myapplication.getRetrofit().create(RetrofitInterface.User.class);
        Call<Owner>logincall=userinterface.getuser(basicAuth);

        logincall.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                if(response.code()==200)
                {
                    Myapplication.setUser(response.body());
                    Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                    Utils.SetString("authhash",basicAuth,getApplicationContext());
                    Utils.SetString("loggedin","true",getApplicationContext());
                    pbar.setVisibility(View.INVISIBLE);
                    Intent loginIntent=new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(loginIntent);
                    finish();



                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.INVISIBLE);
                    btnlogin.setVisibility(View.VISIBLE);


                }




            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                pbar.setVisibility(View.INVISIBLE);
                btnlogin.setVisibility(View.VISIBLE);
            }
        });




    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPassword= (EditText) findViewById(R.id.EtPassword);
        etUsername=(EditText)findViewById(R.id.EtUsername);
        pbar=(ProgressBar)findViewById(R.id.pbar);
        btnlogin=(FloatingActionButton)findViewById(R.id.fbLogin);
//        String loggedin= Utils.getString("loggedin","false",this);
//        if(loggedin.equals("true"))
//        {
//            Intent loginIntent=new Intent(getApplicationContext(),HomeActivity.class);
//            startActivity(loginIntent);
//            finish();
//
//        }


    }
}
