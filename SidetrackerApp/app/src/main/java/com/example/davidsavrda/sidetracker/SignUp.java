package com.example.davidsavrda.sidetracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class SignUp extends ActionBarActivity {
    //Not sure what the URL will be for the sign up yet
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
        //Make the call here
        //After the call if there is an error
        ((TextView) findViewById(R.id.username)).setText("");
        ((TextView) findViewById(R.id.Password)).setText("");
        ((TextView) findViewById(R.id.confirmPassword)).setText("");
        //Otherwise we go to the main view
        Context context = getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("Username", ((TextView) findViewById(R.id.username)).getText().toString());
        startActivity(intent);
    }

    public void cancelPressed(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, Login.class);
        startActivity(intent);

    }
}
