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
    List<WsMedication> medications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_side_effect);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

                WsMedication updatedMed = medications.get(position);
                List<WsSideEffect> sideEffects = updatedMed.getSideEffects();
                WsSideEffect newSideEffect = new WsSideEffect();
                newSideEffect.setDescription(((TextView) findViewById(R.id.Description)).getText().toString());
                sideEffects.add(newSideEffect);
                updatedMed.setSideEffects(sideEffects);
                medications.set(position, updatedMed);
                return RestClient.updateMedication(medications);
            }
        };
        Context context = getApplicationContext();
        Intent intent = new Intent(context, SideEffects.class);
        List<WsMedication> updatedMed = isLoggedIn.execute().get();

        WsMedication med = updatedMed.get(0);



        intent.putExtra("Position", position);
        startActivity(intent);

    }
}
