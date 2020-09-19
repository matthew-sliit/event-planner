package com.example.testapplication.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class DBHandler extends SQLiteOpenHelper {
    public static final String DB_NAME = "EventPlans.sqlite";
    public Context context = null;
    public static final String DBPath = Environment.getDataDirectory().getName()+"//data//com.example.testapplication//databases//";
    public DBHandler(Context context, String SQL_CREATE_ENTRIES){
        super(context,DB_NAME,null,1);
        this.SQL_CREATE_ENTRIES = SQL_CREATE_ENTRIES;
        this.context = context;
        db = context.openOrCreateDatabase(DB_NAME,MODE_PRIVATE,null);
        try{
            String myPath = DBPath +  DB_NAME;
            File dbfile = new File(myPath);
            if( dbfile.exists()){
                Log.d("DBHandler>>","DB Exists!");
            }
            else{
                //this.onCreate(db);
                //db = openOrCreateDatabase(DB_NAME,null);
                Log.d("DBHandler>>","DB DOES NOT Exist!");
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
        Log.d("DBHandler>>","Running OnCreate!");
        //if(!(SQL_CREATE_ENTRIES == null)) {
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
        Log.d("DBHandler>>","Inserting into " + tableName);
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = db.insert(tableName, null, values);
    }
    public Cursor readAllIgnoreArgs(String[] columns, String tableName){
        Log.d("DBHandler>>","Reading from " + tableName);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName,columns,null,null,null,null, null);
        return cursor;
    }
    public void delete(String columnName, String[] values, String tableName){
        Log.d("DBHandler>>","Deleting from " + tableName + " where colName:" + columnName);
        SQLiteDatabase db = getReadableDatabase();
        String selection = columnName + " LIKE ?";
        //String[] selectionArgs = {tupleValue};
        db.delete(tableName,selection,values);
    }
    public int update(ContentValues cv, String columnName, String id, String tableName){
        Log.d("DBHandler>>","Updating " + tableName + " using id=" + id + " colName=" + columnName);
        SQLiteDatabase db = getReadableDatabase();
        String selectRecordUsingColumn = columnName + " LIKE ?";
        String[] record_id = {id};
        return db.update(tableName,cv,selectRecordUsingColumn,record_id);
    }

}
