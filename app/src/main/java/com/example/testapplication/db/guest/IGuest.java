package com.example.testapplication.db.guest;



import java.util.List;

public interface IGuest {

    void addGuest(String guestname, String gender, String age, String invitation, String phone, String email, String address);
    void addGuest();
    List<Guest_Impl> getGuestList();
    Guest_Impl getGuestById(int id);
    //pass id since name can be duplicated
    void removeGuest(int guestID);
    void updateGuest(String[] colNamesToUpdate, String[] values);
    void updateGuest(Guest_Impl obj);
}
