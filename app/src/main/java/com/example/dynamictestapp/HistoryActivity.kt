package com.example.dynamictestapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamictestapp.database.*
import com.example.dynamictestapp.utils.isNetworkAvailable
import com.example.dynamictestapp.utils.isServerReachable
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TestHistoryAdapter
    private val client = OkHttpClient()
    private val apiUrl = "http://10.0.2.2:5000/api/test-results/1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.historyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TestHistoryAdapter(emptyList())
        recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val online = isNetworkAvailable(this@HistoryActivity) && isServerReachable()

            withContext(Dispatchers.Main) {
                if (online) {
                    loadFromServer()
                } else {
                    loadFromLocal()
                }
            }
        }
    }

    private fun loadFromServer() {
        val request = Request.Builder().url(apiUrl).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HistoryActivity", "API çağrısı başarısız: ${e.message}")
                runOnUiThread { loadFromLocal() }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { json ->
                    try {
                        val gson = Gson()
                        val listType = object : TypeToken<List<TestResult>>() {}.type
                        val results = gson.fromJson<List<TestResult>>(json, listType)

                        runOnUiThread {
                            adapter.updateData(results)
                        }

                    } catch (e: Exception) {
                        Log.e("HistoryActivity", "JSON parse hatası: ${e.message}")
                        runOnUiThread { loadFromLocal() }
                    }
                }
            }
        })
    }


    private fun loadFromLocal() {
        val db = TestDatabase.getDatabase(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            val results = db.testResultDao().getAllResults()
            val analyticsList = db.testAnalyticsDao().getAllAnalytics()

            // Her bir sonucu uygun analizle eşleştir (gerekirse ID ile)
            val enrichedResults = results.map { result ->
                val analytics = analyticsList.find { it.resultId == result.id }
                if (analytics != null) {
                    result.copy(
                        correct_easy = analytics.correct_easy,
                        wrong_easy = analytics.wrong_easy,
                        correct_medium = analytics.correct_medium,
                        wrong_medium = analytics.wrong_medium,
                        correct_hard = analytics.correct_hard,
                        wrong_hard = analytics.wrong_hard
                    )
                } else result
            }

            withContext(Dispatchers.Main) {
                adapter.updateData(enrichedResults)
            }
        }
    }
}
