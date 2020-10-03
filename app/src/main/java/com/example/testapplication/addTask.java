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


import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.example.testapplication.db.task.ITask;
import com.example.testapplication.db.task.Task_Impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class addTask extends AppCompatActivity {

    EditText et_tname,et_tdesc,et_tdate;
    RadioGroup rdstatus;
    RadioButton rdPend,rdComplete;
    Spinner spinnerT;
    //String[] categoryType ={"Ceremony", "Decoration", "Reception", "Jewelry"};

    String spinnerItem = null;

    private String has_title="Add Task";//,guestname="null",String,age,invitation,phone,email,address;
    private int id = 0,eid = 0;

    private class TaskLayoutClass{
        ICategory category;
        String tname,status ,categoryTemp;
        public int id = 0, eid_t = 0;
        private Context c;
        public TaskLayoutClass(Context c, int eid_) {
             task_ =new Task_Impl(c,eid_);
            this.c=c;
            category = new Category(c);
            this.eid_t = eid_;
        }
        public Task_Impl task_;
        public void InitVariables(){
            rdstatus=(RadioGroup)findViewById(R.id.rdStatus);
            rdPend=(RadioButton)findViewById(R.id.rdPend);
            rdComplete=(RadioButton)findViewById(R.id.rdComplete);

        spinnerT = (Spinner)findViewById(R.id.spinnerT);
        String[] categoryType = (String[]) category.getAllCategory().toArray(new String[0]);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(c,android.R.layout.simple_spinner_item,categoryType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerT.setAdapter(adapter);

        }


        public void loadValuesFromLayout(){

            this.task_.tname = ((EditText)findViewById(R.id.et_tname)).getText().toString();
            if(this.status!=null){
                this.task_.status=this.status;}
            /*
            if(null != categoryTemp){
                int index = Arrays.asList(categoryType).indexOf(categoryTemp);
                spinnerT.setSelection(index, true);
                spinnerItem = categoryTemp;
            }
             */
            try {
                this.task_.category = spinnerT.getSelectedItem().toString();
            }catch (NullPointerException e){
                this.task_.category="Ceremony";
            }
            this.task_.description = ((EditText)findViewById(R.id.et_tdesc)).getText().toString();
            //this.task_.category = ((EditText)findViewById(R.id.ca)).getText().toString();
            this.task_.tdate = ((EditText)findViewById(R.id.et_tdate)).getText().toString();
        }

        public void setValuesToLayout(int id){
            this.task_=task_.getTaskById(eid_t,id);
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

        Bundle b = getIntent().getExtras();
        if(b!=null) {
            eid = b.getInt(ConstantBundleKeys.EVENT_ID, 0);
        }
        tlayout= new TaskLayoutClass(this,eid);
        final Context context = this;
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
                //Intent i = new Intent(getApplicationContext(),taskList.class);
                //startActivity(i);
                finish();
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
                ((EditText)findViewById(R.id.et_tdate)).setText(df.format(calender.getTime()));
            }
        };

        ((EditText)findViewById(R.id.et_tdate)).setOnClickListener(new View.OnClickListener() {
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
        ITask task = new Task_Impl(this,eid);
        TaskLayoutClass guestlayout = tlayout;

        if(v.getId() == R.id.tsave_btn){
            Log.d("BUTTON","Save Button Pressed!");
            guestlayout.loadValuesFromLayout();
            guestlayout.task_.addTask();
            Log.d("addTask>>","Task Added with eid=" + eid + " and id="+id);

            //  logInputs();
            //default go back to list view

           // Intent i = new Intent(getApplicationContext(),taskList.class);
           // startActivity(i);
            finish();
        }
        if(v.getId() == R.id.lg_add_task){
            Bundle b = new Bundle();
            b.putString(ConstantBundleKeys.SET_TO_CATEGORY,"false");
            b.putString(ConstantBundleKeys.TITLE,"Add Category");
            b.putString(ConstantBundleKeys.EDIT_CATEGORY_MODE,"Add name"); //placeholder
            b.putString(ConstantBundleKeys.IS_IN_SETTING,"false");
            b.putString(ConstantBundleKeys.PRE_ACTIVITY,has_title);
            Intent i = new Intent(getApplicationContext(),EditCategoryActivity.class);
            i.putExtras(b);
            startActivity(i);
        }

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //refresh activity
        finish();
        startActivity(getIntent());
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
