package com.example.davidsavrda.sidetracker;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Medication extends ActionBarActivity {


    public ListView alarms;
    public TextView title;
    public TextView details;
    String username;
    String password;
    String name;
    List<String> sideEffects;
    List<String> alarmDays;
    List<String> alarmTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        username = getIntent().getExtras().getString("Username");
        password = getIntent().getExtras().getString("Password");
        name = getIntent().getExtras().getString("Medication");
        sideEffects = Arrays.asList(getIntent().getExtras().getString("SideEffects").split(","));
        alarmDays = Arrays.asList(getIntent().getExtras().getString("Days").split(","));
        alarmTime = Arrays.asList(getIntent().getExtras().getString("Times").split(","));

        title = (TextView) findViewById(R.id.Title);
        title.setText(name);
        details = (TextView) findViewById(R.id.textView6);
        details.setText("");
        alarms = (ListView) findViewById(R.id.AlarmListView);
        ArrayList<String> alarmInfos = new ArrayList<String>();
        for(int index = 0; index < alarmDays.size(); index++){
            alarmInfos.add("Day: " + alarmDays.get(index) + " Time: " + alarmTime.get(index));
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alarmInfos);
        alarms.setAdapter(arrayAdapter);
        alarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String toRemove = arrayAdapter.getItem(position);
                arrayAdapter.remove(toRemove);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medication, menu);
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

    public void viewSideEffect(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, SideEffects.class);
        intent.putExtra("Medication", name);
        intent.putExtra("Password", password);
        intent.putExtra("Username", username);
        intent.putExtra("SideEffects", sideEffects.toString());
        intent.putExtra("Times", alarmTime.toString());
        intent.putExtra("Days", alarmDays.toString());

        startActivity(intent);
    }

    public void addAlarm(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, Alarm.class);
        intent.putExtra("Medication", name);
        intent.putExtra("Username", username);
        intent.putExtra("Password", password);
        intent.putExtra("Days", alarmDays.toString());
        intent.putExtra("Times", alarmTime.toString());
        intent.putExtra("SideEffects", sideEffects.toString());
        startActivity(intent);
    }


}
