package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.budget.Budget_payments;

import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.zip.DataFormatException;

public class BudgetPaymentsActivity extends AppCompatActivity {
    private String has_title="Add Payment",pre_title = "Add Budget";
    private boolean editor = false;
    private int event_id = 0, budget_id = 0, pid = 0;

    private class LayoutHandler{
        private Context c;
        public LayoutHandler(Context c){
            this.c = c;
            payment_model = new Budget_payments(c);
            payment_model.event_id = event_id;
            initVariables(); //uses paymodel instance
        }

        public Budget_payments payment_model;
        public EditText nameInput, amtInput, dateInput;
        public RadioGroup radioGroup;
        public RadioButton radioPaid, radioPending;
        public SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        private void initVariables(){
            //init public var
            nameInput = ((EditText)findViewById(R.id.bp_editText_name));
            amtInput = ((EditText)findViewById(R.id.bp_editText_amt));
            dateInput = ((EditText)findViewById(R.id.bp_editText_date));
            radioGroup = ((RadioGroup) findViewById(R.id.bp_rg_ps));
            radioPaid = ((RadioButton)findViewById(R.id.bp_radio_paid));
            radioPending = ((RadioButton)findViewById(R.id.bp_radio_pending));
            //prevent keyboard input
            dateInput.setKeyListener(null);
            //set default date to now
            Date currentTime = Calendar.getInstance().getTime();
            dateInput.setText(df.format(currentTime));
            //model init
            payment_model.status = "Not Paid";
            //set default mode for radio buttons
            radioPaid.setChecked(false);
            radioPending.setChecked(true);
            //set onChangeListener for radio group
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(i == R.id.bp_radio_paid){
                        payment_model.status = "Paid";
                    }else{
                        payment_model.status = "Not Paid";
                    }
                }
            });
        }
        public String date2String(Date date){
            String formattedDate = df.format(date);
            return  formattedDate;
        }
        public Date string2Date(String strDate){
            try{
                return df.parse(strDate);
            }catch (ParseException e){
                return null;
            }
        }
        public void setValuesToLayout(int eid, int bid, int pid){
            Budget_payments bp = payment_model.getPaymentById(eid,bid,pid);
            nameInput.setText(bp.name);
            amtInput.setText(String.valueOf(bp.amt));
            dateInput.setText(df.format(bp.rdate));
            if(bp.status.equalsIgnoreCase("paid")){
                radioPaid.setChecked(true);
                radioPending.setChecked(false);
            }
        }
        public void readValuesFromLayout(){
            payment_model.name = nameInput.getText().toString();
            String date = dateInput.getText().toString();
            payment_model.rdate = string2Date(date);
            String amt = amtInput.getText().toString();
            try {
                payment_model.amt = Double.parseDouble(amt);
            }catch (NumberFormatException e){
                payment_model.amt = 0.00;
            }
        }
    }
    //private boolean bid_safeAdd = false;
    LayoutHandler paylayout;
    /*
    ========================= OnCreate ===============================
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_payments);

        final Bundle b = getIntent().getExtras();
        if(b!=null) {
            //passed from BudgetPaymentViewHolder when editing else default values
            has_title = b.getString(ConstantBundleKeys.TITLE, "Add Payment");
            pre_title = b.getString(ConstantBundleKeys.PRE_TITLE, "Add Budget");
            event_id = b.getInt(ConstantBundleKeys.EVENT_ID, 0);
            budget_id = b.getInt(ConstantBundleKeys.BUDGET_ID, 0);
            pid = b.getInt(ConstantBundleKeys.BUDGET_PAY_ID, 0);
            //true when passed from add budget
            //bid_safeAdd = b.getBoolean("bid_safeAdd",false);
        }
        paylayout = new LayoutHandler(this);
        if(b!=null){
            if(has_title.equalsIgnoreCase("add payment")){
                editor = false;//means to Edit Payment
            }else{
                editor = true;
                paylayout.setValuesToLayout(event_id,budget_id,pid);
            }
            Log.d("BudPayAct>>","has_title -> " + has_title);
            Log.d("BudPayAct>>","pre_title -> " + pre_title);
        }
        if(editor) {
            Log.d("BudgetPayment>>", "Editor -> eid:" + event_id + ", bid:{" + budget_id + "}, pid{" + pid + "}");
        }else{
            Log.d("BudgetPayment>>", "Adder -> eid:" + event_id + ", bid:{" + budget_id + "}");
        }
        //toolbar
        Toolbar toolbar = findViewById(R.id.abb_menu); //set toolbar
        setSupportActionBar(toolbar);
        //set toolbar title
        toolbar.setTitle(has_title);//either default or from bundle
        //for back button
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //default previous intent
                Intent i = new Intent(getApplicationContext(),AddEditBudgetActivity.class);
                Bundle bp = new Bundle();
                bp.putInt(ConstantBundleKeys.EVENT_ID,event_id);
                bp.putInt(ConstantBundleKeys.ID,budget_id);
                bp.putString(ConstantBundleKeys.TITLE,pre_title);
                i.putExtras(bp);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//clear stack
                startActivity(i);
                finish();
            }
        });
        ((ImageButton)findViewById(R.id.bp_btn_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paylayout.payment_model.removePayment(event_id,budget_id,pid);
                Intent i = new Intent(getApplicationContext(), AddEditBudgetActivity.class);
                Bundle bp = new Bundle();
                bp.putInt(ConstantBundleKeys.EVENT_ID,event_id);
                bp.putInt(ConstantBundleKeys.ID,budget_id);
                bp.putString(ConstantBundleKeys.TITLE,pre_title);
                i.putExtras(bp);
                startActivity(i);
            }
        });
        ((ImageButton)findViewById(R.id.bp_btn_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paylayout.readValuesFromLayout();
                paylayout.payment_model.addPayment(event_id,budget_id);//added
                Intent i = new Intent(getApplicationContext(), AddEditBudgetActivity.class);
                Bundle bp = new Bundle();
                bp.putInt(ConstantBundleKeys.EVENT_ID,event_id);
                bp.putInt(ConstantBundleKeys.ID,budget_id);
                bp.putString(ConstantBundleKeys.TITLE,pre_title);
                i.putExtras(bp);
                startActivity(i);
            }
        });
        //for date
        final Calendar calender = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calender.set(Calendar.YEAR, year);
                calender.set(Calendar.MONTH, monthOfYear);
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                paylayout.dateInput.setText(paylayout.df.format(calender.getTime()));
            }

        };

        final Context c = this;
        paylayout.dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(c, date, calender
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
            Log.d("ADD_BUDGET>>","Navigating to AppSettingsActivity!");
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}