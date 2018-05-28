package com.iknife.todo

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.iknife.todo.R.string.task_text
import kotlinx.android.synthetic.main.activity_task_info.*

class TaskInfoActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_info)

        getIncomingIntent()
    }

    private fun getIncomingIntent()
    {
        if(intent.hasExtra("task_text"))
        {
            val taskInfo: String = intent.getStringExtra("task_text")
            setTaskInfoName(taskInfo)
        }
        else
        {
            task_name_info.text = "beka"
        }



    }

    private fun setTaskInfoName(taskInfo :String)
    {
        task_name_info.text = taskInfo

    }

}