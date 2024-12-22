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
    private val historyList = mutableListOf<History>()

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

    // Fungsi untuk memparsing data JSON menjadi list of History
    private fun parseHistoryData(jsonString: String): List<History> {
        val resultList = mutableListOf<History>()
        val jsonArray = JSONArray(jsonString)

        // Parsing setiap elemen JSON
        for (i in 0 until jsonArray.length()) {
            val entry = jsonArray.getJSONObject(i)
            val name = entry.getString("name")
            val points = entry.getString("points")
            val reward = entry.getString("reward")
            resultList.add(History(name, points, reward))
        }

        return resultList
    }

    private fun deleteHistoryItem(item: History) {
        val historyData = sharedPreferences.getString("history", "[]") ?: "[]"
        val jsonArray = JSONArray(historyData)
        val newJsonArray = JSONArray()

        // Tambahkan semua item kecuali item yang akan dihapus
        for (i in 0 until jsonArray.length()) {
            val entry = jsonArray.getJSONObject(i)
            if (entry.getString("name") != item.name ||
                entry.getString("points") != item.points ||
                entry.getString("reward") != item.reward
            ) {
                newJsonArray.put(entry)
            }
        }

        // Simpan data baru ke SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("history", newJsonArray.toString())
        editor.apply()

        // Perbarui daftar dan adapter
        historyList.clear() // Kosongkan daftar terlebih dahulu
        historyList.addAll(parseHistoryData(newJsonArray.toString())) // Tambahkan data terbaru
        historyAdapter.notifyDataSetChanged() // Beri tahu adapter untuk memperbarui tampilan
    }


}
