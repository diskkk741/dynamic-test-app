package com.example.dynamictestapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_analytics")
data class TestAnalytics(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val resultId: Int, // test_results.id ile ilişki
    val correct_easy: Int,
    val wrong_easy: Int,
    val correct_medium: Int,
    val wrong_medium: Int,
    val correct_hard: Int,
    val wrong_hard: Int,
    val total_time_sec: Int
)