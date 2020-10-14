package com.example.testapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.AddEditBudgetActivity;
import com.example.testapplication.R;
import com.example.testapplication.constants.ConstantBundleKeys;
import com.example.testapplication.db.budget.Budget_payments;
import com.example.testapplication.viewholder.BudgetPaymentViewHolder;

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
    private int eid = 0, bid = 0;
    public BudgetPaymentAdapter(Context currentAct, int eid, int bid) {
        /*
        if(currentAct == null){
            Log.d("BudgetAdapter>>","CurrentAct is null!");
        }else{
            Log.d("BudgetAdapter>>","CurrentAct is NOT null!");
        }

         */
        this.eid = eid;
        this.bid = bid;
        Log.d("BudgetPayAdap>>","eid:" + eid + " & bid:" + bid);
        Budget_payments bp = new Budget_payments(currentAct);
        List<Budget_payments> lb = new ArrayList<Budget_payments>();
        lb = bp.getBudgetPaymentList(eid,bid); //pass event id and budget id
        if (!lb.isEmpty()) {
            this.models.addAll(lb);
            Log.d("BudgetPayAdap>>","List Not Empty, Found("+lb.size()+")");
            /*
            int i = 0;
            for(Budget_payments ib : lb){
                Log.d("BudgetAdapter>>","models.get("+i+").id -> "+models.get(i).name);
                Log.d("BudgetAdapter>>","List.get("+i+").name -> "+lb.get(i).name);
                i++;
            }
             */

        }else{
            Log.d("BudgetPayAdap>>","List is Empty!");
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
     * @return new BudgetPaymentViewHolder(view,context);
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new BudgetPaymentViewHolder(view,context);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //bind data
        ((BudgetPaymentViewHolder) holder).bindData(models.get(position)); //bind each obj from model
        ((BudgetPaymentViewHolder) holder).btn_delete.setVisibility(View.INVISIBLE);
        //check
        if(models.get(position).status.equalsIgnoreCase("paid")){
            ((BudgetPaymentViewHolder) holder).btn_paid.setVisibility(View.INVISIBLE);
            ((BudgetPaymentViewHolder) holder).bp_show_paid.setText("Paid");
            /*
            //paid button changes to delete
            ((BudgetPaymentViewHolder) holder).btn_paid.setBackgroundResource(R.drawable.ic_delete);
            //((BudgetPaymentViewHolder) holder).btn_paid.setImageResource(R.drawable.ic_delete);
            ((BudgetPaymentViewHolder) holder).btn_paid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //payment delete
                    models.get(position).removePayment(eid,bid,models.get(position).payment_id);
                    //notifyItemChanged(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });*/
        }else {
            //listen
            ((BudgetPaymentViewHolder) holder).btn_paid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //payment tick
                    models.get(position).status = "Paid";
                    //update
                    models.get(position).updatePayment(eid, bid, models.get(position).payment_id);
                    //tell adapter
                    //notifyItemChanged(position);
                    notifyDataSetChanged();
                    //fixed activity not refreshing when clicking on paid from adapter
                    //also causes a bit instability
                    Intent i = new Intent(context.getApplicationContext(), AddEditBudgetActivity.class);
                    Bundle b = new Bundle();
                    b.putInt(ConstantBundleKeys.EVENT_ID,eid);//int pk
                    b.putInt(ConstantBundleKeys.ID,bid);//int pk
                    b.putString("title","Edit Budget");
                    i.putExtras(b);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                    ((Activity)context).finish();
                }
            });
        }
        /*
        //listen
        ((BudgetPaymentViewHolder) holder).btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //payment delete
                models.get(position).removePayment(eid,bid,models.get(position).payment_id);
                //notifyItemChanged(position);
                notifyItemRemoved(position);
            }
        });

         */
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
