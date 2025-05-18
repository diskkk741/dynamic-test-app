package com.example.dynamictestapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamictestapp.database.TestResult

class TestHistoryAdapter(
    private var results: List<TestResult>
) : RecyclerView.Adapter<TestHistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val txtScore: TextView = itemView.findViewById(R.id.txtScore)
        val txtDuration: TextView = itemView.findViewById(R.id.txtDuration)
        val txtSyncStatus: TextView = itemView.findViewById(R.id.txtSyncStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_test_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]

        holder.txtDate.text = "Tarih: ${result.date}"
        holder.txtScore.text = "Skor: ${result.score} / ${result.totalQuestions}"
        holder.txtDuration.text = "Süre: ${result.durationInSeconds} sn"

        if (result.isSynced) {
            holder.txtSyncStatus.text = "✅ Sunucuya gönderildi"
            holder.txtSyncStatus.setTextColor(0xFF4CAF50.toInt())
        } else {
            holder.txtSyncStatus.text = "⏳ Henüz senkronize edilmedi"
            holder.txtSyncStatus.setTextColor(0xFFF44336.toInt())
        }
    }

    override fun getItemCount(): Int = results.size

    fun updateData(newList: List<TestResult>) {
        results = newList
        notifyDataSetChanged()
    }
}
