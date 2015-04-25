package com.example.davidsavrda.sidetracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.davidsavrda.sidetracker.client.RestClient;
import com.example.davidsavrda.sidetracker.model.WsAlarm;
import com.example.davidsavrda.sidetracker.model.WsMedication;
import com.example.davidsavrda.sidetracker.model.WsSideEffect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SideEffects extends ActionBarActivity {
    List sideEffects;
    List days;
    List times;
    ListView sideEffectList;
    String username;
    String password;
    String medicationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side__effects);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        username = getIntent().getExtras().getString("Username");
        password = getIntent().getExtras().getString("Password");
        medicationName = getIntent().getExtras().getString("Medication");
        sideEffects = Arrays.asList(getIntent().getExtras().getString("SideEffects").split(","));
        days = Arrays.asList(getIntent().getExtras().getString("Days").split(","));
        times = Arrays.asList(getIntent().getExtras().getString("Times").split(","));

        //Stuff to keep
        sideEffectList = (ListView) findViewById(R.id.sideEffects);
        final ArrayList<String> names = new ArrayList<String>();
        for(int index = 0; index < sideEffects.size(); index++){
            names.add(sideEffects.get(index).toString());
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        sideEffectList.setAdapter(arrayAdapter);
        sideEffectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                ((TextView) findViewById(R.id.Description)).setText(names.get(position));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_side__effects, menu);
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

    public void addSideEffect(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, AddSideEffect.class);
        intent.putExtra("Medication", medicationName);
        intent.putExtra("Username", username);
        intent.putExtra("Password", password);
        startActivity(intent);
    }


}
