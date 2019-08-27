package com.example.moneytracker.database

import androidx.room.TypeConverter
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import org.joda.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return if (value == null) null else LocalDateTime(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
}