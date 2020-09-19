package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddBudgetActivity extends AppCompatActivity{
    //MainActivity ma = new MainActivity();
    //Class cls = ma.getClass();
    private String has_title="Add Budget", name="null",desc="null",amt="0.00",paid="0.00",balance="0.00";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            has_title=b.getString("title","Add Budget");
            name=b.getString("name","Enter name");
            desc=b.getString("desc","null");
            amt=b.getString("amount","0.00");
            balance=b.getString("balance","0.00");
        }
        Toolbar toolbar = findViewById(R.id.abb_menu);
        setSupportActionBar(toolbar);
        toolbar.setTitle(has_title);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListBudgetsActivity.class);
                startActivity(i);
            }
        });

        ((EditText)findViewById(R.id.nameInput)).setText(name, TextView.BufferType.EDITABLE);
        ((EditText)findViewById(R.id.amountInput)).setText(amt, TextView.BufferType.EDITABLE);
        ((TextView)findViewById(R.id.SelectedBudgetBalance)).setText(balance);

        findViewById(R.id.amountInput).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    setBalance();
                }
            }
        });

        final String[] defaultCat = getResources().getStringArray(R.array.default_categories);
        Spinner cat = findViewById(R.id.ab_cat); //spinner
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,defaultCat);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
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
        if(v.getId() == R.id.addBBtn){
            Log.d("BUTTON","AddPayment Button Pressed!");
        }
        if(v.getId() == R.id.saveBtn){
            Log.d("BUTTON","Save Button Pressed!");
            readInputs();
            Intent i = new Intent(getApplicationContext(),ListBudgetsActivity.class);
            startActivity(i);
        }
        if(v.getId()==R.id.adbud_catp){
            Bundle b = new Bundle();
            Intent i = new Intent(getApplicationContext(),EditCategoryActivity.class);
            b.putString("cat_edit","Add name"); //placeholder
            b.putString("is_in_setting","false");
            b.putString("pre_activity","addBudget");
            b.putString("set_to_cat","false");
            b.putString("title","Add Category");
            i.putExtras(b);
            startActivity(i);
        }
    }
    public void readInputs(){
        String name = ((EditText) findViewById(R.id.nameInput)).getText().toString();
        Log.d("NAME","name: " + name);
        /*
        double amount = 0.00;
        try {
            amount = Double.parseDouble(((EditText) findViewById(R.id.amountInput)).getText().toString()); //input is always a number
        }catch (NumberFormatException e){
            Log.d("AMOUNT","amount read Exception: " + e.getMessage());
        }
        Log.d("AMOUNT","amount: " + amount);

         */
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
    }
}
