package com.example.testapplication;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.test.RenamingDelegatingContext;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.testapplication.db.task.Task_Impl;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TaskDBUnitTest {
    private Task_Impl task_model, task_model_result;
    private int event_id = 0, task_id = 0;
    private Context context;
    @Before
    public void setUp() {
        context = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(),"test_");
        task_model = new Task_Impl(context);
        task_model_result = new Task_Impl(context);
    }
    @Test
    public void db_recordInsertSearchUpdateDelete(){
        /*
        ================= INSERT NEW =========================
         */
        //assign data
        task_model.eid = event_id;
        task_model.tname = "Get rings";
        task_model.category = "jewelry";
        task_model.description = "inform the shop to arrange the rings for the engagement";
        task_model.status = "pending";
        task_model.tdate="12-11-2020";

        //save to db and get the primary key of the record
         task_model.addTask();
        //read from db to new instance
        /*
        ================= SEARCH NSERTED ====================
         */
        task_model_result = task_model_result.getTaskById(task_id);//get saved record
        //assert
        assertEquals(task_model.tname,task_model_result.tname);

        /*
        ==================== UPDATE =========================
         */
        task_model = task_model.getTaskById(task_id);//get saved record
        //set another name
        String prev_name = task_model.tname;
        String newName = "Order Flowers";
        task_model.tname = newName;
        task_model.updateTask(task_model);
        //read from db to new instance
        task_model_result = task_model_result.getTaskById(task_id);//get saved record
        //assert update name should be 'Billy'
        assertEquals(newName,task_model_result.tname);
        //assert update name should NOT be 'James'
        assertNotEquals(prev_name,task_model_result.tname);

        /*
        ==================== REMOVE =========================
         */
        task_model.removeTask(task_id);
        task_model_result = task_model_result.getTaskById(task_id);
        assertNotEquals(task_model.tname,task_model_result.tname);
    }
    @Test
    public void db_InsertReadList(){
        /*
        =============== INSERT SETS OF DATA ==================
         */
        //assign dataset1
        String[] names = {"buy food","bring flowers","Add curtains"};
        String[] categories = {"Reception","Decoration","Decoration"};
        String[] descriptions = {"order food from a hotel","call florist","order curtains"};
        String[] statuses = {"pending","completed","completed"};
        String[] dates = {"12-11-2020","20-11-2020","10-12-2020"};

        for(int i=0;i<names.length;i++){
            task_model.tname = names[i];
            task_model.category = categories[i];
            task_model.description = descriptions[i];
            task_model.status = statuses[i];
            task_model.tdate = dates[i];

            //save to db
            task_model.addTask();
        }
        /*
        =============== RETRIEVE SETS OF DATA ==================
         */
        List<Task_Impl> list = new ArrayList<>();
        list = task_model_result.getTaskList();
        for(int i=0;i<list.size();i++){
            task_model = list.get(i); //get object element from list to class obj
            //assert with original
            assertEquals(names[i],task_model.tname);
            assertEquals(categories[i],task_model.category);
            assertEquals(descriptions[i],task_model.description);
            assertEquals(statuses[i],task_model.status);
            assertEquals(dates[i],task_model.tdate);

        }
    }
}


