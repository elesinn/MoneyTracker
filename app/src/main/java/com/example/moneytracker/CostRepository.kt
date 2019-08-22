package com.example.moneytracker

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.moneytracker.database.Cost
import com.example.moneytracker.database.CostDao

class CostRepository(private val costDao: CostDao) {

    val todayCost: LiveData<Cost> = costDao.getToday()

    @WorkerThread
    suspend fun insert(cost: Cost) {
        costDao.insert(cost)
    }
}