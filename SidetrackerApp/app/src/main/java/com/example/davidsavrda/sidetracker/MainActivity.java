package com.example.davidsavrda.sidetracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;

import com.example.davidsavrda.sidetracker.client.RestClient;
import com.example.davidsavrda.sidetracker.model.WsAlarm;
import com.example.davidsavrda.sidetracker.model.WsMedication;
import com.example.davidsavrda.sidetracker.model.WsSideEffect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
    private List<WsMedication> medications;
    private ListView medicationsList;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // NOTE (LESWING) HACK to allow syncronous network IO
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getExtras().getString("Username");
        password = getIntent().getExtras().getString("Password");

        AsyncTask<Object, Void, List<WsMedication>> isLoggedIn = new AsyncTask<Object, Void, List<WsMedication>>() {
            @Override
            protected List<WsMedication> doInBackground(Object... params) {
                return RestClient.getMedications();
            }


        };
        medications = new ArrayList<>();
        try {
             medications = isLoggedIn.execute().get();
        }
        catch (InterruptedException e){

        }
        catch (ExecutionException e){

        }
        //Now we get back to handling things that will actually be there
        medicationsList = (ListView) findViewById(R.id.medicationList);
        ArrayList<String> names = new ArrayList<String>();
        for(int index = 0; index < medications.size(); index++){
            names.add(medications.get(index).getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        medicationsList.setAdapter(arrayAdapter);
        medicationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Context context = getApplicationContext();
                Intent intent = new Intent(context, Medication.class);
                intent.putExtra("Username", username);
                intent.putExtra("Password", password);
                intent.putExtra("Position", position);
                intent.putExtra("Medication", (medications.get(position)).getName());
                intent.putExtra("MedicationID", (medications.get(position)).getId());
                intent.putExtra("NumberOfMeds", medications.size());

                ArrayList<String> medNames = new ArrayList<String>();
                ArrayList<Long> medIds = new ArrayList<Long>();



                for(int index = 0; index < medications.size(); index++){
                    medNames.add(medications.get(index).getName());
                    medIds.add(medications.get(index).getId());

                    List<WsSideEffect> sideEffects = medications.get(index).getSideEffects();
                    List<WsAlarm> alarms = medications.get(index).getAlarms();
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
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
