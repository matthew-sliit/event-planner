package com.example.testapplication.db.commontables;

import com.example.testapplication.db.commontables.EventsTable;

public class PaymentTable {
    //columns in this table
    public String tableName = "Payments";//?
    public static final String EVENT_ID = "Eid"; //event id
    public static final String ID = "Pid";//table pk
    public String REF_ID = "Rid"; //Budget or Vendor id
    public static final String NAME = "Name";
    public static final String AMOUNT = "Amount";
    public static final String STATUS = "Status";

    public static final String RECEIVED_DATE = "Paid Date";
    //get from budget or vendor table
    public String REF_TABLE_ID = "Rid";
    public String REF_TABLENAME = "Rname";
    //get from event table
    public String EVENT_TABLENAME="Events";
    public String EVENT_TABLE_ID="id";
    //constructor
    public PaymentTable(String setTableName, String setTablePK, String setRefTableName, String setRefTableId){
        this.tableName = setTableName;
        this.REF_ID = setTablePK;
        EventsTable et = new EventsTable();
        this.EVENT_TABLE_ID = et.EVENT_ID;
        this.EVENT_TABLENAME = et.TABLENAME;
        this.REF_TABLE_ID = setRefTableId;
        this.REF_TABLENAME = setRefTableName;
    }
    public String getIfNotExistStatement() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                EVENT_ID + " TEXT foreign key references "+EVENT_TABLENAME+"("+EVENT_TABLE_ID+"),"+
                REF_ID + " TEXT foreign key references "+REF_TABLENAME+"("+REF_TABLE_ID+"),"+
                NAME + " TEXT," +
                AMOUNT + " TEXT," +
                STATUS + " DECIMAL," +
                RECEIVED_DATE + " TEXT)";
    }
}
