package com.example.testapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.adapter.CompanionAdapter;
import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.guest.Guest_Impl;

public class EditGuest extends AppCompatActivity {
    RadioGroup radioGender, radioAge, radioInvitaion;
    RadioButton radioMale, radioFemale, radioAdult, radioChild, radioSent, radioNotsent;


    private String has_title = "Edit Guest";//,guestname="null",gender,age,invitation,phone,email,address;
    private int id = 0,eid=0;

    private class GuestLayoutClass {

        String guestname, gender, age, invitation, phone, email, address;
        public int id = 0,eid_g=0;
        private Context c;

        public GuestLayoutClass(Context c,int eid) {
            guest_ = new Guest_Impl(c,eid);
            this.c = c;
            this.eid_g=eid;
        }

        public Guest_Impl guest_;

        public void InitVariables() {
            radioGender = (RadioGroup) findViewById(R.id.radioGender);
            radioAge = (RadioGroup) findViewById(R.id.radioAge);
            radioInvitaion = (RadioGroup) findViewById(R.id.radioInvitation);
            radioAdult = (RadioButton) findViewById(R.id.radioAdult);
            radioChild = (RadioButton) findViewById(R.id.radioChild);
            radioFemale = (RadioButton) findViewById(R.id.radioFemale);
            radioMale = (RadioButton) findViewById(R.id.radioMale);
            radioNotsent = (RadioButton) findViewById(R.id.radioNotsent);
            radioSent = (RadioButton) findViewById(R.id.radioSent);

        }

        public void loadValuesFromLayout() {

            this.guest_.guestname = ((EditText) findViewById(R.id.etGuestName)).getText().toString();
            if (this.gender != null) {
                this.guest_.gender = this.gender;
            }
            if (this.age != null) {
                this.guest_.age = this.age;
            }
            if (this.invitation != null) {
                this.guest_.invitation = this.invitation;
            }
            this.guest_.phone = ((EditText) findViewById(R.id.etPhone)).getText().toString();
            this.guest_.email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
            this.guest_.address = ((EditText) findViewById(R.id.etAddress)).getText().toString();
            Log.d("Add Guest>>", "invitation " + invitation);
            Log.d("Add Guest>>", "age " + age);
            Log.d("Add Guest>>", "gender " + gender);

        }

        public void setValuesToLayout(int id) {
            this.InitVariables();

            this.guest_ = guest_.getGuestById(id);
            ((EditText) findViewById(R.id.etGuestName)).setText(this.guest_.guestname, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.etPhone)).setText(this.guest_.phone, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.etEmail)).setText(this.guest_.email, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.etAddress)).setText(this.guest_.address, TextView.BufferType.EDITABLE);
            if (guest_.gender != null) {
                if (guest_.gender.equalsIgnoreCase("male")) {
                    gender = "male";
                    radioMale.setChecked(true);
                } else if (guest_.gender.equalsIgnoreCase("female")) {
                    gender = "female";
                    radioFemale.setChecked(true);
                }
            }
            if (guest_.age != null) {
                if (guest_.age.equalsIgnoreCase("adult")) {
                    age = "adult";
                    radioAdult.setChecked(true);
                } else if (guest_.age.equalsIgnoreCase("child")) {
                    age = "child";
                    radioChild.setChecked(true);
                }
            }
            if (guest_.invitation != null) {
                if (guest_.invitation.equalsIgnoreCase("invitation sent")) {
                    invitation = "invitation sent";
                    radioSent.setChecked(true);
                } else if (guest_.invitation.equalsIgnoreCase("invitation not sent")) {
                    invitation = "invitation not sent";
                    radioNotsent.setChecked(true);
                }
            }

        }

        public void setRadioEvents() {
            radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // find which radio button is selected
                    if (checkedId == R.id.radioMale) {
                        gender = "male";
                    } else if (checkedId == R.id.radioFemale) {
                        gender = "female";
                    }
                    Log.d("Add Guest>>", "gender= " + gender);
                }

            });

            radioAge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // find which radio button is selected
                    if (checkedId == R.id.radioAdult) {
                        age = "adult";
                    } else if (checkedId == R.id.radioChild) {
                        age = "child";
                    }
                }

            });

            radioInvitaion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // find which radio button is selected
                    if (checkedId == R.id.radioSent) {
                        invitation = "invitation sent";
                    } else if (checkedId == R.id.radioNotsent) {
                        invitation = "invitation not sent";
                    }
                }

            });
        }
    }

    Button closeButton;
    AlertDialog.Builder builder;
    GuestLayoutClass glayout;
    private  boolean continueToListFromAdd = false;
    /*
    ====================== OnCreate =========================
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_guest);

        Bundle b = getIntent().getExtras();
        if(b!=null) {
            eid = b.getInt(ConstantBundleKeys.EVENT_ID, 0);
            id = b.getInt("id",0);
            continueToListFromAdd = b.getBoolean("AddToEdit",false);
        }
        Toolbar toolbar = findViewById(R.id.tb_editguest);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i = new Intent(getApplicationContext(), ViewGuest.class);
                //startActivity(i);
                if(continueToListFromAdd){
                    Intent i = new Intent(getApplicationContext(), ViewGuest.class);
                    Bundle b = new Bundle();
                    b.putInt(ConstantBundleKeys.EVENT_ID,eid);
                    //b.putInt(ConstantBundleKeys.ID,id);
                    i.putExtras(b);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//check?
                    startActivity(i);
                }else {
                    finish();
                }
            }
        });

        glayout = new GuestLayoutClass(this,eid);
        glayout.InitVariables();
        glayout.setRadioEvents();
        if (b != null) {
            glayout.setValuesToLayout(id);
        }
        closeButton = (Button) findViewById(R.id.deleteguestbutton);
        builder = new AlertDialog.Builder(this);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure want to delete ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                glayout.guest_.removeGuest(glayout.guest_.id);
                                Intent i = new Intent(getApplicationContext(), ViewGuest.class);
                                Bundle b = new Bundle();
                                b.putInt(ConstantBundleKeys.EVENT_ID,eid);
                                i.putExtras(b);
                                Toast.makeText(getApplicationContext(), "you deleted",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(i);
                            }


                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Guest");
                alert.show();
            }
        });

        ((Button) findViewById(R.id.updateguestbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(((EditText)findViewById(R.id.etGuestName)).getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();}

                else {
                    glayout.loadValuesFromLayout();
                    glayout.guest_.updateGuest(glayout.guest_);
                    /*
                    glayout.loadValuesFromLayout();
                    glayout.guest_.updateGuest(glayout.guest_);
                    Intent i = new Intent(getApplicationContext(), ViewGuest.class);
                    Bundle b = new Bundle();
                    b.putInt(ConstantBundleKeys.EVENT_ID, eid);
                    i.putExtras(b);
                    startActivity(i);
                     */
                    finish();
                }
            }
        });
        CompanionAdapter adapter = new CompanionAdapter(this,id,eid); //Budget Adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_eg);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//item to item decoration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


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
    public void handleOnClick(View v){
        if(v.getId()==R.id.ibtn_addc){
            Intent i = new Intent(getApplicationContext(),AddCompanions.class);
            Bundle b = new Bundle();
            // b.putInt("vid");//int pk
            b.putInt("id",id);//int pk
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);//int pk
            i.putExtras(b);
            startActivity(i);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if(continueToListFromAdd){
                Intent i = new Intent(getApplicationContext(), ViewGuest.class);
                Bundle b = new Bundle();
                b.putInt(ConstantBundleKeys.EVENT_ID,eid);
                b.putInt(ConstantBundleKeys.ID,id);
                i.putExtras(b);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//check?
                startActivity(i);
            }else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    /*
    ============== On Restart ================
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        //refresh activity
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            eid = b.getInt(ConstantBundleKeys.EVENT_ID, 0);
            id = b.getInt("id",0);
            continueToListFromAdd = b.getBoolean("AddToEdit",false);
        }
        Intent i = getIntent();
        if(continueToListFromAdd){
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        if(b!=null) {
            i.putExtras(b);//pass
        }
        finish();
        startActivity(i);
    }
}