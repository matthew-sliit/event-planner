package com.example.testapplication.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.Editvendor;
import com.example.testapplication.R;
import com.example.testapplication.db.vendor.Vendor_impl;

public class VendorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView lb_name, lb_totamt, lb_paidamt; //from layout

    /*
     * ================== ViewHolder constructor ========================
     * @param itemView
     *         The layout view group used to display the data
     * @param currentAct
     *         The context of the activity
     */
    public VendorViewHolder(final View itemView, Context currentAct) {
        super(itemView);
        lb_name = (TextView) itemView.findViewById(R.id.textView_lb_name);
        lb_totamt = (TextView) itemView.findViewById(R.id.textView_lb_totamt);
        lb_paidamt = (TextView) itemView.findViewById(R.id.textView_lb_paidamt);
        itemView.setOnClickListener(this);
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
    public Vendor_impl vendor_model;
    public void bindData(Vendor_impl vendor_model) {
        if(vendor_model != null){
            //Log.d("BudgetViewModel>>","id -> " + budget_model.id);
            this.vendor_model = vendor_model;
            lb_name.setText(vendor_model.name);
            lb_totamt.setText(""+vendor_model.amount);
        }else{
            //this only happens if null list is passed
            this.vendor_model = new Vendor_impl(currentAct);
            lb_name.setText("");
            lb_totamt.setText("");
        }

    }
    /*
     * ================== itemView:onClick ========================
     * handles onclick of any item in recycle view
     */
    @Override
    public void onClick(View view) {
        Log.d("VendorViewHolder::", "OnClick>>navigating to edit payment -> "+ getLayoutPosition());
        Intent i = new Intent(view.getContext(), Editvendor.class);
        Bundle b = new Bundle();
        b.putInt("eid",vendor_model.eid);
        b.putInt("id",vendor_model.id);//int pk
      //  b.putString("title","Edit Budget");
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
