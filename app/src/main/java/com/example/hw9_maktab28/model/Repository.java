package com.example.hw9_maktab28.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {
    private static final Repository ourInstance = new Repository();

    private User loginedUser ;

    public static Repository getInstance() {
        return ourInstance;
    }

    private List<Task> taskList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

    public void addTask(Task task){
        task.setUserID(this.getLoginedUser().getUserId());
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

    public List getUserStateTaskList(State state , UUID userId){
        List<Task> stateTaskList = new ArrayList<>();
        for(Task task : getUserTaskList(userId)){
            if( task.getState().equals(state))
                stateTaskList.add(task);
        }
        return stateTaskList;
    }

    public List<Task> getUserTaskList(UUID userId){
        List<Task> userTaskList = new ArrayList();
        for(Task task : taskList)
            if(task.getUserID().equals(userId))
                userTaskList.add(task);

            return userTaskList;
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

    public User getUser(User user){
        for(User u : userList)
            if(u.equals(user))
                return u;

            return null;
    }

    public void addUser(User user)
    {
        userList.add(user);
    }

    public List<User> getUserList()
    {
        return userList;
    }

    public void setLoginedUser(User user){
        loginedUser = user;
    }

    public User getLoginedUser(){
        return loginedUser;
    }


}
