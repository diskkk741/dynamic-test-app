package com.example.dynamictestapp.models

data class Question(
    val id: Int,
    val question_text: String,
    val option_a: String,
    val option_b: String,
    val option_c: String,
    val option_d: String,
    val correct_answer: String,
    val explanation: String,
    val difficulty: String
)
