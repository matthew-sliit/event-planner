package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testapplication.adapter.EventAdapter;
import com.example.testapplication.db.event.Event_Impl;
import com.example.testapplication.db.event.IEvent;


public class eventList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = findViewById(R.id.toolbar_add_event);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);


        IEvent event = new Event_Impl(this);

        EventAdapter adapter = new EventAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_e);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//item to item decoration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void handleOnClick(View v){
        if(v.getId()==R.id.lg_add_event){
            Intent i = new Intent(getApplicationContext(),AddEvent.class);
            startActivity(i);
        }

    }




}