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
    String username;
    String password;
    String name;
    Long medID;
    int numberOfMeds;
    ArrayList<String> iDs;
    ArrayList<String> names;
    ArrayList<ArrayList<String>> sideEffectsDesc;
    ArrayList<ArrayList<String>> sideEffectID;
    ArrayList<ArrayList<String>> alarmDays;
    ArrayList<ArrayList<String>> alarmTime;
    ArrayList<ArrayList<String>> alarmIDs;
    ArrayList<WsAlarm> alarms;
    ArrayList<WsAlarm> newAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
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
        iDs.addAll(Arrays.asList(getIntent().getExtras().getString("Names").split(",")));
        names.addAll(Arrays.asList(getIntent().getExtras().getString("IDS").split(",")));

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
        if(alarmDays.size() > 0) {
            for (int index = 0; index < alarmDays.get(position).size(); index++) {
                WsAlarm alarmObject = new WsAlarm();
                alarmObject.setDay(alarmDays.get(position).get(index).toString());
                alarmObject.setTime(alarmTime.get(position).get(index).toString());
                alarms.add(alarmObject);
            }
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

                List<WsMedication> medicationCall = new ArrayList<WsMedication>();
                for(int index = 0; index < numberOfMeds; index++){
                    WsMedication newMed = new WsMedication();
                    newMed.setName(name);
                    newMed.setId(medID);
                    List<WsAlarm> alarms = new ArrayList<WsAlarm>();
                    ArrayList<String> days = alarmDays.get(position);
                    ArrayList<String> times = alarmTime.get(position);
                    ArrayList<String> ids = alarmIDs.get(position);
                    for(int alarmIndex = 0; alarmIndex < days.size(); alarmIndex++){
                        WsAlarm newalarm = new WsAlarm();
                        newalarm.setDay(days.get(alarmIndex));
                        newalarm.setTime(times.get(alarmIndex));
                        newalarm.setId(Long.getLong(ids.get(alarmIndex)));
                        alarms.add(newalarm);
                    }
                    alarms.addAll(newAlarm);
                    ArrayList<String> desc = sideEffectsDesc.get(position);
                    ArrayList<String> sideID = sideEffectID.get(position);
                    List<WsSideEffect> sides = new ArrayList<WsSideEffect>();
                    for(int sideIndex =0; sideIndex < sideEffectsDesc.size(); sideIndex++){
                        WsSideEffect newEffect = new WsSideEffect();
                        newEffect.setDescription(desc.get(sideIndex));
                        newEffect.setId(Long.getLong(sideID.get(sideIndex)));
                        sides.add(newEffect);

                    }
                    newMed.setAlarms(alarms);
                    newMed.setSideEffects(sides);
                    medicationCall.add(newMed);
                }



                return RestClient.updateMedication(medicationCall);
            }
        };
        List<WsMedication> updatedMed = isLoggedIn.execute().get();
        Log.w("Return from put", String.valueOf(updatedMed.size()));

        WsMedication med = updatedMed.get(0);

        Intent intent = new Intent(context, Medication.class);

        intent.putExtra("Username", username);
        intent.putExtra("Password", password);
        intent.putExtra("Position", position);
        intent.putExtra("Medication", (updatedMed.get(position)).getName());
        intent.putExtra("MedicationID", (updatedMed.get(position)).getId());
        intent.putExtra("NumberOfMeds", updatedMed.size());

        ArrayList<String> medNames = new ArrayList<String>();
        ArrayList<Long> medIds = new ArrayList<Long>();



        for(int index = 0; index < updatedMed.size(); index++){
            medNames.add(updatedMed.get(index).getName());
            medIds.add(updatedMed.get(index).getId());

            List<WsSideEffect> sideEffects = updatedMed.get(index).getSideEffects();
            List<WsAlarm> alarms = updatedMed.get(index).getAlarms();
            ArrayList<String> sideEffectsDesc = new ArrayList<String>();
            ArrayList<Long> sideEffectsIDs = new ArrayList<Long>();
            for(WsSideEffect side : sideEffects){
                sideEffectsDesc.add(side.getDescription());
                sideEffectsIDs.add(side.getId());
            }
            intent.putExtra("SideEffectDesc" + index, sideEffectsDesc.toString());
            intent.putExtra("SideEffectID" + index, sideEffectsIDs.toString());

            ArrayList<String> days = new ArrayList<String>();
            ArrayList<String> times = new ArrayList<String>();
            ArrayList<Long> alarmId = new ArrayList<Long>();
            for(WsAlarm alarm: alarms){
                days.add(alarm.getDay());
                times.add(alarm.getTime());
                alarmId.add(alarm.getId());
            }
            intent.putExtra("Days" + index, days.toString());
            intent.putExtra("Times" + index, times.toString());
            intent.putExtra("AlarmID" + index, alarmId.toString());

        }

        intent.putExtra("MedNames", medNames.toString());
        intent.putExtra("MedIDs", medIds.toString());

        startActivity(intent);
    }
}
