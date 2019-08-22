package com.example.moneytracker

import androidx.test.runner.AndroidJUnit4


import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.moneytracker.database.Cost
import com.example.moneytracker.database.CostDao
import com.example.moneytracker.database.CostDatabase
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class CostDatabaseTest {

    private lateinit var costDao: CostDao
    private lateinit var db: CostDatabase

    @Before
    fun createDb() {
        val context = androidx.test.InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, CostDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        costDao = db.costDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCost() {
        val cost = Cost(value = 500F)
        costDao.insert(cost)
        val today = costDao.getToday()
        assertEquals(today?.value, 500F)
    }
}
