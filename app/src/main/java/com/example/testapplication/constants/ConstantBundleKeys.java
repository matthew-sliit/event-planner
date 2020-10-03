package com.example.testapplication.constants;

import android.content.Intent;
import android.os.Bundle;

import com.example.testapplication.AppSettingsActivity;

public class ConstantBundleKeys {
    public static final String ID = "id";
    public static final String EVENT_ID = "eid";
    //budget root
    public static final String TITLE = "title";
    public static final String PRE_TITLE = "pre_title";
    public static final String PRE_ACTIVITY = "pre_activity";
    public static final String BUDGET_ID = "bid";
    public static final String IS_IN_SETTING = "is_in_settings";
    public static final String SET_TO_CATEGORY = "set_to_cat";
    public static final String EDIT_CATEGORY_MODE = "cat_edit";
    public static final String BUDGET_PAY_ID = "pid";

    public static Bundle getBundleSkipSettings2Category(){
        Bundle b = new Bundle();
        b.putString("n","n");
        /*
        b.putString("edit","Manage Category");
        b.putString(ConstantBundleKeys.IS_IN_SETTING,"true");
        b.putString(ConstantBundleKeys.PRE_ACTIVITY,"home");
        b.putString(ConstantBundleKeys.TITLE,"Manage Categories");
         */
        b.putString(ConstantBundleKeys.PRE_ACTIVITY,"edit category");
        b.putString(ConstantBundleKeys.IS_IN_SETTING,"true");
        b.putString("edit","Manage Category");
        b.putString(ConstantBundleKeys.TITLE,"Manage Category");
        b.putString(ConstantBundleKeys.SET_TO_CATEGORY,"true");
        return b;
    }
    //guest root


}
