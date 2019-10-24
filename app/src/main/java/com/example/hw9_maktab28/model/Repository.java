package com.example.hw9_maktab28.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.example.hw9_maktab28.greendao.StateConverter;
import com.example.hw9_maktab28.greendao.TaskOpenHelper;

import org.greenrobot.greendao.AbstractDaoMaster;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Repository {
    private static Repository ourInstance;

    private UserDao userDao;
    private TaskDao taskDao;
    private Context mContext;
    private User loginedUser;

    public static Repository getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new Repository(context);

        return ourInstance;
    }

    private Repository(Context context) {
        mContext = context.getApplicationContext();
        SQLiteDatabase db = new TaskOpenHelper(mContext).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        userDao = daoSession.getUserDao();
        taskDao = daoSession.getTaskDao();

    }


    public void insertTask(Task task) {
        task.setUserID(this.getLoginedUser().getUserId());
        taskDao.insert(task);
    }

    public void addUser(User user) {
        userDao.insert(user);
    }


    public List<Task> getTasks() {
        return taskDao.loadAll();
    }


    public Task getTask(UUID uuid) {
        return taskDao.queryBuilder()
                .where(TaskDao.Properties.Uuid.eq(uuid))
                .unique();
    }

    public List<User> getUserList() {
        return userDao.loadAll();
    }


    public User getUser(String inputUsername, String inputPassword) {
        return userDao.queryBuilder()
                .where(UserDao.Properties.Username.eq(inputUsername))
                .where(UserDao.Properties.Password.eq(inputPassword))
                .unique();
    }

    public User getUser(UUID uuid) {
        return userDao.queryBuilder()
                .where(UserDao.Properties.UserId.eq(uuid))
                .unique();
    }


    public void updateTask(Task task) throws Exception {
        taskDao.update(task);
    }

    public void deleteTask(Task task) throws Exception {
        taskDao.delete(task);
    }

    public void deleteUser(User user) throws Exception {
        userDao.delete(user);
    }

    public List getUserStateTaskList(State state, UUID userId) {

        return taskDao.queryBuilder()
                .where(TaskDao.Properties.UserID.eq(userId))
                .where(TaskDao.Properties.State.eq(state.getI()))
                .list();

    /*  List<Task> list = getUserTaskList(userId);
     List<Task> stateList = new ArrayList<>();
     for (Task task : list)
         if(task.getState().equals(state))
             stateList.add(task);

         return stateList;

    */
    }

    public void removeUserTaskList(UUID userId) throws Exception {
        if (getUserTaskList(userId).size() == 0)
            return;
        for (Task task : getUserTaskList(userId))
            if (task.getUserID().equals(userId))
                deleteTask(task);
    }

    public List<Task> getUserTaskList(UUID userId) {

        return taskDao.queryBuilder()
                .where(TaskDao.Properties.UserID.eq(userId))
                .list();
    }

    public List<Task> getAdminTaskList(State state){
        return taskDao.queryBuilder()
                .where(TaskDao.Properties.State.eq(state.getI()))
                .list();
    }


    public void setLoginedUser(UUID userid) {
        loginedUser = getUser(userid);
    }

    public User getLoginedUser() {
        return loginedUser;
    }

    public File getPhotoFile(Task task) {
        return new File(mContext.getFilesDir(), task.getPhotoName());
    }

}
