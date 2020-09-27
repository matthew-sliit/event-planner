package com.example.testapplication.db.event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import com.example.testapplication.db.DBHandler;
import com.example.testapplication.db.commontables.EventsTable;
import com.example.testapplication.db.commontables.SelectEvent;


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
        if(c == null){
            Log.d("Event_Impl>>","context is null!");
        }else{
            Log.d("Event_Impl>>","context is NOT null!");
        }
        db = new DBHandler(c,table.getIfNotExistStatement());
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
        db.insert(cv,table.TABLE_EVENT);
    }

    @Override
    public void selectEvent(Event_Impl obj) {
        String[] cols = {SelectEvent.COLUMN_NAME_ID,SelectEvent.COLUMN_NAME_EVENTNAME};
        Cursor c = selectdb.readAllIgnoreArgs(cols,SelectEvent.TABLE_NAME);
        try {


            int sid = c.getInt(c.getColumnIndexOrThrow(SelectEvent.COLUMN_NAME_ID));
            String sName = c.getString(c.getColumnIndexOrThrow(SelectEvent.COLUMN_NAME_EVENTNAME));
            if (!sName.equalsIgnoreCase(obj.ename)) {
                ContentValues cv = new ContentValues();
                cv.put(SelectEvent.COLUMN_NAME_EID, obj.id);
                cv.put(SelectEvent.COLUMN_NAME_EVENTNAME, obj.ename);

                selectdb.update(cv, SelectEvent.COLUMN_NAME_ID, String.valueOf(sid), SelectEvent.TABLE_NAME);

            }
        }catch (CursorIndexOutOfBoundsException e){
            ContentValues cv = new ContentValues();
            cv.put(SelectEvent.COLUMN_NAME_EID, obj.id);
            cv.put(SelectEvent.COLUMN_NAME_EVENTNAME, obj.ename);
            selectdb.insert(cv,SelectEvent.TABLE_NAME);

        }


    }

    public int getSelectEvent() {
        String[] cols = {SelectEvent.COLUMN_NAME_EID};
        Cursor c = selectdb.readAllIgnoreArgs(cols, SelectEvent.TABLE_NAME);
        try {
            return c.getInt(c.getColumnIndexOrThrow(SelectEvent.COLUMN_NAME_EID));

        }catch (CursorIndexOutOfBoundsException e){
            return -1;
        }
    }

    @Override
    public List<Event_Impl> getEventList() {

        String[] cols = {"eid",table.COLUMN_NAME_EVENTNAME,table.COLUMN_NAME_DATE,table.COLUMN_NAME_TIME};
        try {
            Cursor c = db.readAllIgnoreArgs(cols, table.TABLE_EVENT);
            List<Event_Impl> b = new ArrayList<>();
            Event_Impl ib;
            while(c.moveToNext()){
                ib = new Event_Impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow("eid"));//int
                ib.ename = c.getString(c.getColumnIndexOrThrow("ename"));
                ib.edate = c.getString(c.getColumnIndexOrThrow("edate"));
                ib.etime = c.getString(c.getColumnIndexOrThrow("etime"));

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
        String[] cols = {"eid",table.COLUMN_NAME_EVENTNAME,table.COLUMN_NAME_DATE,table.COLUMN_NAME_TIME};

        try {
            Cursor c = db.readWithWhere(cols,table.TABLE_EVENT,"eid","" + id);
            List<Event_Impl> b = new ArrayList<>();
            Event_Impl ib = new Event_Impl(this.c);
            while(c.moveToNext()){

                ib.id = c.getInt(c.getColumnIndexOrThrow("eid"));//int
                ib.ename = c.getString(c.getColumnIndexOrThrow("ename"));
                ib.edate = c.getString(c.getColumnIndexOrThrow("edate"));
                ib.etime = c.getString(c.getColumnIndexOrThrow("etime"));


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
        String[] idValue = {"" + eventID};
        db.delete("eid",idValue,table.TABLE_EVENT);
        Log.d("Delete>>","del= "+eventID);

    }

    @Override
    public void updateEvent(String[] colNamesToUpdate, String[] values) {

        ContentValues cv = new ContentValues();
        for(int col=0;col<colNamesToUpdate.length;col++){
            cv.put(colNamesToUpdate[col],values[col]);

        }
        String id2Str = "" + this.id;
        db.update(cv,"eid",id2Str,table.TABLE_EVENT);

    }

    @Override
    public void updateEvent(Event_Impl obj) {

        String[] cols = {"eid",table.COLUMN_NAME_EVENTNAME,table.COLUMN_NAME_DATE,table.COLUMN_NAME_TIME};
        String[] v = {String.valueOf(obj.id),obj.ename,obj.edate,obj.etime};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Guest_Impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        String id2Str = "" + obj.id;
        db.update(cv,"eid",id2Str,table.TABLE_EVENT);//update using id

    }



}
