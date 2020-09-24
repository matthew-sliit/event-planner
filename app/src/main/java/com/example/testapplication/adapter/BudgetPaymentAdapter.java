package com.example.testapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.db.budget.Budget_Impl;
import com.example.testapplication.db.budget.Budget_payments;
import com.example.testapplication.db.budget.Ibudget;
import com.example.testapplication.viewholder.BudgetPaymentViewHolder;
import com.example.testapplication.viewholder.BudgetViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BudgetPaymentAdapter extends RecyclerView.Adapter {
    private List<Budget_payments> models = new ArrayList<Budget_payments>();
    /**
     * ================== Adapter constructor ========================
     * @param eid
     * Event id
     * @param bid
     * Budget id
     * @param currentAct
     * Activity context
     */
    private Context context;
    public BudgetPaymentAdapter(Context currentAct, int eid, int bid) {
        /*
        if(currentAct == null){
            Log.d("BudgetAdapter>>","CurrentAct is null!");
        }else{
            Log.d("BudgetAdapter>>","CurrentAct is NOT null!");
        }

         */
        Budget_payments bp = new Budget_payments(currentAct);
        List<Budget_payments> lb = new ArrayList<Budget_payments>();
        lb = bp.getBudgetPaymentList(eid,bid); //pass event id and budget id
        if (lb != null) {
            this.models.addAll(lb);
            /*
            for(Budget_payments ib : lb){
                // Log.d("BudgetAdapter>>","models.get("+i+").id -> "+models.get(i).id);
                // Log.d("BudgetAdapter>>","List.get("+i+").id -> "+lb.get(i).id);
            }

             */
        }
        this.context = currentAct;

    }

    /**
     * ================== onCreateViewHolder ========================
     * @param parent
     *         The ViewGroup into which the new View will be added after it is bound to
     *         an adapter position.
     * @param viewType
     *         The view type of the new View.
     *
     * @return new BudgetViewHolder(view,context);
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new BudgetViewHolder(view,context);
    }

    /**
     * ================== onBindViewHolder ========================
     * connects ViewHolder to Adapter
     *
     * @param holder
     *         The ViewHolder
     * @param position
     *         The position in collection of data
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((BudgetPaymentViewHolder) holder).bindData(models.get(position)); //bind each obj from model
        ((BudgetPaymentViewHolder) holder).btn_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //payment tick
            }
        });
        ((BudgetPaymentViewHolder) holder).btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //payment delete
            }
        });
    }

    /**
     * ===================== getItemCount ========================
     *
     * @return collection size as int
     */
    @Override
    public int getItemCount() {
        return models.size();
    }

    /**
     * ===================== getItemViewType ======================
     *
     * @param position
     *         The position in the collection
     *
     * @return The item layout id
     */
    @Override
    public int getItemViewType(final int position) {
        return R.layout.single_pay_cell; //payment carbview
    }
}
