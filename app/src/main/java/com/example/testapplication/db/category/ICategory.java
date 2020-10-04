package com.example.testapplication.db.category;

import java.util.List;

public interface ICategory {
    String addCategory(String name);
    void setDefault(String[] defaultArray);
    List getAllCategory();
    void deleteCategory(String name);
    void deleteCategoryWithDependencies(String categoryName);
    void updateCategory(String prev_name, String new_name);
    boolean hasCategory(String catName);
}
