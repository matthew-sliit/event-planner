package com.example.testapplication.db.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.testapplication.db.DBHandler;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task_Impl implements ITask {
    private class Task_table {
        public Task_table(){}
        public static final String TABLE_TASK="taskTable";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_TASKNAME="tname";
        public static final String COLUMN_NAME_CAT="category";
        public static final String COLUMN_NAME_DESC="description";
        public static final String COLUMN_NAME_STATUS="status";
        public static final String COLUMN_NAME_DATE="tdate";


        public String getTableCreator(){
            return "CREATE TABLE " + TABLE_TASK+ " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"+
                    COLUMN_NAME_TASKNAME + " TEXT,"+
                    COLUMN_NAME_CAT+" TEXT,"+
                    COLUMN_NAME_DESC+" TEXT,"+
                    COLUMN_NAME_STATUS+" TEXT,"+
                    COLUMN_NAME_DATE+" TEXT )";

        }
        public String getIfNotExistStatement(){
            return "CREATE TABLE " + TABLE_TASK+ " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"+
                    COLUMN_NAME_TASKNAME + " TEXT,"+
                    COLUMN_NAME_CAT+" TEXT,"+
                    COLUMN_NAME_DESC+" TEXT,"+
                    COLUMN_NAME_STATUS+" TEXT,"+
                    COLUMN_NAME_DATE+" TEXT )";
        }
    }

    //Class variables
    public String tname,status="Pending",description,category,tdate;
    public int id = 0;
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

    private Task_Impl.Task_table table = new Task_Impl.Task_table();
    public Task_Impl(Context c) {
        if(c == null){
            Log.d("Task_Impl>>","context is null!");
        }else{
            Log.d("Task_Impl>>","context is NOT null!");
        }
        db = new DBHandler(c,table.getIfNotExistStatement());
        this.c = c;
    }



    @Override
    public void addTask(String tname, String category, String description, String status, String date) {

        ContentValues cv= new ContentValues();
        cv.put(table.COLUMN_NAME_TASKNAME, tname);
        cv.put(table.COLUMN_NAME_CAT, category);
        cv.put(table.COLUMN_NAME_DESC, description);
        cv.put(table.COLUMN_NAME_STATUS, status);
        cv.put(table.COLUMN_NAME_DATE, date);
        db.insert(cv,table.TABLE_TASK);
    }




    @Override
    public void addTask() {

        ContentValues cv= new ContentValues();
        cv.put(table.COLUMN_NAME_TASKNAME, tname);
        cv.put(table.COLUMN_NAME_CAT, category);
        cv.put(table.COLUMN_NAME_DESC, description);
        cv.put(table.COLUMN_NAME_STATUS, status);
        cv.put(table.COLUMN_NAME_DATE, tdate);
        db.insert(cv,table.TABLE_TASK);

    }

    @Override
    public List<Task_Impl> getTaskList() {

        String[] cols = {"id",table.COLUMN_NAME_TASKNAME,table.COLUMN_NAME_CAT,table.COLUMN_NAME_DESC,table.COLUMN_NAME_STATUS,table.COLUMN_NAME_DATE};
        try {
            Cursor c = db.readAllIgnoreArgs(cols, table.TABLE_TASK);
            List<Task_Impl> b = new ArrayList<>();
            Task_Impl ib;
            while(c.moveToNext()){
                ib = new Task_Impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                ib.tname = c.getString(c.getColumnIndexOrThrow("tname"));
                ib.category = c.getString(c.getColumnIndexOrThrow("category"));
                ib.description = c.getString(c.getColumnIndexOrThrow("description"));
                ib.status = c.getString(c.getColumnIndexOrThrow("status"));
                ib.tdate = c.getString(c.getColumnIndexOrThrow("tdate"));
                b.add(ib);
            }
            return b;
        }catch (NullPointerException np){
            Log.d(" Task_Impl>>","NPE " + np.getMessage());
            return null;
        }
    }

    @Override
    public Task_Impl getTaskById(int id) {
        String[] cols = {"id",table.COLUMN_NAME_TASKNAME,table.COLUMN_NAME_CAT,table.COLUMN_NAME_DESC,table.COLUMN_NAME_STATUS,table.COLUMN_NAME_DATE};

        try {
            Cursor c = db.readWithWhere(cols,table.TABLE_TASK,"id","" + id);
            List<Task_Impl> b = new ArrayList<>();
            Task_Impl ib = new Task_Impl(this.c);
            while(c.moveToNext()){

                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                ib.tname = c.getString(c.getColumnIndexOrThrow("tname"));
                ib.category = c.getString(c.getColumnIndexOrThrow("category"));
                ib.description = c.getString(c.getColumnIndexOrThrow("description"));
                ib.status = c.getString(c.getColumnIndexOrThrow("status"));
                ib.tdate = c.getString(c.getColumnIndexOrThrow("tdate"));



                /*Log.d("Budget_Impl>>","================ Printing Read Values ================");
                Log.d("id -> ",""+ib.id);
                Log.d("name -> ",ib.name);
                Log.d("cat -> ",ib.cat);
                Log.d("amt -> ",ib.amt);
                Log.d("desc -> ",ib.desc);*/
            }
            return ib;
        }catch (NullPointerException np){
            Log.d("Task_Impl>>","NPE " + np.getMessage());
            return null;
        }

    }

    @Override
    public void removeTask(int taskID) {
        String[] idValue = {"" + taskID};
        db.delete("id",idValue,table.TABLE_TASK);
        Log.d("Delete>>","del= "+taskID);

    }

    @Override
    public void updateTask(String[] colNamesToUpdate, String[] values) {

        ContentValues cv = new ContentValues();
        for(int col=0;col<colNamesToUpdate.length;col++){
            cv.put(colNamesToUpdate[col],values[col]);

        }
        String id2Str = "" + this.id;
        db.update(cv,"id",id2Str,table.TABLE_TASK);

    }

    @Override
    public void updateTask(Task_Impl obj) {

        String[] cols = {"id",table.COLUMN_NAME_TASKNAME,table.COLUMN_NAME_CAT,table.COLUMN_NAME_DESC,table.COLUMN_NAME_STATUS,table.COLUMN_NAME_DATE};
        String[] v = {obj.tname,obj.category,obj.description,obj.status,obj.tdate};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Guest_Impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        String id2Str = "" + obj.id;
        db.update(cv,"id",id2Str,table.TABLE_TASK);//update using id

    }
}
