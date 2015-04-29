package com.example.davidsavrda.sidetracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
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
import java.util.concurrent.ExecutionException;


public class Alarm extends ActionBarActivity {

    TimePicker time;
    int position;
    String medicationName;
    AlarmManager alarmManager;
    PendingIntent alarmIntent;
    List<WsMedication> medications;
    List<WsAlarm> newAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        position = getIntent().getExtras().getInt("Position");
        AsyncTask<Object, Void, List<WsMedication>> isLoggedIn = new AsyncTask<Object, Void, List<WsMedication>>() {
            @Override
            protected List<WsMedication> doInBackground(Object... params) {
                return RestClient.getMedications();
            }


        };
        medications = new ArrayList<>();
        try {
            medications = isLoggedIn.execute().get();
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

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

    public void addAlarm(View v) throws InterruptedException, ExecutionException{
        Context context = getApplicationContext();
        newAlarm = new ArrayList<WsAlarm>();
        int hour = time.getCurrentHour();
        int minute = time.getCurrentMinute();
        final String newAlarmTime = hour + ":" + minute;
        if(((RadioButton) findViewById(R.id.Daily)).isChecked()){
            Intent intent = new Intent(context, AlarmAlert.class);
            intent.putExtra("Medication", medicationName);
            alarmIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
            WsAlarm mAlarm = new WsAlarm();
            mAlarm.setDay("Monday");
            mAlarm.setTime(newAlarmTime);
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
            mAlarm.setTime(newAlarmTime);
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
            mAlarm.setTime(newAlarmTime);
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
            mAlarm.setTime(newAlarmTime);
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
            mAlarm.setTime(newAlarmTime);
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
            mAlarm.setTime(newAlarmTime);
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
            mAlarm.setTime(newAlarmTime);
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
                mAlarm.setTime(newAlarmTime);
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
                tuesAlarm.setTime(newAlarmTime);
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
                wAlarm.setTime(newAlarmTime);
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
                thAlarm.setTime(newAlarmTime);
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
                fAlarm.setTime(newAlarmTime);
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
                saAlarm.setTime(newAlarmTime);
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
                suAlarm.setTime(newAlarmTime);
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

                WsMedication updatedMed = medications.get(position);
                List<WsAlarm> alarms = new ArrayList<WsAlarm>();
                alarms = updatedMed.getAlarms();
                for(int index = 0; index < newAlarm.size(); index++){
                    alarms.add(newAlarm.get(index));
                }
                updatedMed.setAlarms(alarms);
                medications.set(position, updatedMed);
                return RestClient.updateMedication(medications);




            }
        };
        Log.w("About to make the call", "I want to know if we get here");
        List<WsMedication> updatedMed = isLoggedIn.execute().get();
        Log.w("Return from put", String.valueOf(updatedMed.size()));

        WsMedication med = updatedMed.get(0);

        Intent intent = new Intent(context, Medication.class);


        intent.putExtra("Position", position);




        startActivity(intent);
    }
}
