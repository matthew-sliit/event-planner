package com.example.testapplication;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.test.RenamingDelegatingContext;

import androidx.test.InstrumentationRegistry;


import com.example.testapplication.db.budget.Budget_Impl_updated;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


public class BudgetDBUnitTest {
    private Budget_Impl_updated budget_model, budget_model_result;
    private int event_id = 0, budget_id = 0;
    @Before
    public void setUp() {
        Context context = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
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
        budget_model.name = "James";
        budget_model.amt = String.valueOf(20000.00);
        budget_model.cat = "Chocolates";
        budget_model.desc = "Mr.Willy Wonka Chocolates";
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
        String newName = "Billy";
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
        try{
            budget_model_result = budget_model_result.getBudgetById(event_id,budget_id);
            assertNotEquals(budget_model.name,budget_model_result.name);
        }catch (CursorIndexOutOfBoundsException ignored){

        }
    }
    @Test
    public void db_InsertReadList(){

    }
}
