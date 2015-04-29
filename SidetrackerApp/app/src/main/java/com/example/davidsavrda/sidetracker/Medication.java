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
    int numberOfMeds;
    int position;
    Long medID;
    ArrayList<ArrayList<String>> sideEffectsDesc;
    ArrayList<ArrayList<String>> sideEffectID;
    ArrayList<ArrayList<String>> alarmDays;
    ArrayList<ArrayList<String>> alarmTime;
    ArrayList<ArrayList<String>> alarmIDs;
    ArrayList<String> iDs;
    ArrayList<String> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        username = getIntent().getExtras().getString("Username");
        position = getIntent().getExtras().getInt("Position");
        password = getIntent().getExtras().getString("Password");
        name = getIntent().getExtras().getString("Medication");
        medID = getIntent().getExtras().getLong("MedicationID");
        numberOfMeds = getIntent().getExtras().getInt("NumberOfMeds");
        iDs = new ArrayList<String>();
        names = new ArrayList<String>();
        sideEffectsDesc = new ArrayList<ArrayList<String>>();
        sideEffectID = new ArrayList<ArrayList<String>>();
        alarmDays = new ArrayList<ArrayList<String>>();
        alarmTime = new ArrayList<ArrayList<String>>();
        alarmIDs = new ArrayList<ArrayList<String>>();
        iDs.addAll(Arrays.asList(getIntent().getExtras().getString("MedNames").split(",")));
        names.addAll(Arrays.asList(getIntent().getExtras().getString("MedIDs").split(",")));

        for(int index = 0; index < numberOfMeds; index++){

            ArrayList<String> sideEffectDescs =  new ArrayList<String>();
                    sideEffectDescs.addAll(Arrays.asList(getIntent().getExtras().getString("SideEffectDesc" + index).split(",")));
            sideEffectsDesc.add(sideEffectDescs);
            ArrayList<String> sideEffectIDs = new ArrayList<String>();
                    sideEffectIDs.addAll(Arrays.asList(getIntent().getExtras().getString("SideEffectID" + index).split(",")));
            sideEffectID.add(sideEffectIDs);
            ArrayList<String> days = new ArrayList<String>();
                    days.addAll(Arrays.asList(getIntent().getExtras().getString("Days" + index).split(",")));
            alarmDays.add(days);

            ArrayList<String> times = new ArrayList<String>();
                    times.addAll(Arrays.asList(getIntent().getExtras().getString("Times" + index).split(", ")));
            alarmTime.add(times);
            ArrayList<String> ids = new ArrayList<String>();
                    ids.addAll(Arrays.asList(getIntent().getExtras().getString("AlarmID" + index).split(",")));
            alarmIDs.add(ids);

        }




        title = (TextView) findViewById(R.id.Title);
        title.setText(name);
        details = (TextView) findViewById(R.id.textView6);
        details.setText("");
        alarms = (ListView) findViewById(R.id.AlarmListView);
        ArrayList<String> alarmInfos = new ArrayList<String>();
        for(int index = 0; index < alarmDays.get(position).size(); index++){
            alarmInfos.add("Day: " + alarmDays.get(position).get(index) + " Time: " + alarmTime.get(position).get(index));
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
        intent.putExtra("NumberOfMeds", numberOfMeds);
        intent.putExtra("Position", position);
        intent.putExtra("ID", medID);
        intent.putExtra("Names", names.get(0).toString());
        intent.putExtra("IDS", iDs.get(0).toString());
        for(int index = 0; index < numberOfMeds; index++){
            intent.putExtra("SideEffectDesc" + index, sideEffectsDesc.get(index).toString());
            intent.putExtra("SideEffectID" + index, sideEffectID.get(index).toString());
            intent.putExtra("AlarmID" + index, alarmIDs.get(index).toString());
            intent.putExtra("Days" + index, alarmDays.get(index).toString());
            intent.putExtra("Times" + index, alarmTime.get(index).toString());
        }
        startActivity(intent);
    }

    public void addAlarm(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, Alarm.class);
        intent.putExtra("Medication", name);
        intent.putExtra("Password", password);
        intent.putExtra("Username", username);
        intent.putExtra("NumberOfMeds", numberOfMeds);
        intent.putExtra("Position", position);
        intent.putExtra("ID", medID);
        intent.putExtra("Names", names.get(0).toString());
        intent.putExtra("IDS", iDs.get(0).toString());
        for(int index = 0; index < numberOfMeds; index++){
            intent.putExtra("SideEffectDesc" + index, sideEffectsDesc.get(index).toString());
            intent.putExtra("SideEffectID" + index, sideEffectID.get(index).toString());
            intent.putExtra("AlarmID" + index, alarmIDs.get(index).toString());
            intent.putExtra("Days" + index, alarmDays.get(index).toString());
            intent.putExtra("Times" + index, alarmTime.get(index).toString());
        }
        startActivity(intent);
    }


}
