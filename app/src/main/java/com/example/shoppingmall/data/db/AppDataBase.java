package com.example.shoppingmall.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoppingmall.app.MyApplication;
import com.example.shoppingmall.data.bean.User;
import com.example.shoppingmall.data.dao.UserDao;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static final String TAG = "AppDataBase";

    private static final String DB_NAME = "user_db";

    private static volatile AppDataBase mInstance = null;

    private AppDataBase() {
    }

    public static AppDataBase get() {
        if (mInstance == null) {
            synchronized (AppDataBase.class) {
                if (mInstance == null) {
                    mInstance = create();
                }
            }
        }
        return mInstance;
    }

    private static AppDataBase create() {
        return Room.databaseBuilder(MyApplication.getContext(), AppDataBase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public abstract UserDao getUserDao();
}
