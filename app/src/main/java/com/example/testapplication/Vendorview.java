package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.testapplication.adapter.VendorAdapter;


import com.example.testapplication.db.budget.Budget_Impl;
import com.example.testapplication.db.budget.Ibudget;

public class Vendorview extends AppCompatActivity {
    private int eid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorview);
        Bundle b=getIntent().getExtras();
        if(b != null){
            eid=b.getInt("eid",0);
        }

        Toolbar toolbar = findViewById(R.id.abb_menu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent i = new Intent(getApplicationContext(),GraphActivity.class);
              //  startActivity(i);
            }
        });

        Ibudget budget = new Budget_Impl(this);//Budget Interface

        VendorAdapter adapter = new VendorAdapter(this); //Budget Adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_v);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//item to item decoration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }



    public void handleOnClick(View v){
        if(v.getId()==R.id.lv_add_vendor){
            Intent i = new Intent(getApplicationContext(),Addvendor.class);
            Bundle b=new Bundle();
            b.putInt("eid",eid);
            startActivity(i);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            //Settings btn
            Intent i = new Intent(getApplicationContext(), AppSettingsActivity.class);
            Bundle b = new Bundle();
            b.putString("pre_activity","listBudget");
            i.putExtras(b);
            startActivity(i);
            //finish();//check
        }

        return super.onOptionsItemSelected(item);
    }


}