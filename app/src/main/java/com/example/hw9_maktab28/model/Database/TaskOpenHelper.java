package com.example.hw9_maktab28.model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.hw9_maktab28.model.Database.TaskDBSchema.NAME;

public class TaskOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public TaskOpenHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TaskDBSchema.Task.NAME + "(" +
                TaskDBSchema.Task.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskDBSchema.Task.Cols.UUID + ", " +
                TaskDBSchema.Task.Cols.TITLE + ", " +
                TaskDBSchema.Task.Cols.DESCRIPTION + ", " +
                TaskDBSchema.Task.Cols.DATE + ", " +
                TaskDBSchema.Task.Cols.STATE + ", " +
                TaskDBSchema.Task.Cols.USERID +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE " + UserDBSchema.User.NAME + "(" +
                UserDBSchema.User.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserDBSchema.User.Cols.UUID + ", " +
                UserDBSchema.User.Cols.USERNAME + ", " +
                UserDBSchema.User.Cols.PASSWORD +
                ");");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
