package com.example.davidsavrda.sidetracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import java.util.Calendar;


public class Alarm extends ActionBarActivity {
    ArrayList<AlarmInfo> alarms;
    TimePicker time;
    String username;
    String medicationName;
    AlarmManager alarmManager;
    PendingIntent alarmIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        username = getIntent().getExtras().getString("Username");
        medicationName = getIntent().getExtras().getString("Medication");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        alarms = new ArrayList<AlarmInfo>();
        time = (TimePicker) findViewById(R.id.timePicker);
        time.setIs24HourView(true);
        alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
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
        Context context = getApplicationContext();
        int hour = time.getCurrentHour();
        int minute = time.getCurrentMinute();
        String alarmTime = hour + ":" + minute;
        if(((RadioButton) findViewById(R.id.Daily)).isChecked()){
            Intent intent = new Intent(context, AlarmAlert.class);
            intent.putExtra("Medication", medicationName);
            alarmIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
            AlarmInfo mAlarm = new AlarmInfo("Monday", alarmTime);
            alarms.add(mAlarm);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, 1);

            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            if(hour >= 13) {
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.PM);
            }
            else{
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.AM);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
            AlarmInfo tuesAlarm = new AlarmInfo("Tuesday", alarmTime);
            alarms.add(tuesAlarm);
            calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, 2);

            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            if(hour >= 13) {
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.PM);
            }
            else{
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.AM);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
            AlarmInfo wAlarm = new AlarmInfo("Wednesday", alarmTime);
            calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, 3);

            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            if(hour >= 13) {
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.PM);
            }
            else{
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.AM);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
            alarms.add(wAlarm);
            AlarmInfo thAlarm = new AlarmInfo("Thursday", alarmTime);
            calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, 4);

            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            if(hour >= 13) {
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.PM);
            }
            else{
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.AM);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
            alarms.add(thAlarm);
            AlarmInfo fAlarm = new AlarmInfo("Friday", alarmTime);
            calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, 5);

            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            if(hour >= 13) {
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.PM);
            }
            else{
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.AM);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
            alarms.add(fAlarm);
            AlarmInfo saAlarm = new AlarmInfo("Saturday", alarmTime);
            calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, 6);

            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            if(hour >= 13) {
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.PM);
            }
            else{
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.AM);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
            alarms.add(saAlarm);
            AlarmInfo suAlarm = new AlarmInfo("Sunday", alarmTime);
            calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, 7);

            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            if(hour >= 13) {
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.PM);
            }
            else{
                calendar.set(Calendar.HOUR, hour - 12);
                calendar.set(Calendar.AM_PM, Calendar.AM);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
            alarms.add(suAlarm);
        }
        else{
            Calendar calendar = Calendar.getInstance();
            if(((CheckBox) findViewById(R.id.Monday)).isChecked()){
                AlarmInfo mAlarm = new AlarmInfo("Monday", alarmTime);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, 1);

                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                if(hour >= 13) {
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                }
                else{
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
                alarms.add(mAlarm);
            }
            if(((CheckBox) findViewById(R.id.Tuesday)).isChecked()){
                AlarmInfo tuesAlarm = new AlarmInfo("Tuesday", alarmTime);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, 2);

                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                if(hour >= 13) {
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                }
                else{
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
                alarms.add(tuesAlarm);
            }
            if(((CheckBox) findViewById(R.id.Wednesday)).isChecked()){
                AlarmInfo wAlarm = new AlarmInfo("Wednesday", alarmTime);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, 3);

                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                if(hour >= 13) {
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                }
                else{
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
                alarms.add(wAlarm);
            }
            if(((CheckBox) findViewById(R.id.Thursday)).isChecked()){
                AlarmInfo thAlarm = new AlarmInfo("Thursday", alarmTime);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, 4);

                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                if(hour >= 13) {
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                }
                else{
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
                alarms.add(thAlarm);
            }
            if(((CheckBox) findViewById(R.id.Friday)).isChecked()){
                AlarmInfo fAlarm = new AlarmInfo("Friday", alarmTime);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, 5);

                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                if(hour >= 13) {
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                }
                else{
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
                alarms.add(fAlarm);
            }
            if(((CheckBox) findViewById(R.id.Saturday)).isChecked()){
                AlarmInfo saAlarm = new AlarmInfo("Saturday", alarmTime);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, 6);

                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                if(hour >= 13) {
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                }
                else{
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
                alarms.add(saAlarm);
            }
            if(((CheckBox) findViewById(R.id.Sunday)).isChecked()){
                AlarmInfo suAlarm = new AlarmInfo("Sunday", alarmTime);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, 7);

                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                if(hour >= 13) {
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                }
                else{
                    calendar.set(Calendar.HOUR, hour - 12);
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, alarmIntent);
                alarms.add(suAlarm);
            }

        }

        Intent intent = new Intent(context, Medication.class);
        startActivity(intent);
    }
}
