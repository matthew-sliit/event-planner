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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.db.budget.Budget_Impl;
import com.example.testapplication.db.budget.Ibudget;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.example.testapplication.db.vendor.Vendor_pay_Impl;

public class Addpayment extends AppCompatActivity {

    RadioGroup rdpStatus;
    RadioButton rdPaid,rdPending;
    private String has_title="Add Payment";// name="null",desc="null",amt="0.00",paid="0.00",balance="0.00",category=null;
    private int eid=0,vid=0;
    //private int id = 0;
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
  //new comment
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
        if(b!=null){
            has_title=b.getString("title","Add Budget");
            /*name=b.getString("name","Enter name");
            desc=b.getString("desc","Enter description");
            amt=b.getString("amount","0.00");
            category = b.getString("cat",null);
            balance=b.getString("balance","0.00");*/
           // id = b.getInt("id");
            vid = b.getInt("vid");
           // Log.d("Addpayactivity>>","id -> " + id+" vid ->"+vid);
            vendorlayout= new VendorLayoutClass(this,vid);
           /* if(has_title.equalsIgnoreCase("edit budget")){
                Ibudget budget = new Budget_Impl(this);
                Budget_Impl budget_model = new Budget_Impl(this);
                budget_model=budget.getBudgetById(b.getInt("id"));
                name = budget_model.name;
                desc = budget_model.desc;
                amt = budget_model.amt;
                category = budget_model.cat;
            }*/

        /*}else{
            ((EditText)findViewById(R.id.toolbar)).setHint("Budget Name");
            //((EditText)findViewById(R.id.editTxt_bud_name)).setHint("Budget Name");

        }*/
            Toolbar toolbar = findViewById(R.id.toolbar4); //set toolbar
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
                b.putInt("eid", eid);
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

