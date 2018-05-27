package com.iknife.todo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
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

        private var view = v
        private var task: Task = Task(999, "No label")

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            holderOnClick(this.task)
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

