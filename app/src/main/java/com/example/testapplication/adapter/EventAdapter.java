package com.example.testapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.db.event.Event_Impl;
import com.example.testapplication.db.event.IEvent;
import com.example.testapplication.db.task.ITask;
import com.example.testapplication.db.task.Task_Impl;
import com.example.testapplication.viewholder.EventViewHolder;
import com.example.testapplication.viewholder.TaskViewHolder;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter  extends  RecyclerView.Adapter{

    private List<Event_Impl> models = new ArrayList<Event_Impl>();
    /**
     * ================== Adapter constructor ========================
     */
    private Context context;
    public EventAdapter(Context currentAct) {
        if (currentAct == null) {
            Log.d("EventAdapter>>", "CurrentAct is null!");
        } else {
            Log.d("EventAdapter>>", "CurrentAct is NOT null!");
        }
        IEvent event = new Event_Impl(currentAct);
        List<Event_Impl> lb = new ArrayList<Event_Impl>();
        lb = event.getEventList(); //error
        if (lb != null) {
            this.models.addAll(lb);
            for (Event_Impl ib : lb) {

            }
        }
        this.context = currentAct;
    }
        @NonNull
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return new EventViewHolder(view,context);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
            ((EventViewHolder) holder).bindData(models.get(position)); //bind each obj from model

            ((EventViewHolder) holder).radioselectevent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ((EventViewHolder) holder).event_.selectEvent(models.get(position));
                    notifyItemChanged(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return models.size();
        }

        @Override
        public int getItemViewType(final int position) {
            return R.layout.event_itemview;
        }
    }








