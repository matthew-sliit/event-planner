package com.example.testapplication;

import android.content.Context;

import com.example.testapplication.db.budget.Budget_Impl_updated;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

/*
================== BUDGET model and Database Unit Tests ======================
 */
@RunWith(RobolectricTestRunner.class)
public class BudgetDBUnitTest {
    private Budget_Impl_updated budget_model, budget_model_result;
    private int event_id = 0, budget_id = 0;
    private Context context;
    @Before
    public void setUp() {
        //context = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
        context = RuntimeEnvironment.application;
        budget_model = new Budget_Impl_updated(context,0);
        budget_model_result = new Budget_Impl_updated(context,0);
    }
    @Test
    public void db_recordInsertSearchUpdateDelete(){
        /*
        ================= INSERT NEW =========================
         */
        //assign data
        budget_model.eid = event_id;
        budget_model.name = "Academy Junior Association";
        budget_model.amt = String.valueOf(20000.00);
        budget_model.cat = "Accessories";
        budget_model.desc = "Exercise books, Pencil cases, color books";
        //save to db and get the primary key of the record
        budget_id = budget_model.addBudgetGetId();
        //read from db to new instance
        /*
        ================= SEARCH NSERTED ====================
         */
        budget_model_result = budget_model_result.getBudgetById(event_id,budget_id);//get saved record
        //assert
        assertEquals(budget_model.name,budget_model_result.name);

        /*
        ==================== UPDATE =========================
         */
        budget_model = budget_model.getBudgetById(event_id,budget_id);//get saved record
        //set another name
        String prev_name = budget_model.name;
        String newName = "Academy Senior Association";
        budget_model.name = newName;
        budget_model.updateBudget(budget_model);
        //read from db to new instance
        budget_model_result = budget_model_result.getBudgetById(event_id,budget_id);//get saved record
        //assert update name should be 'Billy'
        assertEquals(newName,budget_model_result.name);
        //assert update name should NOT be 'James'
        assertNotEquals(prev_name,budget_model_result.name);

        /*
        ==================== REMOVE =========================
         */
        budget_model.removeBudget(budget_id);
        budget_model_result = budget_model_result.getBudgetById(event_id,budget_id);
        assertNotEquals(budget_model.name,budget_model_result.name);
    }
    @Test
    public void db_InsertReadList(){
        /*
        =============== INSERT SETS OF DATA ==================
         */
        //assign dataset1
        String[] names = {"2015","2016","2017"};
        Double[] amt = {2500.00,48000.00,110000.00};
        String[] category = {"Basement","Glass Doors","Pool"};
        String[] desc = {"Concrete,iron bars","White tinted","10ft deep, dark blue tiles"};
        for(int i=0;i<names.length;i++){
            budget_model.name = names[i];
            budget_model.setAmt(amt[i]);
            budget_model.cat = category[i];
            budget_model.desc = desc[i];
            //save to db
            budget_model.addBudget();
        }
        /*
        =============== RETRIEVE SETS OF DATA ==================
         */
        List<Budget_Impl_updated> list = new ArrayList<>();
        list = budget_model_result.getBudgetList();
        for(int i=0;i<list.size();i++){
            budget_model = list.get(i); //get object element from list to class obj
            //assert with original
            assertEquals(names[i],budget_model.name);
            assertEquals(amt[i],budget_model.getAmt());
            assertEquals(category[i],budget_model.cat);
            assertEquals(desc[i],budget_model.desc);
        }
    }
}
