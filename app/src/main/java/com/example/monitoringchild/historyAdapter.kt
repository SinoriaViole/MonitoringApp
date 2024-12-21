package com.example.monitoringchild

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    val historyList: MutableList<HistoryItem>,
    private val onDelete: (HistoryItem) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.nameTextView.text = "Nama: ${historyItem.name}"
        holder.timeTextView.text = "Waktu: ${historyItem.time}"
        holder.rewardTextView.text = "Reward: ${historyItem.reward}"

        // Aksi tombol delete
        holder.deleteButton.setOnClickListener {
            onDelete(historyItem) // Callback untuk menghapus item
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.name)
        val timeTextView: TextView = view.findViewById(R.id.time)
        val rewardTextView: TextView = view.findViewById(R.id.reward)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button) // Tombol delete
    }
}
