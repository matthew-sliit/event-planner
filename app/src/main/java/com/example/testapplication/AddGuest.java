package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.db.guest.Guest_Impl;
import com.example.testapplication.db.guest.IGuest;

import java.util.*;
public class AddGuest extends AppCompatActivity {
    EditText etName,etPhone,etEmail,etAddress;
    RadioGroup radioGender,radioAge,radioInvitaion;
    RadioButton radioMale,radioFemale,radioAdult,radioChild,radioSent,radioNotsent;


    private String has_title="Add Guest";//,guestname="null",gender,age,invitation,phone,email,address;
    private int id = 0;

    private class GuestLayoutClass{

        String guestname,gender,age,invitation,phone,email,address;
        public int id = 0;
        private Context c;
        public GuestLayoutClass(Context c) {
             guest_=new Guest_Impl(c);
             this.c=c;
        }
       public Guest_Impl guest_;
        public void InitVariables(){
            radioGender=(RadioGroup)findViewById(R.id.radioGender);
            radioAge=(RadioGroup)findViewById(R.id.radioAge);
            radioInvitaion=(RadioGroup)findViewById(R.id.radioInvitation);
            radioAdult=(RadioButton)findViewById(R.id.radioAdult);
            radioChild=(RadioButton)findViewById(R.id.radioChild);
            radioFemale=(RadioButton)findViewById(R.id.radioFemale);
            radioMale=(RadioButton)findViewById(R.id.radioMale);
            radioNotsent=(RadioButton)findViewById(R.id.radioNotsent);
            radioSent=(RadioButton)findViewById(R.id.radioSent);

        }
        public void loadValuesFromLayout(){

            this.guest_.guestname = ((EditText)findViewById(R.id.etGuestName)).getText().toString();
            if(this.gender!=null){
                this.guest_.gender=this.gender;}
            if(this.age!=null){
                this.guest_.age=this.age;}
            if(this.invitation!=null){
                this.guest_.invitation=this.invitation;}
            this.guest_.phone = ((EditText)findViewById(R.id.etPhone)).getText().toString();
            this.guest_.email = ((EditText)findViewById(R.id.etEmail)).getText().toString();
            this.guest_.address = ((EditText)findViewById(R.id.etAddress)).getText().toString();
            Log.d("Add Guest>>","invitation " + invitation);
            Log.d("Add Guest>>","age " +age);
            Log.d("Add Guest>>","gender " + gender);

        }
        public void setValuesToLayout(int id){

            this.guest_=guest_.getGuestById(id);
            ((EditText)findViewById(R.id.etGuestName)).setText(this.guest_.guestname, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.etPhone)).setText(this.guest_.phone, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.etEmail)).setText(this.guest_.email, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.etAddress)).setText(this.guest_.address, TextView.BufferType.EDITABLE);

        }
    public void setRadioEvents(){
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioMale) {
                    glayout.gender = "male";
                } else if (checkedId == R.id.radioFemale) {
                    glayout.gender = "female";
                }
                Log.d("Add Guest>>","gender= " + glayout.gender);
            }

        });

        radioAge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioAdult) {
                    glayout.age = "adult";
                } else if (checkedId == R.id.radioChild) {
                    glayout.age = "child";
                }
            }

        });

        radioInvitaion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioSent) {
                    glayout.invitation = "invitation sent";
                } else if (checkedId == R.id.radioNotsent) {
                    glayout.invitation = "invitation not sent";
                }
            }

        });
    }
    }

     GuestLayoutClass glayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
    glayout= new GuestLayoutClass(this);
        final Context context = this;
        Bundle b = getIntent().getExtras();
        glayout.InitVariables();
        glayout.setRadioEvents();

       /* Toolbar toolbar = findViewById(R.id.tbAddguest); //set toolbar
        setSupportActionBar(toolbar);
        //set toolbar title
        toolbar.setTitle("Add Guest");//either default or from bundle
        //for back button
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //default previous intent
                Intent i = new Intent(getApplicationContext(),ViewGuest.class);
                startActivity(i);
            }
        });*/

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
        IGuest guest = new Guest_Impl(this);
        GuestLayoutClass guestlayout = glayout;

        if(v.getId() == R.id.addguestbutton){
            Log.d("BUTTON","Save Button Pressed!");
            guestlayout.loadValuesFromLayout();
            guestlayout.guest_.addGuest();

            //  logInputs();
            //default go back to list view

            Intent i = new Intent(getApplicationContext(),ViewGuest.class);
            startActivity(i);
        }

    }
    public void openCompanion(View v){

            Intent i = new Intent(getApplicationContext(),AddCompanions.class);
            startActivity(i);
        }


    public void BackToViewGuest(View v){

        Intent i = new Intent(getApplicationContext(),ViewGuest.class);
        startActivity(i);
    }

}

