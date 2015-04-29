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
import com.example.davidsavrda.sidetracker.model.WsMedication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Medication extends ActionBarActivity {


    public ListView alarms;
    public TextView title;
    public TextView details;
    int position;
    Long medID;
    List<WsMedication> medications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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






        title = (TextView) findViewById(R.id.Title);
        title.setText(medications.get(position).getName());
        details = (TextView) findViewById(R.id.textView6);
        details.setText("");
        alarms = (ListView) findViewById(R.id.AlarmListView);
        ArrayList<String> alarmInfos = new ArrayList<String>();
        for(int index = 0; index < medications.get(position).getAlarms().size(); index++){
            alarmInfos.add("Day: " + medications.get(position).getAlarms().get(index).getDay() + " Time: " + medications.get(position).getAlarms().get(index).getTime());
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

        intent.putExtra("Position", position);

        startActivity(intent);
    }

    public void addAlarm(View v){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, Alarm.class);

        intent.putExtra("Position", position);

        startActivity(intent);
    }


}
