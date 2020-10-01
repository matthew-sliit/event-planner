package com.example.testapplication.db.vendor;

import java.util.List;

public interface IVendor {
    void addVendor(String name, String category, double amount, String number, String address, String email);

    // void addPayment(String name,String amount,String note);
    void addVendor();

    void removeVendor(int id);

    void updateVendor(Vendor_impl obj);

    List getVendor();

    Vendor_impl getVendorbyid(int id);

    int addVendorGetid();

    boolean hasVendor(String name, String email);
}

   // boolean hasVendor(String toString);

