package com.example.testapplication.db.event;



import java.util.List;

public interface IEvent {

    void AddEvent(String ename, String date, String time);
    void AddEvent();
    void selectEvent(Event_Impl obj);
    List<Event_Impl> getEventList();
    Event_Impl getEventById(int id);
    //pass id since name can be duplicated
    void removeEvent(int eventId);
    void updateEvent(String[] colNamesToUpdate, String[] values);
    void updateEvent(Event_Impl obj);


}
