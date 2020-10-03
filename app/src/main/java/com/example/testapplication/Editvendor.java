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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.example.testapplication.db.vendor.Vendor_impl;

public class Editvendor extends AppCompatActivity {

   /*public void sendMessagee(View view)
    {
        setContentView(R.layout.activity_vendorpayview);
    }*/

    private String pre_intent = "settingCAT", is_in_setting = "true",edit = "none", is_in_cat = "true", has_title = "none";
    private int id = 0,eid=0;

    private class VendorLayoutClass {
        private Context c;


        public VendorLayoutClass(Context c, int eid) {
            vendor = new Vendor_impl(c,eid);
            this.c = c;

        }

        public Vendor_impl vendor;

        public void loadValuesFromLayout() {
            this.vendor.name = ((EditText) findViewById(R.id.name1)).getText().toString();
             this.vendor.category = ((Spinner)findViewById(R.id.category_v)).getSelectedItem().toString();
           // this.vendor.category = "c";
            this.vendor.amount = Double.parseDouble(((EditText) findViewById(R.id.amount_vendor)).getText().toString()); //input is always a number
            this.vendor.number = ((EditText) findViewById(R.id.phone_v)).getText().toString();
            this.vendor.address = ((EditText) findViewById(R.id.address_v)).getText().toString();
            this.vendor.email = ((EditText) findViewById(R.id.email_v)).getText().toString();
            //  vendor.addVendor();
        }

        public void setValuesToLayout(int id) {
            this.vendor = vendor.getVendorbyid(id);
            ICategory category = new Category(c);
            ((EditText) findViewById(R.id.name1)).setText(this.vendor.name, TextView.BufferType.EDITABLE);
            // ((EditText)findViewById(R.id.category_v)).setText(this.vendor.category, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.amount_vendor)).setText("" + this.vendor.amount, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.phone_v)).setText(this.vendor.number, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.address_v)).setText(this.vendor.address, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.email_v)).setText(this.vendor.email, TextView.BufferType.EDITABLE);

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
           // final String[] defaultCat = getResources().getStringArray(R.array.default_categories);
            Spinner cat = findViewById(R.id.category_v); //spinner
            ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(c,android.R.layout.simple_spinner_item,defaultOrder);
            catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cat.setAdapter(catAdapter);

        }
    }
    VendorLayoutClass vlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editvendor);

        // Log.d("EditCategoryAct", " has started! ");
        Bundle b = getIntent().getExtras();
        has_title="Edit Vendor";
        if (b != null) {
            id = b.getInt("id");
            eid=b.getInt(ConstantBundleKeys.EVENT_ID,0);
            //Log.d("RELOAD", "id = " + id);
        }
        vlayout=new VendorLayoutClass(this,eid);
        if(b!=null) {
            vlayout.setValuesToLayout(id);
        }
        final Bundle bl=new Bundle();
        bl.putInt(ConstantBundleKeys.EVENT_ID,eid);
        //Log.d("Edit Vendor", "id = " + id);
        Toolbar toolbar = findViewById(R.id.toolbar_add_v); //set toolbar
        setSupportActionBar(toolbar);
        //set toolbar title
        toolbar.setTitle("Edit Vendor");//either default or from bundle
        //for back button
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //default previous intent
              //  Intent i = new Intent(getApplicationContext(),Vendorview.class);
               // i.putExtras(bl);
                //startActivity(i);
                finish();
            }
        });
        ((Button)findViewById(R.id.button_view)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=new Bundle();
                b.putInt("id",id);
                b.putInt(ConstantBundleKeys.EVENT_ID,eid);
             //   setContentView(R.layout.activity_vendorpayview);
                Intent i=new Intent(getApplicationContext(),Vendorpaymentview.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
        ((Button)findViewById(R.id.delete_v)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vlayout.vendor.removeVendor(id);
                Intent i=new Intent(getApplicationContext(),Vendorview.class);
                Bundle b = new Bundle();
                b.putInt("eid",eid);//int pk
                i.putExtras(b);
                i.putExtras(bl);
                startActivity(i);
            }
        });

        ((Button)findViewById(R.id.update_v)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(((EditText) findViewById(R.id.name1)).getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter vendor name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(((EditText) findViewById(R.id.amount_vendor)).getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter required amount", Toast.LENGTH_SHORT).show();
                } else {
                    vlayout.loadValuesFromLayout();
                    vlayout.vendor.updateVendor(vlayout.vendor);

                    Intent i = new Intent(getApplicationContext(), Vendorview.class);
                    i.putExtras(bl);
                    startActivity(i);
                }
            }

        });

        /*VendorPaymentAdapter adapter = new VendorPaymentAdapter(this,id,eid); //Budget Adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_ev);
     //   recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//item to item decoration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
*/
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
            //Log.d("ADD_BUDGET>>","Navigating to AppSettingsActivity!");
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
        if(v.getId()==R.id.imageButton_add_v){
            Intent i = new Intent(getApplicationContext(),Addpayment.class);
            Bundle b = new Bundle();
           // b.putInt("vid");//int pk
            b.putInt("vid",vlayout.vendor.id);//int pk
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);
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