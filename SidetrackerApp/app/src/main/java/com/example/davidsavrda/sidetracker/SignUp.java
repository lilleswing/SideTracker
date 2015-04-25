package com.example.davidsavrda.sidetracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.davidsavrda.sidetracker.client.RestClient;
import com.example.davidsavrda.sidetracker.model.WsAppUser;
import com.example.davidsavrda.sidetracker.model.WsMedication;

import java.util.List;


public class SignUp extends ActionBarActivity {
    //Not sure what the URL will be for the sign up yet
    private WsAppUser user;
    public final static String apiURL = "localhost:8080/signUp?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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


    public void signUpPressed(View v){
        Context context = getApplicationContext();
        final WsAppUser newUser = new WsAppUser();
        newUser.setUserName(((TextView) findViewById(R.id.username)).getText().toString());
        newUser.setPassword(((TextView) findViewById(R.id.username)).getText().toString());

        final AsyncTask<Object, Void, WsAppUser> isLoggedIn = new AsyncTask<Object, Void, WsAppUser>() {
            @Override
            protected WsAppUser doInBackground(Object... params) {
                return RestClient.createUser(newUser);
            }

            @Override
            protected void onPostExecute(WsAppUser result) {

                user = result;
            }

        }.execute();

        if(user == null) {
            ((TextView) findViewById(R.id.username)).setText("");
            ((TextView) findViewById(R.id.Password)).setText("");
            ((TextView) findViewById(R.id.confirmPassword)).setText("");
        }
        //Otherwise we go to the main view
        else {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            intent.putExtra("Username", ((TextView) findViewById(R.id.username)).getText().toString());
            intent.putExtra("Password", ((TextView) findViewById(R.id.Password)).getText().toString());
            startActivity(intent);
        }
    }

    public void cancelPressed(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, Login.class);
        startActivity(intent);

    }
}
