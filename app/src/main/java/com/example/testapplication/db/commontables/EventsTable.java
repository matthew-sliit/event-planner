package com.example.testapplication.db.commontables;

public class EventsTable {
    public EventsTable(){}
    public static final String TABLE_EVENT="eventsTable";
    public static final String COLUMN_NAME_ID="eid";
    public static final String COLUMN_NAME_EVENTNAME="ename";
    public static final String COLUMN_NAME_DATE="edate";
    public static final String COLUMN_NAME_TIME="etime";



    public String getTableCreator(){
        return "CREATE TABLE " + TABLE_EVENT+ " (" +
                COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"+
                COLUMN_NAME_EVENTNAME + " TEXT,"+
                COLUMN_NAME_DATE+" TEXT, "+
                COLUMN_NAME_TIME+" TEXT)";


    }
    public String getIfNotExistStatement(){
        return "CREATE TABLE if not exists " + TABLE_EVENT+ " (" +
                COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"+
                COLUMN_NAME_EVENTNAME + " TEXT,"+
                COLUMN_NAME_DATE+" TEXT,"+
                COLUMN_NAME_TIME+" TEXT )" ;

    }
}
