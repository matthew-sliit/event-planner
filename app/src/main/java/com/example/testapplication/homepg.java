package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.example.testapplication.db.event.Event_Impl;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class homepg extends AppCompatActivity {
    /*
    ================== HomePage =========================
    read event id: done
    pass event id: done
    app settings root: done
    -----------patches-------------
    patch 1: budget root: connected
    patch 2: task root: connected
    patch 3: guest root: connected
    patch 4: vendor root: connected
     */
    private boolean allowActivityLaunch = false;
    private Event_Impl event;
    private Bundle b = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepg);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar4); //set toolbar
        setSupportActionBar(toolbar);
        //get event data
        event = new Event_Impl(this);
        String byDefault = "Default";
        ICategory category = new Category(this);
        //if category is empty
        category.setDefault(getResources().getStringArray(R.array.default_categories));
        try {
            event = event.getEventById(event.getSelectEvent());
            //Log.d("homepg>>","event id -> " +event.id);
            //Log.d("homepg>>","event name -> " +event.ename);
            if (event.ename != null) {
                ((TextView) findViewById(R.id.tv_event)).setText(event.ename);
                b.putInt(ConstantBundleKeys.EVENT_ID,event.id);
                allowActivityLaunch = true;
            }else{
                b.putInt(ConstantBundleKeys.EVENT_ID,0);
                ((TextView) findViewById(R.id.tv_event)).setText(byDefault);
                allowActivityLaunch = false;
            }
        }catch (NullPointerException e){
            ((TextView) findViewById(R.id.tv_event)).setText(byDefault);
            allowActivityLaunch = false;
        }
        Log.d("Homepage>>","Eid passed -> " + event.id);
    }
    //menu layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    //menu right corner buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            //Settings btn
            //Log.d("ADD_BUDGET>>","Navigating to AppSettingsActivity!");
            Intent i = new Intent(getApplicationContext(),ListCategory.class);
            startActivity(i);
        }
        if(item.getItemId()==R.id.action_about_us) {
            Intent i = new Intent(getApplicationContext(), About_us.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    public void openEvents(View view){
        Intent intent = new Intent(this, eventList.class);

        Toast.makeText(this, "Opening Events...", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }
    public void openBudgets(View view){
        if(allowActivityLaunch) {
            Intent intent = new Intent(this, ListBudgetsActivity.class);

            Toast.makeText(this, "Opening Budgets...", Toast.LENGTH_SHORT).show();
            intent.putExtras(b);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Add Event to continue", Toast.LENGTH_SHORT).show();
        }
    }
    public void openTasks(View view){
        if(allowActivityLaunch) {
            Intent intent = new Intent(this, taskList.class);

            Toast.makeText(this, "Opening Tasks...", Toast.LENGTH_SHORT).show();
            intent.putExtras(b);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Add Event to continue", Toast.LENGTH_SHORT).show();
        }
    }
    public void openGuests(View view){
        if(allowActivityLaunch) {
            Intent intent = new Intent(this, ViewGuest.class);

            Toast.makeText(this, "Opening Guests...", Toast.LENGTH_SHORT).show();
            intent.putExtras(b);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Add Event to continue", Toast.LENGTH_SHORT).show();
        }

    }
    public void openVendors(View view){
        if(allowActivityLaunch) {
            Intent intent = new Intent(this, Vendorview.class);

            Toast.makeText(this, "Opening Vendors...", Toast.LENGTH_SHORT).show();
            intent.putExtras(b);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Add Event to continue", Toast.LENGTH_SHORT).show();
        }
    }
    public void openSummary(View view){
        Intent intent = new Intent(this, Summary.class);

        Toast.makeText(this, "Opening Summary...", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //refresh activity
        finish();
        Intent i = getIntent();
        i.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}