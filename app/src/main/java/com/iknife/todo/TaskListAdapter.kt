package com.iknife.todo

import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.task_entry.view.*

class TaskListAdapter(private val tasksCollection : List<Task>, private val holderOnClick: (Task) -> Unit) : RecyclerView.Adapter<TaskListAdapter.TaskHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskHolder{
        val inflatedView = parent.inflate(R.layout.task_entry, false)
        return TaskHolder(inflatedView, holderOnClick)
    }

    override fun getItemCount(): Int = tasksCollection.size

    override fun onBindViewHolder(holder: TaskListAdapter.TaskHolder, position: Int) {
        val taskItem = tasksCollection[position]
        holder.bindTask(taskItem)


    }

    class TaskHolder(v: View, private val holderOnClick: (Task) -> Unit) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var context: Context = v.context
        private var view = v
        private var task: Task = Task(999, "No label")

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            holderOnClick(this.task)
            val intent = Intent(context,TaskInfoActivity::class.java)
            context.startActivity(intent)
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

