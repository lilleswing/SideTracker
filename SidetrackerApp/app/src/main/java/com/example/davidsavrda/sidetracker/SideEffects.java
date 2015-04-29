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
import java.util.concurrent.ExecutionException;


public class SideEffects extends ActionBarActivity {
    List sideEffects;
    List days;
    List times;
    int position;
    ListView sideEffectList;
    List<WsMedication> medications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side__effects);
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

        //Stuff to keep
        sideEffectList = (ListView) findViewById(R.id.sideEffects);
        final ArrayList<String> names = new ArrayList<String>();
        for(int index = 0; index < medications.get(position).getSideEffects().size(); index++){
            names.add(medications.get(position).getSideEffects().get(index).getDescription());
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

        intent.putExtra("Position", position);

        startActivity(intent);
    }


}
