package com.iknife.todo

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.iknife.todo.R.string.task_text
import kotlinx.android.synthetic.main.activity_task_info.*

class TaskInfoActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_task_info)

        getIncomingIntent()
    }

    private fun getIncomingIntent()
    {
        val taskInfo : String = intent.getStringExtra("task_text")

        setTaskInfoName(taskInfo)
    }

    private fun setTaskInfoName(taskInfo :String)
    {
        task_name_info.text = taskInfo

    }

}