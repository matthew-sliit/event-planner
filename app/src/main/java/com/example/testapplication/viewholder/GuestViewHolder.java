package com.example.testapplication.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.EditGuest;
import com.example.testapplication.R;

import com.example.testapplication.db.guest.Guest_Impl;

public class GuestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

     TextView lg_name, lg_invitation;

    public GuestViewHolder(final View itemView, Context currentAct) {
        super(itemView);
        lg_name = (TextView) itemView.findViewById(R.id.lg_name);
        lg_invitation = (TextView) itemView.findViewById(R.id.lg_invitation);
        itemView.setOnClickListener(this);
        this.currentAct = currentAct; //assigning context
    }

    public Guest_Impl guest_;
    public void bindData(Guest_Impl guest_) {
        if(guest_ != null){
            //Log.d("BudgetViewModel>>","id -> " + budget_model.id);
            this.guest_ = guest_;
            lg_name.setText(guest_.guestname);
            if(guest_.invitation.equals("invitation sent")) {
                Log.d("Guest Invitation>>","invitation= " + guest_.invitation);
                lg_invitation.setText(guest_.invitation);
                lg_invitation.setTextColor(Color.GREEN);
            }else if(guest_.invitation.equals("invitation not sent")) {
                lg_invitation.setText(guest_.invitation);
                lg_invitation.setTextColor(Color.RED);
            }
        }else{
            //this only happens if null list is passed
            this.guest_ = new Guest_Impl(currentAct);
            lg_name.setText("");
            lg_invitation.setText("");
        }

    }
    public Context currentAct;//passed from ActivityClass through Adapter
    @Override
    public void onClick(View view) {
        Log.d("SimpleViewHolder::", "OnClick>>layoutPos -> "+ getLayoutPosition());
        Intent i = new Intent(view.getContext(), EditGuest.class);
        Bundle b = new Bundle();
        b.putInt("id",guest_.id);//int pk
        b.putString("title","Edit Guest");

        i.putExtras(b);
        view.getContext().startActivity(i);
    }



}
