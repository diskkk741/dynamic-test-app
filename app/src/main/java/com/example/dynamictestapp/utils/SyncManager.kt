package com.example.dynamictestapp.utils

import android.content.Context
import android.util.Log
import com.example.dynamictestapp.database.TestAnalytics
import com.example.dynamictestapp.database.TestDatabase
import com.example.dynamictestapp.database.TestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object SyncManager {

    private const val SYNC_RESULTS_URL = "http://10.0.2.2:5000/api/sync-results"
    private const val SYNC_ANALYTICS_URL = "http://10.0.2.2:5000/api/sync-analytics"

    private val client = OkHttpClient()

    fun syncOfflineResults(context: Context, userId: Int = 1) {
        val db = TestDatabase.getDatabase(context)

        CoroutineScope(Dispatchers.IO).launch {
            val unsyncedResults: List<TestResult> = db.testResultDao().getUnsyncedResults()

            if (unsyncedResults.isEmpty()) {
                Log.d("SyncManager", "📭 Senkronize edilecek test sonucu yok.")
                return@launch
            }

            val jsonArray = JSONArray()
            for (result in unsyncedResults) {
                val obj = JSONObject().apply {
                    put("user_id", userId)
                    put("date", result.date)
                    put("score", result.score)
                    put("totalQuestions", result.totalQuestions)
                    put("durationInSeconds", result.durationInSeconds)
                    put("correct_easy", result.correct_easy)
                    put("wrong_easy", result.wrong_easy)
                    put("correct_medium", result.correct_medium)
                    put("wrong_medium", result.wrong_medium)
                    put("correct_hard", result.correct_hard)
                    put("wrong_hard", result.wrong_hard)
                }
                jsonArray.put(obj)
            }

            val requestBody = jsonArray.toString().toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url(SYNC_RESULTS_URL)
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("SyncManager", "❌ Test sonucu senkronizasyonu başarısız: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        Log.d("SyncManager", "✅ Tüm test sonuçları sunucuya gönderildi.")
                        CoroutineScope(Dispatchers.IO).launch {
                            db.testResultDao().markResultsAsSynced(unsyncedResults.map { it.id })
                        }
                    } else {
                        Log.e("SyncManager", "⚠️ Test sonucu gönderim hatası: ${response.code}")
                    }
                }
            })
        }
    }

    fun syncOfflineAnalytics(context: Context) {
        val db = TestDatabase.getDatabase(context)

        CoroutineScope(Dispatchers.IO).launch {
            val analyticsList: List<TestAnalytics> = db.testAnalyticsDao().getAllAnalytics()

            if (analyticsList.isEmpty()) {
                Log.d("SyncManager", "📭 Gönderilecek analiz yok.")
                return@launch
            }

            val jsonArray = JSONArray()
            for (analytics in analyticsList) {
                try {
                    val obj = JSONObject().apply {
                        put("result_id", analytics.resultId)
                        put("correct_easy", analytics.correct_easy)
                        put("wrong_easy", analytics.wrong_easy)
                        put("correct_medium", analytics.correct_medium)
                        put("wrong_medium", analytics.wrong_medium)
                        put("correct_hard", analytics.correct_hard)
                        put("wrong_hard", analytics.wrong_hard)
                        put("total_time_sec", analytics.total_time_sec)
                    }
                    jsonArray.put(obj)
                } catch (e: Exception) {
                    Log.e("SyncManager", "❗ JSON oluşturulurken hata: ${e.message}")
                }
            }

            val requestBody = jsonArray.toString().toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url(SYNC_ANALYTICS_URL)
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("SyncManager", "❌ Analitik senkronizasyonu başarısız: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        Log.d("SyncManager", "✅ Tüm analizler sunucuya gönderildi.")
                    } else {
                        Log.e("SyncManager", "⚠️ Analitik gönderim hatası: ${response.code} ${response.message}")
                    }
                }
            })
        }
    }

}