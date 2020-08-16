package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddVendor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendor);

        final String[] defaultCat={"Beauty","Decor"};
        Spinner cat=(Spinner)findViewById(R.id.combo);
        ArrayAdapter<String> catAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,defaultCat);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catAdapter);
    }
}