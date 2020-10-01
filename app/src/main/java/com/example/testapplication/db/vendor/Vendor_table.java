package com.example.testapplication.db.vendor;

public class Vendor_table{
    public static final String TABLENAME="VendorTable";
    public static final String NAME="Name";
    public static final String ID="id";
    public static final String CATEGORY="Category";
    public static final String AMOUNT="Amount";
    public static final String NUMBER="Number";
    public static final String ADDRESS="Address";
    public static final String EMAIL="Email";



    public Vendor_table() {
    }

    public String getTableCreator(){

        return "CREATE TABLE if not exists " + TABLENAME + " (" +
                ID + " integer primary key,"+
                NAME+" TEXT,"+
                CATEGORY+" TEXT,"+
                AMOUNT+" DOUBLE,"+
                NUMBER+" TEXT,"+
                ADDRESS+" TEXT,"+
                EMAIL+" TEXT)";


    }


}

