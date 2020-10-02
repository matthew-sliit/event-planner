package com.example.testapplication.db.commontables;

import com.example.testapplication.constants.TableNames;

public class SelectEvent {

    public static final String TABLE_NAME= TableNames.SelectEventTable;
    public static final String COLUMN_NAME_EID=EventsTable.EVENT_ID;
    public static final String COLUMN_NAME_ID="id";
    public static final String Event_Tablename=EventsTable.TABLENAME;
    public static final String COLUMN_NAME_EVENTNAME=EventsTable.COLUMN_NAME_EVENTNAME;

    public static String getIfNotExistStatement(){
        return "CREATE TABLE if not exists " + TABLE_NAME+ " (" +
                COLUMN_NAME_ID + " INTEGER PRIMARY KEY ," +
                COLUMN_NAME_EID + " INTEGER REFERENCES "+Event_Tablename+" on delete cascade on update cascade,"+
                COLUMN_NAME_EVENTNAME + " TEXT REFERENCES "+Event_Tablename+" on delete cascade on update cascade)";
    }

}
