package com.example.dynamictestapp.database

import androidx.room.*

@Dao
interface TestResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: TestResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResultWithReturn(result: TestResult): Long

    @Query("SELECT * FROM test_results ORDER BY date DESC")
    suspend fun getAllResults(): List<TestResult>

    @Query("SELECT * FROM test_results WHERE isSynced = 0")
    suspend fun getUnsyncedResults(): List<TestResult>

    @Query("UPDATE test_results SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markResultsAsSynced(ids: List<Int>)
}