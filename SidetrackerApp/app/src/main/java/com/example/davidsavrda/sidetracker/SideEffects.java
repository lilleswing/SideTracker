package com.example.davidsavrda.sidetracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.example.davidsavrda.sidetracker.client.RestClient;
import com.example.davidsavrda.sidetracker.model.WsAlarm;
import com.example.davidsavrda.sidetracker.model.WsMedication;
import com.example.davidsavrda.sidetracker.model.WsSideEffect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SideEffects extends ActionBarActivity {
    List sideEffects;
    List days;
    List times;
    int position;
    ListView sideEffectList;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side__effects);
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

        //Stuff to keep
        sideEffectList = (ListView) findViewById(R.id.sideEffects);
        final ArrayList<String> names = new ArrayList<String>();
        if(sideEffectsDesc.size() > 0) {
            for (int index = 0; index < sideEffectsDesc.get(position).size(); index++) {
                names.add(sideEffectsDesc.get(position).get(index));
            }
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        sideEffectList.setAdapter(arrayAdapter);
        sideEffectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                ((TextView) findViewById(R.id.Description)).setText(names.get(position));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_side__effects, menu);
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

    public void addSideEffect(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, AddSideEffect.class);
        intent.putExtra("Medication", name);
        intent.putExtra("Username", username);
        intent.putExtra("Password", password);
        intent.putExtra("NumberOfMeds", numberOfMeds);
        intent.putExtra("Position", position);
        intent.putExtra("ID", medID);
        intent.putExtra("Names", names.toString());
        intent.putExtra("IDS", iDs.toString());
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
