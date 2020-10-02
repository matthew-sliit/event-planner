package com.example.testapplication.db.guest;

import com.example.testapplication.constants.TableNames;
import com.example.testapplication.db.commontables.EventsTable;

public class Guest_table {
        public Guest_table(){}
        public static final String TABLE_GUESTS= TableNames.GuestTable;
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_EID="eid";
        public static final String COLUMN_NAME_GUESTNAME="guestname";
        public static final String COLUMN_NAME_GENDER="gender";
        public static final String COLUMN_NAME_AGE="age";
        public static final String COLUMN_NAME_INVITATION="invitation";
        public static final String COLUMN_NAME_PHONE="phone";
        public static final String COLUMN_NAME_EMAIL="email";
        public static final String COLUMN_NAME_ADDRESS="address";

        public String getIfNotExistStatement(){
            return "CREATE TABLE IF NOT EXISTS " + TABLE_GUESTS+ " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"+
                    COLUMN_NAME_EID + " INTEGER REFERENCES "+ EventsTable.TABLENAME + " ON DELETE CASCADE ON UPDATE CASCADE," +
                    COLUMN_NAME_GUESTNAME + " TEXT,"+
                    COLUMN_NAME_GENDER+" TEXT,"+
                    COLUMN_NAME_AGE+" TEXT,"+
                    COLUMN_NAME_INVITATION+" TEXT,"+
                    COLUMN_NAME_PHONE+" TEXT,"+
                    COLUMN_NAME_EMAIL+" TEXT,"+
                    COLUMN_NAME_ADDRESS+" TEXT)";
        }
    }

