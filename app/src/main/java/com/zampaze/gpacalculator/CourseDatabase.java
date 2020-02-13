package com.zampaze.gpacalculator;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CourseDetail.class}, version = 1, exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase {
    private static final String LOG_TAG = CourseDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "course_history_database";
    private static CourseDatabase sInstance;

    public static CourseDatabase getInstance(Context context) {
        if (sInstance == null) {
            // create for first time
            synchronized (LOCK) {
                Log.i(LOG_TAG, "Creating new Database Instance");
                // create database if not exist, else connect to existing
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CourseDatabase.class, CourseDatabase.DATABASE_NAME)
                        // allow same thread queries only for testing
//                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.i(LOG_TAG, "Getting the Database Instance");
        return sInstance;
    }

    public abstract CourseDatabaseDao courseDatabaseDao();
}
