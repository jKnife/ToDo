package com.iknife.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab : FloatingActionButton = fab
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Elo", Snackbar.LENGTH_LONG).setAction("Action",null).show()
        }

    }



}
