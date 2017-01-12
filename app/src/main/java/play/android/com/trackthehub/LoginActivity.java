package play.android.com.trackthehub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import play.android.com.trackthehub.util.LoginAsyncTask;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    ProgressBar pbar;
    FloatingActionButton btnlogin;

    public void login(View v) throws ExecutionException, InterruptedException {
        pbar.setVisibility(View.VISIBLE);
        btnlogin.setVisibility(View.INVISIBLE);

        String username=etUsername.getText().toString();
        String password=etPassword.getText().toString();
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
                    Toast.makeText(getApplicationContext(), "something went wrong please try again", Toast.LENGTH_SHORT).show();
                   pbar.setVisibility(View.INVISIBLE);
                    btnlogin.setVisibility(View.VISIBLE);
                }
                else
                {

                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.putString("token", token[0]);
                    editor.apply();
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
        SharedPreferences sharedPref = this.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        String token=sharedPref.getString("token","-1");
        if(token.equals("-1"))
        {

        }
        else
        {
            Intent loginIntent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(loginIntent);
            finish();
        }

    }
}
