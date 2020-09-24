package com.example.testapplication.db.commontables;

public class EventsTable {
    public static final String TABLENAME= "Events";
    public static final String EVENT_ID = "EventID";
    public static final String NAME = "EventID";
    public static final String DUE_DATE = "EventID";
    public static final String CREATED = "EventID";
    public String getIfNotExistsStatement(){
        return "CREATE TABLE IF NOT EXISTS " + TABLENAME + " (" +
                EVENT_ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT,"+
                DUE_DATE + " DATE," +
                CREATED + " DATE)";
    }
}
