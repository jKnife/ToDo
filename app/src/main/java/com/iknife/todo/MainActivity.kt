package com.iknife.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.iknife.todo.R.id.input_bar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TaskListAdapter

    var tasksList = arrayListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)

        task_list.layoutManager = linearLayoutManager

        adapter = TaskListAdapter(tasksList)

        task_list.adapter = adapter

        val inputBar : EditText = findViewById(input_bar)

        inputBar.setOnEditorActionListener { v, actionId, event ->
            var handled: Boolean = false
            if(actionId == EditorInfo.IME_ACTION_DONE){
                tasksList.add(Task(inputBar.text.toString()))
               // Toast.makeText(applicationContext, "h", Toast.LENGTH_SHORT).show()
            }
            handled
        }



    }
}
