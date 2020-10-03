package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.testapplication.adapter.GuestAdapter;
import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.guest.Guest_Impl;
import com.example.testapplication.db.guest.IGuest;

public class ViewGuest extends AppCompatActivity {
    private int eid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_guest);
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            eid = b.getInt(ConstantBundleKeys.EVENT_ID, 0);
        }

        Toolbar toolbar = findViewById(R.id.toolbar_add_g);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),homepg.class);
                startActivity(i);
            }
        });

        IGuest guest = new Guest_Impl(this,eid);//Guest Interface

        GuestAdapter adapter = new GuestAdapter(this,eid); //Guest Adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_g);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//item to item decoration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void handleOnClick(View v){
        if(v.getId()==R.id.lg_add_guest){
            Intent i = new Intent(getApplicationContext(),AddGuest.class);
            Bundle b = new Bundle();
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);
            i.putExtras(b);
            startActivity(i);
        }

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
            //Log.d("ADD_GUEST>>","Navigating to AppSettingsActivity!");
            Intent i = new Intent(getApplicationContext(),ListCategory.class);
            startActivity(i);
        }
        if(item.getItemId()==R.id.action_about_us) {
            Intent i = new Intent(getApplicationContext(), About_us.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //refresh activity
        finish();
        startActivity(getIntent());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(getApplicationContext(),homepg.class);
            Bundle b = new Bundle();
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);
            i.putExtras(b);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}