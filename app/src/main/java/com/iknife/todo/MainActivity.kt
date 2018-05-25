package com.iknife.todo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.github.clans.fab.FloatingActionMenu
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.LinearLayoutManager
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TaskListAdapter

    var tasksList = arrayListOf<Task>(Task("Task 1"), Task("Task 2"), Task("Task 3"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)

        task_list.layoutManager = linearLayoutManager

        adapter = TaskListAdapter(tasksList)

        task_list.adapter = adapter

        val fabTask : FloatingActionButton = floatingActionItemTask
        fabTask.setOnClickListener { view ->
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }

        val fabProject : FloatingActionButton = floatingActionItemProject
        fabProject.setOnClickListener { view ->
            val intent = Intent(this, CreateProjectActivity::class.java)
            startActivity(intent)
        }

    }
}
