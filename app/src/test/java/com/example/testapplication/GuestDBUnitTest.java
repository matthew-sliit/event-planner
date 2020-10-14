package com.example.testapplication;
import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.test.RenamingDelegatingContext;
//import androidx.test.InstrumentationRegistry;
import com.example.testapplication.db.guest.Guest_Impl;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class GuestDBUnitTest {
    private Guest_Impl guest_model, guest_model_result;
    private int event_id = 0, guest_id = 0;
    private Context context;
    @Before
    public void setUp() {
        //context = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
        context = RuntimeEnvironment.application;
        guest_model = new Guest_Impl(context,0);
        guest_model_result = new Guest_Impl(context,0);
    }
    @Test
    public void db_recordInsertSearchUpdateDelete(){
        /*
        ================= INSERT NEW =========================
         */
        //assign data
        guest_model.eid = event_id;
        guest_model.guestname = "Ashan Perera";
        guest_model.gender = "male";
        guest_model.age = "adult";
        guest_model.invitation = "invitation sent";
        guest_model.phone="0776666616";
        guest_model.email="ash25@gmail.com";
        guest_model.address="Dehiwala";
        //save to db and get the primary key of the record
        guest_id = guest_model.addGuestGetId();
        //read from db to new instance
        /*
        ================= SEARCH NSERTED ====================
         */
        guest_model_result = guest_model_result.getGuestById(guest_id);//get saved record
        //assert
        assertEquals(guest_model.guestname,guest_model_result.guestname);

        /*
        ==================== UPDATE =========================
         */
        guest_model = guest_model.getGuestById(guest_id);//get saved record
        //set another name
        String prev_name = guest_model.guestname;
        String newName = "Sachith Perera";
        guest_model.guestname = newName;
        guest_model.updateGuest(guest_model);
        //read from db to new instance
        guest_model_result = guest_model_result.getGuestById(guest_id);//get saved record
        //assert update name should be 'Billy'
        assertEquals(newName,guest_model_result.guestname);
        //assert update name should NOT be 'James'
        assertNotEquals(prev_name,guest_model_result.guestname);

        /*
        ==================== REMOVE =========================
         */
        guest_model.removeGuest(guest_id);
        guest_model_result = guest_model_result.getGuestById(guest_id);
        assertNotEquals(guest_model.guestname,guest_model_result.guestname);
    }
    @Test
    public void db_InsertReadList(){
        /*
        =============== INSERT SETS OF DATA ==================
         */
        //assign dataset1
        String[] names = {"Kamal","Malki","Deshan"};
        String[] gender = {"male","female","male"};
        String[] age = {"adult","adult","child"};
        String[] invi = {"invitation sent","invitation not sent","invitation not sent"};
        String[] phone = {"0718563","0778963","07136984"};
        String[] email = {"kml@gmail.com","malk@gmail.com","desh@yahoo.com"};
        String[] addr = {"Panadura","Gampaha","Kalutara"};
        for(int i=0;i<names.length;i++){
            guest_model.guestname = names[i];
            guest_model.gender = gender[i];
            guest_model.age = age[i];
            guest_model.invitation = invi[i];
            guest_model.phone = phone[i];
            guest_model.email = email[i];
            guest_model.address=addr[i];
            //save to db
            guest_model.addGuest();
        }
        /*
        =============== RETRIEVE SETS OF DATA ==================
         */
        List<Guest_Impl> list = new ArrayList<>();
        list = guest_model_result.getGuestList();
        for(int i=0;i<list.size();i++){
            guest_model = list.get(i); //get object element from list to class obj
            //assert with original
            assertEquals(names[i],guest_model.guestname);
            assertEquals(gender[i],guest_model.gender);
            assertEquals(age[i],guest_model.age);
            assertEquals(invi[i],guest_model.invitation);
            assertEquals(phone[i],guest_model.phone);
            assertEquals(email[i],guest_model.email);
            assertEquals(addr[i],guest_model.address);
        }
    }
}
