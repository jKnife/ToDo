package com.iknife.todo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.iknife.todo.database.TaskData
import com.iknife.todo.database.TasksDatabase
import kotlinx.android.synthetic.main.task_entry.view.*

class TaskListAdapter(private val tasksCollection : MutableList<Task>, private val database: TasksDatabase?, private val updateFunction: () -> Unit) : RecyclerView.Adapter<TaskListAdapter.TaskHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskHolder{
        val inflatedView = parent.inflate(R.layout.task_entry, false)
        return TaskHolder(inflatedView, database, tasksCollection, this, updateFunction)
    }

    override fun getItemCount(): Int = tasksCollection.size

    override fun onBindViewHolder(holder: TaskListAdapter.TaskHolder, position: Int) {
        val taskItem = tasksCollection[position]
        holder.bindTask(taskItem)


    }

    fun removeTask(position: Int, database: TasksDatabase?){
        val task = tasksCollection[position-1]
        Log.e("TasksDatabase", "Deleting task: $task")
        database?.tasksDataDao()?.deleteTask(task.toTaskData())
        notifyItemRemoved(position)
        tasksCollection.removeAt(tasksCollection.indexOfFirst { it == task })
        updateFunction()
    }

    fun completeTask(position: Int, database: TasksDatabase?){
        val task = tasksCollection[position-1]
        task.toggleCompleted()
        Log.i("CREATED TASK", "${task.id}, ${task.label}, ${task.completed}")
        database?.tasksDataDao()?.addTask(task.toTaskData())
        tasksCollection.add(task)
        notifyItemRemoved(position)
        tasksCollection.removeAt(position)
    }

    class TaskHolder(v: View, private val database: TasksDatabase?, private val tasksCollection: MutableList<Task>, private val adapter: TaskListAdapter, private val updateFunction: () -> Unit) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private val context: Context = v.context
        private var view = v
        private var task: Task = Task(999, "No label", false)

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val newTask : Task = tasksCollection.first { it == task }
            Log.i("COMPLETE", "Pre toggle $newTask")
            newTask.toggleCompleted()
            Log.i("COMPLETE", "Post toggle $newTask")

            database?.tasksDataDao()?.addTask(newTask.toTaskData())
            Log.i("COMPLETE", "Pre add $newTask")
            tasksCollection.add(newTask)
            Log.i("COMPLETE", "Post add $newTask")

            val oldTaskPosition = if(!task.completed){
                tasksCollection.indexOf(task)+1
            } else {
                tasksCollection.indexOf(task)-1
            }

            Log.i("COMPLETE", "oldTaskPosition $oldTaskPosition")

            adapter.notifyItemRemoved(oldTaskPosition)
            Log.i("COMPLETE", "Pre remove ${tasksCollection.first { it == task }}")
            tasksCollection.remove(tasksCollection.first { it == task })
            Log.i("COMPLETE", "Post remove ${tasksCollection.first { it == task }}")


            updateFunction()
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

