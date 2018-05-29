package com.iknife.todo

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.iknife.todo.R.styleable.SwipeRevealLayout
import com.iknife.todo.database.TaskData
import com.iknife.todo.database.TasksDatabase
import kotlinx.android.synthetic.main.task_entry.view.*

class TaskListAdapter(private val tasksCollection : MutableList<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskHolder>(){

    private val viewBinderHelper :ViewBinderHelper =  ViewBinderHelper()


    fun Adapter()
    {
        viewBinderHelper.setOpenOnlyOne(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskHolder{
        val inflatedView = parent.inflate(R.layout.task_entry, false)
        return TaskHolder(inflatedView)
    }

    override fun getItemCount(): Int = tasksCollection.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val taskItem = tasksCollection[position]
        viewBinderHelper.bind(holder.swipeRevealLayout,taskItem.id.toString())
        holder.bindTask(taskItem)

    }

    fun removeTask(position: Int, database: TasksDatabase?){
        Log.e("TasksDatabase", "Deleting task id:${tasksCollection[position].id}")
        database?.tasksDataDao()?.deleteTask(TaskData(tasksCollection[position].id, tasksCollection[position].label))
        notifyItemRemoved(position)
        tasksCollection.removeAt(position)
    }

    class TaskHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private val context: Context = v.context
        private var view = v
        private var task: Task = Task(999, "No label")

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            //Item on click
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

