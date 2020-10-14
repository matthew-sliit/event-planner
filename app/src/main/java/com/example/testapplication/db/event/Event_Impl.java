package com.example.testapplication.db.event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.testapplication.db.DBHandler;
import com.example.testapplication.db.budget.Budget_Impl_updated;
import com.example.testapplication.db.commontables.EventsTable;
import com.example.testapplication.db.commontables.SelectEvent;
import com.example.testapplication.db.guest.Guest_Impl;
import com.example.testapplication.db.task.Task_Impl;
import com.example.testapplication.db.vendor.Vendor_impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event_Impl implements IEvent{


    //Class variables
    public String ename,edate,etime;
    public int id = 0;
    private DBHandler db,selectdb;
    private Context c;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private EventsTable table = new EventsTable();
    public Event_Impl(Context c) {
        /*
        if(c == null){
            Log.d("Event_Impl>>","context is null!");
        }else{
            Log.d("Event_Impl>>","context is NOT null!");
        }
         */
        db = new DBHandler(c,table.getIfNotExistsStatement());
        selectdb = new DBHandler(c, SelectEvent.getIfNotExistStatement());
        this.c = c;
    }




    @Override
    public void AddEvent(String ename, String date, String time) {

    }

    @Override
   public void AddEvent() {

        ContentValues cv= new ContentValues();
        cv.put(table.COLUMN_NAME_EVENTNAME, ename);
        cv.put(table.COLUMN_NAME_DATE, edate);
        cv.put(table.COLUMN_NAME_TIME, etime);
        db.insert(cv,table.TABLENAME);
    }

    @Override
    public void selectEvent(Event_Impl obj) {
        String[] cols = {SelectEvent.COLUMN_NAME_EID,SelectEvent.COLUMN_NAME_ID};
        Cursor c = selectdb.readAllIgnoreArgs(cols,SelectEvent.TABLE_NAME);
        int eid = -1, sid = -1;
        try {
            while (c.moveToNext()) {
                eid = c.getInt(c.getColumnIndexOrThrow(SelectEvent.COLUMN_NAME_EID));
                sid = c.getInt(c.getColumnIndexOrThrow(SelectEvent.COLUMN_NAME_ID));
                //String sName = c.getString(c.getColumnIndexOrThrow(SelectEvent.COLUMN_NAME_EVENTNAME));
            }
            Log.d("EventImpl>>", "setSelectEvent reads eid from db -> " + eid);
            if(eid==-1){
                //no record
                ContentValues cv = new ContentValues();
                cv.put(SelectEvent.COLUMN_NAME_EID, obj.id);
                cv.put(SelectEvent.COLUMN_NAME_EVENTNAME, obj.ename);
                selectdb.insert(cv,SelectEvent.TABLE_NAME);
            }else {
                if (eid != obj.id) {
                    Log.d("EventImpl>>", "setSelectEvent resets eid in db -> " + obj.id);
                    ContentValues cv = new ContentValues();
                    cv.put(SelectEvent.COLUMN_NAME_EID, obj.id);
                    cv.put(SelectEvent.COLUMN_NAME_EVENTNAME, obj.ename);
                    selectdb.update(cv, SelectEvent.COLUMN_NAME_ID, String.valueOf(sid), SelectEvent.TABLE_NAME);
                } else {
                    //don't update, pointless
                }
            }
        }catch (CursorIndexOutOfBoundsException e){
            Log.d("Event_impl>>","Cursor Error in selectEvent!");
            ContentValues cv = new ContentValues();
            cv.put(SelectEvent.COLUMN_NAME_EID, obj.id);
            cv.put(SelectEvent.COLUMN_NAME_EVENTNAME, obj.ename);
            selectdb.insert(cv,SelectEvent.TABLE_NAME);
        }
    }

    public int getSelectEvent() {
        String[] cols = {SelectEvent.COLUMN_NAME_EID};
        Cursor c = selectdb.readAllIgnoreArgs(cols, SelectEvent.TABLE_NAME);
        int sid = 0;
        try {
            while (c.moveToNext()) {
                sid = c.getInt(c.getColumnIndexOrThrow(SelectEvent.COLUMN_NAME_EID));
            }
            Log.d("EventImpl>>","getSelectEvent eid-> "  + sid);
            return sid;
        }catch (CursorIndexOutOfBoundsException e){
            return -1;
        }
    }

    @Override
    public List<Event_Impl> getEventList() {

        String[] cols = {EventsTable.EVENT_ID,EventsTable.COLUMN_NAME_EVENTNAME,EventsTable.COLUMN_NAME_DATE,EventsTable.COLUMN_NAME_TIME};
        try {
            Cursor c = db.readAllIgnoreArgs(cols, EventsTable.TABLENAME);
            List<Event_Impl> b = new ArrayList<>();
            Event_Impl ib;
            while(c.moveToNext()){
                ib = new Event_Impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow(EventsTable.EVENT_ID));//int
                ib.ename = c.getString(c.getColumnIndexOrThrow(EventsTable.COLUMN_NAME_EVENTNAME));
                ib.edate = c.getString(c.getColumnIndexOrThrow(EventsTable.COLUMN_NAME_DATE));
                ib.etime = c.getString(c.getColumnIndexOrThrow(EventsTable.COLUMN_NAME_TIME));
                b.add(ib);
            }
            return b;
        }catch (NullPointerException np){
            Log.d(" Event_Impl>>","NPE " + np.getMessage());
            return null;
        }
    }


    @Override
    public Event_Impl getEventById(int id) {
        String[] cols = {EventsTable.EVENT_ID,EventsTable.COLUMN_NAME_EVENTNAME,EventsTable.COLUMN_NAME_DATE,EventsTable.COLUMN_NAME_TIME};

        try {
            Cursor c = db.readWithWhere(cols,EventsTable.TABLENAME,EventsTable.EVENT_ID,"" + id);
            List<Event_Impl> b = new ArrayList<>();
            Event_Impl ib = new Event_Impl(this.c);
            while(c.moveToNext()){

                ib.id = c.getInt(c.getColumnIndexOrThrow(EventsTable.EVENT_ID));//int
                ib.ename = c.getString(c.getColumnIndexOrThrow(EventsTable.COLUMN_NAME_EVENTNAME));
                ib.edate = c.getString(c.getColumnIndexOrThrow(EventsTable.COLUMN_NAME_DATE));
                ib.etime = c.getString(c.getColumnIndexOrThrow(EventsTable.COLUMN_NAME_TIME));


                /*Log.d("Budget_Impl>>","================ Printing Read Values ================");
                Log.d("id -> ",""+ib.id);
                Log.d("name -> ",ib.name);
                Log.d("cat -> ",ib.cat);
                Log.d("amt -> ",ib.amt);
                Log.d("desc -> ",ib.desc);*/
            }
            return ib;
        }catch (NullPointerException np){
            Log.d("Event_Impl>>","NPE " + np.getMessage());
            return null;
        }

    }

    @Override
    public void removeEvent(int eventID) {
        Log.d("EventImpl>>","RemoveActiveEvent -> "  +ename);
        String[] idValue = {"" + eventID};
        db.delete(EventsTable.EVENT_ID,idValue,EventsTable.TABLENAME);
        Log.d("Delete>>","del= "+eventID);
        //changed
        //will delete all data mapped to event
        try{
            //budget
            Budget_Impl_updated budget = new Budget_Impl_updated(c,eventID);
            for(Budget_Impl_updated b : budget.getBudgetList()){
                b.removeBudget(b.id);
            }
            //tasks
            Task_Impl task = new Task_Impl(c,eventID);
            for(Task_Impl t : task.getTaskList()){
                t.removeTask(t.id);
            }
            //vendors
            Vendor_impl vendor = new Vendor_impl(c,eventID);
            for(Vendor_impl v : vendor.getVendor()){
                v.removeVendor(v.id);
            }
            //guests
            Guest_Impl guest = new Guest_Impl(c,eventID);
            for(Guest_Impl g : guest.getGuestList()){
                g.removeGuest(g.id);
            }
        }catch (SQLiteException e){
            //skip
        }
    }

    @Override
    public void updateEvent(String[] colNamesToUpdate, String[] values) {

        ContentValues cv = new ContentValues();
        for(int col=0;col<colNamesToUpdate.length;col++){
            cv.put(colNamesToUpdate[col],values[col]);

        }
        String id2Str = "" + this.id;
        db.update(cv,EventsTable.EVENT_ID,id2Str,table.TABLENAME);

    }

    @Override
    public void updateEvent(Event_Impl obj) {

        String[] cols = {EventsTable.EVENT_ID,table.COLUMN_NAME_EVENTNAME,table.COLUMN_NAME_DATE,table.COLUMN_NAME_TIME};
        String[] v = {String.valueOf(obj.id),obj.ename,obj.edate,obj.etime};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Guest_Impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        String id2Str = "" + obj.id;
        db.update(cv,EventsTable.EVENT_ID,id2Str,table.TABLENAME);//update using id

    }



}
