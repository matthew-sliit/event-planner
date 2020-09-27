package com.example.testapplication.db.commontables;

public class EventsTable {
    public static final String TABLENAME= "EventTableTest";
    public static final String EVENT_ID = "EventID";
    public static final String NAME = "Event_Name";
    public static final String DUE_DATE = "Event_Due_Date";
    public static final String CREATED = "Event_Created_Date";
    public String getIfNotExistsStatement(){
        return "CREATE TABLE IF NOT EXISTS " + TABLENAME + " (" +
                EVENT_ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT,"+
                DUE_DATE + " DATE," +
                CREATED + " DATE)";
    }
}
