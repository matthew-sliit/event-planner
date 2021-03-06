package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.constants.ConstantBundleKeys;
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
        private int gid=0,eid_c=0;
        String gender,age;

        public GuestLayoutClass(Context c,int gid,int eid){
            guest=new Companion_Impl(c);
            this.c=c;
            this.gid=gid;
            this.eid_c=eid;
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
    /*
    ==================== OnCreate ==========================
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_companions);
        final Context context = this;
        Bundle b = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.tbAddGuest); //set toolbar
        setSupportActionBar(toolbar);
        //set toolbar title
        toolbar.setTitle("Add Companion");//either default or from bundle
        //for back button
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        glayout = new GuestLayoutClass(this, gid,eid);
        glayout.InitVariables();
        glayout.setRadioEvents();

        if (b != null) {
            eid=b.getInt(ConstantBundleKeys.EVENT_ID,0);
            gid = b.getInt("id",0);
            //Log.d("Addpayactivity>>", "id -> " + id + " gid ->" + gid);
            glayout = new GuestLayoutClass(this, gid,eid);
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

    public void handleClick(View v){

        if(v.getId() == R.id.addcombtn){
            if (TextUtils.isEmpty(((EditText) findViewById(R.id.etcName)).getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("BUTTON", "Save Button Pressed!");
                glayout.loadValuesFromLayout();
                glayout.guest.addCom(eid, gid);

                //  logInputs();
                //default go back to list view
                Intent i = new Intent(getApplicationContext(), EditGuest.class);
                Bundle b = new Bundle();
                b.putInt("id", gid);//int pk
                b.putInt(ConstantBundleKeys.EVENT_ID, eid);//int pk
                b.putBoolean("AddToEdit",true);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        }

    }

    public void BackToEditGuest(View v){

        Intent i = new Intent(getApplicationContext(),EditGuest.class);
        Bundle b = new Bundle();
        b.putInt("id",glayout.gid);//int pk
        b.putInt(ConstantBundleKeys.EVENT_ID,eid);//int pk
        i.putExtras(b);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}