package com.example.testapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.db.event.Event_Impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class editEvent extends AppCompatActivity {

    private String has_title="Edit Event";
    private int id = 0;

    private class EventLayoutClass {

        String eventname, date, time;
        public int id = 0;
        private Context c;

        public EventLayoutClass(Context c) {
            event_ = new Event_Impl(c);
            this.c = c;
        }
        public Event_Impl event_;

        public void loadValuesFromLayout() {

            this.event_.ename = ((EditText) findViewById(R.id.editName)).getText().toString();
            this.event_.edate = ((EditText) findViewById(R.id.editDate)).getText().toString();
            this.event_.etime = ((EditText) findViewById(R.id.editTime)).getText().toString();
        }

        public void setValuesToLayout(int id) {
           // this.InitVariables();

            this.event_ = event_.getEventById(id);
            ((EditText) findViewById(R.id.editName)).setText(this.event_.ename, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.editDate)).setText(this.event_.edate, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.editTime)).setText(this.event_.etime, TextView.BufferType.EDITABLE);
        }

    }
        Button closeButton;
        AlertDialog.Builder builder;
        EventLayoutClass elayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_event);

            Toolbar toolbar = findViewById(R.id.edittb);
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.back_btn);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(),eventList.class);
                    startActivity(i);
                }
            });

            elayout=new EventLayoutClass(this);

            Bundle b = getIntent().getExtras();
            if(b!=null){

                id=b.getInt("id",0);

                elayout.setValuesToLayout(id);
            }
            closeButton = (Button) findViewById(R.id.discard);
            builder = new AlertDialog.Builder(this);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Uncomment the below code to Set the message and title from the strings.xml file
                   // builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                    //Setting message manually and performing action on button click
                    builder.setMessage("Are you sure want to delete ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    elayout.event_.removeEvent(elayout.event_.id);
                                    Intent i = new Intent(getApplicationContext(),eventList.class);

                                    Toast.makeText(getApplicationContext(),"Event Deleted",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(i);
                                }


                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Delete Event");
                    alert.show();
                }
            });

            ((Button)findViewById(R.id.updatebtn)).setKeyListener(null);

            ((Button)findViewById(R.id.updatebtn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    elayout.loadValuesFromLayout();
                    if(validated()) {
                        elayout.event_.updateEvent(elayout.event_);
                        Intent i = new Intent(getApplicationContext(), eventList.class);
                        startActivity(i);
                    }
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
                    ((EditText)findViewById(R.id.editDate)).setText(df.format(calender.getTime()));
                }
            };

            ((EditText)findViewById(R.id.editDate)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new DatePickerDialog(view.getContext(), date, calender
                            .get(Calendar.YEAR), calender.get(Calendar.MONTH),
                            calender.get(Calendar.DAY_OF_MONTH)).show();

                }
            });


        }
    public boolean validated(){
        if(elayout.event_.ename.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(elayout.event_.edate.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(elayout.event_.etime.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter a time", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}


