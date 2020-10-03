package com.example.testapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.adapter.VendorPaymentAdapter;
import com.example.testapplication.constants.ConstantBundleKeys;

public class Vendorpaymentview extends AppCompatActivity {
    private int eid = 0, id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorpayview);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            eid = b.getInt(ConstantBundleKeys.EVENT_ID,0);
            id=b.getInt("id",0);
        }
        Log.d("VendorPayView>>","Receiving eid -> " + eid);
        Toolbar toolbar = findViewById(R.id.abbb_menu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Intent i = new Intent(getApplicationContext(),GraphActivity.class);
                //  startActivity(i);
                finish();
            }
        });
        VendorPaymentAdapter adapter = new VendorPaymentAdapter(this,id,eid); //Budget Adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_ev);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//item to item decoration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Helper method which will generate a basic ArrayList of 100 items
     *
     * @return A List of SimpleViewModels

    private List<SimpleViewModel> generateSimpleList() {
    List<SimpleViewModel> simpleViewModelList = new ArrayList<>();

    for (int i = 0; i < 20; i++) {
    simpleViewModelList.add(new SimpleViewModel(String.format(Locale.US, "", i)));
    }

    return simpleViewModelList;
    }
     */
    public void handleOnClick(View v){
        if(v.getId()==R.id.lv_add_vendor2){
            Intent i = new Intent(getApplicationContext(),Addpayment.class);
            Bundle b = new Bundle();
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);
            b.putInt("id",id);
            i.putExtras(b);
            startActivity(i);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            //Settings btn
            Intent i = new Intent(getApplicationContext(),ListCategory.class);
            startActivity(i);
            //finish();//check
        }
        if(item.getItemId()==R.id.action_about_us) {
            Intent i = new Intent(getApplicationContext(), About_us.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //refresh activity
        finish();
        startActivity(getIntent());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Bundle b = new Bundle();
            Intent i = new Intent(getApplicationContext(),Editvendor.class);
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);
            i.putExtras(b);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

