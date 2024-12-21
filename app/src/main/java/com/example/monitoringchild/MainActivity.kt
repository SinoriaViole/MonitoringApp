package com.example.monitoringchild

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.monitoringchild.ui.theme.MonitoringchildTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Menunjuk ke layout XML Anda

        // Inisialisasi Toolbar jika ingin menjadikannya ActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bantuanCard: CardView = findViewById(R.id.bantuanCard)
        bantuanCard.setOnClickListener{
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }

        val monitoringCard: CardView = findViewById(R.id.monitoringCard)
        monitoringCard.setOnClickListener{
            val intent = Intent(this, MonitoringActivity::class.java)
            startActivity(intent)
        }

        val katalogCard: CardView = findViewById(R.id.katalogCard)
        katalogCard.setOnClickListener{
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
        }

        val riwayatCard: CardView = findViewById(R.id.riwayatCard)
        riwayatCard.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

    }
}