package com.example.moneytracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytracker.database.Cost
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.R.attr.data
import android.view.View
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    private lateinit var costViewModel: CostViewModel
    private var currentCosts = emptyList<Cost>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CostListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        costViewModel = ViewModelProviders.of(this).get(CostViewModel::class.java)

        costViewModel.allCost.observe(this, Observer { cost ->
            // Update the cached copy of the words in the adapter.
            cost?.let { adapter.setAllCosts(it) }
        })

        //Calendar
        calendarView.setDate(calendarView.date)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()

            adapter.setCostsForDay(dayOfMonth, month, year)

            if (adapter.getCostPerDayCount() == 0) {
                recyclerView.visibility = View.GONE
                emptyDay.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyDay.visibility = View.GONE
            }
        }

        addCost.setOnClickListener {
            val intent = Intent(this@MainActivity, NewCostActivity::class.java)
            startActivityForResult(intent, newCostActivityRequestCode)
        }
    }

    companion object {
        const val newCostActivityRequestCode = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newCostActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val value =  it.getStringExtra(NewCostActivity.VALUE_REPLY)
                var dateText = it.getStringExtra(NewCostActivity.DATE_REPLY)
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                val time = LocalDateTime.parse(dateText,formatter)
                val cost = Cost(value=value.toFloat(),time = time)
                costViewModel.insert(cost)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
