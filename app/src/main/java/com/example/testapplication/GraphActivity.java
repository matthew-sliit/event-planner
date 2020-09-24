package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.example.testapplication.db.budget.Budget_Impl;
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
            //List<Budget_Impl> budgetList = new ArrayList<>(budget.getBudgetList());
            Budget_payments payments = new Budget_payments(c);
            List<Budget_payments> paymentList = new ArrayList<>(payments.getBudgetPaymentList(eid,bid));
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
                double btot = 0.00, ptot = 0.00;
                for(Budget_Impl b : budget.getBudgetListByCategory(c)){
                    for(Budget_payments p : paymentList){
                        ptot += p.amt;
                    }
                    try {
                        btot += Double.parseDouble(b.amt);
                    }catch (NumberFormatException e){
                        Log.d("GraphAct>>","On exception-> " + e.getMessage());
                    }
                }
                totalAmt.add(new BarEntry(index,Integer.parseInt(""+btot)));
                paidAmt.add(new BarEntry(index,Integer.parseInt(""+ptot)));
                labels.add(c);
                index++;
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

            //identify max and min values of budget
            /*for(Budget_Impl b : budgetList){
                Double amt = Double.parseDouble(b.amt);
                if(amt > maxBudget){
                    maxBudget = amt;
                }
                if(amt < minBudget){
                    minBudget = amt;
                }
            }*/
            //identify range
            //double maxM10 = Math.log10(maxBudget), minM10 = Math.log10(minBudget);
            //identify number of categories
            //int categoryCount = categories.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Toolbar toolbar = (Toolbar) findViewById(R.id.menu_bud);
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
        ArrayList<BarEntry> totalAmt = new ArrayList<>();
        totalAmt.add(new BarEntry(0, 1000));
        totalAmt.add(new BarEntry(1, 2000));
        totalAmt.add(new BarEntry(2, 20000));
        totalAmt.add(new BarEntry(3, 6000));
        totalAmt.add(new BarEntry(4, 4000));
        totalAmt.add(new BarEntry(5, 5000));
        totalAmt.add(new BarEntry(6, 4000));
        totalAmt.add(new BarEntry(7, 5000));
        // creating dataset for Bar Group1
        BarDataSet barDataSet1 = new BarDataSet(totalAmt, "Category Total");
        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        //barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet1.setColors(ColorTemplate.rgb("ABEBC6"));

        ArrayList<BarEntry> paidAmt = new ArrayList<>();
        paidAmt.add(new BarEntry(0.2f, 10000));
        paidAmt.add(new BarEntry(1.2f, 1500));
        paidAmt.add(new BarEntry(2.2f, 16000));
        paidAmt.add(new BarEntry(3.2f, 6000));
        paidAmt.add(new BarEntry(4.2f, 400));
        paidAmt.add(new BarEntry(5.2f, 500));
        paidAmt.add(new BarEntry(6.2f, 400));
        paidAmt.add(new BarEntry(7.2f, 500));
        BarDataSet barDataSet2 = new BarDataSet(paidAmt, "Paid Amount");
        barDataSet2.setColors(ColorTemplate.rgb("EC7063"));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Chocolates");
        labels.add("Decorations");
        labels.add("Vehicles");
        labels.add("Flowers");
        labels.add("Ceremony");
        labels.add("Gifts");
        labels.add("C2");
        labels.add("C3");
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        // initialize the Bardata with argument labels and dataSet
        BarData data = new BarData(dataSets);
        data.setBarWidth(0.6f);
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
        if(v.getId()==R.id.btn_lb_list){
            //load list activity
            Intent i = new Intent(getApplicationContext(),ListBudgetsActivity.class);
            startActivity(i);
        }
        if(v.getId()==R.id.btn_add_budpay){
            Intent i = new Intent(getApplicationContext(), AddEditBudgetActivity.class);
            startActivity(i);
        }

    }
}