package com.example.davidsavrda.sidetracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;


public class MainActivity extends ActionBarActivity {
    private ArrayList<MedicationInfo> medications;
    private ListView medicationsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Placeholder work so that we can make sure everything more or less works without FHIR and DB
        //This will be replaced with a look up to get all of the medications and their information for the person
        medications = new ArrayList<MedicationInfo>();
        for(int i = 0; i < 5; i++){
            MedicationInfo newMed = new MedicationInfo();
            newMed.name = "Med" + i;
            newMed.detail = "These are just placeholders, this is for Med" + i;
            AlarmInfo alarm = new AlarmInfo();
            alarm.day = "Monday";
            alarm.time = "9:00";
            medications.add(newMed);
        }
        //Now we get back to handling things that will actually be there
        medicationsList = (ListView) findViewById(R.id.medicationList);
        ArrayList<String> names = new ArrayList<String>();
        for(MedicationInfo med: medications){
            names.add(med.name);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        medicationsList.setAdapter(arrayAdapter);
        medicationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Context context = getApplicationContext();
                Intent intent = new Intent(context, Medication.class);
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
