package com.example.moneytracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytracker.database.Cost
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var costViewModel: CostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CostListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        costViewModel = ViewModelProviders.of(this).get(CostViewModel::class.java)

        costViewModel.todayCost.observe(this, Observer { cost ->
            // Update the cached copy of the words in the adapter.
            cost?.let { adapter.setCost(it) }
        })
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
                val cost = Cost(value = it.getStringExtra(NewCostActivity.EXTRA_REPLY).toFloat())
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
