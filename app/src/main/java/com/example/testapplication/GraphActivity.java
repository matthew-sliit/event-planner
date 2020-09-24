package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import com.example.testapplication.db.budget.Budget_Impl;
import com.example.testapplication.db.budget.Budget_payments;
import com.example.testapplication.db.category.Category;
import com.github.mikephil.charting.charts.BarChart;
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

    private int pid = 0, eid = 0, bid = 0;
    private class BarChartHandler{
        private Context c;
        private int bid = 0, eid = 0;
        public BarChartHandler(Context c){
            this.c = c;
            barChart = (HorizontalBarChart) findViewById(R.id.barchart);
        }
        private HorizontalBarChart barChart;
        public void draw(){
            Budget_Impl budget = new Budget_Impl(c);
            List<Budget_Impl> budgetList = new ArrayList<>(budget.getBudgetList());
            Budget_payments payments = new Budget_payments(c);
            List<Budget_payments> paymentList = new ArrayList<>(payments.getBudgetPaymentList(eid,bid));
            double minBudget =Double.parseDouble(budgetList.get(0).amt), maxBudget=0;
            Category category = new Category(c);
            List<Category> categories = new ArrayList<>(category.getAllCategory());

            //identify max and min values of budget
            for(Budget_Impl b : budgetList){
                Double amt = Double.parseDouble(b.amt);
                if(amt > maxBudget){
                    maxBudget = amt;
                }
                if(amt < minBudget){
                    minBudget = amt;
                }
            }
            //identify range
            double maxM10 = Math.log10(maxBudget), minM10 = Math.log10(minBudget);
            //identify number of categories
            int categoryCount = categories.size();


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Toolbar toolbar = (Toolbar) findViewById(R.id.gmenu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivityEmu.class);
                startActivity(i);
            }
        });

        HorizontalBarChart barChart = (HorizontalBarChart) findViewById(R.id.barchart);
        // create BarEntry for Bar Group 1
        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(8f, 0));
        bargroup1.add(new BarEntry(2f, 1));
        bargroup1.add(new BarEntry(5f, 2));
        bargroup1.add(new BarEntry(20f, 3));
        bargroup1.add(new BarEntry(15f, 4));
        bargroup1.add(new BarEntry(19f, 5));
        // creating dataset for Bar Group1
        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Bar Group 1");
        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);

        // initialize the Bardata with argument labels and dataSet
        BarData data = new BarData(dataSets);
        //BarData data = new BarData(labels, dataSets);
        barChart.setData(data);
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
        if(v.getId()==R.id.listBtn){
            //load list activity
            Intent i = new Intent(getApplicationContext(),ListBudgetsActivity.class);
            startActivity(i);
        }
        if(v.getId()==R.id.addBBtn){
            Intent i = new Intent(getApplicationContext(),AddBudgetActivity.class);
            startActivity(i);
        }

    }
}