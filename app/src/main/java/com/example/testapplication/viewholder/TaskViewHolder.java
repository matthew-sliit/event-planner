package com.example.testapplication.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.EditTask;
import com.example.testapplication.R;
import com.example.testapplication.adapter.TaskAdapter;
import com.example.testapplication.db.task.Task_Impl;

public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView lg_tname, lg_status;
    private int eid = 0;
    public TaskViewHolder(final View itemView, Context currentAct, int eid) {
        super(itemView);
        lg_tname = (TextView) itemView.findViewById(R.id.lg_tname);
        lg_status = (TextView) itemView.findViewById(R.id.lg_status);
        itemView.setOnClickListener(this);
        this.currentAct = currentAct; //assigning context
        this.eid = eid;
    }

    public Task_Impl task_;
    public void bindData(Task_Impl task_) {
        if(task_ != null){
            //Log.d("BudgetViewModel>>","id -> " + budget_model.id);
            this.task_ = task_;
            lg_tname.setText(task_.tname);
            if(task_.status.equals("completed")) {
                lg_status.setText(task_.status);
                lg_status.setTextColor(Color.GREEN);
            }else if(task_.status.equals("pending")) {
                lg_status.setText(task_.status);
                lg_status.setTextColor(Color.RED);
            }
        }else{
            //this only happens if null list is passed
            this.task_ = new Task_Impl(currentAct,eid);
            lg_tname.setText("");
            lg_status.setText("");
        }

    }
    public Context currentAct;//passed from ActivityClass through Adapter
    @Override
    public void onClick(View view) {
        Log.d("SimpleViewHolder::", "OnClick>>layoutPos -> "+ getLayoutPosition());
        Intent i = new Intent(view.getContext(), EditTask.class);
        Bundle b = new Bundle();
        b.putInt("eid",task_.eid);
        b.putInt("id",task_.id);//int pk
        //b.putString("title","Edit Task");

        i.putExtras(b);
        view.getContext().startActivity(i);
    }


}



