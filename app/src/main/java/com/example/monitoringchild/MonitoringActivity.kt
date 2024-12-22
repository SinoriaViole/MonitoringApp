package com.example.monitoringchild

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.BitmapFactory
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Size
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MonitoringActivity : AppCompatActivity() {
    companion object {
        private const val MOTION_THRESHOLD = 5000
        private const val STABILITY_THRESHOLD = 3000
        private const val AUDIO_THRESHOLD = 70000
        private const val DEBOUNCE_TIME = 500L
        private const val CALIBRATION_FRAMES = 30
    }

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var tvMotionStatus: TextView
    private lateinit var tvNoiseLevel: TextView
    private lateinit var btnStopMonitoring: Button
    private var cameraProvider: ProcessCameraProvider? = null
    private var timer: CountDownTimer? = null
    private var startTime: Long = 0L
    private var points = 100
    private var lastFrame: Bitmap? = null
    private var isMonitoring = false
    private var isMotionDetected = false
    private var noMotionCounter = 0
    private var lastStateChangeTime = 0L
    private var averageDifference = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitoring)

        initializeViews()
        setupButtons()
        requestPermissions()
    }

    private fun initializeViews() {
        tvMotionStatus = findViewById(R.id.tv_motion_status)
        tvNoiseLevel = findViewById(R.id.tv_noise_level)
        btnStopMonitoring = findViewById(R.id.btn_stop_monitoring)
        cameraExecutor = Executors.newSingleThreadExecutor()
        btnStopMonitoring.isEnabled = false
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btn_start_monitoring).setOnClickListener {
            if (hasPermissions()) {
                startMonitoring()
            } else {
                Toast.makeText(this, "Harap berikan izin kamera dan mikrofon untuk melanjutkan", Toast.LENGTH_LONG).show()
                requestPermissions()
            }
        }

        btnStopMonitoring.setOnClickListener {
            stopMonitoring()
        }
    }

    private fun hasPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val audioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        return cameraPermission == PackageManager.PERMISSION_GRANTED && audioPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        ActivityCompat.requestPermissions(this, permissions, 101)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Izin diberikan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Izin ditolak. Tidak dapat melanjutkan.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startMonitoring() {
        if (isMonitoring) {
            Toast.makeText(this, "Monitoring sudah berjalan!", Toast.LENGTH_SHORT).show()
            return
        }

        isMonitoring = true
        startTime = System.currentTimeMillis()
        startTimer()

        try {
            startCamera()
            startNoiseDetection()
            btnStopMonitoring.isEnabled = true
            Toast.makeText(this, "Monitoring dimulai!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("MonitoringActivity", "Error starting monitoring: ${e.message}", e)
            Toast.makeText(this, "Terjadi kesalahan saat memulai monitoring: ${e.message}", Toast.LENGTH_LONG).show()
            stopMonitoring()
        }
    }

    private fun startTimer() {
        val timerTextView = findViewById<TextView>(R.id.tv_timer)
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val elapsedTime = System.currentTimeMillis() - startTime
                val hours = (elapsedTime / (1000 * 60 * 60)).toInt()
                val minutes = ((elapsedTime / (1000 * 60)) % 60).toInt()
                val seconds = ((elapsedTime / 1000) % 60).toInt()
                timerTextView.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }
            override fun onFinish() {}
        }.start()
    }

    private fun stopMonitoring() {
        isMonitoring = false
        cameraExecutor.shutdown()
        timer?.cancel()
        val finalPoints = points
        findViewById<TextView>(R.id.tv_timer).text = "00:00:00"
        btnStopMonitoring.isEnabled = false
        cameraProvider?.unbindAll()
        showResultDialog()
        lastFrame?.recycle()
        lastFrame = null

    }

    private fun showResultDialog() {
        val dialogBuilder = android.app.AlertDialog.Builder(this)
        dialogBuilder.setTitle("Hasil Monitoring")

        // Tentukan reward berdasarkan rentang poin yang diminta
        val reward = when (points) {
            in 10..30 -> "Selamat anda mendapatkan permen"
            in 31..50 -> "Selamat anda mendapatkan kue"
            in 51..70 -> "Selamat anda mendapatkan mainan kecil"
            in 71..90 -> "Selamat anda mendapatkan buku cerita"
            in 91..100 -> "Selamat anda mendapatkan Jam main lebih banyak"
            else -> "Tidak Ada Reward"
        }

        // Buat layout untuk tampilan pesan dalam dialog
        val layout = android.widget.LinearLayout(this)
        layout.orientation = android.widget.LinearLayout.VERTICAL
        layout.setPadding(50, 30, 50, 30)  // Menambahkan padding untuk estetika

        // Buat TextView untuk menampilkan total poin
        val pointsText = android.widget.TextView(this)
        pointsText.text = "Total Poin: $points"
        pointsText.textSize = 16f
        pointsText.setTextColor(android.graphics.Color.BLACK)
        pointsText.setPadding(0, 0, 0, 20)  // Memberikan jarak antar elemen
        layout.addView(pointsText)

        // Buat TextView untuk menampilkan hadiah
        val rewardText = android.widget.TextView(this)
        rewardText.text = "Hadiah: $reward"
        rewardText.textSize = 16f
        rewardText.setTextColor(android.graphics.Color.BLACK)
        layout.addView(rewardText)

        // Set layout yang sudah diatur ke dalam dialog
        dialogBuilder.setView(layout)

        // Tombol Reset
        dialogBuilder.setPositiveButton("Reset") { _, _ ->
            Toast.makeText(this, "Data direset", Toast.LENGTH_SHORT).show()
            resetMonitoring()
        }

        // Tombol Simpan
        dialogBuilder.setNegativeButton("Simpan") { _, _ ->
            showNameInputDialog(points, reward)
        }

        // Tampilkan dialog
        dialogBuilder.create().show()
        resetMonitoring()
    }


    private fun showNameInputDialog(points: Int, reward: String) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Masukkan Nama")

        // Buat LinearLayout untuk tata letak input
        val layout = android.widget.LinearLayout(this)
        layout.orientation = android.widget.LinearLayout.VERTICAL
        layout.setPadding(50, 30, 50, 30)  // Menambahkan padding untuk estetika

        // Buat EditText untuk input nama
        val input = android.widget.EditText(this)
        input.hint = "Masukkan nama anda"
        input.setTextColor(android.graphics.Color.BLACK)  // Set warna teks
        input.setHintTextColor(android.graphics.Color.GRAY)  // Set warna hint
        input.setBackgroundResource(android.R.drawable.edit_text)  // Menambahkan background agar lebih menarik

        // Sesuaikan lebar input
        val layoutParams = android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = layoutParams

        // Menambahkan input ke dalam layout
        layout.addView(input)

        // Set layout dialog
        builder.setView(layout)

        // Tombol Simpan
        builder.setPositiveButton("Simpan") { _, _ ->
            val name = input.text.toString()
            if (name.isNotEmpty()) {
                saveRewardToHistory(name, points, reward)
            } else {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                showNameInputDialog(points, reward) // Tampilkan dialog lagi jika nama kosong
            }
        }

        // Tombol Batal
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.cancel()
        }

        // Tampilkan dialog
        builder.show()
        resetMonitoring()
    }


    private fun saveRewardToHistory(name: String, points: Int, reward: String) {
        val sharedPreferences = getSharedPreferences("RewardHistory", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Ambil data lama
        val currentData = sharedPreferences.getString("history", "[]") ?: "[]"
        val historyList = try {
            JSONArray(currentData)
        } catch (e: Exception) {
            JSONArray() // Jika parsing gagal
        }

        // Tambahkan data baru
        val newEntry = JSONObject().apply {
            put("name", name)
            put("points", points)
            put("reward", reward)
        }
        historyList.put(newEntry)

        // Simpan kembali ke SharedPreferences
        editor.putString("history", historyList.toString()).apply()

        Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
    }

    private fun resetMonitoring() {
//        points = 50
        tvMotionStatus.text = "Normal"
        tvNoiseLevel.text = "Normal"
        tvMotionStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        tvNoiseLevel.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                setupCameraUseCase()
            } catch (e: Exception) {
                Log.e("MonitoringActivity", "Error setting up camera: ${e.message}", e)
                Toast.makeText(this, "Gagal memulai kamera: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun setupCameraUseCase() {
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(findViewById<PreviewView>(R.id.camera_preview).surfaceProvider)
            }

        val imageAnalyzer = ImageAnalysis.Builder()
            .setTargetResolution(Size(640, 480))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor) { image ->
                    if (image.format != ImageFormat.YUV_420_888) {
                        Log.e("CameraX", "Format tidak didukung: ${image.format}")
                        image.close()
                        return@setAnalyzer
                    }

                    val bitmap = imageToBitmap(image)
                    if (bitmap != null) {
                        val motionDetected = detectMotion(bitmap)
                        runOnUiThread {
                            updateMotionUI(motionDetected)
                        }
                    }
                    image.close()
                }
            }

        try {
            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                this,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageAnalyzer
            )
        } catch (e: Exception) {
            Log.e("MonitoringActivity", "Use case binding failed", e)
        }
    }

    private fun updateMotionUI(motionDetected: Boolean) {
        if (motionDetected) {
//            points = (points - 1).coerceIn(0, 100)
            tvMotionStatus.text = "Gerakan Terdeteksi!"
            tvMotionStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        } else {
            tvMotionStatus.text = "Normal"
            tvMotionStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        }
    }

    private fun detectMotion(currentFrame: Bitmap): Boolean {
        if (lastFrame == null) {
            lastFrame = currentFrame.copy(Bitmap.Config.ARGB_8888, true)
            return false
        }

        val diff = calculateFrameDifference(lastFrame!!, currentFrame)
        Log.d("MotionDetection", "Difference: $diff, Threshold: $MOTION_THRESHOLD, NoMotionCounter: $noMotionCounter")

        // Debounce check
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastStateChangeTime < DEBOUNCE_TIME) {
            return isMotionDetected
        }

        lastFrame?.recycle()
        lastFrame = currentFrame.copy(Bitmap.Config.ARGB_8888, true)

        return when {
            diff > MOTION_THRESHOLD -> {
                noMotionCounter = 0
                lastStateChangeTime = currentTime
                isMotionDetected = true
                true
            }
            diff < STABILITY_THRESHOLD -> {
                noMotionCounter++
                if (noMotionCounter >= 5) {
                    noMotionCounter = 0
                    lastStateChangeTime = currentTime
                    isMotionDetected = false
                    false
                } else {
                    isMotionDetected
                }
            }
            else -> isMotionDetected
        }
    }

    private fun calculateFrameDifference(frame1: Bitmap, frame2: Bitmap): Int {
        var difference = 0
        val width = frame1.width
        val height = frame1.height
        val sampleStep = 2

        for (y in 0 until height step sampleStep) {
            for (x in 0 until width step sampleStep) {
                val pixel1 = frame1.getPixel(x, y)
                val pixel2 = frame2.getPixel(x, y)

                val brightness1 = getBrightness(pixel1)
                val brightness2 = getBrightness(pixel2)

                if (Math.abs(brightness1 - brightness2) > 25) {
                    difference++
                }
            }
        }

        return difference * (sampleStep * sampleStep)
    }

    private fun getBrightness(pixel: Int): Int {
        val r = pixel shr 16 and 0xff
        val g = pixel shr 8 and 0xff
        val b = pixel and 0xff
        return (r + g + b) / 3
    }

    private fun startNoiseDetection() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val bufferSize = AudioRecord.getMinBufferSize(
            44100,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            return
        }

        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            44100,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        if (audioRecord.state != AudioRecord.STATE_INITIALIZED) {
            return
        }

        audioRecord.startRecording()

        Thread {
            val buffer = ShortArray(1024)
            while (isMonitoring) {
                val read = audioRecord.read(buffer, 0, buffer.size)
                if (read > 0) {
                    val volume = buffer.take(read).map { it.toInt() * it }.average()
                    runOnUiThread {
                        updateNoiseUI(volume)
                    }
                }
            }
            audioRecord.stop()
            audioRecord.release()
        }.start()
    }

    private fun updateNoiseUI(volume: Double) {
        if (!isMonitoring) return

        if (volume > AUDIO_THRESHOLD) {
            points = (points - 5).coerceIn(0, 100)
            tvNoiseLevel.text = "Bising"
            tvNoiseLevel.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        } else {
            points = (points + 1).coerceIn(0, 100)
            tvNoiseLevel.text = "Normal"
            tvNoiseLevel.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        }
    }

    private fun imageToBitmap(image: ImageProxy): Bitmap? {
        val yBuffer = image.planes[0].buffer // Luminance
        val uBuffer = image.planes[1].buffer // Chroma U
        val vBuffer = image.planes[2].buffer // Chroma V

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        // Copy Y buffer
        yBuffer.get(nv21, 0, ySize)

        // Copy U and V buffer - NV21 format interleaves them
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = android.graphics.YuvImage(
            nv21,
            ImageFormat.NV21,
            image.width,
            image.height,
            null
        )

        val out = java.io.ByteArrayOutputStream()
        yuvImage.compressToJpeg(android.graphics.Rect(0, 0, image.width, image.height), 100, out)
        val imageBytes = out.toByteArray()

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
