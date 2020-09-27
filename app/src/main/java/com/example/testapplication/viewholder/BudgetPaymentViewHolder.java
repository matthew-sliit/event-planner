package com.example.testapplication.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.BudgetPaymentsActivity;
import com.example.testapplication.R;
import com.example.testapplication.db.budget.Budget_payments;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BudgetPaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView bp_name, bp_date, bp_amt; //from layout
    public ImageButton btn_paid, btn_delete;
    public TextView bp_show_paid;
    private int eid = 0, bid = 0;
    /*
     * ================== ViewHolder constructor ========================
     * @param itemView
     *         The layout view group used to display the data
     * @param currentAct
     *         The context of the activity
     */
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    public BudgetPaymentViewHolder(final View itemView, Context currentAct) {
        super(itemView);
        this.currentAct = currentAct;
        bp_name = (TextView) itemView.findViewById(R.id.sc_pay_name);
        bp_date = (TextView) itemView.findViewById(R.id.sc_pay_belowName);
        bp_amt = (TextView) itemView.findViewById(R.id.sc_pay_rightTop);
        bp_show_paid = (TextView) itemView.findViewById(R.id.sc_pay_tv_onbtns);
        btn_paid = (ImageButton)itemView.findViewById(R.id.sc_image_btn_paid);
        btn_delete = (ImageButton)itemView.findViewById(R.id.sc_image_btn_del);
        itemView.setOnClickListener(this);
        //this.eid = event_id;
        //this.bid  =budget_id;
    }
    public Context currentAct;//passed from ActivityClass through Adapter
    public Budget_payments pay_model; //instance var
    /*
     * ================== bindData ========================
     * method to bind data to adapter
     *
     * @param budget_model
     *         The viewmodel that contains the data
     */
    public void bindData(final Budget_payments payment) {
        if(payment != null){
            //Log.d("BudgetViewModel>>","id -> " + budget_model.id);
            //Log.d("BudgetPayViewH>>","amt: " + String.valueOf(payment.amt));
            Log.d("BudgetPayViewH>>","status: " + payment.status);
            //Log.d("BudgetPayViewH>>","date: " + df.format(payment.rdate));
            this.pay_model = payment;
            bp_name.setText(payment.name);
            String amt = String.valueOf(payment.amt);
            bp_amt.setText(amt, TextView.BufferType.EDITABLE);
            if(payment.rdate == null){
                Date currentTime = Calendar.getInstance().getTime();
                bp_date.setText(df.format(currentTime));
            }else {
                bp_date.setText(df.format(payment.rdate));
            }
            if(payment.status.equalsIgnoreCase("paid")){
                //turn pay button to invisible
                //btn_delete.setVisibility(View.INVISIBLE);
                //btn_delete.setId(R.id.sc_image_btn_paid);
                //btn_paid.setVisibility(View.INVISIBLE);
                //btn_paid.setId(R.id.sc_image_btn_del);
                //btn_paid.setForegroundTintMode(PorterDuff.Mode.CLEAR);
                //btn_paid.setImageResource(R.drawable.ic_delete);
            }else{
                btn_paid.setVisibility(View.VISIBLE);
            }
        }else{
            //this only happens if null list is passed
            this.pay_model = new Budget_payments(currentAct);
            bp_name.setText("");
            bp_date.setText("");
            bp_amt.setText("0.00");
        }
    }

    /*
     * ================== itemView:onClick ========================
     * handles onclick of any item in recycle view
     */
    @Override
    public void onClick(View view) {

        Log.d("BudgetPayment>>","Navigating to Edit Payments");

        Intent i = new Intent(view.getContext(), BudgetPaymentsActivity.class);
        Bundle b = new Bundle();
        //pass eventid, budgetid and payid
        b.putInt("eid",pay_model.event_id);
        b.putInt("bid",pay_model.budget_id);
        b.putInt("pid",pay_model.payment_id);
        b.putString("title","Edit Payment");
        b.putString("pre_title","Edit Budget");
        i.putExtras(b);
        view.getContext().startActivity(i);

    }
}
