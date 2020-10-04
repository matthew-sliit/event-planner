package com.example.testapplication.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.db.vendor.IVendor_pay;
import com.example.testapplication.db.vendor.Vendor_pay_Impl;
import com.example.testapplication.viewholder.VendorPaymentViewHolder;
import com.example.testapplication.R;

import java.util.ArrayList;
import java.util.List;

public class VendorPaymentAdapter extends RecyclerView.Adapter{
    private List<Vendor_pay_Impl> models = new ArrayList<Vendor_pay_Impl>();
    /**
     * ================== Adapter constructor ========================
     */
    private Context context;
    private int id=0,eid=0;
    public VendorPaymentAdapter(Context currentAct,int vid,int eid) {
        if(currentAct == null){
            Log.d("BudgetAdapter>>","CurrentAct is null!");
        }else{
            Log.d("BudgetAdapter>>","CurrentAct is NOT null!");
        }
        IVendor_pay budget = new Vendor_pay_Impl(currentAct);
        List<Vendor_pay_Impl> lb = new ArrayList<Vendor_pay_Impl>();
        this.eid=eid;
        this.id=vid;
        lb = budget.getPayment(eid,id); //error
        if (!lb.isEmpty()) {
            this.models.addAll(lb);
            int i=0;
            for(Vendor_pay_Impl ib : lb){
                 Log.d("BudgetAdapter>>","models.get("+i+").id -> "+ib.name);
                // Log.d("BudgetAdapter>>","List.get("+i+").id -> "+lb.get(i).id);
                 i++;
            }
        }else{
            Log.d("VpAdapter>>","ListNull");
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
        return new VendorPaymentViewHolder(view,context,id);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((VendorPaymentViewHolder) holder).bindData(models.get(position)); //bind each obj from model
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
        return R.layout.payment_itemview;
    }

}

