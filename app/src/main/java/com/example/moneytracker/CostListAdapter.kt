package com.example.moneytracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytracker.database.Cost
import java.time.format.DateTimeFormatter

class CostListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<CostListAdapter.CostListHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var allCosts = emptyList<Cost>() // Cached copy of words
    private var costsForDay = emptyList<Cost>() // Cached copy of words

    inner class CostListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val costValueView: TextView = itemView.findViewById(R.id.cost)
        val costTimeView: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostListHolder {

        var itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return CostListHolder(itemView)
    }

    override fun onBindViewHolder(holder: CostListHolder, position: Int) {
        if (allCosts.size != null && costsForDay.size != null) {
            val current = costsForDay[position]
            holder.costValueView.text = current.value.toString()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            holder.costTimeView.text = current.time?.format(formatter)
        }
    }

    internal fun setAllCosts(costs: List<Cost>) {
        this.allCosts = costs
        notifyDataSetChanged()
    }

    internal fun setCostsForDay(dayOfMonth: Int, month: Int, year: Int) {
        this.costsForDay = findCostsByDay(dayOfMonth, month, year)
        notifyDataSetChanged()
    }

    override fun getItemCount() = allCosts.size

    internal fun findCostsByDay(dayOfMonth: Int, month: Int, year: Int): List<Cost> {
        var costs = mutableListOf<Cost>()
        this.allCosts.forEach {
            if (it.time.dayOfMonth == dayOfMonth && it.time.monthValue == month+1 && it.time.year == year ) {
                costs.add(it)
            }
        }
        return costs
    }

    fun getCostPerDayCount(): Int{
        return this.costsForDay.size
    }
}