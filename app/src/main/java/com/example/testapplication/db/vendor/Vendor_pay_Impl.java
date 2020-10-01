package com.example.testapplication.db.vendor;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.testapplication.db.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class Vendor_pay_Impl implements IVendor_pay {

    private Context c;
    private DBHandler db;

    private Vendor_pay_table table = new Vendor_pay_table();

    public Vendor_pay_Impl(Context c) {
        this.c = c;
        db = new DBHandler(c, table.getTableCreator());
    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private class Vendor_pay_table {

        public static final String TABLENAME = "Vendorpaytable2";
        public static final String NAME = "Name";
        public static final String ID = "id";
        public static final String V_ID = "Vid";
        public static final String AMOUNT = "Amount";
        public static final String STATUS = "status";
        public static final String NOTE = "Note";


        public Vendor_pay_table() {
        }


        public String getTableCreator() {

            return "CREATE TABLE if not exists " + TABLENAME + " (" +
                    ID +" integer primary key," +
                    NAME + " TEXT," +
                    V_ID + " INTEGER references " + Vendor_table.TABLENAME + "(" + Vendor_table.ID + ") on delete cascade on update cascade ," +
                    AMOUNT + " TEXT," +
                    STATUS + " TEXT, "+
                    NOTE + " TEXT)";


        }
    }
    public int id=0,vid=0,eid=0;
    public String name = null;
    public String amount = null;
    public String status = "paid";
    public String note = null;

    @Override
    public void addPayment(String name, String amount,String status,String note) {
        ContentValues cv = new ContentValues();
        cv.put(table.NAME, name);
        cv.put(table.AMOUNT, amount);
        cv.put(table.NOTE, note);
        cv.put(table.STATUS,status);
        db.insert(cv, table.TABLENAME);

    }

    @Override
    public void removePayment(int id) {
        String[] idValue = {"" + id};
        db.delete("id",idValue,table.TABLENAME);
    }

    @Override
    public void addPayment(int event_id, int vendor_id) {
        ContentValues cv= new ContentValues();
        cv.put(Vendor_pay_table.V_ID,vendor_id);
        cv.put(table.NAME,name);
        cv.put(table.AMOUNT,amount);
        cv.put(table.NOTE,note);
        cv.put(table.STATUS,status);
        db.insert(cv,table.TABLENAME);

    }
    @Override
    public void removePayment(int id,int eid,int vendor_id) {
        String[] idValue = {"" + id};
        db.delete(Vendor_pay_table.TABLENAME,getWhereStatementWOWhere(eid,vendor_id,id),null);
    }



   /* @Override
    public void updatePayment(Vendor_pay_Impl obj) {
        ContentValues cv= new ContentValues();
        cv.put(Vendor_pay_table.V_ID,obj.vid);
        cv.put(table.ID,obj.id);
        cv.put(table.NAME,obj.name);
        cv.put(table.AMOUNT,obj.amount);
        cv.put(table.STATUS,obj.status);
        cv.put(table.NOTE,obj.note);
        db.update(cv,getWhereStatementWOWhere(obj.eid,obj.vid,obj.id),table.TABLENAME);
     /*   String[] cols = {table.NAME,table.AMOUNT,table.NOTE};
        String[] v = {obj.name,obj.amount,obj.note};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Vendor_pay_Impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        String id2Str = "" + obj.id;
        db.update(cv,"id",id2Str,table.TABLENAME);//update using id*/
    //}
    @Override
    public void updatePayment(Vendor_pay_Impl obj) {
        Log.d("Vendor_pay_Impl>>","n->"+obj.name+ " a->"+obj.amount+ " s->"+obj.status+ " no->"+obj.note);
        String[] cols = {table.NAME,table.AMOUNT,table.STATUS,table.NOTE};
        String[] v = {obj.name,obj.amount,obj.status,obj.note};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Vendor_pay_Impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        String id2Str = "" + obj.id;
        db.update(cv,"id",id2Str,table.TABLENAME);//update using id
    }

    @Override
    public List getPayment(int eventid,int vendorid) {
        String[] cols = {"id",table.NAME,table.AMOUNT,table.STATUS,table.NOTE};
        List<Vendor_pay_Impl> b = new ArrayList<>();
        try {
           // Cursor c = db.readAllIgnoreArgs(cols, table.TABLENAME);
            Cursor c = db.readAllWitSelection(cols, table.TABLENAME,getWhereStatementWOWhere(vendorid,eventid));
            Vendor_pay_Impl ib;
            while(c.moveToNext()){
                ib = new Vendor_pay_Impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                ib.name = c.getString(c.getColumnIndexOrThrow("Name"));
                ib.amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                ib.status = c.getString(c.getColumnIndexOrThrow("status"));
                ib.note = c.getString(c.getColumnIndexOrThrow("Note"));
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


   /* @Override
    public void updatePayment(Vendor_pay_Impl obj) {

        String[] cols = {table.NAME,table.AMOUNT,table.STATUS,table.NOTE};
        String[] v = {obj.name,obj.amount,obj.status,obj.note};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Vendor_impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        String id2Str = "" + obj.id;
        db.update(cv,"id",id2Str,table.TABLENAME);//update using id
    }*/

    @Override
    public List getPayment() {
        String[] cols = {"id",table.NAME,table.AMOUNT,table.STATUS,table.NOTE};
        List<Vendor_pay_Impl> b = new ArrayList<>();
        try {
            Cursor c = db.readAllIgnoreArgs(cols, table.TABLENAME);
            Vendor_pay_Impl ib;
            while(c.moveToNext()){
                ib = new Vendor_pay_Impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                ib.name = c.getString(c.getColumnIndexOrThrow("Name"));
                ib.amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                ib.status = c.getString(c.getColumnIndexOrThrow("status"));
                ib.note = c.getString(c.getColumnIndexOrThrow("Note"));
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
    public Vendor_pay_Impl getVendorPaybyid(int id,int vendorid,int eventid) {
        String[] cols = {"id",table.NAME,table.AMOUNT,table.STATUS,table.NOTE};
        //Log.d("BudgetImpl>>","id -> " + id);
        try {
           // Cursor c = db.readWithWhere(cols,table.TABLENAME,"id","" + id);
            Cursor c = db.readAllWitSelection(cols, table.TABLENAME,getWhereStatementWOWhere(eventid,vendorid,id));
            List<Vendor_pay_Impl> b = new ArrayList<>();
            Vendor_pay_Impl ib = new Vendor_pay_Impl(this.c);
            while(c.moveToNext()){
                ib = new Vendor_pay_Impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                ib.name = c.getString(c.getColumnIndexOrThrow("Name"));
                ib.amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                ib.status = c.getString(c.getColumnIndexOrThrow("status"));
                ib.note = c.getString(c.getColumnIndexOrThrow("Note"));

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
    private String getWhereStatementWOWhere(int eid, int vid, int pid){
        //return Vendor_pay_table.EVENT_TABLE_ID + " = " + eid + " AND " + tableColNames.REF_ID + " = " + bid + " AND " + PaymentTable.ID + " = " + pid;
        return Vendor_pay_table.V_ID + " = " + vid + " AND " + Vendor_pay_table.ID + " = " + pid;
    }
    private String getWhereStatementWOWhere(int vid,int eid) {
        //return Vendor_pay_table.EVENT_TABLE_ID + " = " + eid + " AND " + tableColNames.REF_ID + " = " + bid + " AND " + PaymentTable.ID + " = " + pid;
        return Vendor_pay_table.V_ID + " = " + vid;
    }

}

