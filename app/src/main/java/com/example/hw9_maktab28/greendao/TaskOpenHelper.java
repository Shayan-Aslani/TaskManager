package com.example.hw9_maktab28.greendao;

import android.content.Context;

import com.example.hw9_maktab28.model.DaoMaster;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

public class TaskOpenHelper extends DaoMaster.DevOpenHelper {

    private static final String NAME = "Task.db";

    public TaskOpenHelper(Context context) {
        super(context, NAME);
    }
}
