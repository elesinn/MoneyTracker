package com.example.moneytracker.database

import androidx.room.TypeConverter
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return if (value == null) null else LocalDateTime.parse(value, formatter);
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formatDateTime = date?.format(formatter)
        return formatDateTime
    }
}