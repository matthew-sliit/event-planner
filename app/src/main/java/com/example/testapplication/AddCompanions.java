package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.testapplication.db.guest.Companion_Impl;
import com.example.testapplication.db.guest.Guest_Impl;
import com.example.testapplication.db.guest.ICompanion;
import com.example.testapplication.db.guest.IGuest;

public class AddCompanions extends AppCompatActivity {
    RadioGroup radioGender,radioAge;
    RadioButton radioMale,radioFemale,radioAdult,radioChild;
    private int id = 0,eid=0,gid=0;

    private class GuestLayoutClass{
        private Context c;
        private int gid=0;
        String gender,age;

        public GuestLayoutClass(Context c,int gid){
            guest=new Companion_Impl(c);
            this.c=c;
            this.gid=gid;

        }
        public Companion_Impl guest;
        public void InitVariables(){
            radioGender=(RadioGroup)findViewById(R.id.radioGender);
            radioAge=(RadioGroup)findViewById(R.id.radioAge);
            radioAdult=(RadioButton)findViewById(R.id.radioAdult);
            radioChild=(RadioButton)findViewById(R.id.radioChild);
            radioFemale=(RadioButton)findViewById(R.id.radioFemale);
            radioMale=(RadioButton)findViewById(R.id.radioMale);

        }
        public void loadValuesFromLayout(){
            this.guest.cname = ((EditText)findViewById(R.id.etcName)).getText().toString();

            this.guest.note=  ((EditText)findViewById(R.id.etcomNote)).getText().toString();
            if(this.gender!=null){
                this.guest.gender=this.gender;}
            if(this.age!=null){
                this.guest.age=this.age;}

        }
        public void setValuesToLayout(int id){
            this.guest=guest.getComById(id,eid,gid);
            ((EditText)findViewById(R.id.etcName)).setText(this.guest.cname, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.etcomNote)).setText(this.guest.note, TextView.BufferType.EDITABLE);


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
        }
    }
    GuestLayoutClass glayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_companions);
        final Context context = this;
        Bundle b = getIntent().getExtras();
        glayout = new GuestLayoutClass(this, gid);
        glayout.InitVariables();
        glayout.setRadioEvents();

        if (b != null) {

            id = b.getInt("id");
            gid = b.getInt("gid");
            Log.d("Addpayactivity>>", "id -> " + id + " gid ->" + gid);
            glayout = new GuestLayoutClass(this, gid);

        }
    }


    public void handleClick(View v){

        if(v.getId() == R.id.addcombtn){
            Log.d("BUTTON","Save Button Pressed!");
            glayout.loadValuesFromLayout();
            glayout.guest.addCom(eid,gid);

            //  logInputs();
            //default go back to list view
            Intent i = new Intent(getApplicationContext(),EditGuest.class);
            Bundle b = new Bundle();
            b.putInt("id",glayout.gid);//int pk
            i.putExtras(b);
            startActivity(i);
        }

    }

    public void BackToEditGuest(View v){

        Intent i = new Intent(getApplicationContext(),EditGuest.class);
        startActivity(i);
    }
    }