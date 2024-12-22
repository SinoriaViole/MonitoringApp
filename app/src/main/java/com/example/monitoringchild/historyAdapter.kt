package com.example.monitoringchild

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    val historyList: MutableList<History>,
    private val onDeleteClick: (History) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.name)
        val tvPoint: TextView = itemView.findViewById(R.id.points)
        val tvReward: TextView = itemView.findViewById(R.id.reward)
        val btnDelete: ImageButton = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList.getOrNull(position)  // Mengambil item dengan pengamanan posisi
        if (history != null) {
            holder.tvName.text = "Nama: ${history.name}"
            holder.tvPoint.text = "Point: ${history.points}"
            holder.tvReward.text = "Reward: ${history.reward}"

            holder.btnDelete.setOnClickListener {
                // Pastikan item masih ada di list sebelum dihapus
                onDeleteClick(history)  // Fungsi untuk menghapus item
                if (historyList.isNotEmpty() && position < historyList.size) {
                    historyList.removeAt(position)  // Menghapus item dari list
                    notifyItemRemoved(position)     // Memperbarui RecyclerView
                }
            }
        }
    }

    override fun getItemCount(): Int = historyList.size
}
