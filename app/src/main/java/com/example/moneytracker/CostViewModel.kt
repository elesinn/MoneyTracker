package com.example.moneytracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.database.Cost
import com.example.moneytracker.database.CostDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CostRepository
    val allCost: LiveData<List<Cost>>
    init {
        val costDao = CostDatabase.getInstance(application, viewModelScope).costDao()
        repository = CostRepository(costDao)
        allCost = repository.allCost
    }

    fun insert(cost: Cost) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cost)
    }

}