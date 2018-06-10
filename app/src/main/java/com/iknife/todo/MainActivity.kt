package com.iknife.todo

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import com.iknife.todo.R.id.toolbar
import com.iknife.todo.database.TaskData
import com.iknife.todo.database.TasksDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input_bar.*

class MainActivity : AppCompatActivity() {


    private lateinit var  toolbar :Toolbar
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TaskListAdapter
    private lateinit var  mDrawerLayout: DrawerLayout
    private lateinit var mToggle: ActionBarDrawerToggle
    private var tasksList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()

        //Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener {  menuItem ->
            menuItem.isChecked = true
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected

            true
        }

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



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mDrawerLayout = findViewById(R.id.drawer_layout)
        return when(item.itemId){
            android.R.id.home ->{
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setUpToolbar()
    {
        mDrawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        mToggle = ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
    }


}


