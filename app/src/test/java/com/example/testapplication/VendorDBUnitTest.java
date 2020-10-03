package com.example.testapplication;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.test.RenamingDelegatingContext;
import androidx.test.InstrumentationRegistry;
import com.example.testapplication.db.guest.Guest_Impl;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.test.RenamingDelegatingContext;

import androidx.test.InstrumentationRegistry;


import com.example.testapplication.db.budget.Budget_Impl_updated;
import com.example.testapplication.db.vendor.Vendor_impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
public class VendorDBInstrumentedTest {
    private Vendor_impl vendor_model, vendor_model_result;
    private int event_id = 0, vendor_id = 0;
    private Context context;
    @Before
    public void setUp() {
        context = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
        vendor_model = new Vendor_impl(context,0);
        vendor_model_result = new Vendor_impl(context,0);
    }
    @Test
    public void db_recordInsertSearchUpdateDelete(){
        /*
        ================= INSERT NEW =========================
         */
        //assign data
        vendor_model.eid = event_id;
        vendor_model.name = "Vidura Sathsara";
        vendor_model.category = "Decor";
        vendor_model.address="Colombo";
        vendor_model.email="ash25@gmail.com";

        //save to db and get the primary key of the record
        vendor_id = vendor_model.addVendorGetid();
        //read from db to new instance
        /*
        ================= SEARCH NSERTED ====================
         */
        vendor_model_result = vendor_model_result.getVendorbyid(vendor_id);//get saved record
        //assert
        assertEquals(vendor_model.name,vendor_model_result.name);

        /*
        ==================== UPDATE =========================
         */
        vendor_model = vendor_model.getVendorbyid(vendor_id);//get saved record
        //set another name
        String prev_name = vendor_model.name;
        String newName = "Sachith Perera";
        vendor_model.name = newName;
        vendor_model.updateVendor(vendor_model);
        //read from db to new instance
        vendor_model_result = vendor_model_result.getVendorbyid(vendor_id);//get saved record
        //assert update name should be 'Billy'
        assertEquals(newName,vendor_model_result.name);
        //assert update name should NOT be 'James'
        assertNotEquals(prev_name,vendor_model_result.name);

        /*
        ==================== REMOVE =========================
         */
        vendor_model.removeVendor(vendor_id);
        vendor_model_result = vendor_model_result.getVendorbyid(vendor_id);
        assertNotEquals(vendor_model.name,vendor_model_result.name);
    }
    @Test
    public void db_InsertReadList(){
        /*
        =============== INSERT SETS OF DATA ==================
         */
        //assign dataset1
        String[] names = {"Kamal","Malki","Deshan"};
        String[] category = {"Decor","Cake","Toys"};
        String[] address = {"Colombo","Panadura","Kalutara"};
        String[] email = {"kml@gmail.com","malk@gmail.com","desh@yahoo.com"};
        for(int i=0;i<names.length;i++){
            vendor_model.name = names[i];
            vendor_model.category = category[i];
            vendor_model.address = address[i];
            vendor_model.email = email[i];
            //save to db
            vendor_model.addVendor();
        }
        /*
        =============== RETRIEVE SETS OF DATA ==================
         */
        List<Vendor_impl> list = new ArrayList<>();
        list = vendor_model_result.getVendor();
        for(int i=0;i<list.size();i++){
            vendor_model = list.get(i); //get object element from list to class obj
            //assert with original
            assertEquals(names[i],vendor_model.name);
            assertEquals(category[i],vendor_model.category);
            assertEquals(address[i],vendor_model.address);
            assertEquals(email[i],vendor_model.email);

        }
    }
}

