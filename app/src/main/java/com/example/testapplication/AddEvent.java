package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testapplication.db.event.Event_Impl;
import com.example.testapplication.db.event.IEvent;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class AddEvent extends AppCompatActivity {

   EditText eName,edate,etime;



    private String has_title="Add Event", category ;//,guestname="null",String,age,invitation,phone,email,address;
    private int id = 0;

    private class EventLayoutClass{

        String eName,edate ,etime;
        public int id = 0;
        private Context c;
        public EventLayoutClass(Context c) {
             event_ =new Event_Impl(c);
            this.c=c;
        }
        public Event_Impl event_;
        public void InitVariables(){
           /* rdstatus=(RadioGroup)findViewById(R.id.rdStatus);
            rdPend=(RadioButton)findViewById(R.id.rdPend);
            rdComplete=(RadioButton)findViewById(R.id.rdComplete);

            spinnerT = (Spinner)findViewById(R.id.spinnerT);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,android.R.layout.simple_spinner_item,categoryType);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerT.setAdapter(adapter); */

        }
        public void loadValuesFromLayout(){

            this.event_.ename = ((EditText)findViewById(R.id.eName)).getText().toString();




            this.event_.etime = ((EditText)findViewById(R.id.Etime)).getText().toString();
            //this.task_.category = ((EditText)findViewById(R.id.ca)).getText().toString();
            this.event_.edate = ((EditText)findViewById(R.id.Edate)).getText().toString();


        }

        public void setValuesToLayout(int id){

            this.event_=event_.getEventById(id);
            ((EditText)findViewById(R.id.eName)).setText(this.event_.ename, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.Edate)).setText(this.event_.etime, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.Etime)).setText(this.event_.edate, TextView.BufferType.EDITABLE);

        }


      /*  public void setRadioEvents(){
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
            }); */

        }
    EventLayoutClass elayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        elayout= new EventLayoutClass(this);
        final Context context = this;
        Bundle b = getIntent().getExtras();
        elayout.InitVariables();


        Toolbar toolbar = findViewById(R.id.tbAddEvent); //set toolbar
        setSupportActionBar(toolbar);
        //set toolbar title
        toolbar.setTitle("Add Event");//either default or from bundle
        //for back button
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),eventList.class);
                startActivity(i);
            }
        });

        final Calendar calender = Calendar.getInstance();
        final SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calender.set(Calendar.YEAR, year);
                calender.set(Calendar.MONTH, monthOfYear);
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ((EditText)findViewById(R.id.Edate)).setText(df.format(calender.getTime()));
            }
        };

        ((EditText)findViewById(R.id.Edate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(view.getContext(), date, calender
                        .get(Calendar.YEAR), calender.get(Calendar.MONTH),
                        calender.get(Calendar.DAY_OF_MONTH)).show();

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
            Log.d("ADD_Event>>","Navigating to AppSettingsActivity!");
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
        IEvent event = new Event_Impl(this);
        EventLayoutClass eventlayout = elayout;

        if(v.getId() == R.id.Esave){
            Log.d("BUTTON","Save Button Pressed!");
            eventlayout.loadValuesFromLayout();
            eventlayout.event_.AddEvent();

            //  logInputs();
            //default go back to list view

            Intent i = new Intent(getApplicationContext(),eventList.class);
            startActivity(i);
        }
    }







}