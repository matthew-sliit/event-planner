package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;

public class EditCategoryActivity extends AppCompatActivity {
    private String pre_intent = "settingCAT", is_in_setting = "true",edit = "none", is_in_cat = "true", has_title = "none";
    private int id = 0;
    /*
    ===================== OnCreate ==========================
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        Log.d("EditCategoryAct"," has started! ");
        Bundle b = getIntent().getExtras();
        if(b!=null){
            pre_intent = b.getString(ConstantBundleKeys.PRE_ACTIVITY,"settingCAT");
            is_in_setting = b.getString(ConstantBundleKeys.IS_IN_SETTING,"true");
            edit = b.getString(ConstantBundleKeys.EDIT_CATEGORY_MODE,"none");
            is_in_cat = b.getString(ConstantBundleKeys.SET_TO_CATEGORY,"true");
            has_title = b.getString(ConstantBundleKeys.TITLE,"none");
            id = b.getInt(ConstantBundleKeys.ID);
            Log.d("RELOAD","previous activity = " + pre_intent);
            Log.d("RELOAD","is_in_setting = " + is_in_setting);
            Log.d("RELOAD","is_in_cat = " + is_in_cat);
        }
        ((TextView)findViewById(R.id.editcat_er_msg)).setTextColor(Color.parseColor("#FFFFFF"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.editcat_menu);
        setSupportActionBar(toolbar);
        Log.d("EditCat>>","has_title -> " + has_title);
        if(!has_title.equalsIgnoreCase("none")){

            toolbar.setTitle(has_title);
        }
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Bundle b = new Bundle();
                b.putString(ConstantBundleKeys.PRE_ACTIVITY,pre_intent);
                b.putString(ConstantBundleKeys.IS_IN_SETTING,is_in_setting);
                b.putString("edit","Manage Category");
                b.putString(ConstantBundleKeys.SET_TO_CATEGORY,"true");

                Log.d("EditCatAct>>","getting id as " + id);
                Intent j;
                if(pre_intent.equalsIgnoreCase("add budget") || pre_intent.equalsIgnoreCase("edit budget")){
                    j = new Intent(getApplicationContext(), AddEditBudgetActivity.class);
                    b.putString(ConstantBundleKeys.TITLE,pre_intent);
                    b.putInt(ConstantBundleKeys.ID,id);
                    Log.d("EditCatAct>>","putting id as " + id);
                }else {
                    j = new Intent(getApplicationContext(), AppSettingsActivity.class);
                    b.putString(ConstantBundleKeys.TITLE,"Manage Category");
                }
                j.putExtras(b);
                startActivity(j);
                 */
                /*
                Bundle b = new Bundle();
                b.putString(ConstantBundleKeys.PRE_ACTIVITY,pre_intent);
                b.putString(ConstantBundleKeys.IS_IN_SETTING,is_in_setting);
                b.putString("edit","Manage Category");
                b.putString(ConstantBundleKeys.TITLE,"Manage Category");
                b.putString(ConstantBundleKeys.SET_TO_CATEGORY,"true");
                Intent j = new Intent(getApplicationContext(),AppSettingsActivity.class);
                j.putExtras(b);
                startActivity(j);
                 */
                finish();
            }
        });
        final TextView text = (TextView)findViewById(R.id.cate_name);
        if(edit.equalsIgnoreCase("add name")){
            text.setText("");
            text.setHint("Enter category name");
        }else{
            text.setText(edit);
        }
        final Context context = this; //context set
        //for text box
        ((EditText)findViewById(R.id.cate_name)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("EditCategoryAct","EditText Before TextChanged!");
                ((TextView)findViewById(R.id.editcat_er_msg)).setTextColor(Color.parseColor("#FFFFFF"));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("EditCategoryAct","EditText onTextChanged!");
                ICategory category = new Category(context);//initiate
                TextView inputCategory = (TextView) findViewById(R.id.cate_name);
                if (category.hasCategory(inputCategory.getText().toString())) {
                    ((TextView) findViewById(R.id.editcat_er_msg)).setText(R.string.category_error_txt);
                    ((TextView) findViewById(R.id.editcat_er_msg)).setTextColor(Color.parseColor("#FF0000"));
                }else{
                    ((TextView)findViewById(R.id.editcat_er_msg)).setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("EditCategoryAct","EditText After TextChanged!");
            }
        });
        (findViewById(R.id.cate_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ICategory category = new Category(context);//initiate
                Log.d("BUTTON","Saving Category!");
                TextView inputCategory = (TextView) findViewById(R.id.cate_name);
                if (category.hasCategory(edit)) {
                    Log.d("EditCategoryAct", "Value Exists");
                    //value exists, proceed to update
                    if (!inputCategory.getText().toString().equals(edit)) {
                        //value changed
                        category.updateCategory(edit, inputCategory.getText().toString()); //update
                    }
                } else {

                    //new value, check if there's already a value
                    if (!category.hasCategory(inputCategory.getText().toString())) {
                        //new value
                        String s = category.addCategory(inputCategory.getText().toString()); //save new
                    } else {
                        ((TextView) findViewById(R.id.editcat_er_msg)).setText(R.string.category_error_txt);
                        ((TextView) findViewById(R.id.editcat_er_msg)).setTextColor(Color.parseColor("#FF0000"));
                    }
                }

                /*
                Bundle b = new Bundle();
                b.putString(ConstantBundleKeys.PRE_ACTIVITY,pre_intent);
                b.putString(ConstantBundleKeys.IS_IN_SETTING,is_in_setting);
                b.putString("edit","Manage Category");
                b.putString(ConstantBundleKeys.TITLE,"Manage Category");
                b.putString(ConstantBundleKeys.SET_TO_CATEGORY,"true");
                Intent j = new Intent(getApplicationContext(),AppSettingsActivity.class);
                j.putExtras(b);
                startActivity(j);

                 */
                finish();
            }
        });
        //delete btn
        (findViewById(R.id.cate_del)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ICategory category = new Category(context);
                Log.d("BUTTON","Deleting Category " + text.getText().toString());
                category.deleteCategory(text.getText().toString());

                /*
                Bundle b = new Bundle();
                b.putString(ConstantBundleKeys.PRE_ACTIVITY,pre_intent);
                b.putString(ConstantBundleKeys.IS_IN_SETTING,is_in_setting);
                b.putString("edit","Manage Category");
                b.putString(ConstantBundleKeys.TITLE,"Manage Category");
                b.putString(ConstantBundleKeys.SET_TO_CATEGORY,"true");
                Intent j = new Intent(getApplicationContext(),AppSettingsActivity.class);
                j.putExtras(b);
                startActivity(j);

                 */
                finish();
            }
        });
    }
    public void handleClick(View v){
        /*
        if(v.getId() == R.id.cate_save){
            ICategory category = new Category(this);
            Log.d("BUTTON","Saving Category!");
            TextView inputCategory = (TextView) findViewById(R.id.cate_name);
            String s = category.addCategory(inputCategory.getText().toString());
        }
        Bundle b = new Bundle();
        b.putString("pre_activity",pre_intent);
        b.putString("is_in_setting",is_in_setting);
        b.putString("edit","Manage Category");
        b.putString("title","Manage Category");
        b.putString("set_to_cat","true");
        Intent j = new Intent(getApplicationContext(),AppSettingsActivity.class);
        j.putExtras(b);
        startActivity(j);

         */
    }
    //menu right corner buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        if(item.getItemId()==R.id.action_settings){
            //Settings btn
            //Log.d("ADD_BUDGET>>","Navigating to AppSettingsActivity!");
            //Intent i = new Intent(getApplicationContext(),ListCategory.class);
            //startActivity(i);
        }
         */
        if(item.getItemId()==R.id.action_about_us) {
            Intent i = new Intent(getApplicationContext(), About_us.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            /*
            Bundle b = new Bundle();
            b.putString(ConstantBundleKeys.PRE_ACTIVITY,pre_intent);
            b.putString(ConstantBundleKeys.IS_IN_SETTING,is_in_setting);
            b.putString("edit","Manage Category");
            b.putString(ConstantBundleKeys.TITLE,"Manage Category");
            b.putString(ConstantBundleKeys.SET_TO_CATEGORY,"true");
            Intent j = new Intent(getApplicationContext(),AppSettingsActivity.class);
            j.putExtras(b);
            startActivity(j);

             */
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}