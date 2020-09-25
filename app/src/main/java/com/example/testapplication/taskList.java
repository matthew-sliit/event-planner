package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.testapplication.adapter.TaskAdapter;
import com.example.testapplication.db.task.ITask;
import com.example.testapplication.db.task.Task_Impl;


public class taskList extends AppCompatActivity {

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Toolbar toolbar = findViewById(R.id.toolbar_add_g);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);


        ITask task = new Task_Impl(this);//Guest Interface

        TaskAdapter adapter = new TaskAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_t);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//item to item decoration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void handleOnClick(View v){
        if(v.getId()==R.id.lg_add_task){
            Intent i = new Intent(getApplicationContext(),addTask.class);
            startActivity(i);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



}


