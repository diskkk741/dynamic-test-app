package com.example.dynamictestapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dynamictestapp.database.QuestionDatabaseHelper
import com.example.dynamictestapp.utils.loadQuestionsFromAssets
import com.example.dynamictestapp.utils.SyncManager
import com.example.dynamictestapp.utils.isNetworkAvailable
import com.example.dynamictestapp.utils.isServerReachable
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // ✅ JSON'dan verileri oku
        val questions = loadQuestionsFromAssets(this)
        Log.d("TEST", "Toplam JSON'dan okunan soru sayısı: ${questions.size}")
        questions.forEach {
            Log.d("TEST", "${it.id} - ${it.question_text}")
        }

        // ✅ Veritabanındaki soru sayısını kontrol et
        val dbHelper = QuestionDatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM ${QuestionDatabaseHelper.TABLE_NAME}", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()

        // 🛠 Eğer yeterli kayıt yoksa tekrar yükle
        if (count < 100) {
            dbHelper.insertQuestions(questions)
            Toast.makeText(this, "✅ Sorular veritabanına yüklendi ($count vardı)", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("SQLite", "✅ Sorular zaten yüklü ($count kayıt)")
        }

        // ▶️ Teste başla butonu
        val btn = findViewById<Button>(R.id.btnStartTest)
        btn.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        // Sistem bar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ OFFLINE TESTLERİ SUNUCUYA GÖNDER
        CoroutineScope(Dispatchers.IO).launch {
            val online = isNetworkAvailable(this@MainActivity) && isServerReachable()
            if (online) {
                Log.d("SYNC", "🌐 Çevrimiçi: Sync başlatılıyor")
                SyncManager.syncOfflineResults(this@MainActivity, userId = 1)
                SyncManager.syncOfflineAnalytics(this@MainActivity) // ✅ Analitik senkronizasyonu
            } else {
                Log.d("SYNC", "📴 Offline modda: Sync iptal")
            }
        }
    }
}