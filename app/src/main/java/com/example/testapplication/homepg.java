package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class homepg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepg);

    }

    public void openGuests(View view){
        Intent intent = new Intent(this,ViewGuest.class);

        Toast.makeText(this,"Opening Guests...",Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

    public void openSummary(View view){
        Intent intent = new Intent(this,Summary.class);

        Toast.makeText(this,"Opening Summary...",Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }
}