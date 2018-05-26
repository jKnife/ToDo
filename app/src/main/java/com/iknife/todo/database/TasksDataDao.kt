package com.iknife.todo.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.iknife.todo.Task

@Dao
interface TasksDataDao{
    @Query("SELECT * FROM tasksData")
    fun getTasks(): ArrayList<Task>

    @Query("DELETE FROM tasksData")
    fun purgeTasks()

    @Insert(onConflict = REPLACE)
    fun addTask(tasksData: TasksData)
}