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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.example.testapplication.db.task.Task_Impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class EditTask extends AppCompatActivity {

    RadioGroup rdstatus;
    RadioButton rdPend, rdComplete;
    Spinner spinnerT;
    String[] categoryType = {"Ceremony", "Decoration", "Reception", "Jewelry"};

    String spinnerItem = null;

    private String has_title = "Edit Task";//,guestname="null",gender,age,invitation,phone,email,address;
    private int id = 0;

    private class TaskLayoutClass {

        String tname, status;
        ICategory category;
        public int id = 0, eid__ = 0;
        private Context c;
        public TaskLayoutClass(Context c, int eid_) {
            task_ = new Task_Impl(c, eid_);
            this.c = c;
            this.eid__ = eid_;
            category = new Category(c);
        }

        public Task_Impl task_;

        public void InitVariables() {
            rdstatus = (RadioGroup) findViewById(R.id.rdStatus);
            rdPend = (RadioButton) findViewById(R.id.rdPend);
            rdComplete = (RadioButton) findViewById(R.id.rdComplete);

            spinnerT = (Spinner) findViewById(R.id.spinnerT);
        }


        public void loadValuesFromLayout() {

            this.task_.tname = ((EditText) findViewById(R.id.et_tname)).getText().toString();
            if (this.status != null) {
                this.task_.status = this.status;
            }
            //?
            /*
            if (null != category) {
                int index = Arrays.asList(categoryType).indexOf(category);
                spinnerT.setSelection(index, true);
                spinnerItem = category;
            }*/
            try {
                this.task_.category = spinnerT.getSelectedItem().toString();
            }catch (NullPointerException e){
                this.task_.category="Ceremony";
            }
            this.task_.description = ((EditText) findViewById(R.id.et_tdesc)).getText().toString();
            //this.task_.category = ((EditText)findViewById(R.id.ca)).getText().toString();
            this.task_.tdate = ((EditText) findViewById(R.id.et_tdate)).getText().toString();
        }

        public void setValuesToLayout(int id) {
            this.InitVariables();
            this.task_ = task_.getTaskById(eid__,id);
            Log.d("EditTask>>","Bundle: id = " + id + " eid=" + eid__);
            Log.d("EditTask>>","Object: id = " + task_.id + " eid=" + task_.eid);
            ((EditText) findViewById(R.id.et_tname)).setText(this.task_.tname, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.et_tdesc)).setText(this.task_.description, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.et_tdate)).setText(this.task_.tdate, TextView.BufferType.EDITABLE);

            if (task_.status != null) {
                if (task_.status.equalsIgnoreCase("pending")) {
                    status = "pending";
                    rdPend.setChecked(true);
                } else if (task_.status.equalsIgnoreCase("completed")) {
                    status = "completed";
                    rdComplete.setChecked(true);
                }
            }
            String[] defaultOrder = (String[]) category.getAllCategory().toArray(new String[0]);
            for(int i=0;i<defaultOrder.length;i++){
                if(defaultOrder[i].equals(task_.category)){ //from bundle
                    //Log.d("AddBudgetAct>>","setting default category as " + category);
                    String temp = defaultOrder[i];
                    defaultOrder[i] = defaultOrder[0];
                    defaultOrder[0] = temp;
                    break; //for loop
                }
            }
            final String[] defaultCat = defaultOrder;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, defaultCat);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerT.setAdapter(adapter);
        }

        public void setRadioEvents() {
            rdstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // find which radio button is selected
                    if (checkedId == R.id.rdPend) {
                        status = "pending";
                    } else if (checkedId == R.id.rdComplete) {
                        status = "completed";
                    }
                    Log.d("Add Task>>", "status= " + status);
                }

            });
        }
    }
        Button closeButton;
        AlertDialog.Builder builder;
        TaskLayoutClass tlayout;

        private int eid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Bundle b = getIntent().getExtras();
        if(b!=null) {
            id=b.getInt("id",0);
            eid = b.getInt(ConstantBundleKeys.EVENT_ID, 0);
        }

        Toolbar toolbar = findViewById(R.id.addtb);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tlayout=new TaskLayoutClass(this,eid);
        tlayout.InitVariables();
        tlayout.setRadioEvents();
        if(b!=null){
            tlayout.setValuesToLayout(id);
        }
        closeButton = (Button) findViewById(R.id.tdelete_btn);
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
                                tlayout.task_.removeTask(tlayout.task_.id);
                                Intent i = new Intent(getApplicationContext(),taskList.class);
                                Bundle b = new Bundle();
                                b.putInt(ConstantBundleKeys.EVENT_ID,eid);
                                i.putExtras(b);
                                Toast.makeText(getApplicationContext(),"Task Deleted",
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
                alert.setTitle("Delete Task");
                alert.show();
            }
        });

        ((Button)findViewById(R.id.tupdate_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tlayout.loadValuesFromLayout();
                tlayout.task_.updateTask(tlayout.task_);
                /*
                Intent i = new Intent(getApplicationContext(),taskList.class);
                startActivity(i);
                 */
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
    @Override
    protected void onRestart() {
        super.onRestart();
        //refresh activity
        finish();
        startActivity(getIntent());
    }
}
