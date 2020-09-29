package com.example.testapplication.db.appsettings;

import android.app.Application;
import android.content.Context;

import com.example.testapplication.db.DBHandler;

public class DecimalSeperator {
    private static class DecimalSeperatorTable{
        public static final String DECIMAL_FORMAT_TYPE = "Decimal_Format_Type";
        public static final String ID = "id";
        public static final String TABLENAME = "Decimal_Format";
        public static String getDecimalFormatTableStatement(){
            return "CREATE TABLE IF NOT EXISTS " + TABLENAME + " (";
        }
    }
    private DBHandler db;
    public DecimalSeperator(Context c){
        db = new DBHandler(c,DecimalSeperatorTable.getDecimalFormatTableStatement());
    }
    public void setDecimalSeperatorToDot(){

    }
    public void setDecimalSeperatorToComma(){

    }
    public String formatDouble(){
        return null;
    }
}
