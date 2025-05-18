package com.example.dynamictestapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ResultActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val resultUrl = "http://10.0.2.2:5000/api/test-result"
    private val analyticsUrl = "http://10.0.2.2:5000/api/test-analytics"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val txtFinalScore: TextView = findViewById(R.id.txtFinalScore)
        val txtTotalQuestions: TextView = findViewById(R.id.txtTotalQuestions)
        val btnRestart: Button = findViewById(R.id.btnRestart)

        val txtEasy: TextView = findViewById(R.id.txtEasyStats)
        val txtMedium: TextView = findViewById(R.id.txtMediumStats)
        val txtHard: TextView = findViewById(R.id.txtHardStats)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)
        val userId = 1

        val easyCorrect = intent.getIntExtra("easyCorrect", 0)
        val mediumCorrect = intent.getIntExtra("mediumCorrect", 0)
        val hardCorrect = intent.getIntExtra("hardCorrect", 0)
        val easyWrong = intent.getIntExtra("easyWrong", 0)
        val mediumWrong = intent.getIntExtra("mediumWrong", 0)
        val hardWrong = intent.getIntExtra("hardWrong", 0)

        txtFinalScore.text = "Doğru Sayısı: $score"
        txtTotalQuestions.text = "Toplam Soru: $total"
        txtEasy.text = "Easy: ✅ $easyCorrect  ❌ $easyWrong"
        txtMedium.text = "Medium: ✅ $mediumCorrect  ❌ $mediumWrong"
        txtHard.text = "Hard: ✅ $hardCorrect  ❌ $hardWrong"

        // 🔁 İlk olarak test sonucunu kaydet
        val resultJson = JSONObject().apply {
            put("user_id", userId)
            put("score", score)
            put("total", total)
        }

        val resultRequestBody = resultJson.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val resultRequest = Request.Builder()
            .url(resultUrl)
            .post(resultRequestBody)
            .build()

        val btnViewHistory: Button = findViewById(R.id.btnViewHistory)

        btnViewHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        client.newCall(resultRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API", "Test sonucu gönderilemedi: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                try {
                    val jsonResponse = JSONObject(body ?: "{}")
                    val resultId = jsonResponse.getInt("result_id")

                    // ✅ Sonuç ID geldikten sonra analytics'i gönder
                    sendAnalytics(resultId, easyCorrect, easyWrong, mediumCorrect, mediumWrong, hardCorrect, hardWrong)

                } catch (e: Exception) {
                    Log.e("API", "Yanıt parse edilemedi: ${e.message}")
                }
            }
        })

        btnRestart.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun sendAnalytics(
        resultId: Int,
        easyCorrect: Int,
        easyWrong: Int,
        mediumCorrect: Int,
        mediumWrong: Int,
        hardCorrect: Int,
        hardWrong: Int
    ) {
        val analyticsJson = JSONObject().apply {
            put("result_id", resultId)
            put("correct_easy", easyCorrect)
            put("wrong_easy", easyWrong)
            put("correct_medium", mediumCorrect)
            put("wrong_medium", mediumWrong)
            put("correct_hard", hardCorrect)
            put("wrong_hard", hardWrong)
        }

        val analyticsBody = analyticsJson.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val analyticsRequest = Request.Builder()
            .url(analyticsUrl)
            .post(analyticsBody)
            .build()

        client.newCall(analyticsRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API", "Analytics gönderilemedi: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("API", "Analytics başarıyla gönderildi.")
            }
        })
    }
}
