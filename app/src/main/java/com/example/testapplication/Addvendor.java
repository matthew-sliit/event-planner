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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.example.testapplication.db.vendor.Vendor_impl;

public class Addvendor extends AppCompatActivity {


    public void sendMessage(View view)
    {
        setContentView(R.layout.activity_addpayment);
    }

    private String has_title="Add Vendor";// name="null",desc="null",amt="0.00",paid="0.00",balance="0.00",category=null;
    private int id = 0,eid=0;

    private class VendorLayoutClass{
        private Context c;
        public VendorLayoutClass(Context c,int eid){
            vendor=new Vendor_impl(c,eid);
            this.c=c;
        }
        public Vendor_impl vendor;
        public void loadValuesFromLayout() {
            this.vendor.name = ((EditText) findViewById(R.id.name1)).getText().toString();
            try {
                this.vendor.category = ((Spinner) findViewById(R.id.category_v)).getSelectedItem().toString();
                //this.vendor.category="c";
            } catch (NullPointerException e) {
                ICategory category = new Category(c);
                String[] defaultOrder = (String[]) category.getAllCategory().toArray(new String[0]);

                this.vendor.category = defaultOrder[0];
            }
            this.vendor.amount = Double.parseDouble(((EditText) findViewById(R.id.amount_vendor)).getText().toString()); //input is always a number
            this.vendor.number = ((EditText) findViewById(R.id.phone_v)).getText().toString();
            this.vendor.address = ((EditText) findViewById(R.id.address_v)).getText().toString();
            this.vendor.email = ((EditText) findViewById(R.id.email_v)).getText().toString();
            //  vendor.addVendor();
        }

        public void setCategoryAdapter(){
            Spinner cat = findViewById(R.id.category_v); //spinner
            ICategory category=new Category(c);
            String[] defaultOrder = (String[]) category.getAllCategory().toArray(new String[0]);
            ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(c,android.R.layout.simple_spinner_item,defaultOrder);
            catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cat.setAdapter(catAdapter);
        }
        public void setValuesToLayout(int id){
            this.vendor=vendor.getVendorbyid(id);
            ICategory category=new Category(c);
            ((EditText)findViewById(R.id.name1)).setText(this.vendor.name, TextView.BufferType.EDITABLE);
           // ((EditText)findViewById(R.id.category_v)).setText(this.vendor.category, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.amount_vendor)).setText(""+this.vendor.amount, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.phone_v)).setText(this.vendor.number, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.address_v)).setText(this.vendor.address, TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.email_v)).setText(this.vendor.email, TextView.BufferType.EDITABLE);

            String[] defaultOrder = (String[]) category.getAllCategory().toArray(new String[0]);
            for(int i=0;i<defaultOrder.length;i++){
                if(defaultOrder[i].equals(this.vendor.category)){ //from bundle
                    //Log.d("AddBudgetAct>>","sthetting default category as " + category);
                    String temp = defaultOrder[i];
                    defaultOrder[i] = defaultOrder[0];
                    defaultOrder[0] = temp;
                    break; //for loop
                }
            }
            final String[] defaultCat = getResources().getStringArray(R.array.default_categories);
            Spinner cat = findViewById(R.id.category_v); //spinner
            ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(c,android.R.layout.simple_spinner_item,defaultCat);
            catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cat.setAdapter(catAdapter);
        }
    }
    VendorLayoutClass vlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            has_title = b.getString("title", "Add Budget");
            id = b.getInt("id");
            eid = b.getInt(ConstantBundleKeys.EVENT_ID,0);
            Log.d("AddBudgetAct>>", "id -> " + id);
        }
        vlayout=new VendorLayoutClass(this,eid);
        vlayout.setCategoryAdapter();

        Toolbar toolbar = findViewById(R.id.toolbar_add_v); //set toolbar
        setSupportActionBar(toolbar);
        //set toolbar title
        toolbar.setTitle("Add Vendor");//either default or from bundle
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
        VendorLayoutClass vendorlayout = new VendorLayoutClass(this,eid);
        if(v.getId() == R.id.imageButton_add_v){
            if (TextUtils.isEmpty(((EditText)findViewById(R.id.name1)).getText().toString())){
                Toast.makeText(getApplicationContext(), "Please enter vendor name", Toast.LENGTH_SHORT).show();}
            else if (TextUtils.isEmpty(((EditText)findViewById(R.id.amount_vendor)).getText().toString())){
                Toast.makeText(getApplicationContext(), "Please enter required amount", Toast.LENGTH_SHORT).show();}
            else {
                Log.d("BUTTON", "AddPayment Button Pressed!");
                vendorlayout.loadValuesFromLayout();
                int newid = vendorlayout.vendor.addVendorGetid();
                Bundle b = new Bundle();
                b.putInt(ConstantBundleKeys.EVENT_ID,eid);//int pk
                b.putInt("id", newid);//int pk
                Intent i = new Intent(getApplicationContext(), Addpayment.class);
                i.putExtras(b);
                startActivity(i);
            }
        }
        if(v.getId() == R.id.save_v){
            if (TextUtils.isEmpty(((EditText)findViewById(R.id.name1)).getText().toString())){
                Toast.makeText(getApplicationContext(), "Please enter vendor name", Toast.LENGTH_SHORT).show();}
            else if (TextUtils.isEmpty(((EditText)findViewById(R.id.amount_vendor)).getText().toString())){
                Toast.makeText(getApplicationContext(), "Please enter required amount", Toast.LENGTH_SHORT).show();}
            else {
            Log.d("BUTTON","Save Button Pressed!");
            vendorlayout.loadValuesFromLayout();
            vendorlayout.vendor.addVendor();

          //  logInputs();
            //default go back to list view
            Intent i = new Intent(getApplicationContext(),Vendorview.class);
                Bundle b = new Bundle();
                b.putInt(ConstantBundleKeys.EVENT_ID,eid);//int pk
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
            b.putInt("id",id);
            Log.d("BudgetAct>>","putting id as " + id);
            b.putString("set_to_cat","false");
            b.putString("title","Add Category");
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Bundle b = new Bundle();
            Intent i = new Intent(getApplicationContext(),homepg.class);
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);
            i.putExtras(b);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

