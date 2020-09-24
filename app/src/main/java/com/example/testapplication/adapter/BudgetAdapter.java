package com.example.testapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.db.budget.Budget_Impl;
import com.example.testapplication.db.budget.Ibudget;
import com.example.testapplication.viewholder.BudgetViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter{
    private List<Budget_Impl> models = new ArrayList<Budget_Impl>();
    /**
     * ================== Adapter constructor ========================
     */
    private Context context;
    public BudgetAdapter(Context currentAct) {
        if(currentAct == null){
            Log.d("BudgetAdapter>>","CurrentAct is null!");
        }else{
            Log.d("BudgetAdapter>>","CurrentAct is NOT null!");
        }
        Ibudget budget = new Budget_Impl(currentAct);
        List<Budget_Impl> lb = new ArrayList<Budget_Impl>();
        lb = budget.getBudgetList(); //error
        if (lb != null) {
            this.models.addAll(lb);
            for(Budget_Impl ib : lb){
               // Log.d("BudgetAdapter>>","models.get("+i+").id -> "+models.get(i).id);
               // Log.d("BudgetAdapter>>","List.get("+i+").id -> "+lb.get(i).id);
            }
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
     *         The position in our collection of data
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((BudgetViewHolder) holder).bindData(models.get(position)); //bind each obj from model
        ((BudgetViewHolder) holder).btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models.get(position).removeBudget(models.get(position).id);
                models.remove(models.get(position));//remove from list
                notifyItemRemoved(position);//notify adapter
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
        return R.layout.single_cell;
    }

}
