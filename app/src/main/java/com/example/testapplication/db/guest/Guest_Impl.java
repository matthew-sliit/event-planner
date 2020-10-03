package com.example.testapplication.db.guest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.testapplication.db.DBHandler;
import com.example.testapplication.db.budget.Budget_Impl_updated;


import java.util.ArrayList;
import java.util.List;

public class Guest_Impl  implements IGuest {

    //Class variables
    public String guestname,gender="male",age="adult",invitation="invitation sent",phone,email,address;
    public int id = 0, eid = 0;
    private DBHandler db;
    private Context c;

    /*
    Getters and Setters for class variables
     */

    public String getGuestname() {
        return guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getInvitation() {
        return invitation;
    }

    public void setInvitation(String invitation) {
        this.invitation = invitation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Guest_table table = new Guest_table();
    public Guest_Impl(Context c, int eid) {
        /*
        if(c == null){
            Log.d("Guest_Impl>>","context is null!");
        }else{
            Log.d("Guest_Impl>>","context is NOT null!");
        }
         */
        db = new DBHandler(c,table.getIfNotExistStatement());
        this.c = c;
        this.eid = eid;
    }

    @Override
        public void addGuest(String guestname, String gender, String age, String invitation, String phone, String email, String address, int eid) {

        ContentValues cv= new ContentValues();
            cv.put(Guest_table.COLUMN_NAME_EID, eid);
            cv.put(table.COLUMN_NAME_GUESTNAME, guestname);
            cv.put(table.COLUMN_NAME_GENDER, gender);
            cv.put(table.COLUMN_NAME_AGE, age);
            cv.put(table.COLUMN_NAME_INVITATION, invitation);
            cv.put(table.COLUMN_NAME_PHONE, phone);
            cv.put(table.COLUMN_NAME_EMAIL, email);
            cv.put(table.COLUMN_NAME_ADDRESS, address);
            db.insert(cv,table.TABLE_GUESTS);
        }

    @Override
    public void addGuest() {
        ContentValues cv= new ContentValues();
        cv.put(Guest_table.COLUMN_NAME_EID, eid);
        cv.put(table.COLUMN_NAME_GUESTNAME, guestname);
        cv.put(table.COLUMN_NAME_GENDER, gender);
        cv.put(table.COLUMN_NAME_AGE, age);
        cv.put(table.COLUMN_NAME_INVITATION, invitation);
        cv.put(table.COLUMN_NAME_PHONE, phone);
        cv.put(table.COLUMN_NAME_EMAIL, email);
        cv.put(table.COLUMN_NAME_ADDRESS, address);
        db.insert(cv,table.TABLE_GUESTS);
    }
    @Override
    public int addGuestGetId() {
        ContentValues cv= new ContentValues();
        cv.put(Guest_table.COLUMN_NAME_EID, eid);
        cv.put(table.COLUMN_NAME_GUESTNAME, guestname);
        cv.put(table.COLUMN_NAME_GENDER, gender);
        cv.put(table.COLUMN_NAME_AGE, age);
        cv.put(table.COLUMN_NAME_INVITATION, invitation);
        cv.put(table.COLUMN_NAME_PHONE, phone);
        cv.put(table.COLUMN_NAME_EMAIL, email);
        cv.put(table.COLUMN_NAME_ADDRESS, address);
        long i = db.insertGetId(cv,table.TABLE_GUESTS);
        return (int)i;
    }

    @Override
        public List<Guest_Impl> getGuestList() {
            String[] cols = {Guest_table.COLUMN_NAME_ID,Guest_table.COLUMN_NAME_EID,table.COLUMN_NAME_GUESTNAME,table.COLUMN_NAME_GENDER,table.COLUMN_NAME_AGE,table.COLUMN_NAME_INVITATION,table.COLUMN_NAME_PHONE
            ,table.COLUMN_NAME_EMAIL,table.COLUMN_NAME_ADDRESS};
            try {
                Cursor c = db.readAllWitSelection(cols, table.TABLE_GUESTS,getWhereEidStatement());
                List<Guest_Impl> b = new ArrayList<>();
                Guest_Impl ib;
                while(c.moveToNext()){
                    ib = new Guest_Impl(this.c,eid);
                    ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                    ib.eid = c.getInt(c.getColumnIndexOrThrow(Guest_table.COLUMN_NAME_EID));//int
                    ib.guestname = c.getString(c.getColumnIndexOrThrow("guestname"));
                    ib.gender = c.getString(c.getColumnIndexOrThrow("gender"));
                    ib.age = c.getString(c.getColumnIndexOrThrow("age"));
                    ib.invitation = c.getString(c.getColumnIndexOrThrow("invitation"));
                    ib.phone = c.getString(c.getColumnIndexOrThrow("phone"));
                    ib.email = c.getString(c.getColumnIndexOrThrow("email"));
                    ib.address = c.getString(c.getColumnIndexOrThrow("address"));
                    Log.d("Guest_Impl>>","id " + ib.id);
                    Log.d("Guest_Impl>>","name " + ib.guestname);
                    Log.d("Guest_Impl>>","invitation " + ib.invitation);
                    b.add(ib);
                }
                return b;
            }catch (NullPointerException np){
                Log.d("Guest_Impl>>","NPE " + np.getMessage());
                return null;
            }
        }

        @Override
        public Guest_Impl getGuestById(int id) {
            String[] cols = {Guest_table.COLUMN_NAME_ID,Guest_table.COLUMN_NAME_EID,table.COLUMN_NAME_GUESTNAME,table.COLUMN_NAME_GENDER,table.COLUMN_NAME_AGE,table.COLUMN_NAME_INVITATION,table.COLUMN_NAME_PHONE
                    ,table.COLUMN_NAME_EMAIL,table.COLUMN_NAME_ADDRESS};
            try {
                Cursor c = db.readAllWitSelection(cols,table.TABLE_GUESTS,getWhereEidaBidStatement(eid,id));
                List<Guest_Impl> b = new ArrayList<>();
                Guest_Impl ib = new Guest_Impl(this.c,eid);
                while(c.moveToNext()){
                    ib.id = c.getInt(c.getColumnIndexOrThrow("id"));//int
                    ib.eid = c.getInt(c.getColumnIndexOrThrow(Guest_table.COLUMN_NAME_EID));//int
                    ib.guestname = c.getString(c.getColumnIndexOrThrow("guestname"));
                    ib.gender = c.getString(c.getColumnIndexOrThrow("gender"));
                    ib.age = c.getString(c.getColumnIndexOrThrow("age"));
                    ib.invitation = c.getString(c.getColumnIndexOrThrow("invitation"));
                    ib.phone = c.getString(c.getColumnIndexOrThrow("phone"));
                    ib.email = c.getString(c.getColumnIndexOrThrow("email"));
                    ib.address = c.getString(c.getColumnIndexOrThrow("address"));

                /*Log.d("Budget_Impl>>","================ Printing Read Values ================");
                Log.d("id -> ",""+ib.id);
                Log.d("name -> ",ib.name);
                Log.d("cat -> ",ib.cat);
                Log.d("amt -> ",ib.amt);
                Log.d("desc -> ",ib.desc);*/
                }
                return ib;
            }catch (NullPointerException np){
                Log.d("Guest_Impl>>","NPE " + np.getMessage());
                return null;
            }
        }

        @Override
        public void removeGuest(int guestID) {
            db.delete(table.TABLE_GUESTS,getWhereEidaBidStatement(guestID),null);

        }

        @Override
        public void updateGuest(String[] colNamesToUpdate, String[] values) {
            ContentValues cv = new ContentValues();
            for(int col=0;col<colNamesToUpdate.length;col++){
                cv.put(colNamesToUpdate[col],values[col]);

            }
            String id2Str = "" + this.id;
            db.update(cv,"id",id2Str,table.TABLE_GUESTS);
        }

        @Override
        public void updateGuest(Guest_Impl obj) {
            String[] cols = {table.COLUMN_NAME_GUESTNAME,table.COLUMN_NAME_GENDER,table.COLUMN_NAME_AGE,table.COLUMN_NAME_INVITATION,table.COLUMN_NAME_PHONE
                    ,table.COLUMN_NAME_EMAIL,table.COLUMN_NAME_ADDRESS};
            String[] v = {obj.guestname,obj.gender,obj.age,obj.invitation,obj.phone,obj.email,obj.address};
            ContentValues cv = new ContentValues();
            for(int col=0;col<cols.length;col++){
                cv.put(cols[col],v[col]);
                Log.d("Guest_Impl>>","col->"+cols[col] + " val->"+v[col]);
            }
            //String id2Str = "" + obj.id;
           // db.update(cv,"id",id2Str,table.TABLE_GUESTS);//update using id
            db.update(cv,getUpdateWhere(obj.eid,obj.id), Guest_table.TABLE_GUESTS);

        }

    //for debugging
    public void printList (List<Guest_Impl> e){
        for(Guest_Impl b : e){
            Log.d("Guest_Impl>>","id -> " + b.id);
            Log.d("Guest_Impl>>","name -> " + b.guestname);
            Log.d("Guest_Impl>>","cat -> " + b.gender);
            Log.d("Guest_Impl>>","amt -> " + b.age);
            Log.d("Guest_Impl>>","desc -> " + b.invitation);
            Log.d("Guest_Impl>>","desc -> " + b.phone);
            Log.d("Guest_Impl>>","desc -> " + b.email);
            Log.d("Guest_Impl>>","desc -> " + b.address);
        }
    }
    private String getWhereEidStatement(){
        return Guest_table.COLUMN_NAME_EID + " LIKE " + eid;
    }
    private String getWhereEidaBidStatement(int gid){
        return Guest_table.COLUMN_NAME_EID + " LIKE " + eid + " AND " + Guest_table.COLUMN_NAME_ID + " LIKE " + gid;
    }
    private String getWhereEidaBidStatement(int eid, int gid){
        return Guest_table.COLUMN_NAME_EID + " LIKE " + eid + " AND " + Guest_table.COLUMN_NAME_ID + " LIKE " + gid;
    }

    private String getUpdateWhere(int eid_, int gid_){
        return Guest_table.COLUMN_NAME_EID + " LIKE " + eid_ +
                " AND " + Guest_table.COLUMN_NAME_ID + " LIKE " + gid_;
    }
}

