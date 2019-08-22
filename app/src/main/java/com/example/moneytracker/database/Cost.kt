package com.example.moneytracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "costs")
data class Cost(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "value")
    var value: Float,

    @ColumnInfo(name = "time_milli")
    var time: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "category")
    var category: Long = 0,

    @ColumnInfo(name = "comment")
    var comment: String = "Empty comment"
)