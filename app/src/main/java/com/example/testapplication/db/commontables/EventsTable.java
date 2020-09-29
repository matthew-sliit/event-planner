package com.example.testapplication.db.commontables;

public class EventsTable {
    public static final String TABLENAME= "EventTableTest1";
    public static final String EVENT_ID = "EventID";
    public static final String COLUMN_NAME_EVENTNAME = "Event_Name";
    public static final String COLUMN_NAME_DATE = "Event_Date";
    public static final String COLUMN_NAME_TIME = "Event_Time";
    public String getIfNotExistsStatement(){
        return "CREATE TABLE IF NOT EXISTS " + TABLENAME + " (" +
                EVENT_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_EVENTNAME + " TEXT,"+
                COLUMN_NAME_DATE + " DATE," +
                COLUMN_NAME_TIME + " DATE)";
    }
}
