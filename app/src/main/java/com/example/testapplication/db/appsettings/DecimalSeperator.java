package com.example.testapplication.db.appsettings;

import android.app.Application;

public class DecimalSeperator {
    private static DecimalSeperator instance = null;
    public static DecimalSeperator getInstance(){
        if(instance == null) {
            instance = new DecimalSeperator();
        }
        return instance;
    }
}
