package com.example.testapplication.db.commontables;

import com.example.testapplication.db.commontables.EventsTable;

public class PaymentTable {
    //columns in this table
    public String tableName = "Payments";//?
    public static final String ID = "Pid";//table pk
    public static final String NAME = "Name";
    public static final String AMOUNT = "Amount";
    public static final String STATUS = "Status";

    public static final String RECEIVED_DATE = "Paid_Date";
    //get from budget or vendor table
    public String REF_ID = "Rid";
    public String REF_TABLENAME = "Rname";
    //get from event table
    public String EVENT_TABLENAME="Events";
    public String EVENT_TABLE_ID="id";
    //constructor
    public PaymentTable(String setTableName, String setTablePK, String setRefTableName){
        this.tableName = setTableName;
        this.REF_ID = setTablePK;
        EventsTable et = new EventsTable();
        this.EVENT_TABLE_ID = EventsTable.EVENT_ID;
        this.EVENT_TABLENAME = EventsTable.TABLENAME;
        this.REF_TABLENAME = setRefTableName;
    }
    public String getIfNotExistStatement() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                EVENT_TABLE_ID + " TEXT references "+EVENT_TABLENAME + " ON DELETE CASCADE ON UPDATE CASCADE,"+
                REF_ID + " TEXT references "+REF_TABLENAME+ "(id) ON DELETE CASCADE ON UPDATE CASCADE,"+
                NAME + " TEXT," +
                AMOUNT + " DECIMAL," +
                STATUS + " String," +
                RECEIVED_DATE + " DATE)";
    }
    public String[] getIOColumns(){
        String[] cols = {NAME,AMOUNT,STATUS,RECEIVED_DATE,REF_ID,EVENT_TABLE_ID};
        return cols;
    }
    public String[] getAllColumns(){
        String[] cols = {ID,EVENT_TABLE_ID,REF_ID,NAME,AMOUNT,STATUS,RECEIVED_DATE};
        return cols;
    }
}
