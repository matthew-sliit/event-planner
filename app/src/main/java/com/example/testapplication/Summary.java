package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.testapplication.R;
import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.budget.Budget_Impl_updated;
import com.example.testapplication.db.budget.Budget_payments;
import com.example.testapplication.db.budget.Ibudget;
import com.example.testapplication.db.guest.Guest_Impl;
import com.example.testapplication.db.task.Task_Impl;
import com.example.testapplication.db.vendor.Vendor_impl;
import com.example.testapplication.db.vendor.Vendor_pay_Impl;

import java.util.ArrayList;
import java.util.List;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(getApplicationContext(), ViewGuest.class);
                //startActivity(i);
                finish();
            }
        });
        int eid = 0 ;
        Bundle data = getIntent().getExtras();
        if(data!=null){
            eid = data.getInt(ConstantBundleKeys.EVENT_ID,0);
        }
        //budget var
        TextView showBudgetTotal = findViewById(R.id.sum_bpen);
        TextView showBudgetUsed = findViewById(R.id.sum_bpaid);
        TextView label_budgetTotal = findViewById(R.id.tv_bpen);
        String[] bt = {"Total Budget:","Total Used:"};
        label_budgetTotal.setText(bt[0]);
        TextView label_budgetUsed = findViewById(R.id.tv_bpay);
        label_budgetUsed.setText(bt[1]);
        //budget
        double budget = 0.00, budgetUsed = 0.00;
        Budget_Impl_updated budget_model = new Budget_Impl_updated(this,eid);
        Budget_payments budgetPayment_model = new Budget_payments(this);
        for(Budget_Impl_updated b : budget_model.getBudgetList()){
            budget += Double.parseDouble(b.amt);
            for(Budget_payments bp : budgetPayment_model.getBudgetPaymentList(eid,b.id)){
                if(bp.status.equalsIgnoreCase("paid")) {
                    budgetUsed += bp.amt;
                }
            }
        }
        showBudgetTotal.setGravity(Gravity.END);
        showBudgetTotal.setText(String.valueOf(budget));
        showBudgetUsed.setGravity(Gravity.END);
        showBudgetUsed.setText(String.valueOf(budgetUsed));
        //task var
        TextView showTaskPending = findViewById(R.id.sum_tpen);
        TextView showTaskDone = findViewById(R.id.sum_tpaid);
        TextView showTaskCount = findViewById(R.id.tot_tas);
        //task
        int taskCount = 0, taskDone = 0, taskRemain = 0;
        Task_Impl task_model = new Task_Impl(this,eid);
        for(Task_Impl t : task_model.getTaskList()){
            taskCount++;
            if(t.status.equalsIgnoreCase("completed")){
                taskDone++;
            }else{
                taskRemain++;
            }
        }
        showTaskPending.setText(String.valueOf(taskRemain));
        showTaskDone.setText(String.valueOf(taskDone));
        showTaskCount.setText(String.valueOf(taskCount));
        //guest var
        TextView showGuestTotal = findViewById(R.id.tot_gue);
        TextView showInPending = findViewById(R.id.sum_nsent);
        TextView showSent = findViewById(R.id.sum_sent);
        //guest
        int invitations = 0, pending = 0, sent = 0;
        Guest_Impl guest = new Guest_Impl(this,eid);
        for(Guest_Impl g : guest.getGuestList()){
            invitations++;
            if(g.invitation.equalsIgnoreCase("invitation sent")){
                sent++;
            }else{
                pending++;
            }
        }
        showGuestTotal.setText(String.valueOf(invitations));
        showInPending.setText(String.valueOf(pending));
        showSent.setText(String.valueOf(sent));
        //vendor var
        TextView showVendorPend = findViewById(R.id.sum_vpen);
        TextView showVendorPaid = findViewById(R.id.sum_vpaid);
        //vendor
        int vpend = 0, vpaid = 0, paid_amt = 0;
        Vendor_impl vendor = new Vendor_impl(this,eid);
        Vendor_pay_Impl vendor_pay = new Vendor_pay_Impl(this);
        for(Vendor_impl v : vendor.getVendor()){
            paid_amt = 0;//reset for each vendor
            for(Vendor_pay_Impl vp : vendor_pay.getPayment(eid,v.id)){
                paid_amt += Double.parseDouble(vp.amount);
            }
            if(paid_amt >= v.amount){
                vpaid++;
            }else{
                vpend++;
            }
        }
        showVendorPaid.setText(String.valueOf(vpaid));
        showVendorPend.setText(String.valueOf(vpend));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}