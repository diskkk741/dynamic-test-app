package com.example.dynamictestapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TestResult::class, TestAnalytics::class], version = 3)
abstract class TestDatabase : RoomDatabase() {
    abstract fun testResultDao(): TestResultDao
    abstract fun testAnalyticsDao(): TestAnalyticsDao

    companion object {
        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getDatabase(context: Context): TestDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestDatabase::class.java,
                    "test_app_db"
                )
                    .fallbackToDestructiveMigration() // önemli!
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
