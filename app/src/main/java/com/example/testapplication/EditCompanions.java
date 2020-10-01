package com.example.testapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.db.guest.Companion_Impl;
import com.example.testapplication.db.guest.Guest_Impl;

public class EditCompanions extends AppCompatActivity {
    RadioGroup radioGender,radioAge;
    RadioButton radioMale,radioFemale,radioAdult,radioChild;
    private String has_title="Add Companions";// name="null",desc="null",amt="0.00",paid="0.00",balance="0.00",category=null;
    private int id = 0,eid=0,gid=0;


    private class GuestLayoutClass {
        private Context c;
        private int gid = 0;
        String gender,age;
        public GuestLayoutClass(Context c, int gid) {
            guest = new Companion_Impl(c);
            this.c = c;
            this.gid = gid;

        }
        public Companion_Impl guest;
        public void InitVariables() {
            radioGender=(RadioGroup)findViewById(R.id.radioGender);
            radioAge=(RadioGroup)findViewById(R.id.radioAge);
            radioAdult=(RadioButton)findViewById(R.id.radioAdult);
            radioChild=(RadioButton)findViewById(R.id.radioChild);
            radioFemale=(RadioButton)findViewById(R.id.radioFemale);
            radioMale=(RadioButton)findViewById(R.id.radioMale);
        }


        public void loadValuesFromLayout() {
            this.guest.cname = ((EditText)findViewById(R.id.etcName)).getText().toString();

            this.guest.note=  ((EditText)findViewById(R.id.etcomNote)).getText().toString();
            if(this.gender!=null){
                this.guest.gender=this.gender;}
            if(this.age!=null){
                this.guest.age=this.age;}

        }

        public void setValuesToLayout(int id) {
            this.guest=guest.getComById(id,eid,gid);
            ((EditText)findViewById(R.id.etcName)).setText(this.guest.cname, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.etcomNote)).setText(this.guest.note, TextView.BufferType.EDITABLE);
            Log.d("EditCom>>","id= " +id+" gid= " +gid+" eid= " +eid+" name= " +guest.cname);

            if (guest.gender != null) {
                if (guest.gender.equalsIgnoreCase("male")) {

                    radioMale.setChecked(true);
                } else if (guest.gender.equalsIgnoreCase("female")) {
                    radioFemale.setChecked(true);
                }
            }

            if (guest.age != null) {
                if (guest.age.equalsIgnoreCase("adult")) {

                    radioAdult.setChecked(true);
                } else if (guest.age.equalsIgnoreCase("child")) {
                    radioChild.setChecked(true);
                }
            }
        }

        public void setRadioEvents() {
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
    Button delBtn;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_companions);
        delBtn=(Button) findViewById(R.id.delCom);
        final Context context = this;
        Bundle b = getIntent().getExtras();
        if(b!=null) {

            id = b.getInt("id");
            gid = b.getInt("gid");
            eid=b.getInt("eid",0);
            Log.d("Editcom>>", "id -> " + id + " gid ->" + gid);

        }
        glayout = new GuestLayoutClass(this, gid);
        glayout.InitVariables();
        glayout.setValuesToLayout(id);
        glayout.setRadioEvents();

        builder = new AlertDialog.Builder(this);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to delete and exit ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int did) {
                                glayout.guest.removeCom(id,eid,gid);
                                Intent i = new Intent(getApplicationContext(), EditGuest.class);
                                Bundle b = new Bundle();
                                b.putInt("id",gid);//int pk
                                b.putInt("eid",eid);//int pk
                                b.putString("title","Edit Guest");

                                i.putExtras(b);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });

        ((Button)findViewById(R.id.updatecombtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glayout.loadValuesFromLayout();
                //glayout.guest.id=id;
                //glayout.guest.gid=gid;
                //glayout.guest.eid=eid;
              glayout.guest.updateCom(glayout.guest);
                Intent i = new Intent(view.getContext(), EditGuest.class);
                Bundle b = new Bundle();
                b.putInt("id",gid);//int pk
                b.putInt("eid",eid);//int pk
                b.putString("title","Edit Guest");

                i.putExtras(b);
                view.getContext().startActivity(i);
            }
        });
    }

}