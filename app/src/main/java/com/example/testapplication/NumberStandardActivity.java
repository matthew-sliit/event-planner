package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

public class NumberStandardActivity extends AppCompatActivity {
    private boolean us_decimal_pressed = false, eur_decimal_pressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_standard);

        Toolbar toolbar = findViewById(R.id.numset_menu);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Number Standard");
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AppSettingsActivity.class);
                Bundle b = new Bundle();
                b.putString("is_in_setting", "true");
                i.putExtras(b);
                startActivity(i);
            }
        });

    }
    public void handleSelection(View v){
        /*
        RadioButton us_decimal = (RadioButton)findViewById(R.id.set_us);
        RadioButton eur_decimal = (RadioButton)findViewById(R.id.set_eur);
        if(((RadioButton)findViewById(R.id.set_us)).isChecked() && !us_decimal_pressed){
            us_decimal_pressed = true;
            return;
        }else if(((RadioButton)findViewById(R.id.set_eur)).isChecked() && !eur_decimal_pressed){
            eur_decimal_pressed = true;
            return;
        }
        if(v.getId()==R.id.set_eur){
            if(eur_decimal_pressed){
                eur_decimal.setChecked(false);
            }
        }
        if(v.getId()==R.id.set_us){
            if(us_decimal_pressed){
                us_decimal.setChecked(false);
            }
        }

         */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}