package com.iknife.todo

import android.arch.lifecycle.ViewModel

class TasksListViewModel(val tasksList: MutableList<Task>) : ViewModel()