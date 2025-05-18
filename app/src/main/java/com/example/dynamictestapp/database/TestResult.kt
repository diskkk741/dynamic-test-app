package com.example.dynamictestapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_results")
data class TestResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val score: Int,
    val totalQuestions: Int,
    val durationInSeconds: Int,
    val correct_easy: Int = 0,
    val wrong_easy: Int = 0,
    val correct_medium: Int = 0,
    val wrong_medium: Int = 0,
    val correct_hard: Int = 0,
    val wrong_hard: Int = 0,
    val isSynced: Boolean = false
)