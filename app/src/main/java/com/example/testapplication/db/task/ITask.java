package com.example.testapplication.db.task;


import java.util.Date;
import java.util.List;

public interface ITask {

    void addTask(String tname, String category, String desc, String status, String date);
    void addTask();
    List<Task_Impl> getTaskList();
    Task_Impl getTaskById(int id);
    //pass id since name can be duplicated
    void removeTask(int taskID);
    void updateTask(String[] colNamesToUpdate, String[] values);
    void updateTask(Task_Impl obj);

}
