package com.example.davidsavrda.sidetracker;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.content.Context;
import android.widget.TextView;

import com.example.davidsavrda.sidetracker.client.RestClient;
import com.example.davidsavrda.sidetracker.model.WsAppUser;

import android.util.Log;

import java.util.concurrent.ExecutionException;


public class Login extends ActionBarActivity {
    private TextView usernameEditText;
    private TextView passwordEditText;
    private Boolean login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = ((TextView) findViewById(R.id.username));
        passwordEditText = ((TextView) findViewById(R.id.Password));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loginClicked(View v) throws ExecutionException, InterruptedException{
        Context context = getApplicationContext();

        String givenUsername = usernameEditText.getText().toString();
        String givenPassword = passwordEditText.getText().toString();
        RestClient.setAuth(givenUsername, givenPassword);

        final AsyncTask<Object, Void, Boolean> isLoggedIn = new AsyncTask<Object, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Object... params) {
                Boolean result = RestClient.login();
                Log.w("Result", result.toString());
                return result;
            }
            @Override
            protected void onPostExecute(Boolean result) {
                login = result;
            }
        };
        login = isLoggedIn.execute().get();
        Intent intent = new Intent(context, MainActivity.class);
        Log.w("Sidetracker", login.toString());
        if(!((TextView) findViewById(R.id.URL)).getText().toString().isEmpty()){
            RestClient.setUrl(((TextView) findViewById(R.id.URL)).getText().toString());
        }
        if(login) {
            intent.putExtra("Username", ((TextView) findViewById(R.id.username)).getText().toString());
            intent.putExtra("Password", ((TextView) findViewById(R.id.Password)).getText().toString());
            startActivity(intent);
        }
        else{
            ((TextView) findViewById(R.id.username)).setText("Error Logging in");
            ((TextView) findViewById(R.id.Password)).setText("");
        }
    }

    public void signUpPressed(View v){
        Context context = getApplicationContext();
        if(!((TextView) findViewById(R.id.URL)).getText().toString().isEmpty()){
            RestClient.setUrl(((TextView) findViewById(R.id.URL)).getText().toString());
        }
        Intent intent = new Intent(context, SignUp.class);
        startActivity(intent);
    }
}
