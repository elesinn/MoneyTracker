package com.example.moneytracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.android.synthetic.main.activity_main.view.*

@Dao
interface CostDao {
    @Insert
    fun insert(cost: Cost)

    @Update
    fun update(cost: Cost)

    @Query("SELECT * from costs WHERE id = :key")
    fun get(key: Long): Cost?

    @Query("DELETE FROM costs")
    fun clear()

    @Query("SELECT * FROM costs ORDER BY id DESC LIMIT 1")
    fun getToday(): LiveData<Cost>

    @Query("SELECT * FROM costs ORDER BY id DESC")
    fun getAllCosts(): LiveData<List<Cost>>

    @Query("SELECT COUNT(*) FROM costs")
    fun getCount() : Int
}