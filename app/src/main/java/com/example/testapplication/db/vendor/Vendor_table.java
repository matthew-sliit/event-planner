package com.example.testapplication.db.vendor;

import com.example.testapplication.constants.TableNames;
import com.example.testapplication.db.category.Category;
import com.example.testapplication.db.commontables.CategoryTable;
import com.example.testapplication.db.commontables.EventsTable;

public class Vendor_table{
    public static final String TABLENAME= TableNames.VendorTable;
    public static final String NAME="Name";
    public static final String ID="id";
    public static final String EID=EventsTable.EVENT_ID;
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
                EID + " integer references "+ EventsTable.TABLENAME + " on delete cascade on update cascade," +
                NAME+" TEXT,"+
                CATEGORY+" TEXT,"+
                //CATEGORY+" TEXT references "+ CategoryTable.TABLE_NAME + " ("+CategoryTable.COL_NAME_CAT+") on delete cascade on update cascade," +
                AMOUNT+" DOUBLE,"+
                NUMBER+" TEXT,"+
                ADDRESS+" TEXT,"+
                EMAIL+" TEXT)";

    }


}

