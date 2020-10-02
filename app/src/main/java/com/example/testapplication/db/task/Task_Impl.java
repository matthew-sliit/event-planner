package com.example.testapplication.db.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import com.example.testapplication.constants.TableNames;
import com.example.testapplication.db.DBHandler;
import com.example.testapplication.db.budget.Budget_Impl_updated;
import com.example.testapplication.db.commontables.EventsTable;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task_Impl implements ITask {
    private class Task_table {
        public Task_table(){}
        public static final String TABLE_TASK="taskTableTest";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_EID= EventsTable.EVENT_ID;
        public static final String COLUMN_NAME_TASKNAME="tname";
        public static final String COLUMN_NAME_CAT="category";
        public static final String COLUMN_NAME_DESC="description";
        public static final String COLUMN_NAME_STATUS="status";
        public static final String COLUMN_NAME_DATE="tdate";

        public String getIfNotExistStatement(){
            return "CREATE TABLE if not exists " + TABLE_TASK+ " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"+
                    COLUMN_NAME_EID + " INTEGER REFERENCES " + EventsTable.TABLENAME + " ON DELETE CASCADE ON UPDATE CASCADE," +
                    COLUMN_NAME_TASKNAME + " TEXT,"+
                    COLUMN_NAME_CAT+" TEXT,"+
                    COLUMN_NAME_DESC+" TEXT,"+
                    COLUMN_NAME_STATUS+" TEXT,"+
                    COLUMN_NAME_DATE+" TEXT )";
        }
    }

    //Class variables
    public String tname,status="Pending",description,category,tdate;
    public int eid = 0 , id = 0;
    private DBHandler db;
    private Context c;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return tdate;
    }

    public void setDate(String tdate) {
        this.tdate = tdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Task_table table = new Task_table();
    public Task_Impl(Context c, int eid) {
        if(c == null){
            Log.d("Task_Impl>>","context is null!");
        }else{
            Log.d("Task_Impl>>","context is NOT null!");
        }
        db = new DBHandler(c,table.getIfNotExistStatement());
        this.c = c;
        this.eid = eid;
    }
    @Override
    public void addTask(String tname, String category, String description, String status, String date) {
        ContentValues cv= new ContentValues();
        cv.put(Task_table.COLUMN_NAME_EID, eid);
        cv.put(Task_table.COLUMN_NAME_TASKNAME, tname);
        cv.put(Task_table.COLUMN_NAME_CAT, category);
        cv.put(Task_table.COLUMN_NAME_DESC, description);
        cv.put(Task_table.COLUMN_NAME_STATUS, status);
        cv.put(Task_table.COLUMN_NAME_DATE, date);
        db.insert(cv,Task_table.TABLE_TASK);
    }

    @Override
    public void addTask() {
        Log.d("TaskImpl>>","eid -> " + eid);
        ContentValues cv= new ContentValues();
        cv.put(Task_table.COLUMN_NAME_EID, eid);
        cv.put(Task_table.COLUMN_NAME_TASKNAME, tname);
        cv.put(Task_table.COLUMN_NAME_CAT, category);
        cv.put(Task_table.COLUMN_NAME_DESC, description);
        cv.put(Task_table.COLUMN_NAME_STATUS, status);
        cv.put(Task_table.COLUMN_NAME_DATE, tdate);
        db.insert(cv,Task_table.TABLE_TASK);
    }

    @Override
    public List<Task_Impl> getTaskList() {

        String[] cols = {Task_table.COLUMN_NAME_ID,Task_table.COLUMN_NAME_EID,table.COLUMN_NAME_TASKNAME,table.COLUMN_NAME_CAT,table.COLUMN_NAME_DESC,table.COLUMN_NAME_STATUS,table.COLUMN_NAME_DATE};
        try {
            Cursor c = db.readAllWitSelection(cols, table.TABLE_TASK,getWhereEidStatement());
            List<Task_Impl> b = new ArrayList<>();
            Task_Impl ib;
            while(c.moveToNext()){
                ib = new Task_Impl(this.c, eid);
                ib.id = c.getInt(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_ID));//int
                ib.eid = c.getInt(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_EID));//int
                ib.tname = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_TASKNAME));
                ib.category = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_CAT));
                ib.description = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_DESC));
                ib.status = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_STATUS));
                ib.tdate = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_DATE));
                b.add(ib);
            }
            return b;
        }catch (NullPointerException np){
            Log.d(" Task_Impl>>","NPE " + np.getMessage());
            return null;
        }
    }

    @Override
    public Task_Impl getTaskById(int eid, int id) {
        String[] cols = {Task_table.COLUMN_NAME_ID,Task_table.COLUMN_NAME_EID,table.COLUMN_NAME_TASKNAME,Task_table.COLUMN_NAME_EID,table.COLUMN_NAME_CAT,table.COLUMN_NAME_DESC,table.COLUMN_NAME_STATUS,table.COLUMN_NAME_DATE};
        try {
            Cursor c = db.readAllWitSelection(cols,table.TABLE_TASK,getWhereEidaBidStatement(eid,id));
            //List<Task_Impl> b = new ArrayList<>();
            Task_Impl ib = new Task_Impl(this.c, eid);
            while(c.moveToNext()){
                ib.id = c.getInt(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_ID));//int
                ib.eid = c.getInt(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_EID));//int
                ib.tname = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_TASKNAME));
                ib.category = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_CAT));
                ib.description = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_DESC));
                ib.status = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_STATUS));
                ib.tdate = c.getString(c.getColumnIndexOrThrow(Task_table.COLUMN_NAME_DATE));
                Log.d("Budget_Impl>>","================ Printing Read Values ================");
                Log.d("id -> ",""+ib.id);
                Log.d("name -> ",""+ib.tname);
                Log.d("cat -> ",""+ib.category);
                Log.d("amt -> ",""+ib.status);
                Log.d("desc -> ",""+ib.tdate);
            }
            return ib;
        }catch (CursorIndexOutOfBoundsException np){
            Log.d("Task_Impl>>","NPE " + np.getMessage());
            return null;
        }catch (NullPointerException e){
            Log.d("Task_Impl>>","NPE " + e.getMessage());
            return null;
        }

    }

    @Override
    public void removeTask(int taskID) {
        db.delete(table.TABLE_TASK,getWhereEidaBidStatement(taskID),null);
        Log.d("Delete>>","del= "+taskID);
    }

    @Override
    public void updateTask(String[] colNamesToUpdate, String[] values) {

        ContentValues cv = new ContentValues();
        for(int col=0;col<colNamesToUpdate.length;col++){
            cv.put(colNamesToUpdate[col],values[col]);

        }
        String id2Str = "" + this.id;
        db.update(cv,Task_table.COLUMN_NAME_ID,id2Str,table.TABLE_TASK);

    }

    @Override
    public void updateTask(Task_Impl obj) {

        String[] cols = {Task_table.COLUMN_NAME_ID,table.COLUMN_NAME_TASKNAME,table.COLUMN_NAME_CAT,table.COLUMN_NAME_DESC,table.COLUMN_NAME_STATUS,table.COLUMN_NAME_DATE};
        String[] v = {String.valueOf(obj.id),obj.tname,obj.category,obj.description,obj.status,obj.tdate};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Guest_Impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        db.update(cv,getUpdateWhere(obj.eid,obj.id),table.TABLE_TASK);//update using id

    }
    private String getWhereEidStatement(){
        return Task_table.COLUMN_NAME_EID + " LIKE " + eid;
    }
    private String getWhereEidaBidStatement(int bid){
        return Task_table.COLUMN_NAME_EID + " LIKE " + eid + " AND " + Task_table.COLUMN_NAME_ID + " LIKE " + bid;
    }
    private String getWhereEidaBidStatement(int eid, int tid){
        return Task_table.COLUMN_NAME_EID + " LIKE " + eid + " AND " + Task_table.COLUMN_NAME_ID + " LIKE " + tid;
    }

    private String getUpdateWhere(int eid_, int tid_){
        return Task_table.COLUMN_NAME_EID + " LIKE " + eid_ +
                " AND " + Task_table.COLUMN_NAME_ID + " LIKE " + tid_;
    }
}
