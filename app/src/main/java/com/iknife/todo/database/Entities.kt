package com.iknife.todo.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class TaskData(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        @ColumnInfo(name = "label") var label: String
    ){
}