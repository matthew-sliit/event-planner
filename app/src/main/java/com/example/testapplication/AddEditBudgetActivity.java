package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.testapplication.adapter.BudgetPaymentAdapter;
import com.example.testapplication.db.budget.Budget_Impl_updated;
import com.example.testapplication.db.budget.Budget_payments;
import com.example.testapplication.db.budget.Ibudget;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddEditBudgetActivity extends AppCompatActivity{
    //MainActivity ma = new MainActivity();
    //Class cls = ma.getClass();
    private String has_title="Add Budget", name="null",desc="null",amt="0.00",paid="0.00",balance="0.00",category=null;
    private int id = 0, eid = 0;

    //for organizing layout data
    private class BudgetLayoutClass{
        EditText nameInput, amtInput,descInput;
        Spinner catInput;
        TextView showBalance;
        Context c;
        Budget_Impl_updated budget_model;
        Budget_payments budget_payments;

        public BudgetLayoutClass(Context c){
            this.c = c;
            budget_model = new Budget_Impl_updated(c,eid);
            budget_payments=  new Budget_payments(c);
            initVar();
        }
        public void initVar(){
            nameInput = ((EditText)findViewById(R.id.editTxt_bud_name));
            amtInput = ((EditText)findViewById(R.id.editTxt_bud_amt));
            catInput = ((Spinner)findViewById(R.id.ab_cat_spin));
            descInput = ((EditText)findViewById(R.id.editTxt_bud_desc));
            showBalance = ((TextView)findViewById(R.id.textView_bud_balance));
        }
        public void loadValuesFromLayout(){
            budget_model.name = nameInput.getText().toString();
            budget_model.amt = (amtInput.getText().toString());
            budget_model.cat = catInput.getSelectedItem().toString();
            budget_model.desc = descInput.getText().toString();
            showBalance.setText(amt);
        }
        public void setValuesToLayout(int eid, int bid){
            budget_model = budget_model.getBudgetById(eid,bid);
            nameInput.setText(budget_model.name);
            amtInput.setText(budget_model.amt);
            descInput.setText(budget_model.desc);
            blayout.setShowBalance();
        }
        public void initValuesToLayout(){
            nameInput.setText("");
            nameInput.setHint(R.string.hint_name);
            descInput.setText("");
            descInput.setHint(R.string.hint_description);
            showBalance.setText("0.00");
        }
        public void setShowBalance(){
            List<Budget_payments> budget_paymentsList = new ArrayList<>(budget_payments.getBudgetPaymentList(eid,id));
            double paidSum = 0;
            //not empty list
            if(!budget_paymentsList.isEmpty()) {
                //foreach item in list
                for (Budget_payments bp : budget_paymentsList) {
                    //only if paid
                    if(bp.status.equalsIgnoreCase("paid")) {
                        //add to paidSum
                        paidSum += bp.amt;
                    }
                }
            }
            try {
                if (!(budget_model == null)) {
                    //get budget
                    double budgetAmount = Double.parseDouble(budget_model.amt);
                    //balance = budget - paidSum
                    double balance = budgetAmount - paidSum;
                    DecimalFormat df = new DecimalFormat("####0.00");
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    showBalance.setText(df.format(balance));//show
                } else {
                    showBalance.setText("0.00");//show
                }
            }catch (NullPointerException e){
                showBalance.setText("0.00");//show
            }
        }
        public void setShowBalanceWhenAddingBudget(){
            showBalance.setText(amtInput.getText().toString());//show
        }
    }
    BudgetLayoutClass blayout;
    Ibudget budget;

    /**
     * ===================== OnCreate ==========================
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_budget);
        final Context context = this;
        blayout = new BudgetLayoutClass(this);
        //budget = new Budget_Impl_updated(this,eid);
        Ibudget ibudget = new Budget_Impl_updated(this,eid);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            has_title=b.getString("title","Add Budget");
            id = b.getInt("id",0);
            eid = b.getInt("eid",0);
            Log.d("AddBudgetAct>>","id -> " + id);
            if(has_title.equalsIgnoreCase("edit budget")){
                blayout.budget_model = ibudget.getBudgetById(eid,id);
                category = blayout.budget_model.cat;
                blayout.setValuesToLayout(eid,id);
            }
        }else{
            //blayout.budget_model = new Budget_Impl_updated(this,eid);
            blayout.initValuesToLayout();
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
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//clear stack
                startActivity(i);
                finish();
            }
        });
        Log.d("AddEditBAct>>","name -> " + name);

        findViewById(R.id.editTxt_bud_amt).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(has_title.equalsIgnoreCase("add budget")){
                        blayout.setShowBalanceWhenAddingBudget();
                    }else {
                        blayout.setShowBalance();
                    }
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

        if(has_title.equals("Add Budget")){
            //((ImageButton)findViewById(R.id.btn_adbud_del)).setVisibility(View.INVISIBLE);
        }else{
            //if title is Edit Budget
            ((ImageButton)findViewById(R.id.btn_adbud_del)).setVisibility(View.VISIBLE);
            ((ImageButton)findViewById(R.id.btn_adbud_del)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Ibudget budget = new Budget_Impl_updated(context,eid);
                    budget.removeBudget(id);

                    //go back to list view
                    Intent i = new Intent(getApplicationContext(),ListBudgetsActivity.class);
                    startActivity(i);
                }
            });
        }
        BudgetPaymentAdapter adapter = new BudgetPaymentAdapter(this,eid,id); //Budget Adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_bud);
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

    public boolean validated(){
        EditText name = ((EditText) findViewById(R.id.editTxt_bud_name));
        EditText amt = ((EditText)findViewById(R.id.editTxt_bud_amt));

        if(name.getText().toString().isEmpty()){
            //name empty
            Toast.makeText(getApplicationContext(),"Please enter name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(amt.getText().toString().isEmpty()){
            //amount empty
            Toast.makeText(getApplicationContext(),"Please enter amount",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * ===================== handleCLick ==============================
     * btn_add_budpay -> Add Budget Payment
     * @param v
     */
    public void handleClick(View v){
        Ibudget budget = new Budget_Impl_updated(this,eid);
        if(v.getId() == R.id.btn_add_budpay){
            //adding pay before creating budget will cause problems
            Log.d("BUTTON","AddPayment Button Pressed!");
            if(validated()) {
                Bundle b = new Bundle();
                if(has_title.equalsIgnoreCase("add budget")){
                    blayout.loadValuesFromLayout();
                    int newId = blayout.budget_model.addBudgetGetId();
                    b.putInt("bid",newId);
                    b.putBoolean("bid_safeAdd",true);
                }else{
                    b.putInt("bid", id);
                    b.putBoolean("bid_safeAdd",false);
                }
                Intent i = new Intent(getApplicationContext(), BudgetPaymentsActivity.class);
                b.putString("title", "Add Payment");
                b.putString("pre_title", "Edit Budget");
                i.putExtras(b);
                Log.d("BudPayAct>>","has_title -> " + "Add payment");
                Log.d("BudPayAct>>","pre_title -> " + has_title);
                startActivity(i);
            }
        }

        if(v.getId() == R.id.btn_adbud_save){
            Log.d("BUTTON","Save Button Pressed!");
            blayout.loadValuesFromLayout();
            if(validated()) {
                if (has_title.equals("Add Budget")) {
                    //save new
                    blayout.loadValuesFromLayout();
                    blayout.budget_model.addBudget();//adds using it's own values
                } else {
                    Log.d("Updating using ", "id->" + this.id);
                    //condition for when user clicks on a listed Budget
                    //click for when save button is pressed
                    //hence update existing record using values from user inputs
                    blayout.loadValuesFromLayout();
                    blayout.budget_model.updateBudget(blayout.budget_model);
                }
                logInputs();
                //default go back to list view
                //Log.d("BudPayAct>>","has_title -> " + has_title);
                //Log.d("BudPayAct>>","pre_title -> " + has_title);
                Intent i = new Intent(getApplicationContext(), ListBudgetsActivity.class);
                startActivity(i);
            }
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
}
