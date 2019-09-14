package com.example.hw9_maktab28.model;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static final Repository ourInstance = new Repository();

    public static Repository getInstance() {
        return ourInstance;
    }

    private List<Task> taskList = new ArrayList<>();

    public void addTask(Task task){
        taskList.add(task);
    }

    public List getTaskList()
    {
        return taskList;
    }
}
