package com.iknife.todo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import com.github.clans.fab.FloatingActionMenu
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabTask : com.github.clans.fab.FloatingActionButton = floatingActionItemTask
        fabTask.setOnClickListener { view ->
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }

        val fabProject : com.github.clans.fab.FloatingActionButton = floatingActionItemProject
        fabProject.setOnClickListener { view ->
            val intent = Intent(this, CreateProjectActivity::class.java)
            startActivity(intent)
        }

    }



}
