package com.example.testapplication.db.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.testapplication.constants.TableNames;
import com.example.testapplication.db.DBHandler;
import com.example.testapplication.db.commontables.PaymentTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Budget_payments {
    private PaymentTable tableColNames;
    private Context currentAct;
    private DBHandler db;
    //local var
    public int payment_id=0;
    public int budget_id=0;
    public int event_id = 0;
    public String name=null;
    public Double amt = 0.00;
    public String status=null;
    //local date var
    public Date rdate=new Date();
    //date format for local date var
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    //constructor
    public Budget_payments(Context c){
        currentAct = c;
        tableColNames = new PaymentTable(TableNames.Budget_payment,"Bid","Budget");
        db=new DBHandler(c,tableColNames.getIfNotExistStatement());
    }
    public void addPayment(int event_id, int budget_id){
        ContentValues cv= new ContentValues();
        //
        cv.put(tableColNames.REF_ID,budget_id); //Bid
        cv.put(tableColNames.EVENT_TABLE_ID,event_id); //eid
        cv.put(PaymentTable.NAME,name);
        cv.put(PaymentTable.AMOUNT,amt);
        cv.put(PaymentTable.STATUS,status);
        cv.put(PaymentTable.RECEIVED_DATE, df.format(rdate));
        //insert
        db.insert(cv,tableColNames.tableName);
    }
    public void removePayment(int event_id, int budget_id, int payment_id){
        db.delete(tableColNames.tableName,getWhereStatementWOWhere(event_id,budget_id,payment_id),null);
    }

    public void updatePayment(int event_id, int budget_id, int payment_id){
        ContentValues cv= new ContentValues();
        cv.put(tableColNames.REF_ID,budget_id); //Bid
        cv.put(tableColNames.EVENT_TABLE_ID,event_id); //eid
        cv.put(PaymentTable.NAME,name);
        cv.put(PaymentTable.AMOUNT,amt);
        cv.put(PaymentTable.STATUS,status);
        cv.put(PaymentTable.RECEIVED_DATE, df.format(rdate));
        db.update(cv,getWhereStatementWOWhere(event_id,budget_id,payment_id),tableColNames.tableName);
    }
    public Budget_payments getPaymentById(int event_id, int budget_id, int payment_id){
        try{
            //Cursor c = db.readAllIgnoreArgs(tableColNames.getAllColumns(), tableColNames.tableName);
            Cursor c = db.readAllWitSelection(tableColNames.getAllColumns(),tableColNames.tableName,getWhereStatementWOWhere(event_id,budget_id,payment_id));
            Budget_payments bp = new Budget_payments(currentAct);
            while(c.moveToNext()) {
                bp = new Budget_payments(currentAct);
                bp.payment_id = c.getInt(c.getColumnIndexOrThrow(PaymentTable.ID));//int
                bp.event_id = c.getInt(c.getColumnIndexOrThrow(tableColNames.EVENT_TABLE_ID));//int
                bp.budget_id = c.getInt(c.getColumnIndexOrThrow(tableColNames.REF_ID));//int
                bp.amt = c.getDouble(c.getColumnIndexOrThrow(PaymentTable.AMOUNT));//int
                bp.name = c.getString(c.getColumnIndexOrThrow(PaymentTable.NAME));
                bp.status = c.getString(c.getColumnIndexOrThrow(PaymentTable.STATUS));
                String date = c.getString(c.getColumnIndexOrThrow(PaymentTable.RECEIVED_DATE));
                try {
                    bp.rdate = df.parse(date);
                } catch (ParseException pe) {
                    rdate = null;
                    Log.d("Budget_Payment>>", "Parsing Datetime Failed FOR " + bp.rdate);
                }
            }
            return bp;
        }catch (NullPointerException e){

        }
        return null;
    }
    public List<Budget_payments> getBudgetPaymentList(int event_id, int budget_id){
        List<Budget_payments> bps = new ArrayList<>();
        try{
            Cursor c = db.readAllWitSelection(tableColNames.getAllColumns(),tableColNames.tableName,getWhereStatementWOWhere(event_id,budget_id));
            Budget_payments bp;
            while(c.moveToNext()){
                bp = new Budget_payments(currentAct);
                bp.payment_id = c.getInt(c.getColumnIndexOrThrow(PaymentTable.ID));//int
                bp.event_id = c.getInt(c.getColumnIndexOrThrow(tableColNames.EVENT_TABLE_ID));//int
                bp.budget_id = c.getInt(c.getColumnIndexOrThrow(tableColNames.REF_ID));//int
                bp.name = c.getString(c.getColumnIndexOrThrow(PaymentTable.NAME));
                bp.amt = c.getDouble(c.getColumnIndexOrThrow(PaymentTable.AMOUNT));//int
                bp.status = c.getString(c.getColumnIndexOrThrow(PaymentTable.STATUS));
                String date = c.getString(c.getColumnIndexOrThrow(PaymentTable.RECEIVED_DATE));
                try{
                    bp.rdate = df.parse(date);
                }catch (ParseException pe){
                    rdate = null;
                    Log.d("Budget_Payment>>", "Parsing Datetime Failed FOR " + date);
                }
                bps.add(bp);
            }
            return bps;
        }catch (NullPointerException e){
            return null;
        }
    }
    public double getPaidTotalAmountForBudget(int event_id, int budget_id){
        Budget_Impl_updated budget = new Budget_Impl_updated(currentAct,event_id);
        List<Budget_payments> bp = new ArrayList<>(getBudgetPaymentList(event_id,budget_id));
        double sum=0.00;
        for(Budget_payments bps : bp){
            sum+=bps.amt;
        }
        return sum;
    }
    private String getWhereStatement(int eid, int bid, int pid){
        return " WHERE " + tableColNames.EVENT_TABLE_ID + " = " + eid + " AND " + tableColNames.REF_ID + " = " + bid + " AND " + PaymentTable.ID + " = " + pid;
    }
    private String getWhereStatement(int eid, int bid){
        return " WHERE " + tableColNames.EVENT_TABLE_ID + " = " + event_id + " AND " + tableColNames.REF_ID + " = " + budget_id;
    }
    private String getWhereStatementWOWhere(int eid, int bid, int pid){
        return tableColNames.EVENT_TABLE_ID + " = " + eid + " AND " + tableColNames.REF_ID + " = " + bid + " AND " + PaymentTable.ID + " = " + pid;
    }
    private String getWhereStatementWOWhere(int eid, int bid){
        return tableColNames.EVENT_TABLE_ID + " = " + eid + " AND " + tableColNames.REF_ID + " = " + bid;
    }
}
