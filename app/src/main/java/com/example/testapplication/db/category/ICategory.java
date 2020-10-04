package com.example.testapplication.db.category;

import java.util.List;

public interface ICategory {
    String addCategory(String name);
    List getAllCategory();
    void deleteCategory(String name);
    void updateCategory(String prev_name, String new_name);
    boolean hasCategory(String catName);
}
