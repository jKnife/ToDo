package com.iknife.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import com.iknife.todo.database.TaskData
import com.iknife.todo.database.TasksDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input_bar.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TaskListAdapter

    private var tasksList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get database instance
        val database = TasksDatabase.getInstance(this)

        //Setup RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        task_list.layoutManager = linearLayoutManager
        adapter = TaskListAdapter(tasksList){
            val task = it
            Log.e("TasksDatabase", "Deleting task id:${task.id}")
            database?.tasksDataDao()?.deleteTask(TaskData(task.id, task.label))
            val taskInList = tasksList.first{it.id == task.id}
            this.adapter.notifyItemRemoved(tasksList.indexOf(taskInList))
            tasksList.remove(taskInList)
        }
        task_list.adapter = adapter

        //Populate RecyclerView with data from database
        val tasksFromDB = database?.tasksDataDao()?.getTasks()!!
        tasksFromDB.forEach {
            tasksList.add(it)
        }

        //Set Input Bar keyboard-submit to create a new task and add it to both DB and in-memory List
        input_bar.setOnEditorActionListener { _, actionId, _ ->
            val handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val input = input_bar.text.toString()
                database.tasksDataDao().addTask(TaskData(null, input))
                tasksList.add(Task(tasksList.size.toLong(), input))
                input_bar.text.clear()
            }
            handled
        }


    }
}
