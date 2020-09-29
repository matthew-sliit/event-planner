package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityEmu extends AppCompatActivity {
    String[] events = {"Birthday E0","Convocation E1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_emu);


        Toolbar toolbar = findViewById(R.id.menu2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu_foreground);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(v);
                //home btn
            }
        });

        //spinner
        Spinner event = ((Spinner)findViewById(R.id.spinner_h_emu));
        ArrayAdapter<String> eveAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,events);
        eveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        event.setAdapter(eveAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            //Settings btn
            Log.d("BUTTON","Action Settings Pressed!");
            //route.addNewRoute(this,new AppSettingsActivity(),null);

            Bundle b = new Bundle();

            //spinner getText
            String getEvent = ((Spinner)findViewById(R.id.spinner_h_emu)).getSelectedItem().toString();


            Intent i = new Intent(getApplicationContext(),AppSettingsActivity.class);
            startActivity(i);
        }
        /*
        if(item.getItemId()==R.id.action_settings){
            //About us page
        }
         */
        return super.onOptionsItemSelected(item);
    }
    public void handleClick(View v){
        if(v.getId()==R.id.budgetBtn){
            //budget management btn
            Intent i = new Intent(getApplicationContext(),GraphActivity.class);
            startActivity(i);
        }
    }
}