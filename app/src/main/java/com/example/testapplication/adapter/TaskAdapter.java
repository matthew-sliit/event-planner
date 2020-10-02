package com.example.testapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.db.task.ITask;
import com.example.testapplication.db.task.Task_Impl;
import com.example.testapplication.viewholder.TaskViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter {

    private List<Task_Impl> models = new ArrayList<Task_Impl>();
    /**
     * ================== Adapter constructor ========================
     */
    private Context context;
    private int eid= 0;
    public TaskAdapter(Context currentAct, int eid) {
        if (currentAct == null) {
            Log.d("GuestAdapter>>", "CurrentAct is null!");
        } else {
            Log.d("GuestAdapter>>", "CurrentAct is NOT null!");
        }
        ITask task = new Task_Impl(currentAct, eid);
        List<Task_Impl> lb = new ArrayList<Task_Impl>();
        lb = task.getTaskList(); //error
        if (lb != null) {
            this.models.addAll(lb);
            for (Task_Impl ib : lb) {

            }
        }
        this.context = currentAct;
        this.eid = eid;
    }

    @NonNull
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new TaskViewHolder(view,context,eid);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TaskViewHolder) holder).bindData(models.get(position)); //bind each obj from model
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.task_itemview;
    }
}

