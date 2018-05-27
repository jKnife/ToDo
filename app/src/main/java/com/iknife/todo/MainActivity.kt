package com.iknife.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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

        //Setup RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        task_list.layoutManager = linearLayoutManager
        adapter = TaskListAdapter(tasksList)
        task_list.adapter = adapter

        //Get database instance
        val database = TasksDatabase.getInstance(this)

        //Populate RecyclerView with data from database
        val tasksFromDB = database?.tasksDataDao()?.getTasks()!!
        tasksFromDB.forEach {
            tasksList.add(it)
        }

        //Set Input Bar keyboard-submit to create a new task and add it to both DB and in-memory List
        input_bar.setOnEditorActionListener { _, actionId, _ ->
            val handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val newTask = Task(input_bar.text.toString())
                database.tasksDataDao().addTask(TaskData(null, newTask.label))
                tasksList.add(newTask)
            }
            handled
        }


    }
}
