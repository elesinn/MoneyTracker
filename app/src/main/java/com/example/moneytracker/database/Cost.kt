package com.example.moneytracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import org.joda.time.LocalDateTime
import java.util.*

@Entity(tableName = "costs")
data class Cost(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "value")
    var value: Float,

    @ColumnInfo(name = "date")
    var time: LocalDateTime = LocalDateTime(),

    @ColumnInfo(name = "category")
    var category: Long = 0,

    @ColumnInfo(name = "comment")
    var comment: String = "Empty comment"
)