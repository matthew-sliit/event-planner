package com.example.testapplication.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.AddEditBudgetActivity;
import com.example.testapplication.R;
import com.example.testapplication.db.budget.Budget_Impl;

public class BudgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView lb_name, lb_totamt, lb_paidamt, lb_cat; //from layout
    public ImageButton btn_delete;

    /*
     * ================== ViewHolder constructor ========================
     * @param itemView
     *         The layout view group used to display the data
     * @param currentAct
     *         The context of the activity
     */
    public BudgetViewHolder(final View itemView, Context currentAct) {
        super(itemView);
        lb_name = (TextView) itemView.findViewById(R.id.sc_name);
        lb_totamt = (TextView) itemView.findViewById(R.id.sc_rightTop);
        lb_paidamt = (TextView) itemView.findViewById(R.id.sc_rightBot);
        lb_cat = (TextView) itemView.findViewById(R.id.sc_belowName);
        btn_delete = (ImageButton) itemView.findViewById(R.id.sc_image_btn_del);
        itemView.setOnClickListener(this);
        ((ImageButton) itemView.findViewById(R.id.sc_image_btn_del)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
    public Budget_Impl budget_model;
    public void bindData(Budget_Impl budget_model) {
        if(budget_model != null){
            //Log.d("BudgetViewModel>>","id -> " + budget_model.id);
            this.budget_model = budget_model;
            lb_name.setText(budget_model.name);
            lb_totamt.setText(budget_model.amt);
            lb_paidamt.setText("Paid: 00.00");
            lb_cat.setText(budget_model.cat);
        }else{
            //this only happens if null list is passed
            this.budget_model = new Budget_Impl(currentAct);
            lb_name.setText("");
            lb_totamt.setText("");
            lb_paidamt.setText("Paid: 00.00");
            lb_cat.setText("");
        }

    }
    /*
     * ================== itemView:onClick ========================
     * handles onclick of any item in recycle view
     */
    @Override
    public void onClick(View view) {
        //Log.d("BudgetViewHolder::", "OnClick>>layoutPos -> "+ getLayoutPosition());
        Intent i = new Intent(view.getContext(), AddEditBudgetActivity.class);
        Bundle b = new Bundle();
        b.putInt("id",budget_model.id);//int pk
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
