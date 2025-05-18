package com.example.dynamictestapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamictestapp.database.QuestionDatabaseHelper
import com.example.dynamictestapp.database.TestDatabase
import com.example.dynamictestapp.database.TestResult
import com.example.dynamictestapp.models.Question
import com.example.dynamictestapp.utils.isNetworkAvailable
import com.example.dynamictestapp.utils.isServerReachable
import com.example.dynamictestapp.database.TestAnalytics
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TestActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val baseUrl = "http://10.0.2.2:5000/api/questions"

    private lateinit var recyclerView: RecyclerView
    private lateinit var txtScore: TextView

    private var score = 0
    private var currentQuestionCount = 0
    private val maxQuestions = 25
    private val askedQuestionIds = mutableSetOf<Int>()
    private var currentDifficulty = "easy"
    private lateinit var currentQuestion: Question
    private var isOnline = false

    private var easyCorrect = 0
    private var mediumCorrect = 0
    private var hardCorrect = 0
    private var easyWrong = 0
    private var mediumWrong = 0
    private var hardWrong = 0

    private var startTimeMillis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        recyclerView = findViewById(R.id.recyclerView)
        txtScore = findViewById(R.id.txtScore)
        recyclerView.layoutManager = LinearLayoutManager(this)
        txtScore.text = "Skor: $score"

        CoroutineScope(Dispatchers.IO).launch {
            val netAvailable = isNetworkAvailable(this@TestActivity)
            val serverReachable = isServerReachable()
            isOnline = netAvailable && serverReachable

            withContext(Dispatchers.Main) {
                if (isOnline) {
                    Toast.makeText(this@TestActivity, "🔗 Çevrimiçi Mod", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@TestActivity, "📴 Çevrimdışı Mod: Sorular cihazdan yüklenecek", Toast.LENGTH_SHORT).show()
                }
                startTimeMillis = System.currentTimeMillis()
                fetchQuestion(currentDifficulty)
            }
        }
    }

    private fun fetchQuestion(difficulty: String) {
        if (isOnline) {
            val url = "$baseUrl?difficulty=$difficulty"
            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("API", "Bağlantı Hatası: ${e.message}")
                    runOnUiThread {
                        Toast.makeText(this@TestActivity, "Sunucuya ulaşılamıyor, çevrimdışı moda geçiliyor", Toast.LENGTH_SHORT).show()
                        isOnline = false
                        fetchOfflineQuestion(difficulty)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    response.body?.string()?.let {
                        val jsonArray = JSONArray(it)
                        if (jsonArray.length() > 0) {
                            val q = jsonArray.getJSONObject(0)
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
                            handleFetchedQuestion(question)
                        }
                    }
                }
            })
        } else {
            fetchOfflineQuestion(difficulty)
        }
    }

    private fun fetchOfflineQuestion(difficulty: String) {
        val dbHelper = QuestionDatabaseHelper(this)
        val allQuestions = dbHelper.getQuestionsByDifficulty(difficulty)
        val filtered = allQuestions.filterNot { askedQuestionIds.contains(it.id) }

        if (filtered.isNotEmpty()) {
            val question = filtered.random()
            handleFetchedQuestion(question)
        } else {
            runOnUiThread {
                Toast.makeText(
                    this,
                    "❌ Bu zorluk seviyesinde cihazda kayıtlı soru bulunamadı!",
                    Toast.LENGTH_LONG
                ).show()
            }
            Log.e("OFFLINE", "Cihazda '$difficulty' seviyesinde hiç soru yok.")
        }
    }

    private fun handleFetchedQuestion(question: Question) {
        if (askedQuestionIds.contains(question.id)) {
            fetchQuestion(currentDifficulty)
            return
        }

        askedQuestionIds.add(question.id)
        currentQuestionCount++
        currentQuestion = question

        runOnUiThread {
            recyclerView.adapter = QuestionAdapter(listOf(question)) { isCorrect ->
                handleAnswer(isCorrect)
            }
        }
    }

    private fun handleAnswer(isCorrect: Boolean) {
        if (isCorrect) {
            score++
            txtScore.text = "Skor: $score"
            when (currentDifficulty) {
                "easy" -> easyCorrect++
                "medium" -> mediumCorrect++
                "hard" -> hardCorrect++
            }
        } else {
            when (currentDifficulty) {
                "easy" -> easyWrong++
                "medium" -> mediumWrong++
                "hard" -> hardWrong++
            }
        }

        currentDifficulty = when {
            isCorrect && currentDifficulty == "easy" -> "medium"
            isCorrect && currentDifficulty == "medium" -> "hard"
            !isCorrect && currentDifficulty == "medium" -> "easy"
            !isCorrect && currentDifficulty == "hard" -> "medium"
            else -> currentDifficulty
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (currentQuestionCount >= maxQuestions) {
                val durationInSeconds = ((System.currentTimeMillis() - startTimeMillis) / 1000).toInt()
                val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

                if (!isOnline) {
                    val db = TestDatabase.getDatabase(applicationContext)

                    CoroutineScope(Dispatchers.IO).launch {
                        Log.d("DEBUG", "📥 OFFLINE modda kayıt başlıyor...")

                        val result = TestResult(
                            date = date,
                            score = score,
                            totalQuestions = maxQuestions,
                            durationInSeconds = durationInSeconds,
                            correct_easy = easyCorrect,
                            wrong_easy = easyWrong,
                            correct_medium = mediumCorrect,
                            wrong_medium = mediumWrong,
                            correct_hard = hardCorrect,
                            wrong_hard = hardWrong,
                            isSynced = false
                        )

                        val resultId = db.testResultDao().insertResultWithReturn(result)
                        Log.d("DEBUG", "✅ TestResult kaydedildi: resultId = $resultId")

                        val analytics = TestAnalytics(
                            resultId = resultId.toInt(),
                            correct_easy = easyCorrect,
                            wrong_easy = easyWrong,
                            correct_medium = mediumCorrect,
                            wrong_medium = mediumWrong,
                            correct_hard = hardCorrect,
                            wrong_hard = hardWrong,
                            total_time_sec = durationInSeconds
                        )

                        try {
                            db.testAnalyticsDao().insertAnalytics(analytics)
                            Log.d("DEBUG", "✅ TestAnalytics kaydedildi: $analytics")
                        } catch (e: Exception) {
                            Log.e("DEBUG", "❌ Analytics INSERT HATASI: ${e.message}")
                        }
                    }
                }


                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra("score", score)
                    putExtra("total", maxQuestions)
                    putExtra("easyCorrect", easyCorrect)
                    putExtra("mediumCorrect", mediumCorrect)
                    putExtra("hardCorrect", hardCorrect)
                    putExtra("easyWrong", easyWrong)
                    putExtra("mediumWrong", mediumWrong)
                    putExtra("hardWrong", hardWrong)
                }
                startActivity(intent)
                finish()
            } else {
                fetchQuestion(currentDifficulty)
            }
        }, 2000)
    }

}