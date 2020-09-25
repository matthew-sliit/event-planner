package com.example.testapplication.db.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.testapplication.db.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class Budget_Impl implements Ibudget {
    //Inner class to organize table
    private class Budget_table{
        public Budget_table(){}
        private static final String tableName = "Budget";
        public static final String NAME = "Name";
        public static final String CATEGORY = "Category";
        public static final String AMOUNT = "Amount";
        public static final String DESC = "Desc";
        public String getTableCreator(){
            return "CREATE TABLE " + tableName + " (" +
                    "id INTEGER PRIMARY KEY, " +
                    NAME + " TEXT," +
                    CATEGORY+" TEXT," +
                    AMOUNT+" DECIMAL,"+
                    DESC+" TEXT)";
        }
        public String getIfNotExistStatement(){
            return "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "id INTEGER PRIMARY KEY, " +
                    NAME + " TEXT," +
                    CATEGORY+" TEXT," +
                    AMOUNT+" DECIMAL,"+
                    DESC+" TEXT)";
        }
    }
    /*
    Getters and Setters for class variables
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    private Budget_table table = new Budget_table();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    //Class variables
    public int id = 0;
    public String name = null;
    public String cat = null;
    public String amt = null;
    public String desc = null;
    private DBHandler db;
    private Context c;

    //constructor
    public Budget_Impl(Context c){
        if(c == null){
            Log.d("Budget_Impl>>","context is null!");
        }else{
            Log.d("Budget_Impl>>","context is NOT null!");
        }
        db = new DBHandler(c,table.getIfNotExistStatement());
        this.c = c;
    }

    //add
    @Override
    public void addBudget(String budget_name, String catName, double amt, String desc) {
        ContentValues cv= new ContentValues();
        cv.put("Name",budget_name);
        cv.put("Category",catName);
        cv.put("Amount",amt);
        cv.put("Desc",desc);
        db.insert(cv,table.tableName);
    }

    //get list, read
    @Override
    public List<Budget_Impl> getBudgetList() {
        String[] cols = {"id",table.NAME,table.CATEGORY,table.AMOUNT,table.DESC};
        try {
            Cursor c = db.readAllIgnoreArgs(cols, table.tableName);
            List<Budget_Impl> b = new ArrayList<>();
            Budget_Impl ib;
            while(c.moveToNext()){
                ib = new Budget_Impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                ib.name = c.getString(c.getColumnIndexOrThrow("Name"));
                ib.cat = c.getString(c.getColumnIndexOrThrow("Category"));
                ib.amt = c.getString(c.getColumnIndexOrThrow("Amount"));
                ib.desc = c.getString(c.getColumnIndexOrThrow("Desc"));
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
    public Budget_Impl getBudgetById(int id) {
        String[] cols = {"id",table.NAME,table.CATEGORY,table.AMOUNT,table.DESC};
        //Log.d("BudgetImpl>>","id -> " + id);
        try {
            Cursor c = db.readWithWhere(cols,table.tableName,"id","" + id);
            List<Budget_Impl> b = new ArrayList<>();
            Budget_Impl ib = new Budget_Impl(this.c);
            while(c.moveToNext()){
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                ib.name = c.getString(c.getColumnIndexOrThrow("Name"));
                ib.cat = c.getString(c.getColumnIndexOrThrow("Category"));
                ib.amt = c.getString(c.getColumnIndexOrThrow("Amount"));
                ib.desc = c.getString(c.getColumnIndexOrThrow("Desc"));
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

    //for debugging
    public void printList (List<Budget_Impl> e){
        for(Budget_Impl b : e){
            Log.d("Budget_Impl>>","id -> " + b.id);
            Log.d("Budget_Impl>>","name -> " + b.name);
            Log.d("Budget_Impl>>","cat -> " + b.cat);
            Log.d("Budget_Impl>>","amt -> " + b.amt);
            Log.d("Budget_Impl>>","desc -> " + b.desc);
        }
    }

    //delete
    @Override
    public void removeBudget(int budgetID) {
        String[] idValue = {"" + budgetID};
        db.delete("id",idValue,table.tableName);
    }

    //update Specific
    @Override
    public void updateBudget(String[] colNamesToUpdate, String[] values) {
        ContentValues cv = new ContentValues();
        for(int col=0;col<colNamesToUpdate.length;col++){
            cv.put(colNamesToUpdate[col],values[col]);

        }
        String id2Str = "" + this.id;
        db.update(cv,"id",id2Str,table.tableName);
    }

    //update All
    @Override
    public void updateBudget(Budget_Impl obj) {
        String[] cols = {table.NAME,table.CATEGORY,table.AMOUNT,table.DESC};
        String[] v = {obj.name,obj.cat,obj.amt,obj.desc};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Budget_Impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        String id2Str = "" + obj.id;
        db.update(cv,"id",id2Str,table.tableName);//update using id
    }
}
