package com.example.testapplication.db.budget;

import java.util.List;

public interface Ibudget {
    void addBudget(String budget_name,String catName, double amt, String desc);
    void addBudget();
    int addBudgetGetId();
    List<Budget_Impl_updated> getBudgetList();
    List<Budget_Impl_updated> getBudgetListByCategory(String category);
    Budget_Impl_updated getBudgetById(int eid, int id);
    //pass id since name can be duplicated
    void removeBudget(int budgetID);
    void updateBudget(String[] colNamesToUpdate, String[] values);
    void updateBudget(Budget_Impl_updated obj);
}
