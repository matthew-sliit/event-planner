package com.example.testapplication.db.category;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.testapplication.db.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class Category implements ICategory {
    public static final boolean debugger_mode = false;
    private static class CategoryTable{
        public static final String TABLE_NAME = "CATEGORIES";
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
    private DBHandler db;
    private static CategoryTable ct = new CategoryTable();

    public Category(Context context){
        db = new DBHandler(context, ct.getTableCreator());
    }
    @Override
    public String addCategory(String name) {
        debug("Adding Category -> " + name);
        ContentValues values =new ContentValues();
        values.put(CategoryTable.COL_NAME_CAT,name);
        db.insert(values, CategoryTable.TABLE_NAME);
        return null;
    }
    public List getAllCategory(){
        debug("Getting all Categories!");
        Cursor c = db.readAllIgnoreArgs(ct.getCols(), CategoryTable.TABLE_NAME);
        List<String> cats = new ArrayList<>();
        while(c.moveToNext()){
            String cat = c.getString(c.getColumnIndexOrThrow(CategoryTable.COL_NAME_CAT));
            cats.add(cat);
            debug("Reading -> " + cat);
        }
        return cats;
    }

    @Override
    public void deleteCategory(String name) {
        String[] n = {name};
        db.delete(CategoryTable.COL_NAME_CAT,n, CategoryTable.TABLE_NAME);
    }

    @Override
    public void updateCategory(String prev_name, String new_name) {
        ContentValues cv = new ContentValues();
        cv.put(CategoryTable.COL_NAME_CAT,new_name);
        int i = db.update(cv, CategoryTable.COL_NAME_CAT,prev_name, CategoryTable.TABLE_NAME);
    }
    public boolean hasCategory(String catName){
        List ar = new ArrayList();
        ar = this.getAllCategory();
        for(int i=0;i<ar.size();i++){
            if(ar.get(i).equals(catName)){
                return true;
            }
        }
        return false;
    }
    private void debug(String msg){
        if(debugger_mode) {
            Log.d("Category>>", msg);
        }
    }

}
