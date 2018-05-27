package com.iknife.todo.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.iknife.todo.Task

@Dao
interface TasksDataDao{
    @Query("SELECT * FROM taskData")
    fun getTasks(): List<Task>

    @Query("DELETE FROM taskData")
    fun purgeTasks()

    @Insert(onConflict = REPLACE)
    fun addTask(taskData: TaskData)
}