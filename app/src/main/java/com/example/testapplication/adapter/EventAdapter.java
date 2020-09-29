package com.example.testapplication.adapter;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.db.event.Event_Impl;
import com.example.testapplication.db.event.IEvent;
import com.example.testapplication.viewholder.EventViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class EventAdapter  extends  RecyclerView.Adapter{

    private List<Event_Impl> models = new ArrayList<Event_Impl>();
    /**
     * ================== Adapter constructor ========================
     */
    private Context context;
    public EventAdapter(Context currentAct) {
        event = new Event_Impl(currentAct);
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
        //EventAdapter ea = new EventAdapter(context);
        private Event_Impl event; //Error possible
        private RadioButton lastCheckedRB = null; //save last checked radio button
        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
            Log.d("EventAdapter>>","running onBindViewH pos->"+position);
            //final Lifecycle.Event event = models.get(position);
            //reset Listener
            ((EventViewHolder) holder).radioselectevent.setOnCheckedChangeListener(null);
            //reset button state
            ((EventViewHolder) holder).radioselectevent.setChecked(false);
            int sid = event.getSelectEvent();
            if(sid>=0){
                if(models.get(position).id==sid){
                    Log.d("EventViewH>>","RadioButton checked! For eid " + models.get(position).id);
                    ((EventViewHolder) holder).radioselectevent.setChecked(true);
                }
            }

            //setListener
            ((EventViewHolder) holder).radioselectevent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean pressed) {
                    Log.d("EventAdapter>>","OncheckChange Detected!");
                    if(lastCheckedRB!=null){
                        lastCheckedRB.setChecked(false);
                        //Log.d("EventAdapter>>","OncheckChange Setting previous button to uncheck! eid ->" + prev_pos);
                    }
                    lastCheckedRB = ((EventViewHolder) holder).radioselectevent;
                    models.get(position).selectEvent(models.get(position));
                    notifyDataSetChanged();
                }
            });
            //bind data
            ((EventViewHolder) holder).bindData(models.get(position)); //bind each obj from model
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








