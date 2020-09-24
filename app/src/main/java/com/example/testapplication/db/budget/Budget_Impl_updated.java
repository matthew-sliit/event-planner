package com.example.testapplication.db.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.testapplication.db.DBHandler;
import com.example.testapplication.db.commontables.EventsTable;
import com.example.testapplication.db.commontables.PaymentTable;

import java.util.ArrayList;
import java.util.List;

public class Budget_Impl_updated implements Ibudget {
    //Inner class to organize table
    private class Budget_table{

        public Budget_table(){}
        private static final String tableName = "Budget";
        private static final String EID = EventsTable.EVENT_ID;
        public static final String ID = "id";
        public static final String NAME = "Name";
        public static final String CATEGORY = "Category";
        public static final String AMOUNT = "Amount";
        public static final String DESC = "Desc";
        public String getIfNotExistStatement(){
            return "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    ID + " INTEGER PRIMARY KEY, " +
                    EID + " INTEGER REFERENCES "+EventsTable.TABLENAME+" ON DELETE CASCADE ON UPDATE CASCADE," +
                    NAME + " TEXT," +
                    CATEGORY+" TEXT," +
                    AMOUNT+" DECIMAL,"+
                    DESC+" TEXT)";
        }
        public String[] getColumns(){
            String[] cols = {EID,ID,NAME,CATEGORY,AMOUNT,DESC};
            return cols;
        }
    }
    Budget_table table = new Budget_table();
    //var
    public int eid = 0;
    public int id = 0;
    public String name = null;
    public String cat = null;
    public String amt = null;
    public String desc = null;
    //instance var
    private DBHandler db;
    private Context c;

    public Budget_Impl_updated(Context c, int eid){
        this.c = c;
        this.eid = eid;
    }
    @Override
    public void addBudget(String budget_name, String catName, double amt, String desc) {
        ContentValues cv= new ContentValues();
        cv.put(Budget_table.NAME,budget_name);
        cv.put(Budget_table.EID,eid);
        cv.put(Budget_table.CATEGORY,catName);
        cv.put(Budget_table.AMOUNT,amt);
        cv.put(Budget_table.DESC,desc);
        db.insert(cv,Budget_table.tableName);
    }

    @Override
    public List<Budget_Impl> getBudgetList() {
        try {
            Cursor c = db.readAllWitSelection(table.getColumns(), Budget_table.tableName,getWhereEidStatement()); //where eid like..
            List<Budget_Impl> b = new ArrayList<>();
            Budget_Impl ib;
            while(c.moveToNext()){
                ib = new Budget_Impl(this.c);
                ib.eid = c.getInt(c.getColumnIndexOrThrow(Budget_table.EID));//int
                ib.id = c.getInt(c.getColumnIndexOrThrow(Budget_table.ID));//int
                ib.name = c.getString(c.getColumnIndexOrThrow(Budget_table.NAME));
                ib.cat = c.getString(c.getColumnIndexOrThrow(Budget_table.CATEGORY));
                ib.amt = c.getString(c.getColumnIndexOrThrow(Budget_table.AMOUNT));
                ib.desc = c.getString(c.getColumnIndexOrThrow(Budget_table.DESC));
                /*Log.d("Budget_Impl>>","================ Printing Read Values ================");
                Log.d("id -> ",""+ib.id);
                Log.d("name -> ",ib.name);
                Log.d("cat -> ",ib.cat);
                Log.d("amt -> ",ib.amt);
                Log.d("desc -> ",ib.desc);*/
                b.add(ib);
            }
            return b;
        }catch (NullPointerException np){
            Log.d("Budget_Impl>>","NPE " + np.getMessage());
            return null;
        }
    }

    @Override
    public List<Budget_Impl> getBudgetListByCategory(String category) {
        try {
            Cursor c = db.readAllWitSelection(table.getColumns(), Budget_table.tableName,getWhereEidStatement() + " AND " + Budget_table.CATEGORY + " LIKE " + category); //where eid like..
            List<Budget_Impl> b = new ArrayList<>();
            Budget_Impl ib;
            while(c.moveToNext()){
                ib = new Budget_Impl(this.c);
                ib.eid = c.getInt(c.getColumnIndexOrThrow(Budget_table.EID));//int
                ib.id = c.getInt(c.getColumnIndexOrThrow(Budget_table.ID));//int
                ib.name = c.getString(c.getColumnIndexOrThrow(Budget_table.NAME));
                ib.cat = c.getString(c.getColumnIndexOrThrow(Budget_table.CATEGORY));
                ib.amt = c.getString(c.getColumnIndexOrThrow(Budget_table.AMOUNT));
                ib.desc = c.getString(c.getColumnIndexOrThrow(Budget_table.DESC));
                /*Log.d("Budget_Impl>>","================ Printing Read Values ================");
                Log.d("id -> ",""+ib.id);
                Log.d("name -> ",ib.name);
                Log.d("cat -> ",ib.cat);
                Log.d("amt -> ",ib.amt);
                Log.d("desc -> ",ib.desc);*/
                b.add(ib);
            }
            return b;
        }catch (NullPointerException np){
            Log.d("Budget_Impl>>","NPE " + np.getMessage());
            return null;
        }
    }

    @Override
    public Budget_Impl getBudgetById(int bid) {
        try {
            Cursor c = db.readAllWitSelection(table.getColumns(), Budget_table.tableName,getWhereEidaBidStatement(bid)); //where eid and bid
            Budget_Impl ib = new Budget_Impl(this.c);
            while(c.moveToNext()){
                ib = new Budget_Impl(this.c);
                ib.eid = c.getInt(c.getColumnIndexOrThrow(Budget_table.EID));//int
                ib.id = c.getInt(c.getColumnIndexOrThrow(Budget_table.ID));//int
                ib.name = c.getString(c.getColumnIndexOrThrow(Budget_table.NAME));
                ib.cat = c.getString(c.getColumnIndexOrThrow(Budget_table.CATEGORY));
                ib.amt = c.getString(c.getColumnIndexOrThrow(Budget_table.AMOUNT));
                ib.desc = c.getString(c.getColumnIndexOrThrow(Budget_table.DESC));
                /*Log.d("Budget_Impl>>","================ Printing Read Values ================");
                Log.d("id -> ",""+ib.id);
                Log.d("name -> ",ib.name);
                Log.d("cat -> ",ib.cat);
                Log.d("amt -> ",ib.amt);
                Log.d("desc -> ",ib.desc);*/
            }
            return ib;
        }catch (NullPointerException np){
            Log.d("Budget_Impl>>","NPE " + np.getMessage());
            return null;
        }
    }

    @Override
    public void removeBudget(int budgetID) {
        db.delete(Budget_table.tableName,getWhereEidaBidStatement(budgetID),null);
    }

    @Override
    public void updateBudget(String[] colNamesToUpdate, String[] values) {
        //for specific updates
    }

    @Override
    public void updateBudget(Budget_Impl obj) {
        //EID,ID,NAME,CATEGORY,AMOUNT,DESC
        /*
        String[] v = {""+obj.eid,""+obj.id,obj.cat,obj.amt,obj.desc};
        ContentValues cv = new ContentValues();
        for(int col=0;col<table.getColumns().length;col++){
            cv.put(table.getColumns()[col],v[col]);
            //Log.d("Budget_Impl>>","col->"+table.getColumns()[col] + " val->"+v[col]);
        }

         */
        ContentValues cv = new ContentValues();
        cv.put(Budget_table.NAME,obj.name);
        cv.put(Budget_table.AMOUNT,obj.amt);
        cv.put(Budget_table.CATEGORY,obj.cat);
        cv.put(Budget_table.DESC,obj.desc);
        //String id2Str = "" + obj.id;
        //db.update(cv,"id",id2Str,table.tableName);//update using id
        db.update(cv,getUpdateWhere(obj.eid,obj.id),Budget_table.tableName);
    }
    private String getWhereEidStatement(){
        return " WHERE " + Budget_table.EID + " LIKE " + eid;
    }
    private String getWhereEidaBidStatement(int bid){
        return " WHERE " + Budget_table.EID + " LIKE " + eid + " AND " + Budget_table.ID + " LIKE " + bid;
    }
    private String getUpdateWhere(int eid_, int bid_){
        return " WHERE " + Budget_table.EID + " LIKE " + eid_ +
                " AND " + Budget_table.ID + " LIKE " + bid_;
    }
}
