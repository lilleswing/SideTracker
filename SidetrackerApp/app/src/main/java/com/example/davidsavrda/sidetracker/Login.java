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
    private TextView usernameEditText;
    private TextView passwordEditText;


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

    public void loginClicked(View v){
        Context context = getApplicationContext();

        String givenUsername = usernameEditText.getText().toString();
        String givenPassword = passwordEditText.getText().toString();
        RestClient.setAuth(givenUsername, givenPassword);

        final AsyncTask<Object, Void, Boolean> isLoggedIn = new AsyncTask<Object, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Object... params) {
                return RestClient.login();
            }
        }.execute();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("Username", ((TextView) findViewById(R.id.username)).getText().toString());
        intent.putExtra("Password", ((TextView) findViewById(R.id.Password)).getText().toString());
        startActivity(intent);
    }
}
