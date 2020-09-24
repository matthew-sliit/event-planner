package com.example.testapplication.db.commontables;

public class EventsTable {
    public static final String TABLENAME= "Events";
    public static final String EVENT_ID = "EventID";

    public String getIfNotExistsStatement(){
        return "";
    }
}
