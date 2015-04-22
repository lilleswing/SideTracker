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


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;


public class Login extends ActionBarActivity {
    public final static String apiURL = "localhost:8080/login?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void loginClicked(View v){
        Context context = getApplicationContext();
        String url = "http://localhost:8080/login?username=" + ((TextView) findViewById(R.id.username)).getText().toString() +
                "&password=" + ((TextView) findViewById(R.id.Password));
        //This occurs when the login was unssuccessful
        if(false) {
            ((TextView) findViewById(R.id.ErrorMessage)).setText("Error logging in");
            ((TextView) findViewById(R.id.username)).setText("");
            ((TextView) findViewById(R.id.Password)).setText("");
        }
        //This occurs when the login was successful
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("Username", ((TextView) findViewById(R.id.username)).getText().toString());
        startActivity(intent);
    }

    public void signUpPressed(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, SignUp.class);
        startActivity(intent);

    }

}
