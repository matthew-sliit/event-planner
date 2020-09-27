package com.example.testapplication.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.editEvent;
import com.example.testapplication.R;
import com.example.testapplication.db.event.Event_Impl;


public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView lg_ename;
    public RadioButton radioselectevent;

    public EventViewHolder(final View itemView, Context currentAct) {
        super(itemView);
        lg_ename = (TextView) itemView.findViewById(R.id.lg_ename);

        radioselectevent = (RadioButton) itemView.findViewById(R.id.radioeventselect);

        itemView.setOnClickListener(this);
        this.currentAct = currentAct; //assigning context
    }



    public Event_Impl event_;
    public void bindData(Event_Impl event_) {

        int sid = event_.getSelectEvent();

        radioselectevent.setChecked(false);

        if(sid>=0){
            if(event_.id==sid){
                radioselectevent.setChecked(true);
            }
        }

        if(event_ != null){
            //Log.d("BudgetViewModel>>","id -> " + budget_model.id);
            this.event_= event_;
            lg_ename.setText(event_.ename);

        }else{
            //this only happens if null list is passed
            this.event_ = new Event_Impl(currentAct);
            lg_ename.setText("");

        }

    }
    public Context currentAct;//passed from ActivityClass through Adapter
    @Override
    public void onClick(View view) {
        Log.d("SimpleViewHolder::", "OnClick>>layoutPos -> "+ getLayoutPosition());
        Intent i = new Intent(view.getContext(), editEvent.class);
        Bundle b = new Bundle();
        b.putInt("id",event_.id);//int pk
        b.putString("title","Edit Event");

        i.putExtras(b);
        view.getContext().startActivity(i);
    }


}

