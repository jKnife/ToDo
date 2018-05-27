package com.iknife.todo

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.iknife.todo.database.TaskData
import com.iknife.todo.database.TasksDatabase
import kotlinx.android.synthetic.main.task_entry.view.*

class TaskListAdapter(private val tasksCollection : List<Task>, private val databaseInstance: TasksDatabase?) : RecyclerView.Adapter<TaskListAdapter.TaskHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskHolder{
        val inflatedView = parent.inflate(R.layout.task_entry, false)
        return TaskHolder(inflatedView, databaseInstance, tasksCollection)
    }

    override fun getItemCount(): Int = tasksCollection.size

    override fun onBindViewHolder(holder: TaskListAdapter.TaskHolder, position: Int) {
        val taskItem = tasksCollection[position]
        holder.bindTask(taskItem)
    }

    class TaskHolder(v: View, private val databaseInstance: TasksDatabase?, private val tasksCollection: List<Task>) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view = v
        private var task: Task = Task(999, "No label")

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.e("TaskView", "Deleting task id:${task.id}")
            databaseInstance?.tasksDataDao()?.deleteTask(TaskData(task.id, task.label))
            tasksCollection.drop(tasksCollection.first{it.id == task.id}.id.toInt())
        }

        companion object {
            private val TASK_KEY = "TASK"
        }

        fun bindTask(task: Task){
            this.task = task
            view.label.text = task.label
        }
    }

}

