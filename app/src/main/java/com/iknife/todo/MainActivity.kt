package com.iknife.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
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

        //Populate RecyclerView with data from database
        val tasksFromDB = database?.tasksDataDao()?.getTasks()!!
        tasksFromDB.forEach {
            tasksList.add(it)
        }
        tasksList.sortBy {
            it.completed
        }

        val firstCompleted = tasksList.indexOfFirst { it.completed }

        //Setup Sectioned list
        val sections = arrayListOf<SimpleSectionedRecyclerViewAdapter.Section>()
        if (tasksList.size > 0) sections.add(SimpleSectionedRecyclerViewAdapter.Section(0, "To Do"))
        if (firstCompleted != -1) sections.add(SimpleSectionedRecyclerViewAdapter.Section(firstCompleted, "Completed"))


        //Setup RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        task_list.layoutManager = linearLayoutManager

        adapter = TaskListAdapter(tasksList, database) {
            updateSections(sections, task_list.adapter as SimpleSectionedRecyclerViewAdapter, database)
        }

        val dummy = arrayOfNulls<SimpleSectionedRecyclerViewAdapter.Section>(sections.size)
        val mSectionedAdapter = SimpleSectionedRecyclerViewAdapter(this, R.layout.section, R.id.section_text, adapter)
        mSectionedAdapter.setSections(sections.toArray(dummy))

        task_list.adapter = mSectionedAdapter
        //Implement fling callback
        val flingCallback = object : FlingToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                adapter.removeTask(viewHolder!!.adapterPosition, database)
            }
        }
        //Attach Touch Helper to Recycler View
        val taskTouchHelper = ItemTouchHelper(flingCallback)
        taskTouchHelper.attachToRecyclerView(task_list)

        //Set Input Bar keyboard-submit to create a new task and add it to both DB and in-memory List
        input_bar.setOnEditorActionListener { _, actionId, _ ->
            val handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val input = input_bar.text.toString()
                database.tasksDataDao().addTask(TaskData(null, input, false))
                tasksList.add(Task(tasksList.size.toLong(), input, false))
                input_bar.text.clear()
            }
            handled
        }


    }

    private fun updateSections(sections: ArrayList<SimpleSectionedRecyclerViewAdapter.Section>, adapter: SimpleSectionedRecyclerViewAdapter, database: TasksDatabase) {
        tasksList.clear()
        val tasksFromDB = database.tasksDataDao().getTasks()
        tasksFromDB.forEach {
            tasksList.add(it)
        }
        tasksList.sortBy {
            it.completed
        }

        val firstCompleted = tasksList.indexOfFirst { it.completed }

        if (firstCompleted == 0) {
            sections.removeAt(sections.indexOfFirst { it.title == "To Do" })
        } else if (sections.indexOfFirst { it.title == "To Do" } == -1) {
            sections.add(0, SimpleSectionedRecyclerViewAdapter.Section(0, "To Do"))
        }

        if (firstCompleted != -1) {
            if (sections.indexOfFirst { it.title == "Completed" } != -1) {
                sections[sections.indexOfFirst { it.title == "Completed" }] = SimpleSectionedRecyclerViewAdapter.Section(firstCompleted, "Completed")
            } else {
                sections.add(SimpleSectionedRecyclerViewAdapter.Section(firstCompleted, "Completed"))
            }
        } else {
            sections.removeAt(sections.indexOfFirst { it.title == "Completed" })
        }

        val dummy = arrayOfNulls<SimpleSectionedRecyclerViewAdapter.Section>(sections.size)
        adapter.setSections(sections.toArray(dummy))
    }
}
