package com.example.testapplication.db.budget;

import android.content.ContentValues;
import android.content.Context;

import com.example.testapplication.db.DBHandler;
import com.example.testapplication.db.commontables.PaymentTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Budget_payments {
    private PaymentTable table;
    private Context currentAct;
    private DBHandler db;
    //local var
    public int payment_id=0;
    public int budget_id=0;
    public int event_id = 0;
    public String name=null;
    public String cat=null;
    public String status=null;
    public Date rdate=null;
    public int amt = 0;

    //constructor
    public Budget_payments(Context c){
        currentAct = c;
        table = new PaymentTable("Budget_Payments","Bid","Budget","id");
        db=new DBHandler(c,table.getIfNotExistStatement());
    }
    public void addPayment(int event_id, int budget_id){
        ContentValues cv= new ContentValues();
        cv.put(table.NAME,name);
        cv.put(table.AMOUNT,cat);
        cv.put(table.STATUS,status);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(currentTime);
        cv.put(table.RECEIVED_DATE,""+formattedDate);
        db.insert(cv,table.tableName);
    }
    public void removePayment(int event_id, int budget_id, int payment_id){
        String[] idValue = {"" + payment_id};
        String whereStatement = " WHERE " + table.EVENT_ID + " = " + event_id + " AND " + table.REF_ID + " = " + budget_id + " AND " + table.ID + " = " + payment_id;
        db.delete(table.tableName,whereStatement,null);
    }
    public void updatePayment(int event_id, int budget_id, int payment_id){

    }
    public Budget_payments getPaymentById(int event_id, int budget_id, int payment_id){
        return null;
    }
    public List<Budget_payments> getBudgetPaymentList(int event_id, int budget_id){
        return null;
    }
    public List<Budget_payments> getPaymentListByCategory(int event_id, int budget_id, String categoryName){
        return null;
    }
    public double getPaidAmountForBudget(int event_id, int budget_id){
        Budget_Impl budget = new Budget_Impl(currentAct);
        List<Budget_payments> bp = new ArrayList<>(getBudgetPaymentList(event_id,budget_id));
        double sum=0.00;
        for(Budget_payments bps : bp){
            sum+=bps.amt;
        }
        return sum;
    }

}
