package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        //toolbar
        Toolbar toolbar = findViewById(R.id.abb_menu2);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Categories");
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //setup listview
        ICategory category = new Category(this);//db
        List<String> ar =  new ArrayList<>();
        try {
            List<String> cats = new ArrayList();
            cats = category.getAllCategory();
            for (int i = 0; i < category.getAllCategory().size(); i++) {
                ar.add(cats.get(i));
            }
            //}
        } catch (Exception e) {
            Log.d("SETTINGS ERROR:", "NPEx " + e.getMessage());
        }
        final ListView list = findViewById(R.id.ls_v_cat);
        List<String> option_list = new ArrayList<String>(ar);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,option_list);
        list.setAdapter(arrayAdapter);
        //listview onclick
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i); //returns option_name selected

                Intent k = new Intent(getApplicationContext(), EditCategoryActivity.class);
                Bundle data = new Bundle();
                data.putString(ConstantBundleKeys.EDIT_CATEGORY_MODE,selection);
                data.putString(ConstantBundleKeys.TITLE,"Edit Category");
                data.putString(ConstantBundleKeys.SET_TO_CATEGORY,"true");
                data.putString(ConstantBundleKeys.IS_IN_SETTING,"true");
                k.putExtras(data);
                startActivity(k);
            }
        });
        //add new category button
        ((FloatingActionButton)findViewById(R.id.adbud_catp2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getApplicationContext(), EditCategoryActivity.class);
                Bundle data = new Bundle();
                data.putString(ConstantBundleKeys.EDIT_CATEGORY_MODE,"Add New Category");
                data.putString(ConstantBundleKeys.TITLE,"Add Category");
                data.putString(ConstantBundleKeys.SET_TO_CATEGORY,"true");
                data.putString(ConstantBundleKeys.IS_IN_SETTING,"true");
                k.putExtras(data);
                startActivity(k);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }
    //menu right corner buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            //Settings btn
            //Log.d("ADD_BUDGET>>","Navigating to AppSettingsActivity!");
            //Intent i = new Intent(getApplicationContext(),ListCategory.class);
            //startActivity(i);
        }
        if(item.getItemId()==R.id.action_about_us) {
            Intent i = new Intent(getApplicationContext(), About_us.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    ================ On Restart =====================
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        //refresh activity
        finish();
        startActivity(getIntent());
    }
    /*
    ============ On Android native back ==============
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}