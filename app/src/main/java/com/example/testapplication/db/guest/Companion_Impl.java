package com.example.testapplication.db.guest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.testapplication.db.DBHandler;
import com.example.testapplication.db.commontables.EventsTable;

import java.util.ArrayList;
import java.util.List;

public class Companion_Impl implements ICompanion {


    private class Com_table {
        public Com_table(){}
        public static final String TABLE_COM="companionstable";
        public static final String COLUMN_NAME_ID="cid";
        public static final String COLUMN_NAME_EID= EventsTable.EVENT_ID;
        public static final String COLUMN_NAME_GID=Guest_table.COLUMN_NAME_ID;
        public static final String COLUMN_NAME_CNAME="cname";
        public static final String COLUMN_NAME_GENDER="gender";
        public static final String COLUMN_NAME_AGE="age";
        public static final String COLUMN_NAME_NOTE="note";

        public String getTableCreator(){
            return "CREATE TABLE " + TABLE_COM+ " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"+
                    COLUMN_NAME_EID + " INTEGER REFERENCES "+EventsTable.TABLENAME+" ON DELETE CASCADE ON UPDATE CASCADE ,"+
                    COLUMN_NAME_GID + " INTEGER REFERENCES "+Guest_table.TABLE_GUESTS+" ON DELETE CASCADE ON UPDATE CASCADE ,"+
                    COLUMN_NAME_CNAME + " TEXT,"+
                    COLUMN_NAME_GENDER+" TEXT,"+
                    COLUMN_NAME_AGE+" TEXT,"+
                    COLUMN_NAME_NOTE+" TEXT)";

        }
        public String getIfNotExistStatement(){
            return "CREATE TABLE IF NOT EXISTS " + TABLE_COM+ " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"+
                    COLUMN_NAME_EID + " INTEGER REFERENCES "+EventsTable.TABLENAME+" ON DELETE CASCADE ON UPDATE CASCADE ,"+
                    COLUMN_NAME_GID + " INTEGER REFERENCES "+Guest_table.TABLE_GUESTS+" ON DELETE CASCADE ON UPDATE CASCADE ,"+
                    COLUMN_NAME_CNAME + " TEXT,"+
                    COLUMN_NAME_GENDER+" TEXT,"+
                    COLUMN_NAME_AGE+" TEXT,"+
                    COLUMN_NAME_NOTE+" TEXT)";
        }
    }

    //Class variables
    public String cname,gender="male",age="adult",note;
    public int id = 0,eid=0,gid=0;
    private DBHandler db;
    private Context c;

    public Companion_Impl(Context c) {
        this.c = c;
        db = new DBHandler(c, table.getIfNotExistStatement());
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Com_table table = new Com_table();
    public Companion_Impl(Context c,int eid,int gid) {

        if(c == null){
            Log.d("Companion_Impl>>","context is null!");
        }else{
            Log.d("Companion_Impl>>","context is NOT null!");
        }
        db = new DBHandler(c,table.getIfNotExistStatement());
        this.c = c;
        this.eid=eid;
        this.gid=gid;
    }

    @Override
    public void addCom(String cname, String gender, String age, String note) {
        ContentValues cv= new ContentValues();
        cv.put(table.COLUMN_NAME_CNAME, cname);
        cv.put(table.COLUMN_NAME_GENDER, gender);
        cv.put(table.COLUMN_NAME_AGE, age);
        cv.put(table.COLUMN_NAME_NOTE, note);
        db.insert(cv,table.TABLE_COM);
    }

    @Override
    public void addCom(int eid,int gid) {
        ContentValues cv= new ContentValues();
        cv.put(Com_table.COLUMN_NAME_EID,eid);
        cv.put(Com_table.COLUMN_NAME_GID,gid);
        cv.put(table.COLUMN_NAME_CNAME, cname);
        cv.put(table.COLUMN_NAME_GENDER, gender);
        cv.put(table.COLUMN_NAME_AGE, age);
        cv.put(table.COLUMN_NAME_NOTE, note);
        db.insert(cv,table.TABLE_COM);

    }

    @Override
    public List<Companion_Impl> getComList(int eid,int gid) {
        String[] cols = {Com_table.COLUMN_NAME_ID,table.COLUMN_NAME_CNAME,table.COLUMN_NAME_GENDER,table.COLUMN_NAME_AGE,table.COLUMN_NAME_NOTE};
        try {
            //Cursor c = db.readAllIgnoreArgs(cols, table.TABLE_COM);
            Cursor c = db.readAllWitSelection(cols, table.TABLE_COM,getWhereStatementWOWhere(gid,eid));
            List<Companion_Impl> b = new ArrayList<>();
            Companion_Impl ib;
            while(c.moveToNext()){
                ib = new Companion_Impl(this.c,eid,gid);
                ib.id = c.getInt(c.getColumnIndexOrThrow(Com_table.COLUMN_NAME_ID));//int
                ib.cname = c.getString(c.getColumnIndexOrThrow("cname"));
                ib.gender = c.getString(c.getColumnIndexOrThrow("gender"));
                ib.age = c.getString(c.getColumnIndexOrThrow("age"));
                ib.note = c.getString(c.getColumnIndexOrThrow("note"));
                ib.gid=gid;
                ib.eid=eid;
                b.add(ib);
            }
            return b;
        }catch (NullPointerException np){
            Log.d("Companion_Impl>>","NPE " + np.getMessage());
            return null;
        }
    }

    @Override
    public Companion_Impl getComById(int id,int eid,int gid) {
        String[] cols = {Com_table.COLUMN_NAME_ID,table.COLUMN_NAME_CNAME,table.COLUMN_NAME_GENDER,table.COLUMN_NAME_AGE,table.COLUMN_NAME_NOTE};
        try {
            // Cursor c = db.readWithWhere(cols,table.TABLENAME,"id","" + id);
            Cursor c = db.readAllWitSelection(cols, table.TABLE_COM,getWhereStatementWOWhere(eid,gid,id));
            List<Companion_Impl> b = new ArrayList<>();
            Companion_Impl ib = new Companion_Impl(this.c);
            while(c.moveToNext()){
                ib = new Companion_Impl(this.c);
                ib.id = c.getInt(c.getColumnIndexOrThrow(Com_table.COLUMN_NAME_ID));//int
                ib.cname = c.getString(c.getColumnIndexOrThrow("cname"));
                ib.gender = c.getString(c.getColumnIndexOrThrow("gender"));
                ib.age = c.getString(c.getColumnIndexOrThrow("age"));
                ib.note = c.getString(c.getColumnIndexOrThrow("note"));
                ib.gid=gid;
                ib.eid=eid;
            }
            return ib;
        }catch (NullPointerException np){
            Log.d("Companion_Impl>>","NPE " + np.getMessage());
            return null;
        }
    }

    @Override
    public void removeCom(int comID,int eid,int gid) {

        db.delete(Com_table.TABLE_COM,getWhereStatementWOWhere(eid,gid,comID),null);
        Log.d("Com Impl update>>","id="+comID);
        Log.d("Com Impl update>>","eid="+eid);
        Log.d("Com Impl update>>","gid="+gid);
        Log.d("Com Impl update>>","name="+this.cname);
    }

    @Override
    public void updateCom(String[] colNamesToUpdate, String[] values) {
        ContentValues cv = new ContentValues();
        for(int col=0;col<colNamesToUpdate.length;col++){
            cv.put(colNamesToUpdate[col],values[col]);

        }
        String id2Str = "" + this.id;
        db.update(cv,"id",id2Str,table.TABLE_COM);
    }

    @Override
    public void updateCom(Companion_Impl obj) {
        Log.d("Com Impl update>>","name="+obj.cname);
        Log.d("Com Impl update>>","id="+obj.id);
        Log.d("Com Impl update>>","eid="+obj.eid);
        Log.d("Com Impl update>>","gid="+obj.gid);
        ContentValues cv = new ContentValues();
        cv.put(Com_table.COLUMN_NAME_GID, obj.gid);
        cv.put(table.COLUMN_NAME_ID, obj.id);
        cv.put(table.COLUMN_NAME_CNAME, obj.cname);
        cv.put(table.COLUMN_NAME_GENDER, obj.gender);
        cv.put(table.COLUMN_NAME_AGE, obj.age);
        cv.put(table.COLUMN_NAME_NOTE, obj.note);
        db.update(cv, getWhereStatementWOWhere(obj.eid, obj.gid, obj.id), table.TABLE_COM);
    }

    private String getWhereStatementWOWhere(int eid, int gid, int id){
        return Com_table.COLUMN_NAME_GID+ " = " + gid + " AND " + Com_table.COLUMN_NAME_ID + " = " + id;
    }
    private String getWhereStatementWOWhere(int gid,int eid) {
        return Com_table.COLUMN_NAME_GID+ " = " + gid;
    }
}
