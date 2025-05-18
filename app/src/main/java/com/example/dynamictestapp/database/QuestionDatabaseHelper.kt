package com.example.dynamictestapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dynamictestapp.models.Question

class QuestionDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        const val DATABASE_NAME = "questions.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "questions"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY,
                question_text TEXT,
                option_a TEXT,
                option_b TEXT,
                option_c TEXT,
                option_d TEXT,
                correct_answer TEXT,
                explanation TEXT,
                difficulty TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // ✅ SORULARI VERİTABANINA EKLEME FONKSİYONU
    fun insertQuestions(questions: List<Question>) {
        val db = this.writableDatabase

        for (question in questions) {
            val query = """
            INSERT OR REPLACE INTO $TABLE_NAME 
            (id, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation, difficulty)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

            val statement = db.compileStatement(query)
            statement.bindLong(1, question.id.toLong())
            statement.bindString(2, question.question_text ?: "")
            statement.bindString(3, question.option_a ?: "")
            statement.bindString(4, question.option_b ?: "")
            statement.bindString(5, question.option_c ?: "")
            statement.bindString(6, question.option_d ?: "")
            statement.bindString(7, question.correct_answer ?: "")
            statement.bindString(8, question.explanation ?: "")
            statement.bindString(9, question.difficulty ?: "")

            statement.executeInsert()
        }

        db.close()
    }


    fun getQuestionsByDifficulty(difficulty: String): List<Question> {
        val db = readableDatabase
        val list = mutableListOf<Question>()

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE difficulty = ?",
            arrayOf(difficulty)
        )

        if (cursor.moveToFirst()) {
            do {
                val question = Question(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    question_text = cursor.getString(cursor.getColumnIndexOrThrow("question_text")),
                    option_a = cursor.getString(cursor.getColumnIndexOrThrow("option_a")),
                    option_b = cursor.getString(cursor.getColumnIndexOrThrow("option_b")),
                    option_c = cursor.getString(cursor.getColumnIndexOrThrow("option_c")),
                    option_d = cursor.getString(cursor.getColumnIndexOrThrow("option_d")),
                    correct_answer = cursor.getString(cursor.getColumnIndexOrThrow("correct_answer")),
                    explanation = cursor.getString(cursor.getColumnIndexOrThrow("explanation")),
                    difficulty = cursor.getString(cursor.getColumnIndexOrThrow("difficulty"))
                )
                list.add(question)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

}
