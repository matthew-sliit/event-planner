package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.budget.Budget_Impl_updated;
import com.example.testapplication.db.budget.Budget_payments;
import com.example.testapplication.db.category.Category;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
public class GraphActivity extends AppCompatActivity {

    private int eid = 0;
    private double totalBudget = 0.00, totalPaid = 0.00, totalRemaining = 0.00;
    private class BarChartHandler{
        private Context c;
        //private int bid = 0, eid = 0;
        public BarChartHandler(Context c){
            this.c = c;
            barChart = (HorizontalBarChart) findViewById(R.id.barchart);
            totalBudget = 0.00;
            totalPaid = 0.00;
            totalRemaining = 0.00;
        }
        private HorizontalBarChart barChart;
        public void draw(){
            Budget_Impl_updated budget = new Budget_Impl_updated(c,eid);
            //List<Budget_Impl> budgetList = new ArrayList<>(budget.getBudgetList());
            Budget_payments payments = new Budget_payments(c);
            //List<Budget_payments> paymentList = new ArrayList<>(payments.getBudgetPaymentList(eid,bid));
            //double minBudget =Double.parseDouble(budgetList.get(0).amt), maxBudget=0;
            Category category = new Category(c);
            List<String> categories = new ArrayList<>(category.getAllCategory());

            //init arraylists for bar chart
            ArrayList<BarEntry> totalAmt = new ArrayList<>();
            ArrayList<BarEntry> paidAmt = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<String>();
            int index = 0;//counter
            for(String c : categories){
                //foreach category
                //Log.d("Graph>>","cat -> " + c);
                double btot = 0.00, ptot = 0.00;
                //ignore if there aren't a budget for the category
                if(budget.getBudgetListByCategory(c).size() > 0) {
                    //for each budget
                    for (Budget_Impl_updated b : budget.getBudgetListByCategory(c)) {
                        //for each payment of budget
                        //Log.d("Graph>>","budget_name -> " + b.name);
                        for (Budget_payments p : payments.getBudgetPaymentList(eid,b.id)) {
                            //only if paid
                            if(p.status.equalsIgnoreCase("paid")) {
                                ptot += p.amt;
                            }
                        }
                        try {
                            //budget total per category
                            btot += Double.parseDouble(b.amt);
                        } catch (NumberFormatException e) {
                            Log.d("GraphAct>>", "On exception-> " + e.getMessage());
                        }
                    }
                    totalAmt.add(new BarEntry(index, (int)btot));
                    paidAmt.add(new BarEntry(index, (int)ptot));
                    totalPaid += ptot;
                    totalBudget += btot;
                    labels.add(c);
                    index++;
                }
            }
            BarDataSet totalBudgetAmountSets = new BarDataSet(totalAmt,"Category Total");
            totalBudgetAmountSets.setColors(ColorTemplate.rgb("ABEBC6"));//green
            BarDataSet totalPaidAmountSets = new BarDataSet(paidAmt,"Paid Amount");
            totalPaidAmountSets.setColors(ColorTemplate.rgb("EC7063"));//reddish

            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
            dataSets.add(totalBudgetAmountSets);
            dataSets.add(totalPaidAmountSets);
            // initialize the Bardata with argument labels and dataSet
            BarData data = new BarData(dataSets);
            data.setBarWidth(0.6f);
            //BarData data = new BarData(labels, dataSets);
            barChart.setData(data);
            setSummaryValues();
        }
        public void setSummaryValues(){
            ((TextView)findViewById(R.id.textView3)).setText(String.valueOf(totalBudget));
            ((TextView)findViewById(R.id.textView8)).setText(String.valueOf(totalPaid));
            totalRemaining = totalBudget - totalPaid;
            ((TextView)findViewById(R.id.textView11)).setText(String.valueOf(totalRemaining));
        }
    }

    private BarChartHandler barChartHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            eid = b.getInt(ConstantBundleKeys.EVENT_ID,0);
        }
        Log.d("Graph>>","received eid -> " + eid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.menu_bud);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),homepg.class);
                startActivity(i);
                finish();
            }
        });

        barChartHandler = new BarChartHandler(this);
        barChartHandler.draw();
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
            Intent i = new Intent(getApplicationContext(),ListCategory.class);
            startActivity(i);
        }
        if(item.getItemId()==R.id.action_about_us) {
            Intent i = new Intent(getApplicationContext(), About_us.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    public void handleClick(View v){
        if(v.getId()==R.id.btn_lb_list){
            //load list activity
            Intent i = new Intent(getApplicationContext(),ListBudgetsActivity.class);
            Bundle b = new Bundle();
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);
            i.putExtras(b);
            startActivity(i);
        }
        if(v.getId()==R.id.btn_add_budpay){
            Intent i = new Intent(getApplicationContext(), AddEditBudgetActivity.class);
            Bundle b = new Bundle();
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);
            b.putString(ConstantBundleKeys.PRE_TITLE,"graph");
            i.putExtras(b);
            startActivity(i);
        }

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
            Intent i = new Intent(getApplicationContext(),homepg.class);
            Bundle b = new Bundle();
            b.putInt(ConstantBundleKeys.EVENT_ID,eid);//int pk
            i.putExtras(b);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}