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


public class Medication extends ActionBarActivity {

    public MedicationInfo med;
    public ListView alarms;
    public TextView title;
    public TextView details;
    String username;
    JSONObject medicationInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        username = getIntent().getExtras().getString("Username");
        String medicationString = getIntent().getExtras().getString("Medication");
        try {
            medicationInfo = new JSONObject(medicationString);
        }
        catch(JSONException e){

        }
        med = new MedicationInfo();
        try {
            med.name = medicationInfo.getString("Name");
            med.detail = medicationInfo.getString("Details");
            JSONArray alarms = medicationInfo.getJSONArray("Alarms");
            for(int index = 0; index < alarms.length(); index++){
                JSONObject JSONalarm = alarms.getJSONObject(index);
                String alarmDay = JSONalarm.getString("Day");
                String alarmTime = JSONalarm.getString("Time");
                AlarmInfo info = new AlarmInfo();
                info.day = alarmDay;
                info.time = alarmTime;
                med.alarms.add(info);
            }
            JSONArray sideEffects = medicationInfo.getJSONArray("SideEffects");
            for(int index = 0; index < sideEffects.length(); index++){
                JSONObject JSONsideEffect = sideEffects.getJSONObject(index);
                String name = JSONsideEffect.getString("Name");
                String description = JSONsideEffect.getString("Description");
                SideEffect side = new SideEffect();
                side.name = name;
                side.description = description;
                med.sideEffects.add(side);
            }


        }
        catch(JSONException e){

        }
        title = (TextView) findViewById(R.id.Title);
        title.setText(med.name);
        details = (TextView) findViewById(R.id.textView6);
        details.setText(med.detail);
        alarms = (ListView) findViewById(R.id.AlarmListView);
        ArrayList<String> times = new ArrayList<String>();
        for(AlarmInfo alarmInfo : med.alarms){
            times.add("Day:" + alarmInfo.day + " Time:" + alarmInfo.time);
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, times);
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
        intent.putExtra("Medication", med.name);
        intent.putExtra("Username", username);
        try {
            intent.putExtra("SideEffects", getSideEffects().toString());
        }
        catch (JSONException e){

        }
        startActivity(intent);
    }

    public void addAlarm(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, Alarm.class);
        intent.putExtra("Medication", med.name);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    public JSONObject getSideEffects() throws JSONException{
        JSONObject infoToPass = new JSONObject();
        JSONArray effects = new JSONArray();
        for(SideEffect sides : med.sideEffects){
            JSONObject side = new JSONObject();
            side.put("Name", sides.name);
            side.put("Description", sides.description);
            effects.put(side);
        }
        infoToPass.put("SideEffects", effects);
        return infoToPass;
    }
}
