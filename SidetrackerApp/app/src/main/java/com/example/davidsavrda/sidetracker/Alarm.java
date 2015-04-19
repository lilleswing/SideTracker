package com.example.davidsavrda.sidetracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Alarm extends ActionBarActivity {
    ArrayList<AlarmInfo> alarms;
    TimePicker time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        alarms = new ArrayList<AlarmInfo>();
        time = (TimePicker) findViewById(R.id.timePicker);
        time.setIs24HourView(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
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

    public void addAlarm(View v){
        int hour = time.getCurrentHour();
        int minute = time.getCurrentMinute();
        String alarmTime = hour + ":" + minute;
        if(((RadioButton) findViewById(R.id.Daily)).isChecked()){
            AlarmInfo mAlarm = new AlarmInfo("Monday", alarmTime);
            alarms.add(mAlarm);
            AlarmInfo tuesAlarm = new AlarmInfo("Tuesday", alarmTime);
            alarms.add(tuesAlarm);
            AlarmInfo wAlarm = new AlarmInfo("Wednesday", alarmTime);
            alarms.add(wAlarm);
            AlarmInfo thAlarm = new AlarmInfo("Thursday", alarmTime);
            alarms.add(thAlarm);
            AlarmInfo fAlarm = new AlarmInfo("Friday", alarmTime);
            alarms.add(fAlarm);
            AlarmInfo saAlarm = new AlarmInfo("Saturday", alarmTime);
            alarms.add(saAlarm);
            AlarmInfo suAlarm = new AlarmInfo("Sunday", alarmTime);
            alarms.add(suAlarm);
        }
        else{
            if(((CheckBox) findViewById(R.id.Monday)).isChecked()){
                AlarmInfo mAlarm = new AlarmInfo("Monday", alarmTime);
                alarms.add(mAlarm);
            }
            if(((CheckBox) findViewById(R.id.Tuesday)).isChecked()){
                AlarmInfo tuesAlarm = new AlarmInfo("Tuesday", alarmTime);
                alarms.add(tuesAlarm);
            }
            if(((CheckBox) findViewById(R.id.Wednesday)).isChecked()){
                AlarmInfo wAlarm = new AlarmInfo("Wednesday", alarmTime);
                alarms.add(wAlarm);
            }
            if(((CheckBox) findViewById(R.id.Thursday)).isChecked()){
                AlarmInfo thAlarm = new AlarmInfo("Thursday", alarmTime);
                alarms.add(thAlarm);
            }
            if(((CheckBox) findViewById(R.id.Friday)).isChecked()){
                AlarmInfo fAlarm = new AlarmInfo("Friday", alarmTime);
                alarms.add(fAlarm);
            }
            if(((CheckBox) findViewById(R.id.Saturday)).isChecked()){
                AlarmInfo saAlarm = new AlarmInfo("Saturday", alarmTime);
                alarms.add(saAlarm);
            }
            if(((CheckBox) findViewById(R.id.Sunday)).isChecked()){
                AlarmInfo suAlarm = new AlarmInfo("Sunday", alarmTime);
                alarms.add(suAlarm);
            }

        }
        Context context = getApplicationContext();
        Intent intent = new Intent(context, Medication.class);
        startActivity(intent);
    }
}
