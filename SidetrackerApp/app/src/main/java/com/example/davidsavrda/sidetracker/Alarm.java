package com.example.davidsavrda.sidetracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.example.davidsavrda.sidetracker.client.RestClient;
import com.example.davidsavrda.sidetracker.model.WsAlarm;
import com.example.davidsavrda.sidetracker.model.WsMedication;
import com.example.davidsavrda.sidetracker.model.WsSideEffect;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class Alarm extends ActionBarActivity {
    List sideEffects;
    List days;
    List times;
    String password;
    TimePicker time;
    String username;
    String medicationName;
    AlarmManager alarmManager;
    PendingIntent alarmIntent;
    ArrayList<WsAlarm> alarms;
    ArrayList<WsAlarm> newAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarms = new ArrayList<WsAlarm>();
        username = getIntent().getExtras().getString("Username");
        password = getIntent().getExtras().getString("Password");
        medicationName = getIntent().getExtras().getString("Medication");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        medicationName = getIntent().getExtras().getString("Medication");
        sideEffects = Arrays.asList(getIntent().getExtras().getString("SideEffects").split(","));
        days = Arrays.asList(getIntent().getExtras().getString("Days").split(","));
        times = Arrays.asList(getIntent().getExtras().getString("Times").split(","));
        for(int index = 0; index < days.size(); index++){
            WsAlarm alarmObject = new WsAlarm();
            alarmObject.setDay(days.get(index).toString());
            alarmObject.setTime(times.get(index).toString());
            alarms.add(alarmObject);
        }
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
            WsAlarm mAlarm = new WsAlarm();
            mAlarm.setDay("Monday");
            mAlarm.setTime(alarmTime);
            newAlarm.add(mAlarm);

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
            WsAlarm tuesAlarm = new WsAlarm();
            mAlarm.setDay("Tuesday");
            mAlarm.setTime(alarmTime);
            newAlarm.add(tuesAlarm);
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
            WsAlarm wAlarm = new WsAlarm();
            mAlarm.setDay("Wednesday");
            mAlarm.setTime(alarmTime);
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
            newAlarm.add(wAlarm);
            WsAlarm thAlarm = new WsAlarm();
            mAlarm.setDay("Thursday");
            mAlarm.setTime(alarmTime);
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
            newAlarm.add(thAlarm);
            WsAlarm fAlarm = new WsAlarm();
            mAlarm.setDay("Friday");
            mAlarm.setTime(alarmTime);
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
            newAlarm.add(fAlarm);
            WsAlarm saAlarm = new WsAlarm();
            mAlarm.setDay("Saturday");
            mAlarm.setTime(alarmTime);
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
            newAlarm.add(saAlarm);
            WsAlarm suAlarm = new WsAlarm();
            mAlarm.setDay("Sunday");
            mAlarm.setTime(alarmTime);
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
            newAlarm.add(suAlarm);
        }
        else{
            Calendar calendar = Calendar.getInstance();
            if(((CheckBox) findViewById(R.id.Monday)).isChecked()){
                WsAlarm mAlarm = new WsAlarm();
                mAlarm.setDay("Monday");
                mAlarm.setTime(alarmTime);
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
                newAlarm.add(mAlarm);
            }
            if(((CheckBox) findViewById(R.id.Tuesday)).isChecked()){
                WsAlarm tuesAlarm = new WsAlarm();
                tuesAlarm.setDay("Tuesday");
                tuesAlarm.setTime(alarmTime);
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
                newAlarm.add(tuesAlarm);
            }
            if(((CheckBox) findViewById(R.id.Wednesday)).isChecked()){
                WsAlarm wAlarm = new WsAlarm();
                wAlarm.setDay("Wednesday");
                wAlarm.setTime(alarmTime);
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
                newAlarm.add(wAlarm);
            }
            if(((CheckBox) findViewById(R.id.Thursday)).isChecked()){
                WsAlarm thAlarm = new WsAlarm();
                thAlarm.setDay("Thursday");
                thAlarm.setTime(alarmTime);
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
                newAlarm.add(thAlarm);
            }
            if(((CheckBox) findViewById(R.id.Friday)).isChecked()){
                WsAlarm fAlarm = new WsAlarm();
                fAlarm.setDay("Friday");
                fAlarm.setTime(alarmTime);
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
                newAlarm.add(fAlarm);
            }
            if(((CheckBox) findViewById(R.id.Saturday)).isChecked()){
                WsAlarm saAlarm = new WsAlarm();
                saAlarm.setDay("Saturday");
                saAlarm.setTime(alarmTime);
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
                newAlarm.add(saAlarm);
            }
            if(((CheckBox) findViewById(R.id.Sunday)).isChecked()){
                WsAlarm suAlarm = new WsAlarm();
                suAlarm.setDay("Sunday");
                suAlarm.setTime(alarmTime);
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
                newAlarm.add(suAlarm);
            }

        }
        AsyncTask<Object, Void, List<WsMedication>> isLoggedIn = new AsyncTask<Object, Void, List<WsMedication>>() {
            @Override
            protected List<WsMedication> doInBackground(Object... params) {
                WsMedication newMed = new WsMedication();
                newMed.setName(medicationName);
                newMed.setAlarms(newAlarm);
                List<WsMedication> medForCall = new ArrayList<WsMedication>();
                medForCall.add(newMed);
                return RestClient.updateMedication(medForCall);
            }

            @Override
            protected void onPostExecute(List<WsMedication> result) {

            }
        }.execute();
        Intent intent = new Intent(context, Medication.class);
        startActivity(intent);
    }
}
