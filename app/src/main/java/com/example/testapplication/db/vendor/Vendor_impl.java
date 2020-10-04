package com.example.testapplication.db.vendor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.testapplication.db.DBHandler;
import com.example.testapplication.db.budget.Budget_Impl_updated;
import com.example.testapplication.db.guest.Companion_Impl;

import java.util.ArrayList;
import java.util.List;

public class Vendor_impl implements IVendor {

    private Context c;
    private DBHandler db;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Vendor_table table=new Vendor_table();
   // private Vendor_pay_table table1=new Vendor_pay_table();


    public int eid=0,id=0;
    public String name=null;
    public String category=null;
    public double amount=0;
    public String number=null;
    public String address=null;
    public String email=null;

    public Vendor_impl(Context c, int eid) {
        this.c = c;
        this.eid = eid;
        db=new DBHandler(c,table.getTableCreator());
    }

    @Override
    public void addVendor(String name, String category, double amount, String number, String address, String email) {
        ContentValues cv= new ContentValues();
        cv.put(Vendor_table.EID,eid);
        cv.put(table.NAME,name);
        cv.put(table.CATEGORY,category);
        cv.put(table.AMOUNT,amount);
        cv.put(table.NUMBER,number);
        cv.put(table.ADDRESS,address);
        cv.put(table.EMAIL,email);
        db.insert(cv,table.TABLENAME);

    }

    @Override
    public void addVendor() {
        ContentValues cv= new ContentValues();
        cv.put(table.NAME,name);
        cv.put(Vendor_table.EID,eid);
        cv.put(table.CATEGORY,category);
        cv.put(table.AMOUNT,amount);
        cv.put(table.NUMBER,number);
        cv.put(table.ADDRESS,address);
        cv.put(table.EMAIL,email);
        db.insert(cv,table.TABLENAME);

    }
    @Override
    public int addVendorGetid() {
        ContentValues cv= new ContentValues();
        cv.put(Vendor_table.EID,eid);
        cv.put(table.NAME,name);
        cv.put(table.CATEGORY,category);
        cv.put(table.AMOUNT,amount);
        cv.put(table.NUMBER,number);
        cv.put(table.ADDRESS,address);
        cv.put(table.EMAIL,email);
       return (int)db.insertGetId(cv,table.TABLENAME);

    }

    @Override
    public void removeVendor(int id) {
        Vendor_pay_Impl ci = new Vendor_pay_Impl(c);
        try {
            for (Vendor_pay_Impl c : ci.getPayment(eid, id)) {
                c.removePayment(c.id, eid, id);
            }
        }catch (SQLiteException e){
            //ignore
        }
        db.delete(Vendor_table.TABLENAME,getWhereEidaBidStatement(id),null);
    }

    @Override
    public void updateVendor(Vendor_impl obj) {
        ContentValues cv = new ContentValues();
        cv.put(Vendor_table.NAME,obj.name);
        cv.put(Vendor_table.AMOUNT,obj.amount);
        cv.put(Vendor_table.CATEGORY,obj.category);
        cv.put(Vendor_table.NUMBER,obj.number);
        cv.put(Vendor_table.ADDRESS,obj.address);
        cv.put(Vendor_table.EMAIL,obj.email);
        //String id2Str = "" + obj.id;
        //db.update(cv,"id",id2Str,table.tableName);//update using id
        /*
        db.update(cv,getUpdateWhere(obj.eid,obj.id), Budget_Impl_updated.Budget_table.tableName);

        String[] cols = {table.NAME,table.CATEGORY,table.AMOUNT,table.NUMBER,table.ADDRESS,table.EMAIL};
        String[] v = {obj.name,obj.category,""+obj.amount,obj.number,obj.address,obj.email};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Vendor_impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        String id2Str = "" + obj.id;
        db.update(cv,"id",id2Str,table.TABLENAME);//update using id
         */
        db.update(cv,getUpdateWhere(obj.eid,obj.id), Vendor_table.TABLENAME);
    }
    @Override
    public List<Vendor_impl> getVendor() {
        String[] cols = {"id",Vendor_table.EID,table.NAME,table.CATEGORY,table.AMOUNT,table.NUMBER,table.ADDRESS,table.EMAIL};
        List<Vendor_impl> b = new ArrayList<>();
        try {
            Cursor c = db.readAllWitSelection(cols, table.TABLENAME,getWhereEidStatement());
            Vendor_impl ib;
            while(c.moveToNext()){
                ib = new Vendor_impl(this.c,eid);
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                ib.eid = c.getInt(c.getColumnIndexOrThrow(Vendor_table.EID));//int
                ib.name = c.getString(c.getColumnIndexOrThrow("Name"));
                ib.category = c.getString(c.getColumnIndexOrThrow("Category"));
                ib.amount = Double.parseDouble(c.getString(c.getColumnIndexOrThrow("Amount")));
                ib.number = c.getString(c.getColumnIndexOrThrow("Number"));
                ib.address = c.getString(c.getColumnIndexOrThrow("Address"));
                ib.email = c.getString(c.getColumnIndexOrThrow("Email"));
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
            Log.d("Vendor_Impl>>","NPE " + np.getMessage());
            return null;

        }
        catch (NumberFormatException e){
            return b;
        }

    }

    @Override
    public Vendor_impl getVendorbyid(int id) {
        String[] cols = {"id",Vendor_table.EID,table.NAME,table.CATEGORY,table.AMOUNT,table.NUMBER,table.ADDRESS,table.EMAIL};
        //Log.d("BudgetImpl>>","id -> " + id);
        try {
            Cursor c = db.readAllWitSelection(cols,table.TABLENAME,getWhereEidaBidStatement(eid,id));
            List<Vendor_impl> b = new ArrayList<>();
            Vendor_impl ib = new Vendor_impl(this.c,eid);
            while(c.moveToNext()){
                ib = new Vendor_impl(this.c,eid);
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                ib.eid = c.getInt(c.getColumnIndexOrThrow(Vendor_table.EID));//int
                ib.name = c.getString(c.getColumnIndexOrThrow("Name"));
                ib.category = c.getString(c.getColumnIndexOrThrow("Category"));
                ib.amount = Double.parseDouble(c.getString(c.getColumnIndexOrThrow("Amount")));
                ib.number = c.getString(c.getColumnIndexOrThrow("Number"));
                ib.address = c.getString(c.getColumnIndexOrThrow("Address"));
                ib.email = c.getString(c.getColumnIndexOrThrow("Email"));
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
    public boolean hasVendor(String name, String email) {
        return false;
    }
    private String getWhereEidStatement(){
        return Vendor_table.EID + " LIKE " + eid;
    }
    private String getWhereEidaBidStatement(int vid){
        return Vendor_table.EID+ " LIKE " + eid + " AND " + Vendor_table.ID + " LIKE " + vid;
    }
    private String getWhereEidaBidStatement(int eid, int vid){
        return Vendor_table.EID + " LIKE " + eid + " AND " + Vendor_table.ID + " LIKE " + vid;
    }

    private String getUpdateWhere(int eid_, int vid_){
        return Vendor_table.EID + " LIKE " + eid_ +
                " AND " + Vendor_table.ID + " LIKE " + vid_;
    }
}
