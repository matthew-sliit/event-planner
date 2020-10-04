package com.example.testapplication.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class DBHandler extends SQLiteOpenHelper {
    public static final boolean debugger_mode = true;
    public static final String DB_NAME = "EventPlans.sqlite";
    public Context context = null;
    public static final String DBPath = Environment.getDataDirectory().getName()+"//data//com.example.eventplan//databases//";
    public DBHandler(Context context, String SQL_CREATE_ENTRIES){
        super(context,DB_NAME,null,1);
        this.SQL_CREATE_ENTRIES = SQL_CREATE_ENTRIES;
        this.context = context;
        if(context == null){
            debug("context is null!");
        }else {
            debug("context is NOT null!");
            db = this.context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        }
        try{
            String myPath = DBPath +  DB_NAME;
            File dbfile = new File(myPath);
            if( dbfile.exists()){
                debug("DB Exists!");
                try{
                    db.execSQL(SQL_CREATE_ENTRIES);//getIfNotExists
                }catch (SQLiteException e){
                    //?
                }
            }
            else{
                //this.onCreate(db);
                //db = openOrCreateDatabase(DB_NAME,null);
                debug("DB DOES NOT Exist!");
            }
        }
        catch(SQLiteException e){
            System.out.println("Database doesn't exist");
        }
    }
    public String SQL_CREATE_ENTRIES = null;
    public SQLiteDatabase db = null;
    @Override
    public void onCreate(SQLiteDatabase db) {
        debug("Running OnCreate!");
        //if(!(SQL_CREATE_ENTRIES == null)) {
      //  EventsTable et = new EventsTable();
        //db.execSQL(et.getIfNotExistsStatement());

        db.execSQL(SQL_CREATE_ENTRIES);
        //}
    }
    public void close() {
        this.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(ContentValues values, String tableName){
        debug("Inserting into " + tableName);
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = db.insert(tableName, null, values);
    }
    public long insertGetId(ContentValues values, String tableName){
        debug("Inserting into " + tableName);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(tableName, null, values);
    }
    public Cursor readAllIgnoreArgs(String[] columns, String tableName){
        debug("Reading from " + tableName);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName,columns,null,null,null,null, null);
        return cursor;
    }
    public Cursor readAllWitSelection(String[] columns, String tableName, String selection){
        debug("Reading from " + tableName);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName,columns,selection,null,null,null, null);
        return cursor;
    }
    public Cursor readWithWhere(String[] columns, String tableName,String selectColumn, String whereColumnValue){
        debug("Reading from " + tableName);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName,columns,selectColumn + " like " + whereColumnValue,null,null,null, null);
        return cursor;
    }
    public void delete(String columnName, String[] values, String tableName){
        debug("Deleting from " + tableName + " where colName:" + columnName);
        SQLiteDatabase db = getReadableDatabase();
        String selection = columnName + " LIKE ?";
        //String[] selectionArgs = {tupleValue};
        db.delete(tableName,selection,values);
    }
    public void delete(String tableName, String whereClause, String whereArgs[]){
        SQLiteDatabase db = getReadableDatabase();
        db.delete(tableName,whereClause,whereArgs);
    }
    public int update(ContentValues cv, String columnName, String id, String tableName){
        debug("Updating " + tableName + " using id=" + id + " colName=" + columnName);
        SQLiteDatabase db = getReadableDatabase();
        String selectRecordUsingColumn = columnName + " LIKE ?";
        String[] record_id = {id};
        return db.update(tableName,cv,selectRecordUsingColumn,record_id);
    }
    public int update(ContentValues cv, String selection, String tableName){
        debug("Updating " + tableName + " selection -> " + selection);
        SQLiteDatabase db = getReadableDatabase();
        return db.update(tableName,cv,selection,null);
    }
    private void debug(String msg){
        if(debugger_mode) {
            Log.d("DBHandler>>", msg);
        }
    }

}
