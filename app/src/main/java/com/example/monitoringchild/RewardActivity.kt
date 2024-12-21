package com.example.monitoringchild

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONObject


class RewardActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etTime: EditText
    private lateinit var etPoints: EditText
    private lateinit var btnReward: Button
    private lateinit var btnSave: Button
    private lateinit var tvResultName: TextView
    private lateinit var tvResultTime: TextView
    private lateinit var tvResultReward: TextView
    private lateinit var tvResult: TextView
    private lateinit var cardResult: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reward)

        etName = findViewById(R.id.et_name)
        etTime = findViewById(R.id.et_time)
        etPoints = findViewById(R.id.et_points)
        btnReward = findViewById(R.id.btn_reward)
        btnSave = findViewById(R.id.btn_save)
        tvResultName = findViewById(R.id.tv_result_name)
        tvResultTime = findViewById(R.id.tv_result_time)
        tvResultReward = findViewById(R.id.tv_result_reward)
        tvResult = findViewById(R.id.tv_result)
        cardResult = findViewById(R.id.card_result)

        btnReward.setOnClickListener {
            calculateReward()
        }

        btnSave.setOnClickListener{
            saveReward()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun calculateReward() {
        val name = etName.text.toString().trim()
        val time = etTime.text.toString().trim()
        val points = etPoints.text.toString().trim()

        // Validasi Input
        if (name.isEmpty() || time.isEmpty() || points.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            return
        }

        val pointsValue = points.toIntOrNull()
        if (pointsValue == null || pointsValue < 0 || pointsValue > 100) {
            Toast.makeText(this, "Poin harus berupa angka antara 0-100", Toast.LENGTH_SHORT).show()
            return
        }

        // Tentukan Reward Berdasarkan Poin
        val reward = when (pointsValue) {
            in 10..30 -> "Selamat Anak Anda berhak mendapat hadiah berupa Permen"
            in 31..50 -> "Selamat Anak Anda berhak mendapat hadiah berupa Kue"
            in 51..70 -> "Selamat Anak Anda berhak mendapat hadiah berupa Mainan kecil"
            in 71..90 -> "Selamat Anak Anda berhak mendapat hadiah berupa Buku Cerita"
            in 91..100 -> "Selamat Anak Anda berhak mendapat hadiah berupa Jam main lebih banyak"
            else -> "Tidak Ada Reward"
        }

        // Tampilkan Hasil
        tvResultName.text = "Nama: $name"
        tvResultTime.text = "Waktu: $time"
        tvResultReward.text = "Reward: $reward"

        tvResult.visibility = View.VISIBLE
        cardResult.visibility = View.VISIBLE
    }

    private fun saveReward() {
        val name = etName.text.toString().trim()
        val time = etTime.text.toString().trim()
        val reward = tvResultReward.text.toString()

        // Cek apakah field kosong
        if (name.isEmpty() || time.isEmpty() || reward.isEmpty()) {
            Toast.makeText(this, "Harap hitung reward terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        // Simpan ke SharedPreferences
        val sharedPreferences = getSharedPreferences("RewardHistory", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Ambil data lama (jika ada) atau inisialisasi dengan array kosong
        val currentData = sharedPreferences.getString("history", "[]") ?: "[]"
        val historyList = try {
            JSONArray(currentData)
        } catch (e: Exception) {
            JSONArray()  // Jika parsing JSON gagal, inisialisasi dengan array kosong
        }

        // Tambahkan data baru
        val newEntry = JSONObject().apply {
            put("name", name)
            put("time", time)
            put("reward", reward)
        }
        historyList.put(newEntry)

        // Simpan kembali ke SharedPreferences
        editor.putString("history", historyList.toString()).apply()

        Toast.makeText(this, "Reward untuk $name berhasil disimpan!", Toast.LENGTH_SHORT).show()

        // Reset form
        resetForm()
    }


    private fun resetForm() {
        etName.setText("")  // Kosongkan input nama
        etTime.setText("")  // Kosongkan input waktu
        etPoints.setText("")
        tvResultName.text = ""
        tvResultTime.text = ""
        tvResultReward.text = ""
        tvResult.text = ""
        tvResultReward.setTextColor(Color.BLACK) // Reset warna teks jika ada perubahan
        cardResult.visibility = View.GONE
    }

}