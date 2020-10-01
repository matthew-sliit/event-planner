/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  androidx.databinding.BaseObservable
 *  androidx.databinding.Bindable
 *  com.vlabs.eventplanner.appBase.roomsDB.vendor.VendorRowModel
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.vlabs.eventplanner.appBase.models.vendor;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.vlabs.eventplanner.appBase.roomsDB.vendor.VendorRowModel;
import java.util.ArrayList;

public class VendorListModel
extends BaseObservable {
    private ArrayList<VendorRowModel> arrayList;
    private String noDataDetail;
    private int noDataIcon;
    private String noDataText;

    @Bindable
    public ArrayList<VendorRowModel> getArrayList() {
        return this.arrayList;
    }

    public String getNoDataDetail() {
        return this.noDataDetail;
    }

    public int getNoDataIcon() {
        return this.noDataIcon;
    }

    public String getNoDataText() {
        return this.noDataText;
    }

    public boolean isListData() {
        return this.getArrayList() != null && this.getArrayList().size() > 0;
    }

    public void setArrayList(ArrayList<VendorRowModel> arrayList) {
        this.arrayList = arrayList;
        this.notifyChange();
    }

    public void setNoDataDetail(String string) {
        this.noDataDetail = string;
    }

    public void setNoDataIcon(int n) {
        this.noDataIcon = n;
    }

    public void setNoDataText(String string) {
        this.noDataText = string;
    }
}

