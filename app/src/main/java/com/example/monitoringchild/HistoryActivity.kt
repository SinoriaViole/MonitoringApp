package com.example.monitoringchild

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Pastikan fitur ini diaktifkan sesuai kebutuhan
        setContentView(R.layout.activity_history)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recycler_history)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("RewardHistory", MODE_PRIVATE)
        val historyData = sharedPreferences.getString("history", "[]") ?: "[]"

        // Parsing data JSON menjadi list
        val historyList = parseHistoryData(historyData)

        // Membuat adapter dengan implementasi fitur delete
        historyAdapter = HistoryAdapter(historyList.toMutableList()) { item ->
            deleteHistoryItem(item) // Fungsi untuk menghapus data dari SharedPreferences
            Toast.makeText(this, "Item '${item.name}' telah dihapus.", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = historyAdapter
    }

    // Fungsi untuk memparsing data JSON menjadi list of history
    private fun parseHistoryData(jsonString: String): List<HistoryItem> {
        val resultList = mutableListOf<HistoryItem>()
        val jsonArray = JSONArray(jsonString)

        // Parsing setiap elemen JSON
        for (i in 0 until jsonArray.length()) {
            val entry = jsonArray.getJSONObject(i)
            val name = entry.getString("name")
            val time = entry.getString("time")
            val reward = entry.getString("reward")
            resultList.add(HistoryItem(name, time, reward))
        }

        return resultList
    }

    // Fungsi untuk menghapus item dari SharedPreferences
    private fun deleteHistoryItem(item: HistoryItem) {
        val historyData = sharedPreferences.getString("history", "[]") ?: "[]"
        val jsonArray = JSONArray(historyData)
        val newJsonArray = JSONArray()

        // Tambahkan semua item kecuali item yang akan dihapus
        for (i in 0 until jsonArray.length()) {
            val entry = jsonArray.getJSONObject(i)
            if (entry.getString("name") != item.name ||
                entry.getString("time") != item.time ||
                entry.getString("reward") != item.reward
            ) {
                newJsonArray.put(entry)
            }
        }

        // Simpan kembali data yang diperbarui ke SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("history", newJsonArray.toString())
        editor.apply()

        // Perbarui data pada adapter
        (historyAdapter.historyList as MutableList).remove(item)
        historyAdapter.notifyDataSetChanged()
    }
}
