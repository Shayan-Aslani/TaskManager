package com.example.hw9_maktab28.model;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public Task getTask(UUID uuid) {
        for (Task task : taskList)
            if (task.getId().equals(uuid))
                return task;

        return null;
    }

    public List getStateTaskList(State state){
        List<Task> stateTaskList = new ArrayList<>();
        for(Task task : taskList){
            if(task.getState().equals(state))
                stateTaskList.add(task);
        }
        return stateTaskList;
    }

    public void updateTask(Task task) throws Exception {
        Task t = getTask(task.getId());
        if (t == null)
            throw new Exception("This task does not exist!!!");

        t.setTitle(task.getTitle());
        t.setDate(task.getDate());
        t.setDescription(task.getDescription());
        t.setState(task.getState());
    }

    public void deleteTask(Task task) throws Exception {
        Task t = getTask(task.getId());
        if (t == null)
            throw new Exception("This task does not exist!!!");

        taskList.remove(t);
    }


}
