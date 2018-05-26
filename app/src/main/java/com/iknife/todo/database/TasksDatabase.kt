package com.iknife.todo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(TasksData::class)],version = 1)
abstract class TasksDatabase : RoomDatabase(){
    abstract fun TasksDataDao() : TasksDataDao

    companion object {
        private var INSTANCE: TasksDatabase? = null

        fun getInstance(context: Context): TasksDatabase? {
            if (INSTANCE == null) {
                synchronized(TasksDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            TasksDatabase::class.java, "tasks.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}