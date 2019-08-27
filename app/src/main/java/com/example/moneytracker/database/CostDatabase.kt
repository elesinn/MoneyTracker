package com.example.moneytracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Cost::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CostDatabase : RoomDatabase() {
    abstract fun costDao(): CostDao
    companion object {

        @Volatile
        private var INSTANCE: CostDatabase? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope): CostDatabase{
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CostDatabase::class.java,
                        "money_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(CostDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private class CostDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.costDao())
                }
            }
        }

        fun populateDatabase(costDao: CostDao) {
//            costDao.clear()
            if (costDao.getCount() == 0) {
                var cost = Cost(value = 10F)
                costDao.insert(cost)
            }
        }
    }
}