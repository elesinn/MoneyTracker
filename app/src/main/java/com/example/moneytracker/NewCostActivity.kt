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
import org.joda.time.LocalDateTime
import java.text.SimpleDateFormat
import java.util.*

class NewCostActivity : AppCompatActivity() {

    private lateinit var editCostView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_cost)
        editCostView = findViewById(R.id.edit_word)

        dateValue.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val formatter = DateTimeFormatter()

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            dateValue.text = sdf.format(cal.time)

        }

        dateValue.setOnClickListener {
            DatePickerDialog(this@NewCostActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editCostView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {


                val cost = Cost(value=editCostView.toString().toFloat(),time = LocalDateTime.parse(dateValue.text,sdf)

                replyIntent.putExtra(EXTRA_REPLY, cost)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.costsql.REPLY"
    }
}