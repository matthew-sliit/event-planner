package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditCategoryActivity extends AppCompatActivity {
    private String pre_intent = "settingCAT", is_in_setting = "true",edit = "none", is_in_cat = "true", has_title = "none";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            pre_intent = b.getString("pre_activity","settingCAT");
            is_in_setting = b.getString("is_in_setting","true");
            edit = b.getString("cat_edit","none");
            is_in_cat = b.getString("set_to_cat","true");
            has_title = b.getString("title","none");
            Log.d("RELOAD","previous activity = " + pre_intent);
            Log.d("RELOAD","is_in_setting = " + is_in_setting);
            Log.d("RELOAD","is_in_cat = " + is_in_cat);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.editcat_menu);
        setSupportActionBar(toolbar);
        if(!has_title.equalsIgnoreCase("none")){
            toolbar.setTitle(has_title);
        }
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("pre_activity",pre_intent);
                b.putString("is_in_setting",is_in_setting);
                b.putString("edit","Manage Category");
                b.putString("set_to_cat","true");
                b.putString("title","Manage Category");
                Intent j;
                if(pre_intent.equalsIgnoreCase("addBudget")){
                    j = new Intent(getApplicationContext(), AddBudgetActivity.class);
                }else {
                    j = new Intent(getApplicationContext(), AppSettingsActivity.class);
                }
                j.putExtras(b);
                startActivity(j);
            }
        });
        TextView text = (TextView)findViewById(R.id.cate_name);
        text.setText(edit);

    }
    public void handleClick(View v){
        Bundle b = new Bundle();
        b.putString("pre_activity",pre_intent);
        b.putString("is_in_setting",is_in_setting);
        b.putString("edit","Manage Category");
        b.putString("title","Manage Category");
        b.putString("set_to_cat","true");
        Intent j = new Intent(getApplicationContext(),AppSettingsActivity.class);
        j.putExtras(b);
        startActivity(j);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}