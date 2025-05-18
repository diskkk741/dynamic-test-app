package com.example.dynamictestapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamictestapp.models.Question


class QuestionAdapter(
    private val questions: List<Question>,
    private val onAnswerSelected: (Boolean) -> Unit  // ✅ doğru/yanlış sonucu geri döner
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtQuestion: TextView = itemView.findViewById(R.id.txtQuestion)
        val btnOptionA: Button = itemView.findViewById(R.id.btnOptionA)
        val btnOptionB: Button = itemView.findViewById(R.id.btnOptionB)
        val btnOptionC: Button = itemView.findViewById(R.id.btnOptionC)
        val btnOptionD: Button = itemView.findViewById(R.id.btnOptionD)
        val txtExplanation: TextView = itemView.findViewById(R.id.txtExplanation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]

        holder.txtQuestion.text = question.question_text
        holder.btnOptionA.text = "A) ${question.option_a}"
        holder.btnOptionB.text = "B) ${question.option_b}"
        holder.btnOptionC.text = "C) ${question.option_c}"
        holder.btnOptionD.text = "D) ${question.option_d}"

        holder.txtExplanation.visibility = View.GONE

        val correctAnswer = question.correct_answer.uppercase()
        var answered = false

        fun handleAnswer(button: Button, answerLetter: String) {
            if (answered) return
            answered = true

            val isCorrect = answerLetter == correctAnswer
            onAnswerSelected(isCorrect)  // ✅ TestActivity’ye doğru/yanlış bilgisini yolla

            button.setBackgroundColor(
                if (isCorrect) Color.parseColor("#4CAF50")
                else Color.parseColor("#F44336")
            )

            // ✅ Doğru cevabı da yeşil yap
            when (correctAnswer) {
                "A" -> holder.btnOptionA.setBackgroundColor(Color.parseColor("#4CAF50"))
                "B" -> holder.btnOptionB.setBackgroundColor(Color.parseColor("#4CAF50"))
                "C" -> holder.btnOptionC.setBackgroundColor(Color.parseColor("#4CAF50"))
                "D" -> holder.btnOptionD.setBackgroundColor(Color.parseColor("#4CAF50"))
            }

            holder.txtExplanation.text = "Açıklama: ${question.explanation}"
            holder.txtExplanation.visibility = View.VISIBLE

            holder.btnOptionA.isEnabled = false
            holder.btnOptionB.isEnabled = false
            holder.btnOptionC.isEnabled = false
            holder.btnOptionD.isEnabled = false
        }

        holder.btnOptionA.setOnClickListener { handleAnswer(holder.btnOptionA, "A") }
        holder.btnOptionB.setOnClickListener { handleAnswer(holder.btnOptionB, "B") }
        holder.btnOptionC.setOnClickListener { handleAnswer(holder.btnOptionC, "C") }
        holder.btnOptionD.setOnClickListener { handleAnswer(holder.btnOptionD, "D") }
    }

    override fun getItemCount(): Int = questions.size
}
