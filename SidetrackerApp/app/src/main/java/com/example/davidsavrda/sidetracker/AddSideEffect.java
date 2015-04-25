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
import android.widget.EditText;

import com.example.davidsavrda.sidetracker.client.RestClient;
import com.example.davidsavrda.sidetracker.model.WsMedication;
import com.example.davidsavrda.sidetracker.model.WsSideEffect;

import java.util.ArrayList;
import java.util.List;


public class AddSideEffect extends ActionBarActivity {
    String medicationName;
    String Username;
    String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_side_effect);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    public void addSideEffect(View v){
        final WsSideEffect newSideEffect = new WsSideEffect();
        newSideEffect.setDescription(((EditText) findViewById(R.id.Description)).getText().toString());
        AsyncTask<Object, Void, List<WsMedication>> isLoggedIn = new AsyncTask<Object, Void, List<WsMedication>>() {
            @Override
            protected List<WsMedication> doInBackground(Object... params) {
                WsMedication newMed = new WsMedication();
                newMed.setName(medicationName);
                ArrayList<WsSideEffect> sideEffects = new ArrayList<WsSideEffect>();
                sideEffects.add(newSideEffect);
                newMed.setSideEffects(sideEffects);
                List<WsMedication> medForCall = new ArrayList<WsMedication>();
                medForCall.add(newMed);
                return RestClient.updateMedication(medForCall);
            }

            @Override
            protected void onPostExecute(List<WsMedication> result) {

            }
        }.execute();
        Context context = getApplicationContext();
        Intent intent = new Intent(context, SideEffects.class);
        startActivity(intent);
    }
}
