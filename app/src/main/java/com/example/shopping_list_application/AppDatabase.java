package com.example.shopping_list_application;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Lists.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase databaseInstance = null;

    public static AppDatabase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(
                            context.getApplicationContext(), AppDatabase.class, "UsersDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return databaseInstance;
    }
    public abstract UserDao userDao();
    public abstract ListsDao listsDao();
}

