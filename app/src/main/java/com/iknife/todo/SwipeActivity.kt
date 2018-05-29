package com.iknife.todo

import android.app.Activity
import android.os.Bundle

class SwipeActivity : Activity()
{
    private val adapter :SwipeAdapter = SwipeAdapter()

    override fun onSaveInstanceState(outState :Bundle)
    {
        super.onSaveInstanceState(outState)
        if(adapter != null)
        {
            adapter.saveStates(outState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState :Bundle)
    {
        super.onRestoreInstanceState(savedInstanceState)
        if(adapter != null)
        {
            adapter.restoreStates(savedInstanceState)
        }
    }
}