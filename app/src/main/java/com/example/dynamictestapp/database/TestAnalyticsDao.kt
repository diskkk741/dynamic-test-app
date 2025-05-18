package com.example.dynamictestapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TestAnalyticsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalytics(analytics: TestAnalytics)

    @Query("SELECT * FROM test_analytics WHERE resultId = :resultId")
    suspend fun getAnalyticsByResultId(resultId: Int): TestAnalytics?

    @Query("SELECT * FROM test_analytics")
    suspend fun getAllAnalytics(): List<TestAnalytics>
}