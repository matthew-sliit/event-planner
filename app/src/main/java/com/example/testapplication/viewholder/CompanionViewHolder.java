package com.example.testapplication.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.EditCompanions;
import com.example.testapplication.EditGuest;
import com.example.testapplication.R;
import com.example.testapplication.db.guest.Companion_Impl;


public class CompanionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView lg_name,lg_invitation; //from layout
    private int gid=0,eid=0;
    /*
     * ================== ViewHolder constructor ========================
     * @param itemView
     *         The layout view group used to display the data
     * @param currentAct
     *         The context of the activity
     */
    public CompanionViewHolder(final View itemView, Context currentAct, int gid,int eid) {
        super(itemView);
        lg_name = (TextView) itemView.findViewById(R.id.lg_name);
        lg_invitation = (TextView) itemView.findViewById(R.id.lg_invitation);
        itemView.setOnClickListener(this);
        this.gid=gid;
        this.eid=eid;
        this.currentAct = currentAct; //assigning context
    }
    public Context currentAct;//passed from ActivityClass through Adapter


    public Companion_Impl companion;
    public void bindData(Companion_Impl companion) {
        if(companion != null){
            //Log.d("BudgetViewModel>>","id -> " + budget_model.id);
            this.companion = companion;
            lg_name.setText(companion.cname);
            lg_invitation.setText(""+companion.age);

        }else{
            //this only happens if null list is passed
            this.companion = new Companion_Impl(currentAct);
            lg_name.setText("");
            lg_invitation.setText("");

        }

    }
    /*
     * ================== itemView:onClick ========================
     * handles onclick of any item in recycle view
     */
    @Override
    public void onClick(View view) {
        Log.d("SimpleViewHolder::", "OnClick>>layoutPos -> "+ getLayoutPosition());
        Intent i = new Intent(view.getContext(), EditCompanions.class);
        Bundle b = new Bundle();
        b.putInt("id",companion.id);//int pk
        b.putInt("gid",gid);//int pk
        b.putInt("eid",eid);//int pk
        b.putString("title","Edit Guest");

        i.putExtras(b);
        view.getContext().startActivity(i);
    }
}


