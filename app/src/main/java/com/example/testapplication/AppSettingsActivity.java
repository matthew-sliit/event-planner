package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.category.ICategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AppSettingsActivity extends AppCompatActivity {


    private String pre_intent = "home", is_in_setting = "false",edit = "none", is_in_cat = "false", has_title="none";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
        ICategory category = new Category(this);//db
        //read bundle data from previous intent
        Bundle b = getIntent().getExtras();
        if(b!=null){
            pre_intent = b.getString("pre_activity","home");
            is_in_setting = b.getString("is_in_setting","false");
            edit = b.getString("edit","none");
            is_in_cat = b.getString("set_to_cat","false");
            has_title = b.getString("title","none");
            //Log.d("RELOAD","previous activity = " + pre_intent);
            //Log.d("RELOAD","is_in_setting = " + is_in_setting);
            //Log.d("RELOAD","is_in_cat = " + is_in_cat);
        }
        //setup toolbar
        Toolbar toolbar = findViewById(R.id.aps_menu);
        setSupportActionBar(toolbar);
        if(has_title.equalsIgnoreCase("none")){
            Log.d("RELOAD","SETTING TITLE ");
        }else{
            toolbar.setTitle(has_title);
        }
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                When pressed back, use bundle data to get previous activity
                 */
                //Log.d("BUTTON","Back Button in Settings Pressed!");
                if(is_in_setting.equalsIgnoreCase("true")) {
                    Bundle b = new Bundle();
                    b.putString("pre_activity", pre_intent); //pass
                    Intent j = new Intent(getApplicationContext(), AppSettingsActivity.class);
                    b.putString("set_to_cat","false");
                    b.putString("edit","Manage Category");
                    Log.d("BACK BUTTON","Resetting SET");
                    b.putString("is_in_setting", "false");
                    j.putExtras(b);
                    startActivity(j);
               } /*  else if(pre_intent.equalsIgnoreCase("listBudget")){
                   Intent i = new Intent(getApplicationContext(), ListBudgetsActivity.class);
                    startActivity(i);
                }else{
                    //default
                    Intent i = new Intent(getApplicationContext(), MainActivityEmu.class);
                    startActivity(i);
                }*/
                Log.d("BACK BUTTON","previous activity = " + pre_intent);
                Log.d("BACK BUTTON","is_in_setting = " + is_in_setting);
                Log.d("BACK BUTTON","is_in_cat = " + is_in_cat);

            }
        });
        String[] option_names = {"Number Standard","Manage Category"};
        //contents after toolbar
        FloatingActionButton addCategory = findViewById(R.id.set_addCat);
        //addCategory.setVisibility(View.INVISIBLE);
        if(is_in_setting.equalsIgnoreCase("false")) {
            //when first launched
            //show listview
            addCategory.setVisibility(View.INVISIBLE);
            generateSettingsList(pre_intent,option_names,"false","none");
        }else{
            //after clicking on a Setting Option
            if(edit.equalsIgnoreCase(option_names[0])){
                Log.d("SETTINGS GEN","Loading Number Standard");
               // Intent k = new Intent(getApplicationContext(), NumberStandardActivity.class);
              //  startActivity(k);

            }else if(edit.equalsIgnoreCase(option_names[1])){
                Log.d("SETTINGS GEN","Loading Categories");
                option_names = getResources().getStringArray(R.array.default_categories); //escapes default
                //option_names = null;
                generateSettingsList(pre_intent, option_names,"true",edit);

                addCategory.setVisibility(View.VISIBLE);
                addCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //go to Add Category
                        Intent k = new Intent(getApplicationContext(), EditCategoryActivity.class);
                        Bundle data = new Bundle();
                        data.putString("cat_edit","Add New Category");
                        data.putString("pre_activity",pre_intent);
                        data.putString("set_to_cat","true");
                        data.putString("is_in_setting","true");
                        k.putExtras(data);
                        startActivity(k);

                    }
                });


            }else {
                addCategory.setVisibility(View.INVISIBLE);
                //option_names = getResources().getStringArray(R.array.default_categories); //escapes default
                List<String> ar =  new ArrayList<>(category.getAllCategory());
                boolean exec = false;
                for(int i=0;i<ar.size();i++){
                    //Log.d("AppSettingsAct"," ar[" + i + "] -> " + ar.get(i));
                    //Log.d("AppSettingsAct", "edit -> " + edit);
                    if(ar.get(i).equals(edit)){
                        exec = true;
                        //Log.d("AppSettingsAct", "exec -> TRUE");
                    }
                }
                //if(edit.equalsIgnoreCase(option_names[Arrays.asList(option_names).indexOf(edit)])){
                if(exec){
                    Log.d("SETTINGS GEN","Loading type of Category: " + edit);
                    Intent k = new Intent(getApplicationContext(), EditCategoryActivity.class);
                    Bundle data = new Bundle();
                    data.putString("cat_edit",edit);
                    data.putString("pre_activity",pre_intent);
                    data.putString("set_to_cat","true");
                    data.putString("is_in_setting","true");
                    k.putExtras(data);
                    startActivity(k);
                }
            }
        }
    }
    private void generateSettingsList(String previous_activity, String[] options, final String set_to_cat, final String title){
        ICategory category = new Category(this);//db
        Log.d("SETTINGS>>","GENERATING...!");
        ListView list = findViewById(R.id.listview);
        List<String> ar =  new ArrayList<>();
        //boolean is_cat = false;

        if(options != null && set_to_cat.equals("false")) {
            for (int i = 0; i < options.length; i++) {
                ar.add(options[i]);
            }
        }

        if(set_to_cat.equals("true")) {
            try {
                List<String> cats = new ArrayList();
                cats = category.getAllCategory();
                //Log.d("SETTINGS>>", "cat[0] -> " + cats.get(0) + " size -> " + category.getAllCategory().size());
                //if (!(cats.isEmpty())) {
                for (int i = 0; i < category.getAllCategory().size(); i++) {
                    ar.add(cats.get(i));
                }
                //}
            } catch (Exception e) {
                Log.d("SETTINGS ERROR:", "NPEx " + e.getMessage());
            }
        }
        //String[] option_names = ar.toArray(new String[0]);
        //List<String> option_list = new ArrayList<String>(Arrays.asList(option_names));
        List<String> option_list = new ArrayList<String>(ar);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,option_list);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i); //returns option_name selected
                Log.d("SETTINGS SELECT",selection);

                Intent j = new Intent(getApplicationContext(),AppSettingsActivity.class);
                Bundle b = new Bundle();
                b.putString("pre_activity",pre_intent); //pass
                b.putString("is_in_setting","true");
                b.putString("edit",selection); //for categories
                if(set_to_cat.equalsIgnoreCase("true")){
                    b.putString("set_to_cat","true");
                }
                if(!title.equalsIgnoreCase("none")){
                    Log.d("SETTINGS GEN","title is " + selection);
                    b.putString("title",selection);
                }
                b.putString("title",selection);
                j.putExtras(b);
                startActivity(j);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}