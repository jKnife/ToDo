package com.iknife.todo

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.task_entry.view.*

class TaskListAdapter(private val tasksCollection : ArrayList<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskHolder{
        val inflatedView = parent.inflate(R.layout.task_entry, false)
        return TaskHolder(inflatedView)
    }

    override fun getItemCount(): Int = tasksCollection.size

    override fun onBindViewHolder(holder: TaskListAdapter.TaskHolder, position: Int) {
        val taskItem = tasksCollection[position]
        holder.bindTask(taskItem)
    }

    class TaskHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view = v
        private var task: Task = Task("No label")

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.e("TaskView", "Item pressed")
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

