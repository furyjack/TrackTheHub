package play.android.com.trackthehub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import play.android.com.trackthehub.util.LoginAsyncTask;
import play.android.com.trackthehub.util.Utils;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    ProgressBar pbar;
    FloatingActionButton btnlogin;

    public void login(View v) throws ExecutionException, InterruptedException {
        pbar.setVisibility(View.VISIBLE);
        btnlogin.setVisibility(View.INVISIBLE);

        final String username=etUsername.getText().toString();
        String password=etPassword.getText().toString();
        if(!Utils.getString(username+":token","null",getApplicationContext()).equals("null"))
        {
           
            Utils.SetString("loggedin","true",getApplicationContext());
            Intent loginIntent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(loginIntent);
            finish();
            return;



        }

        final String[] token = {null};
        String url="https://api.github.com/authorizations";
        LoginAsyncTask a=new LoginAsyncTask();
        a.execute(url,username,password);
        a.setPostListener(new LoginAsyncTask.onPostExcecuteListner() {
            @Override
            public void onPostExecute(String s) {
                token[0] =s;
                pbar.setVisibility(View.INVISIBLE);
                btnlogin.setVisibility(View.VISIBLE);

                if(token[0] ==null) {
                    Toast.makeText(getApplicationContext(), R.string.errorlogin, Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.INVISIBLE);
                    btnlogin.setVisibility(View.VISIBLE);
                }
                else
                {


                     Utils.SetString(username+":token",token[0],getApplicationContext());
                    //   write it to file in an encrypted way





                    Utils.SetString("loggedin","true",getApplicationContext());
                    Utils.SetString("username",username,getApplicationContext());
                    Intent loginIntent=new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(loginIntent);
                    finish();



                }

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
        String loggedin= Utils.getString("loggedin","false",this);
        if(loggedin.equals("true"))
        {
            Intent loginIntent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(loginIntent);
            finish();

        }


    }
}
