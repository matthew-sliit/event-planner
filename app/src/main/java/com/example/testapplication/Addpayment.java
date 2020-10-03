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
import com.example.testapplication.db.budget.Ibudget;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.example.testapplication.db.vendor.Vendor_pay_Impl;

public class Addpayment extends AppCompatActivity {

    RadioGroup rdpStatus;
    RadioButton rdPaid,rdPending;
    private String has_title="Add Payment";
    private int eid=0,vid=0;

    private class VendorLayoutClass{
        private Context c;
        private int vid=0;
        String status;

         public VendorLayoutClass(Context c,int vid){
            vendor=new Vendor_pay_Impl(c);
            this.c=c;
            this.vid=vid;

         }
        public Vendor_pay_Impl vendor;
        public void InitVariables(){
            rdpStatus=(RadioGroup)findViewById(R.id.rdpStatus);
            rdPaid=(RadioButton)findViewById(R.id.rdpaid);
            rdPending=(RadioButton)findViewById(R.id.rdPending);

        }
        public void loadValuesFromLayout(){
            this.vendor.name = ((EditText)findViewById(R.id.name1)).getText().toString();
            // this.vendor.category = ((Spinner)findViewById(R.id.category_v)).getSelectedItem().toString();
            this.vendor.amount=  ((EditText)findViewById(R.id.amount1)).getText().toString();
            if(this.status !=null){
                this.vendor.status=this.status;
            }
            this.vendor.note=  ((EditText)findViewById(R.id.note)).getText().toString();
            //  vendor.addVendor();
        }
        public void setValuesToLayout(int id){
            this.vendor=vendor.getVendorPaybyid(id,vid,eid);
            ICategory category=new Category(c);
            ((EditText)findViewById(R.id.name1)).setText(this.vendor.name, TextView.BufferType.EDITABLE);
            // ((EditText)findViewById(R.id.category_v)).setText(this.vendor.category, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.amount_vendor)).setText(this.vendor.amount, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.note)).setText(this.vendor.note, TextView.BufferType.EDITABLE);

           /* String[] defaultOrder = (String[]) category.getAllCategory().toArray(new String[0]);
            for(int i=0;i<defaultOrder.length;i++){
                if(defaultOrder[i].equals(category)){ //from bundle
                    //Log.d("AddBudgetAct>>","setting default category as " + category);
                    String temp = defaultOrder[i];
                    defaultOrder[i] = defaultOrder[0];
                    defaultOrder[0] = temp;
                    break; //for loop
                }
            }
            final String[] defaultCat = defaultOrder;
            Spinner cat = findViewById(R.id.category_v); //spinner
            ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(c,android.R.layout.simple_spinner_item,defaultCat);
            catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cat.setAdapter(catAdapter);
            if(category!=null){
                //set category
            }*/

        }
        public void setRadioEvents(){
            rdpStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // find which radio button is selected
                    if (checkedId == R.id.rdpaid) {
                        vendorlayout.status = "paid";
                    } else if (checkedId == R.id.rdPending) {
                        vendorlayout.status = "pending";
                    }
                }
            });
        }
    }
    VendorLayoutClass vendorlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpayment);
        final Context context = this;
        Bundle b = getIntent().getExtras();
        vendorlayout=new VendorLayoutClass(this,vid);
        vendorlayout.InitVariables();
        vendorlayout.setRadioEvents();
        if(b!=null) {
            has_title = b.getString("title", "Add Budget");
            vid = b.getInt("id", 0);
            eid = b.getInt(ConstantBundleKeys.EVENT_ID, 0);
            // Log.d("Addpayactivity>>","id -> " + id+" vid ->"+vid);
            vendorlayout = new VendorLayoutClass(this, vid);
        }
        Log.d("VendorAddPay>>","Receiving eid -> " + eid);
            Toolbar toolbar = findViewById(R.id.toolbar4); //set toolbar
            setSupportActionBar(toolbar);
            //set toolbar title
            toolbar.setTitle("Add Payment");//either default or from bundle
            //for back button
            toolbar.setNavigationIcon(R.drawable.back_btn);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //default previous intent
                    //Intent i = new Intent(getApplicationContext(),Vendorview.class);
                    //startActivity(i);
                    finish();
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

    public void handleClick(View v){
        if(v.getId() == R.id.imageButton_add_v){
            Log.d("BUTTON","AddPayment Button Pressed!");
        }
        if(v.getId() == R.id.save_v){
            if (TextUtils.isEmpty(((EditText)findViewById(R.id.name1)).getText().toString())){
                Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();}
            else if (TextUtils.isEmpty(((EditText)findViewById(R.id.amount1)).getText().toString())){
                Toast.makeText(getApplicationContext(), "Please enter payment amount", Toast.LENGTH_SHORT).show();}
            else {
                Log.d("BUTTON", "Save Button Pressed!");
                vendorlayout.loadValuesFromLayout();
                vendorlayout.vendor.addPayment(eid, vid);

                //  logInputs();
                //default go back to list view
                Intent i = new Intent(getApplicationContext(), Vendorview.class);
                Bundle b = new Bundle();
                b.putInt("id", vendorlayout.vid);//int pk
                b.putInt(ConstantBundleKeys.EVENT_ID, eid);
                i.putExtras(b);
                startActivity(i);
            }
        }
        if(v.getId()==R.id.category_add_v){
            Bundle b = new Bundle();
            Intent i = new Intent(getApplicationContext(),EditCategoryActivity.class);
            b.putString("cat_edit","Add name"); //placeholder
            b.putString("is_in_setting","false");
            b.putString("pre_activity","Add Vendor");
            b.putInt("id",vid);
          //  Log.d("BudgetAct>>","putting id as " + id);
            b.putString("set_to_cat","false");
            b.putString("title","Add Category");
            i.putExtras(b);
            startActivity(i);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

