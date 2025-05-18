package com.example.dynamictestapp.utils

import android.content.Context
import android.util.Log
import com.example.dynamictestapp.models.Question
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

fun loadQuestionsFromAssets(context: Context): List<Question> {
    val questions = mutableListOf<Question>()
    try {
        val inputStream = context.assets.open("questions.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val rootArray = JSONArray(jsonString)

        for (i in 0 until rootArray.length()) {
            val item = rootArray.getJSONObject(i)
            if (item.getString("type") == "table" && item.has("data")) {
                val dataArray = item.getJSONArray("data")
                for (j in 0 until dataArray.length()) {
                    val q = dataArray.getJSONObject(j)
                    val question = Question(
                        id = q.getInt("id"),
                        question_text = q.getString("question_text"),
                        option_a = q.getString("option_a"),
                        option_b = q.getString("option_b"),
                        option_c = q.getString("option_c"),
                        option_d = q.getString("option_d"),
                        correct_answer = q.getString("correct_answer"),
                        explanation = q.getString("explanation"),
                        difficulty = q.getString("difficulty")
                    )
                    questions.add(question)
                }
            }
        }

        Log.d("TEST", "Toplam JSON'dan okunan soru sayısı: ${questions.size}")
    } catch (e: Exception) {
        Log.e("JSON", "Hata: ${e.message}")
    }

    return questions
}
