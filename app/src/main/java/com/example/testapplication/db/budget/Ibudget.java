package com.example.testapplication.db.budget;

import java.lang.reflect.Type;
import java.util.List;

public interface Ibudget {
    void addBudget(String budget_name,String catName, double amt, String desc);
    List<Budget_Impl> getBudgetList();
    Budget_Impl getBudgetById(int id);
    //pass id since name can be duplicated
    void removeBudget(int budgetID);
    void updateBudget(String[] colNamesToUpdate, String[] values);
    void updateBudget(Budget_Impl obj);
}
