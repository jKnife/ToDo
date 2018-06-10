package com.iknife.todo

import com.iknife.todo.database.TaskData

data class Task(val id: Long, val label: String, var completed: Boolean){
    fun toggleCompleted() {
        this.completed = completed.not()
    }

    fun toTaskData(): TaskData {
        return TaskData(this.id, this.label, this.completed)
    }
}