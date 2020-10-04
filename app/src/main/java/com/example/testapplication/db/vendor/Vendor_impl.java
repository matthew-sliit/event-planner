package com.example.testapplication.db.vendor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.testapplication.db.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class Vendor_impl implements IVendor {

    private Context c;
    private DBHandler db;


    public Vendor_impl(Context c) {
        this.c = c;
        db=new DBHandler(c,table.getTableCreator());
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


  /*  private class Vendor_table{
        public static final String TABLENAME="VendorTable";
        public static final String NAME="Name";
        public static final String CATEGORY="Category";
        public static final String AMOUNT="Amount";
        public static final String NUMBER="Number";
        public static final String ADDRESS="Address";
        public static final String EMAIL="Email";



        public Vendor_table() {
        }

        public String getTableCreator(){

            return "CREATE TABLE if not exists " + TABLENAME + " (" +
                    "id integer primary key,"+
                    NAME+" TEXT,"+
                    CATEGORY+" TEXT,"+
                    AMOUNT+" DOUBLE,"+
                    NUMBER+" TEXT,"+
                    ADDRESS+" TEXT,"+
                    EMAIL+" TEXT)";


        }


    }*/

    private Vendor_table table=new Vendor_table();
   // private Vendor_pay_table table1=new Vendor_pay_table();


    public int eid=0,id=0;
    public String name=null;
    public String category=null;
    public double amount=0;
    public String number=null;
    public String address=null;
    public String email=null;

    @Override
    public void addVendor(String name, String category, double amount, String number, String address, String email) {
        ContentValues cv= new ContentValues();
        cv.put(table.NAME,name);
        cv.put(table.CATEGORY,category);
        cv.put(table.AMOUNT,amount);
        cv.put(table.NUMBER,number);
        cv.put(table.ADDRESS,address);
        cv.put(table.EMAIL,email);
        db.insert(cv,table.TABLENAME);

    }

   /* @Override
    public void addPayment(String name_pay, String pay_amonut, String note) {

    }*/


    @Override
    public void addVendor() {
        ContentValues cv= new ContentValues();
        cv.put(table.NAME,name);
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
        String[] idValue = {"" + id};
        db.delete("id",idValue,table.TABLENAME);
    }

    @Override
    public void updateVendor(Vendor_impl obj) {

        String[] cols = {table.NAME,table.CATEGORY,table.AMOUNT,table.NUMBER,table.ADDRESS,table.EMAIL};
        String[] v = {obj.name,obj.category,""+obj.amount,obj.number,obj.address,obj.email};
        ContentValues cv = new ContentValues();
        for(int col=0;col<cols.length;col++){
            cv.put(cols[col],v[col]);
            Log.d("Vendor_impl>>","col->"+cols[col] + " val->"+v[col]);
        }
        String id2Str = "" + obj.id;
        db.update(cv,"id",id2Str,table.TABLENAME);//update using id
    }
    @Override
    public List getVendor() {
        String[] cols = {"id",table.NAME,table.CATEGORY,table.AMOUNT,table.NUMBER,table.ADDRESS,table.EMAIL};
        List<Vendor_impl> b = new ArrayList<>();
        try {
            Cursor c = db.readAllIgnoreArgs(cols, table.TABLENAME);
            Vendor_impl ib;
            while(c.moveToNext()){
                ib = new Vendor_impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
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
        String[] cols = {"id",table.NAME,table.CATEGORY,table.AMOUNT,table.NUMBER,table.ADDRESS,table.EMAIL};
        //Log.d("BudgetImpl>>","id -> " + id);
        try {
            Cursor c = db.readWithWhere(cols,table.TABLENAME,"id","" + id);
            List<Vendor_impl> b = new ArrayList<>();
            Vendor_impl ib = new Vendor_impl(this.c);
            while(c.moveToNext()){
                ib = new Vendor_impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
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
}
