package com.example.testapplication.db.vendor;

import java.util.List;

public interface IVendor_pay {
    void addPayment(String name,String amount,String status,String note);
    //void addPayment();
    public void removePayment(int id);
    public void updatePayment(Vendor_pay_Impl obj);
        List getPayment();
    public Vendor_pay_Impl getVendorPaybyid(int id,int vendorid,int eventid);
    public List<Vendor_pay_Impl> getPayment(int eventid,int vendorid);
    public void removePayment(int id,int eid,int vendor_id);
    public void addPayment(int event_id, int vendor_id);
    //Vendor_pay_Impl getVendorPaybyid(int id);




}
