package com.example.testapplication.db.commontables;

public class CategoryTable{
    public static final String TABLE_NAME = "CATEGORIES1";
    public static final String COL_NAME_CAT = "CATEGORY_NAME";
    public String getTableCreator(){
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id" + " INTEGER PRIMARY KEY, " +
                COL_NAME_CAT + " TEXT)";
    }
    public String[] getCols(){
        String[] cols = {COL_NAME_CAT};
        return cols;
    }
}