package com.example.davidsavrda.sidetracker;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SideEffects extends ActionBarActivity {
    ArrayList<SideEffect> sideEffects;
    ListView sideEffectList;
    String username;
    String medicationName;
    JSONObject jsonSides;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side__effects);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        sideEffects = new ArrayList<SideEffect>();
        username = getIntent().getExtras().getString("Username");
        medicationName = getIntent().getExtras().getString("Medication");
        String sideEffectString = getIntent().getExtras().getString("SideEffects");
        try {
             jsonSides = new JSONObject(sideEffectString);
             JSONArray sides = jsonSides.getJSONArray("SideEffects");
             for(int index = 0; index < sides.length(); index++){
                 JSONObject side = sides.getJSONObject(index);
                 SideEffect newSideEffect = new SideEffect();
                 newSideEffect.name = side.getString("Name");
                 newSideEffect.description = side.getString("Description");
                 sideEffects.add(newSideEffect);
             }

        }
        catch(JSONException e){

        }


        //Stuff to keep
        sideEffectList = (ListView) findViewById(R.id.sideEffects);
        ArrayList<String> names = new ArrayList<String>();
        for(SideEffect sideEffectLoop : sideEffects){
            names.add(sideEffectLoop.name);
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        sideEffectList.setAdapter(arrayAdapter);
        sideEffectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                ((TextView) findViewById(R.id.Description)).setText(sideEffects.get(position).description);
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
        startActivity(intent);
    }


}
