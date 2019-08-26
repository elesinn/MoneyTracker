package com.example.moneytracker.database

import androidx.room.TypeConverter
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return Timestamp.valueOf(date.format()).time
    }
}