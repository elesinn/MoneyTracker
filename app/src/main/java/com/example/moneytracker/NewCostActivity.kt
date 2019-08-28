package com.example.moneytracker

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.moneytracker.database.Cost
import kotlinx.android.synthetic.main.activity_new_cost.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import kotlinx.serialization.stringify

class NewCostActivity : AppCompatActivity() {

    private lateinit var editCostView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_cost)
        editCostView = findViewById(R.id.edit_word)

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatDateTime = LocalDateTime.now()?.format(formatter)
        dateValue.text = formatDateTime

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            dateValue.text = LocalDateTime.ofInstant(cal.time.toInstant(), ZoneId.systemDefault()).format(formatter)
        }

        dateValue.setOnClickListener {
            DatePickerDialog(this@NewCostActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        timeValue.setOnClickListener{
            // Initialize a new TimePickerFragment
            val newFragment = TimePickerFragment()
            // Show the time picker dialog
            newFragment.show(fragmentManager, "Time Picker")
        }

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editCostView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
              //  var cost = Cost(value=editCostView.toString().toFloat(),time = LocalDateTime.parse(dateValue.text,formatter))
                val dateTime = dateValue.text.toString() + " " + timeValue.text.toString()
                replyIntent.putExtra(DATE_REPLY,dateTime)
                replyIntent.putExtra(VALUE_REPLY,edit_word.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val DATE_REPLY = "DATE_REPLY"
        const val VALUE_REPLY = "VALUE_REPLY"
    }
}