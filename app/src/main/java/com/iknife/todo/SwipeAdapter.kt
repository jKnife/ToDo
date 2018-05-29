package com.iknife.todo

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.chauthai.swipereveallayout.ViewBinderHelper

 class SwipeAdapter :RecyclerView.Adapter<TaskListAdapter.TaskHolder>() {
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskHolder {
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }

     override fun getItemCount(): Int {
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }

     override fun onBindViewHolder(holder: TaskListAdapter.TaskHolder, position: Int) {
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }

     private val viewBinderHelper : ViewBinderHelper = ViewBinderHelper()

    fun saveStates(outState : Bundle)
    {
        viewBinderHelper.saveStates(outState)
    }

    fun restoreStates(inState :Bundle)
    {
        viewBinderHelper.restoreStates(inState)
    }


}