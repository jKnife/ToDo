package com.iknife.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import com.iknife.todo.database.TaskData
import com.iknife.todo.database.TasksDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input_bar.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TaskListAdapter
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mToggle: ActionBarDrawerToggle
    private var tasksList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Get database instance
        val database = TasksDatabase.getInstance(this)

        //Setup RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        task_list.layoutManager = linearLayoutManager
        adapter = TaskListAdapter(tasksList)
        task_list.adapter = adapter

        //Implement fling callback
        val flingCallback = object: FlingToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                adapter.removeTask(viewHolder!!.adapterPosition, database)
            }
        }
        //Attach Touch Helper to Recycler View
        val taskTouchHelper = ItemTouchHelper(flingCallback)
        taskTouchHelper.attachToRecyclerView(task_list)

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
