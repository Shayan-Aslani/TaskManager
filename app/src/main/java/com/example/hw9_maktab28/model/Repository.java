package com.example.hw9_maktab28.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.hw9_maktab28.model.Database.TaskDBSchema;
import com.example.hw9_maktab28.model.Database.TaskOpenHelper;
import com.example.hw9_maktab28.model.Database.UserDBSchema;

import org.greenrobot.greendao.AbstractDaoMaster;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Repository {
    private static Repository ourInstance;

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private User loginedUser;

    public static Repository getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new Repository(context);

        return ourInstance;
    }

    private Repository(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskOpenHelper(mContext).getWritableDatabase();
    }


    public void insertTask(Task task) {
        task.setUserID(this.getLoginedUser().getUserId());
        ContentValues values = getTaskContentValues(task);
        mDatabase.insertOrThrow(TaskDBSchema.Task.NAME, null, values);
    }

    public void addUser(User user) {
        ContentValues values = getUserContentValues(user);
        mDatabase.insertOrThrow(UserDBSchema.User.NAME, null, values);
    }


    private Cursor queryTask(String where, String[] whereArgs) {
        return mDatabase.query(TaskDBSchema.Task.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);
    }

    private Cursor queryUser(String where, String[] whereArgs) {
        return mDatabase.query(UserDBSchema.User.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

    }

    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();

        Cursor cursor = queryTask(null, null);

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                String strUUID = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.UUID));
                String title = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.TITLE));
                String description = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.DESCRIPTION));
                long date = cursor.getLong(cursor.getColumnIndex(TaskDBSchema.Task.Cols.DATE));
                String strUserID = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.USERID));
                String strState = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.STATE));


                Task task = new Task(UUID.fromString(strUUID));
                task.setTitle(title);
                task.setDescription(description);
                task.setDate(new Date(date));
                task.setUserID(UUID.fromString(strUserID));
                task.setState(State.getStateFromString(strState));

                taskList.add(task);

                cursor.moveToNext();
            }

        } finally {
            cursor.close();
        }

        return taskList;
    }


    public Task getTask(UUID uuid) {
        String[] whereArgs = new String[]{uuid.toString()};
        Cursor cursor = queryTask(TaskDBSchema.Task.Cols.UUID + " = ?", whereArgs);

        try {
            if (cursor == null || cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();

            String strUUID = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.UUID));
            String title = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.TITLE));
            String description = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.DESCRIPTION));
            long date = cursor.getLong(cursor.getColumnIndex(TaskDBSchema.Task.Cols.DATE));
            String strUserID = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.USERID));
            String strState = cursor.getString(cursor.getColumnIndex(TaskDBSchema.Task.Cols.STATE));

            Task task = new Task(UUID.fromString(strUUID));
            task.setTitle(title);
            task.setDescription(description);
            task.setDate(new Date(date));
            task.setState(State.Todo);
            task.setUserID(UUID.fromString(strUserID));
            task.setState(State.getStateFromString(strState));

            return task;

        } finally {
            cursor.close();
        }
    }

    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();

        Cursor cursor = queryUser(null, null);

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                String strUUID = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.UUID));
                String username = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.PASSWORD));

                User user = new User(UUID.fromString(strUUID));
                user.setUsername(username);
                user.setPassword(password);

                userList.add(user);

                cursor.moveToNext();
            }

        } finally {
            cursor.close();
        }

        return userList;
    }


    public User getUser(String inputUsername, String inputPassword) {
        String[] whereArgs = new String[]{inputUsername};
        Cursor cursor = queryUser(UserDBSchema.User.Cols.USERNAME + " = ?", whereArgs);

        try {
            if (cursor == null || cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();

            String strUUID = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.UUID));
            String username = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.PASSWORD));

            User user = new User(UUID.fromString(strUUID));
            user.setUsername(username);
            user.setPassword(password);

            return user;

        } finally {
            cursor.close();
        }
    }

    public User getUser(UUID uuid) {
        String[] whereArgs = new String[]{uuid.toString()};
        Cursor cursor = queryUser(UserDBSchema.User.Cols.UUID + " = ?", whereArgs);

        try {
            if (cursor == null || cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();

            String strUUID = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.UUID));
            String username = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.PASSWORD));
            String role = cursor.getString(cursor.getColumnIndex(UserDBSchema.User.Cols.ROLE));

            User user = new User(UUID.fromString(strUUID));
            user.setUsername(username);
            user.setPassword(password);
            if (role.equals("USER"))
                user.setRole(Role.USER);
            else
                user.setRole(Role.ADMIN);

            return user;

        } finally {
            cursor.close();
        }
    }



    public void updateTask(Task task) throws Exception {
        ContentValues values = getTaskContentValues(task);
        String where = TaskDBSchema.Task.Cols.UUID + " = ?";
        String[] whereArgs = new String[]{task.getId().toString()};
        mDatabase.update(TaskDBSchema.Task.NAME, values, where, whereArgs);
    }

    public void deleteTask(Task task) {
        Task c = getTask(task.getId());
        String where = TaskDBSchema.Task.Cols.UUID + " = ?";
        String[] whereArgs = new String[]{task.getId().toString()};
        mDatabase.delete(TaskDBSchema.Task.NAME, where, whereArgs);

    }


    private ContentValues getTaskContentValues(Task task) {
        ContentValues taskValues = new ContentValues();
        taskValues.put(TaskDBSchema.Task.Cols.UUID, task.getId().toString());
        taskValues.put(TaskDBSchema.Task.Cols.TITLE, task.getTitle());
        taskValues.put(TaskDBSchema.Task.Cols.DESCRIPTION, task.getDescription());
        taskValues.put(TaskDBSchema.Task.Cols.DATE, task.getDate().getTime());
        taskValues.put(TaskDBSchema.Task.Cols.USERID, task.getUserID().toString());
        taskValues.put(TaskDBSchema.Task.Cols.STATE, task.getState().toString());

        return taskValues;
    }

    private ContentValues getUserContentValues(User user) {
        ContentValues userValues = new ContentValues();
        userValues.put(UserDBSchema.User.Cols.UUID, user.getUserId().toString());
        userValues.put(UserDBSchema.User.Cols.USERNAME, user.getUsername());
        userValues.put(UserDBSchema.User.Cols.PASSWORD, user.getPassword());
        userValues.put(UserDBSchema.User.Cols.ROLE , user.getRole().name());

        return userValues;
    }

    public List getUserStateTaskList(State state, UUID userId) {
        List<Task> stateTaskList = new ArrayList<>();
        for (Task task : getUserTaskList(userId)) {
            if (task.getState().equals(state))
                stateTaskList.add(task);
        }
        return stateTaskList;


    }

    public void removeUserTaskList(UUID userId) throws Exception {
        if (getUserTaskList(userId).size() == 0)
            return;
        for (Task task : getUserTaskList(userId))
            if (task.getUserID().equals(userId))
                deleteTask(task);

    }

    public List<Task> getUserTaskList(UUID userId) {
        List<Task> userTaskList = new ArrayList();
        List<Task> list = getTasks();
        for (Task task : list)
            if (task.getUserID().equals(userId))
                userTaskList.add(task);

        return userTaskList;
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
