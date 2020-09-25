package com.example.testapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.testapplication.db.task.ITask;
import com.example.testapplication.db.task.Task_Impl;

import java.util.Arrays;

public class addTask extends AppCompatActivity {

    EditText et_tname,et_tdesc,et_tdate;
    RadioGroup rdstatus;
    RadioButton rdPend,rdComplete;
    Spinner spinnerT;
    String categoryType[]={"Ceremony", "Decoration", "Reception", "Jewelry"};

    String spinnerItem = null;

    private String has_title="Add Task", category ;//,guestname="null",String,age,invitation,phone,email,address;
    private int id = 0;

    private class TaskLayoutClass{

        String tname,status, category;
        public int id = 0;
        private Context c;
        public TaskLayoutClass(Context c) {
            Task_Impl task_ =new Task_Impl(c);
            this.c=c;
        }
        public Task_Impl task_;
        public void InitVariables(){
            rdstatus=(RadioGroup)findViewById(R.id.rdStatus);
            rdPend=(RadioButton)findViewById(R.id.rdPend);
            rdComplete=(RadioButton)findViewById(R.id.rdComplete);


        }


        public void loadValuesFromLayout(){

            this.task_.tname = ((EditText)findViewById(R.id.et_tname)).getText().toString();
            if(this.status!=null){
                this.task_.status=this.status;}

            if(null != category){
                int index = Arrays.asList(categoryType).indexOf(category);
                spinnerT.setSelection(index, true);
                spinnerItem = category;
            }


            this.task_.description = ((EditText)findViewById(R.id.et_tdesc)).getText().toString();
            //this.task_.category = ((EditText)findViewById(R.id.ca)).getText().toString();
            this.task_.tdate = ((EditText)findViewById(R.id.et_tdate)).getText().toString();


        }

        public void setValuesToLayout(int id){

            this.task_=task_.getTaskById(id);
            ((EditText)findViewById(R.id.et_tname)).setText(this.task_.tname, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.et_tdesc)).setText(this.task_.description, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.et_tdate)).setText(this.task_.tdate, TextView.BufferType.EDITABLE);

        }


        public void setRadioEvents(){
            rdstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // find which radio button is selected
                    if (checkedId == R.id.rdPend) {
                        tlayout.status = "pending";
                    } else if (checkedId == R.id.rdComplete) {
                        tlayout.status = "completed";
                    }
                    Log.d("Add Guest>>","gender= " + tlayout.status);
                }

            });

            spinnerT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerItem=spinnerT.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }

    TaskLayoutClass tlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        tlayout= new TaskLayoutClass(this);
        final Context context = this;
        Bundle b = getIntent().getExtras();
        tlayout.InitVariables();
        tlayout.setRadioEvents();

        Toolbar toolbar = findViewById(R.id.addtb); //set toolbar
        setSupportActionBar(toolbar);
        //set toolbar title
        toolbar.setTitle("Add Task");//either default or from bundle
        //for back button
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //default previous intent
                Intent i = new Intent(getApplicationContext(),taskList.class);
                startActivity(i);
            }
        });

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
            Log.d("ADD_GUEST>>","Navigating to AppSettingsActivity!");
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
        ITask guest = new Task_Impl(this);
        TaskLayoutClass guestlayout = tlayout;

        if(v.getId() == R.id.tsave_btn){
            Log.d("BUTTON","Save Button Pressed!");
            guestlayout.loadValuesFromLayout();
            guestlayout.task_.addTask();

            //  logInputs();
            //default go back to list view

            Intent i = new Intent(getApplicationContext(),taskList.class);
            startActivity(i);
        }

    }


}


  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final String[] defaultCat = {"Ceremony", "Decoration", "Reception", "Jewelry"};
        Spinner cat = (Spinner) findViewById(R.id.spinnerT);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, defaultCat);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catAdapter);

    } */
