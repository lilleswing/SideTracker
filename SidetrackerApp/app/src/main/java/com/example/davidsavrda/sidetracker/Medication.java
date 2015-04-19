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

import java.util.ArrayList;


public class Medication extends ActionBarActivity {

    public MedicationInfo med;
    public ListView alarms;
    public TextView title;
    public TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Place holder stuff
        med = new MedicationInfo();
        med.name = "Test";
        med.detail = "Details";
        AlarmInfo alarm = new AlarmInfo();
        alarm.time = "9:00";
        alarm.day = "Monday";
        med.alarms.add(alarm);
        //Stuff we will probably keep
        title = (TextView) findViewById(R.id.Title);
        title.setText(med.name);
        details = (TextView) findViewById(R.id.Title);
        details.setText(med.detail);
        alarms = (ListView) findViewById(R.id.AlarmListView);
        ArrayList<String> times = new ArrayList<String>();
        times.add(alarm.time);
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
        startActivity(intent);
    }

    public void addAlarm(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, Alarm.class);
        startActivity(intent);
    }
}
