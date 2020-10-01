package com.example.testapplication.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.Editpayment;
import com.example.testapplication.R;
import com.example.testapplication.db.vendor.Vendor_pay_Impl;

public class VendorPaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView lb_name, lb_totamt, lb_paidamt; //from layout
    private int vid=0;
    /*
     * ================== ViewHolder constructor ========================
     * @param itemView
     *         The layout view group used to display the data
     * @param currentAct
     *         The context of the activity
     */
    public VendorPaymentViewHolder(final View itemView, Context currentAct,int vid) {
        super(itemView);
        lb_name = (TextView) itemView.findViewById(R.id.textView_lb_name);
        lb_totamt = (TextView) itemView.findViewById(R.id.textView_lb_totamt);
        lb_paidamt = (TextView) itemView.findViewById(R.id.textView_lb_paidamt);
        itemView.setOnClickListener(this);
        this.vid=vid;
        this.currentAct = currentAct; //assigning context
    }
    public Context currentAct;//passed from ActivityClass through Adapter

    /*
     * ================== bindData ========================
     * method to bind data to adapter
     *
     * @param budget_model
     *         The viewmodel that contains the data
     */
    public Vendor_pay_Impl vendor_model;
    public void bindData(Vendor_pay_Impl vendor_model) {
        if(vendor_model != null){

            //Log.d("BudgetViewModel>>","id -> " + budget_model.id);
            this.vendor_model = vendor_model;
            lb_name.setText("Payment");
            lb_totamt.setText(""+vendor_model.amount);
            if(vendor_model.status.equals("paid")) {
               // Log.d("Guest Invitation>>","invitation= " + vendor_model.status);
                lb_paidamt.setText(vendor_model.status);
                lb_paidamt.setTextColor(Color.GREEN);
            }else if(vendor_model.status.equals("pending")) {
                lb_paidamt.setText(vendor_model.status);
                lb_paidamt.setTextColor(Color.RED);
            }

        }else{
            //this only happens if null list is passed
            this.vendor_model = new Vendor_pay_Impl(currentAct);
            lb_name.setText("");
            lb_totamt.setText("");
            lb_paidamt.setText("Payment null");
        }

    }
    /*
     * ================== itemView:onClick ========================
     * handles onclick of any item in recycle view
     */
    @Override
    public void onClick(View view) {
        Log.d("SimpleViewHolder::", "OnClick>>layoutPos -> "+ getLayoutPosition());
        Intent i = new Intent(view.getContext(), Editpayment.class);
        Bundle b = new Bundle();
        b.putInt("id",vendor_model.id);//int pk
        b.putInt("vid",vid);//int pk
        b.putString("title","Edit Budget");
        /*
        b.putString("name",budget_model.name);
        b.putString("category",budget_model.cat);
        b.putString("amount",budget_model.amt);
        b.putString("desc",budget_model.desc);
         */
        i.putExtras(b);
        view.getContext().startActivity(i);
    }
}


