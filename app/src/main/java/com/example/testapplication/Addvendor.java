package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.db.budget.Budget_Impl;
import com.example.testapplication.db.budget.Ibudget;
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


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] defaultCat={"Beauty","Decor"};
        Spinner cat=(Spinner)findViewById(R.id.category);
        ArrayAdapter<String> catAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,defaultCat);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catAdapter);
    }*/

    private class VendorLayoutClass{
        private Context c;
        public VendorLayoutClass(Context c){
            vendor=new Vendor_impl(c);
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
            if(category!=null){
                //set category
            }

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
            /*name=b.getString("name","Enter name");
            desc=b.getString("desc","Enter description");
            amt=b.getString("amount","0.00");
            category = b.getString("cat",null);
            balance=b.getString("balance","0.00");*/
            id = b.getInt("id");
            eid = b.getInt("eid",0);
            Log.d("AddBudgetAct>>", "id -> " + id);
           /* if(has_title.equalsIgnoreCase("edit budget")){
                Ibudget budget = new Budget_Impl(this);
                Budget_Impl budget_model = new Budget_Impl(this);
                budget_model=budget.getBudgetById(b.getInt("id"));
                name = budget_model.name;
                desc = budget_model.desc;
                amt = budget_model.amt;
                category = budget_model.cat;
            }*/
        }
        vlayout=new VendorLayoutClass(this);
        vlayout.setCategoryAdapter();
        /*}else{
            ((EditText)findViewById(R.id.toolbar)).setHint("Budget Name");
            //((EditText)findViewById(R.id.editTxt_bud_name)).setHint("Budget Name");

        }*/
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
                Intent i = new Intent(getApplicationContext(),Vendorview.class);
                startActivity(i);
            }
        });
        //added newly
            Ibudget budget = new Budget_Impl(this);//Budget Interface




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
        Ibudget budget = new Budget_Impl(this);
        VendorLayoutClass vendorlayout = new VendorLayoutClass(this);
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
                b.putInt("eid",eid);//int pk
                b.putInt("vid", newid);//int pk
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
                b.putInt("eid",eid);//int pk
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
  /*  public void logInputs(){
        String name = ((EditText) findViewById(R.id.editTxt_bud_name)).getText().toString();
        Log.d("NAME","name: " + name);
        String desc = ((EditText) findViewById(R.id.descInput)).getText().toString();
        Log.d("DESC","desc: " + desc);

        //((TextView) findViewById(R.id.SelectedBudgetBalance)).setVisibility(View.VISIBLE);
        //((TextView) findViewById(R.id.SelectedBudgetBalance)).setText("" + amount);
    }
    public void setBalance(){
        double amount = Double.parseDouble(((EditText) findViewById(R.id.amountInput)).getText().toString()); //input is always a number
        Log.d("AMOUNT","amount: " + amount);
        findViewById(R.id.SelectedBudgetBalance).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.SelectedBudgetBalance)).setText("" + amount);
    }*/
}

