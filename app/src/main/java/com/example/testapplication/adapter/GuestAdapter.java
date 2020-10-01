package com.example.testapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.db.guest.Guest_Impl;
import com.example.testapplication.db.guest.IGuest;
import com.example.testapplication.viewholder.GuestViewHolder;

import java.util.ArrayList;
import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter {
    private List<Guest_Impl> models = new ArrayList<Guest_Impl>();
    /**
     * ================== Adapter constructor ========================
     */
    private Context context;
    public GuestAdapter(Context currentAct) {
        if(currentAct == null){
            Log.d("GuestAdapter>>","CurrentAct is null!");
        }else{
            Log.d("GuestAdapter>>","CurrentAct is NOT null!");
        }
        IGuest guest = new Guest_Impl(currentAct);
        List<Guest_Impl> lb = new ArrayList<Guest_Impl>();
        lb = guest.getGuestList(); //error
        if (lb != null) {
            this.models.addAll(lb);
            for(Guest_Impl ib : lb){

            }
        }
        this.context = currentAct;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new GuestViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((GuestViewHolder) holder).bindData(models.get(position)); //bind each obj from model

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.guest_itemview;
    }
}
