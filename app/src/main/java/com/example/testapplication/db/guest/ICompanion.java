package com.example.testapplication.db.guest;

import java.util.List;

public interface ICompanion {
    void addCom(String cname, String gender, String age, String note);
    void addCom(int eid,int gid);
    List<Companion_Impl> getComList(int eid,int gid);
    Companion_Impl getComById(int id,int eid,int gid);
    //pass id since name can be duplicated
    void removeCom(int comID,int eid,int gid);
    void updateCom(String[] colNamesToUpdate, String[] values);
    void updateCom(Companion_Impl obj);
}
