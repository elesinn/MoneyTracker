package com.example.moneytracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytracker.database.Cost

class CostListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<CostListAdapter.CostListHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cost: Cost = Cost(value = 0F)  // Cached copy of words

    inner class CostListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val costItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostListHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return CostListHolder(itemView)
    }

    override fun onBindViewHolder(holder: CostListHolder, position: Int) {
        val current = cost
        holder.costItemView.text = current.value.toString()
    }

    internal fun setCost(cost: Cost) {
        this.cost = cost
        notifyDataSetChanged()
    }

    override fun getItemCount() = 1
}