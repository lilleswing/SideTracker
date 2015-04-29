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
import android.widget.EditText;
import android.widget.TextView;

import com.example.davidsavrda.sidetracker.client.RestClient;
import com.example.davidsavrda.sidetracker.model.WsAlarm;
import com.example.davidsavrda.sidetracker.model.WsMedication;
import com.example.davidsavrda.sidetracker.model.WsSideEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class AddSideEffect extends ActionBarActivity {
    int position;
    String medicationName;
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
        setContentView(R.layout.activity_add_side_effect);
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
        iDs.addAll(Arrays.asList(getIntent().getExtras().getString("IDS").split(",")));
        names.addAll(Arrays.asList(getIntent().getExtras().getString("Names").split(",")));

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_side_effect, menu);
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

    public void addSideEffect(View v) throws InterruptedException, ExecutionException{

        AsyncTask<Object, Void, List<WsMedication>> isLoggedIn = new AsyncTask<Object, Void, List<WsMedication>>() {
            @Override
            protected List<WsMedication> doInBackground(Object... params) {
                Log.w("NumberofMeds", String.valueOf(numberOfMeds));
                List<WsMedication> medicationCall = new ArrayList<WsMedication>();
                for(int index = 0; index < numberOfMeds; index++){
                    WsMedication newMed = new WsMedication();
                    newMed.setName(names.get(index));
                    newMed.setId(Long.getLong(iDs.get(index)));
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

                    ArrayList<String> desc = sideEffectsDesc.get(position);
                    ArrayList<String> sideID = sideEffectID.get(position);
                    List<WsSideEffect> sides = new ArrayList<WsSideEffect>();
                    for(int sideIndex =0; sideIndex < sideEffectsDesc.size(); sideIndex++){
                        WsSideEffect newEffect = new WsSideEffect();
                        newEffect.setDescription(desc.get(sideIndex));
                        newEffect.setId(Long.getLong(sideID.get(sideIndex)));
                        sides.add(newEffect);

                    }
                    if(position == index){
                        WsSideEffect newEffect = new WsSideEffect();
                        newEffect.setDescription(((TextView) findViewById(R.id.Description)).getText().toString());
                        sides.add(newEffect);
                    }
                    newMed.setAlarms(alarms);
                    newMed.setSideEffects(sides);

                    medicationCall.add(newMed);
                    Log.w("Number", String.valueOf(medicationCall.size()));
                }



                return RestClient.updateMedication(medicationCall);
            }
        };
        Context context = getApplicationContext();
        Intent intent = new Intent(context, SideEffects.class);
        List<WsMedication> updatedMed = isLoggedIn.execute().get();

        WsMedication med = updatedMed.get(0);


        intent.putExtra("Username", username);
        intent.putExtra("Password", password);
        intent.putExtra("Position", position);
        intent.putExtra("Medication", (updatedMed.get(position)).getName());
        intent.putExtra("ID", (updatedMed.get(position)).getId());
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

        intent.putExtra("Names", medNames.toString());
        intent.putExtra("IDS", medIds.toString());
    }
}
