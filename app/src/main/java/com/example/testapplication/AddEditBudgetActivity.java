package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testapplication.db.budget.Budget_Impl;
import com.example.testapplication.db.budget.Ibudget;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;

public class AddEditBudgetActivity extends AppCompatActivity{
    //MainActivity ma = new MainActivity();
    //Class cls = ma.getClass();
    private String has_title="Add Budget", name="null",desc="null",amt="0.00",paid="0.00",balance="0.00",category=null;
    private int id = 0;

    //for organizing layout data
    private class BudgetLayoutClass{
        public BudgetLayoutClass(){
        }
        public String getbName() {
            return bName;
        }

        public void setbName(String bName) {
            this.bName = bName;
        }

        public String getbCat() {
            return bCat;
        }

        public void setbCat(String bCat) {
            this.bCat = bCat;
        }

        public String getbDesc() {
            return bDesc;
        }

        public void setbDesc(String bDesc) {
            this.bDesc = bDesc;
        }
        public String bName = "";
        public String bCat = "";

        public Double getbAmt() {
            return bAmt;
        }

        public void setbAmt(Double bAmt) {
            this.bAmt = bAmt;
        }

        public Double bAmt = 00.00;
        public String bDesc = "";
        public void loadValuesFromLayout(){
            this.bName = ((EditText)findViewById(R.id.editTxt_bud_name)).getText().toString();
            String amt = ((EditText)findViewById(R.id.editTxt_bud_amt)).getText().toString();
            try {
                this.bAmt = Double.parseDouble(amt); //input is always a number
            }catch (NumberFormatException e){
                this.bAmt = 0.00;
            }
            this.bCat = ((Spinner)findViewById(R.id.ab_cat_spin)).getSelectedItem().toString();
            this.bDesc = ((EditText)findViewById(R.id.editTxt_bud_desc)).getText().toString();
        }
        public void setValuesToLayout(){

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_budget);
        final Context context = this;
        Bundle b = getIntent().getExtras();
        if(b!=null){
            has_title=b.getString("title","Add Budget");
            /*
            name=b.getString("name","Enter name");
            desc=b.getString("desc","Enter description");
            amt=b.getString("amount","0.00");
            category = b.getString("cat",null);
            balance=b.getString("balance","0.00");
             */
            id = b.getInt("id");
            Log.d("AddBudgetAct>>","id -> " + id);
            if(has_title.equalsIgnoreCase("edit budget")){
                Ibudget budget = new Budget_Impl(this);
                Budget_Impl budget_model = new Budget_Impl(this);
                budget_model=budget.getBudgetById(b.getInt("id"));
                name = budget_model.name;
                desc = budget_model.desc;
                amt = budget_model.amt;
                category = budget_model.cat;
            }

        }else{
            ((EditText)findViewById(R.id.editTxt_bud_name)).setHint("Budget Name");
            //((EditText)findViewById(R.id.editTxt_bud_name)).setHint("Budget Name");

        }
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
                Intent i = new Intent(getApplicationContext(),ListBudgetsActivity.class);
                startActivity(i);
            }
        });
        Log.d("AddEditBAct>>","name -> " + name);

        if(!name.equalsIgnoreCase("enter name") && !(name.equalsIgnoreCase("null")) && !TextUtils.isEmpty(name)) {
            Log.d("AddEditBAct>>","Setting values to EditText!");
            ((EditText) findViewById(R.id.editTxt_bud_name)).setText(name, TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.editTxt_bud_amt)).setText(amt, TextView.BufferType.EDITABLE);
            ((TextView) findViewById(R.id.textView_bud_balance)).setText(balance);
        }else{
            ((EditText) findViewById(R.id.editTxt_bud_name)).setText("");
            ((EditText) findViewById(R.id.editTxt_bud_name)).setHint(R.string.hint_name);
            ((EditText) findViewById(R.id.editTxt_bud_desc)).setText("");
            ((EditText) findViewById(R.id.editTxt_bud_desc)).setHint(R.string.hint_description);
            ((TextView) findViewById(R.id.textView_bud_balance)).setText("0.00");
        }

        findViewById(R.id.editTxt_bud_amt).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    setBalance();
                }
            }
        });

        ICategory categoryObj = new Category(this);
        //final String[] defaultCat = getResources().getStringArray(R.array.default_categories);
        //get category from db
        //Log.d("AddBudgetAct>>","expected category ->" + category);
        String[] defaultOrder = (String[]) categoryObj.getAllCategory().toArray(new String[0]);
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
        Spinner cat = findViewById(R.id.ab_cat_spin); //spinner
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,defaultCat);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catAdapter);
        if(category!=null){
           //set category
        }

        if(desc != null){
            ((EditText)findViewById(R.id.editTxt_bud_desc)).setText(desc);
        }
        if(has_title.equals("Add Budget")){
            ((ImageButton)findViewById(R.id.btn_adbud_del)).setVisibility(View.INVISIBLE);
        }else{
            //if title is Edit Budget
            ((ImageButton)findViewById(R.id.btn_adbud_del)).setVisibility(View.VISIBLE);
            ((ImageButton)findViewById(R.id.btn_adbud_del)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Ibudget budget = new Budget_Impl(context);
                    budget.removeBudget(id);

                    //go back to list view
                    Intent i = new Intent(getApplicationContext(),ListBudgetsActivity.class);
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
        BudgetLayoutClass budgetlayout = new BudgetLayoutClass();
        if(v.getId() == R.id.btn_add_budpay){
            Log.d("BUTTON","AddPayment Button Pressed!");
        }
        if(v.getId() == R.id.btn_adbud_save){
            Log.d("BUTTON","Save Button Pressed!");
            budgetlayout.loadValuesFromLayout();
            if(has_title.equals("Add Budget")) {
                //save new
                budget.addBudget(budgetlayout.bName, budgetlayout.bCat, budgetlayout.bAmt, budgetlayout.bDesc);
            }else{
                Log.d("Updating using ","id->"+this.id);
                //condition for when user clicks on a listed Budget
                //click for when save button is pressed
                //hence update existing record using values from user inputs
                Budget_Impl budget_model = new Budget_Impl(this);
                budget_model.id = this.id;//from bundle, for mapping record
                budget_model.name = budgetlayout.bName;
                budget_model.cat = budgetlayout.bCat;
                budget_model.amt = "" + budgetlayout.bAmt;
                budget_model.desc = budgetlayout.bDesc;
                //update
                budget.updateBudget(budget_model);
            }
            logInputs();
            //default go back to list view
            Intent i = new Intent(getApplicationContext(),ListBudgetsActivity.class);
            startActivity(i);
        }
        if(v.getId()==R.id.adbud_catp){
            Bundle b = new Bundle();
            Intent i = new Intent(getApplicationContext(),EditCategoryActivity.class);
            b.putString("cat_edit","Add name"); //placeholder
            b.putString("is_in_setting","false");
            b.putString("pre_activity",has_title);
            b.putInt("id",id);
            Log.d("BudgetAct>>","putting id as " + id);
            b.putString("set_to_cat","false");
            b.putString("title","Add Category");
            i.putExtras(b);
            startActivity(i);
        }
    }
    public void logInputs(){
        String name = ((EditText) findViewById(R.id.editTxt_bud_name)).getText().toString();
        Log.d("NAME","name: " + name);
        String desc = ((EditText) findViewById(R.id.editTxt_bud_desc)).getText().toString();
        Log.d("DESC","desc: " + desc);

        //((TextView) findViewById(R.id.SelectedBudgetBalance)).setVisibility(View.VISIBLE);
        //((TextView) findViewById(R.id.SelectedBudgetBalance)).setText("" + amount);
    }
    public void setBalance(){
        double amount = 0.00;
        try {
            String data = ((EditText) findViewById(R.id.editTxt_bud_amt)).getText().toString();
            amount = Double.parseDouble(data); //input is always a number
        }catch (NumberFormatException e){
            amount = 0.00;
        }
        Log.d("AMOUNT","amount: " + amount);
        findViewById(R.id.editTxt_bud_desc).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.textView_bud_balance)).setText("" + amount);
    }
}
