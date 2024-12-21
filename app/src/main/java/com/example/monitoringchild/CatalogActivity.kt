package com.example.monitoringchild

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CatalogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalog)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cardViewTips = findViewById<CardView>(R.id.cardView1)
        cardViewTips.setOnClickListener{
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("title", "Cara Mengelola Waktu Belajar Anak")
            intent.putExtra(
                "content",
                "Mengatur waktu belajar anak adalah tantangan yang sering dihadapi orang tua, tetapi dengan pendekatan yang tepat, anak dapat belajar mengatur waktu dengan baik, meningkatkan produktivitas, dan mengembangkan kebiasaan yang positif. Berikut ini adalah penjabaran dari cara-cara yang dapat diterapkan dalam mengatur waktu belajar anak:\n" +
                        "\n" +
                        "1. Membuat Jadwal Harian yang Seimbang\n" +
                        "Membuat jadwal harian yang mencakup waktu untuk belajar, bermain, dan istirahat sangat penting. Dengan jadwal yang teratur, anak dapat belajar mengelola waktu mereka dan tahu kapan harus fokus belajar, kapan harus beristirahat, dan kapan bisa bermain.\n" +
                        "Jadwal ini juga memberikan struktur yang jelas, membantu anak untuk terbiasa dengan rutinitas yang sehat dan mengurangi kebingungan mengenai apa yang harus dilakukan di setiap waktu.\n" +
                        "\n" +
                        "2. Mengajarkan Prioritas\n" +
                        "Mengajarkan anak untuk menetapkan prioritas sangat membantu dalam mengelola waktu mereka. Misalnya, anak dapat diajarkan untuk menyelesaikan tugas yang lebih mendesak atau lebih sulit terlebih dahulu, sebelum beralih ke tugas yang lebih ringan.\n" +
                        "Ini membantu anak untuk tidak merasa kewalahan dengan banyaknya pekerjaan dan mengurangi stres dalam menyelesaikan tugas sekolah.\n" +
                        "\n" +
                        "3. Menciptakan Lingkungan Belajar yang Nyaman\n" +
                        "Lingkungan yang nyaman dan bebas gangguan sangat mendukung fokus anak. Misalnya, hindari televisi yang menyala atau suara-suara lain yang bisa mengganggu konsentrasi anak saat belajar.\n" +
                        "Pastikan meja belajar anak rapi dan memiliki pencahayaan yang baik. Ruang yang bersih dan teratur dapat meningkatkan kenyamanan dan membantu anak untuk lebih fokus pada tugas mereka.\n" +
                        "\n" +
                        "4. Menggunakan Metode Pomodoro\n" +
                        "Salah satu cara yang efektif untuk meningkatkan fokus adalah dengan menggunakan teknik Pomodoro. Teknik ini melibatkan belajar selama 25 menit, kemudian diselingi dengan istirahat singkat selama 5 menit.\n" +
                        "Teknik ini dapat membantu anak agar tidak merasa lelah atau jenuh saat belajar, serta memberi mereka waktu untuk menyegarkan pikiran sebelum kembali ke tugas belajar.\n" +
                        "\n" +
                        "5. Menggunakan Metode Belajar yang Menarik\n" +
                        "Setiap anak memiliki gaya belajar yang berbeda. Beberapa anak mungkin lebih tertarik pada video edukasi, sementara yang lainnya lebih menikmati permainan interaktif atau aktivitas hands-on.\n" +
                        "Penting untuk menyesuaikan metode belajar dengan minat anak agar mereka merasa terlibat dan termotivasi untuk belajar. Menggunakan metode yang menyenangkan dapat meningkatkan perhatian anak dan membuat pembelajaran lebih efektif.\n" +
                        "\n" +
                        "6. Menghindari Multitasking\n" +
                        "Multitasking dapat mengurangi efisiensi dan fokus. Ajarkan anak untuk menyelesaikan satu tugas terlebih dahulu sebelum beralih ke tugas lainnya.\n" +
                        "Dengan cara ini, anak dapat lebih fokus pada tugas yang ada dan menyelesaikannya dengan lebih baik daripada jika mereka mencoba mengerjakan beberapa hal sekaligus.\n" +
                        "\n" +
                        "7. Memberikan Motivasi dan Apresiasi\n" +
                        "Anak membutuhkan dorongan untuk tetap termotivasi dalam belajar. Berikan pujian atau hadiah kecil untuk usaha dan pencapaian mereka, bukan hanya hasil akhir.\n" +
                        "Hal ini dapat meningkatkan semangat mereka untuk terus belajar dan membuat mereka merasa dihargai atas kerja keras yang mereka lakukan.\n" +
                        "\n" +
                        "8. Menjadi Teladan yang Baik\n" +
                        "Anak sering kali meniru kebiasaan orang tua. Jika orang tua menunjukkan kebiasaan manajemen waktu yang baik, anak akan lebih cenderung untuk mengadopsi kebiasaan serupa.\n" +
                        "Oleh karena itu, penting bagi orang tua untuk menjadi teladan yang baik dalam mengatur waktu, seperti menyelesaikan tugas tepat waktu dan memisahkan waktu kerja dengan waktu istirahat.\n" +
                        "\n" +
                        "9. Mengelola Kualitas Belajar, Bukan Hanya Durasi\n" +
                        "Manajemen waktu belajar anak bukan hanya soal mengatur berapa lama mereka belajar, tetapi juga tentang memastikan bahwa waktu belajar tersebut produktif dan berkualitas.\n" +
                        "Anak perlu diberi waktu untuk istirahat yang cukup agar otak mereka dapat menyerap informasi dengan lebih baik. Keseimbangan antara belajar, bermain, dan istirahat sangat penting untuk menjaga kesehatan mental dan fisik anak.\n" +
                        "\n" +
                        "10. Mengajarkan Tanggung Jawab\n" +
                        "Dengan mengelola waktu belajar dengan baik, anak akan belajar untuk menjadi lebih bertanggung jawab terhadap tugas-tugas mereka. Ini akan membantu mereka mengembangkan keterampilan manajemen waktu yang berguna tidak hanya di masa kecil, tetapi juga saat mereka dewasa.\n" +
                        "\n" +
                        "Dengan pendekatan ini, anak akan belajar untuk bertanggung jawab atas waktu mereka, meningkatkan kemampuan untuk mengatur jadwal belajar mereka sendiri, dan akhirnya mengembangkan kebiasaan positif yang akan bermanfaat sepanjang hidup mereka."

            )
            startActivity(intent)
        }

        val cardViewTips2 = findViewById<CardView>(R.id.cardView2)
        cardViewTips2.setOnClickListener{
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("title", "Panduan Mengenali Gaya Belajar Anak")
            intent.putExtra(
                "content",
                "Setiap anak memiliki gaya belajar yang unik, yang dapat dibedakan menjadi tiga kategori utama: visual, auditori, dan kinestetik. Memahami gaya belajar anak sangat penting untuk menyesuaikan metode pembelajaran yang tepat agar proses belajar menjadi lebih efektif dan menyenangkan. Anak dengan gaya belajar visual lebih mudah memahami informasi melalui gambar, warna, grafik, dan visualisasi. Mereka cenderung lebih cepat menangkap informasi yang disajikan dalam bentuk visual, seperti gambar atau diagram, daripada yang disampaikan secara lisan. Untuk mendukung anak visual, orang tua dapat menggunakan gambar, flashcard, dan diagram, serta menyajikan informasi dalam bentuk visual yang menarik seperti presentasi berwarna atau video edukasi.\n" +
                        "\n" +
                        "Sementara itu, anak dengan gaya belajar auditori lebih mudah memahami informasi yang didengar. Mereka lebih tertarik pada suara dan sering mengingat informasi melalui lirik atau ritme. Anak dengan gaya ini lebih suka berdiskusi, mendengarkan cerita, atau menggunakan lagu edukatif. Untuk mendukung anak auditori, orang tua dapat membacakan materi dengan jelas dan berulang-ulang, mengajak anak berdiskusi, atau menggunakan rekaman suara dan podcast edukasi. Selain itu, lagu-lagu edukatif atau cerita lisan juga dapat memperkuat pemahaman materi.\n" +
                        "\n" +
                        "Anak dengan gaya belajar kinestetik, di sisi lain, lebih suka belajar dengan melibatkan gerakan fisik. Mereka cenderung aktif dan lebih mudah memahami materi melalui aktivitas langsung atau permainan edukatif yang melibatkan gerakan. Anak kinestetik sering merasa bosan jika harus duduk diam terlalu lama dan lebih suka mencoba hal-hal baru dengan tangan atau tubuh mereka. Untuk mendukung anak kinestetik, orang tua bisa melibatkan anak dalam permainan edukatif yang mengharuskan mereka bergerak, memberikan kesempatan untuk eksperimen, atau menggunakan alat peraga yang dapat disentuh dan dipelajari secara langsung.\n" +
                        "\n" +
                        "Dengan mengetahui gaya belajar anak, orang tua dapat menyesuaikan metode pembelajaran yang sesuai, sehingga anak dapat lebih mudah memahami materi. Metode yang tepat akan membuat anak merasa lebih percaya diri, mengoptimalkan potensinya, dan memperoleh pengalaman belajar yang lebih menyenangkan dan produktif. Menghargai dan mendukung gaya belajar anak akan membantu mereka belajar dengan lebih efektif, mengurangi rasa tertekan, dan menciptakan kebiasaan belajar yang positif yang akan bermanfaat di masa depan."
            )
            startActivity(intent)
        }
        val cardViewTips3 = findViewById<CardView>(R.id.cardView3)
        cardViewTips3.setOnClickListener{
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("title", "Tips Menangani anak yang kesulitan fokus belajar")
            intent.putExtra(
                "content",
                "Anak yang kesulitan fokus belajar memerlukan pendekatan khusus agar dapat lebih konsentrasi. Berikut beberapa tips yang bisa diterapkan:\n" +
                        "\n" +
                        "1. Ciptakan Lingkungan Belajar yang Kondusif\n" +
                        "Pastikan tempat belajar bebas gangguan, seperti suara televisi, ponsel, atau mainan yang berserakan. Sediakan meja belajar yang rapi, terang, dan nyaman.\n" +
                        "\n" +
                        "2. Atur Durasi Belajar yang Singkat dan Teratur\n" +
                        "Gunakan metode belajar seperti Pomodoro, di mana anak belajar selama 25 menit dan beristirahat 5 menit. Durasi singkat membantu anak tetap fokus tanpa merasa terbebani.\n" +
                        "\n" +
                        "3. Kurangi Distraksi dan Multitasking\n" +
                        "Pastikan anak fokus pada satu tugas dalam satu waktu. Matikan notifikasi gadget atau jauhkan perangkat yang tidak digunakan selama belajar.\n" +
                        "\n" +
                        "4. Buat Jadwal Belajar yang Menarik\n" +
                        "Susun jadwal yang seimbang antara waktu belajar, istirahat, dan bermain. Libatkan anak dalam membuat jadwal agar mereka merasa memiliki kendali atas rutinitasnya.\n" +
                        "\n" +
                        "5. Gunakan Aktivitas Interaktif\n" +
                        "Belajar tidak harus selalu dalam bentuk membaca atau menulis. Gunakan metode kreatif seperti video edukasi, kuis interaktif, atau permainan yang menyenangkan agar anak lebih tertarik.\n" +
                        "\n" +
                        "6. Berikan Motivasi dan Apresiasi\n" +
                        "Berikan pujian atau hadiah kecil saat anak berhasil menyelesaikan tugas. Motivasi positif dapat meningkatkan semangat belajar mereka.\n" +
                        "\n" +
                        "7. Pastikan Anak Cukup Istirahat dan Nutrisi\n" +
                        "Kualitas tidur yang baik dan pola makan sehat sangat berpengaruh terhadap kemampuan fokus anak. Pastikan anak mendapatkan waktu tidur yang cukup serta asupan makanan bergizi.\n" +
                        "\n" +
                        "8. Libatkan Aktivitas Fisik\n" +
                        "Ajak anak bergerak atau berolahraga ringan saat istirahat. Aktivitas fisik dapat membantu meningkatkan konsentrasi dan mengurangi kejenuhan.\n" +
                        "\n" +
                        "Dengan menerapkan langkah-langkah di atas, anak yang kesulitan fokus belajar dapat lebih mudah berkonsentrasi dan menyelesaikan tugas-tugasnya dengan baik. Kesabaran dan dukungan orang tua sangat penting dalam mendampingi proses belajar anak.\n" +
                        "\n"
            )
            startActivity(intent)
        }

        val cardViewTips4 = findViewById<CardView>(R.id.cardView4)
        cardViewTips4.setOnClickListener{
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("title", "Alat bantu belajar yang bisa digunakan.")
            intent.putExtra(
                "content",
                "Penting bagi orang tua dan pendidik untuk memahami gaya belajar anak, apakah mereka lebih cenderung pada gaya belajar visual, auditori, atau kinestetik. Setiap anak memiliki cara yang unik dalam menyerap informasi, dan dengan mengetahui gaya belajar mereka, proses belajar dapat disesuaikan agar lebih efektif dan menyenangkan. Berikut adalah penjabaran mengenai masing-masing gaya belajar dan bagaimana cara menyesuaikan metode belajar dengan kebutuhan anak:\n" +
                        "\n" +
                        "1. Gaya Belajar Visual\n" +
                        "Anak dengan gaya belajar visual cenderung lebih mudah memahami informasi melalui gambar, warna, grafik, dan visualisasi. Mereka lebih cepat menangkap konsep yang disajikan dalam bentuk visual daripada yang disampaikan hanya secara lisan. Untuk mendukung anak dengan gaya ini, orang tua atau pendidik bisa menggunakan gambar, flashcard, diagram, atau mind maps untuk menjelaskan materi. Selain itu, menyajikan informasi melalui video edukasi atau presentasi berwarna juga dapat membantu anak visual lebih mudah memahami topik yang diajarkan. Membuat catatan berwarna-warni atau gambar untuk menggambarkan konsep juga akan sangat bermanfaat bagi mereka.\n" +
                        "\n" +
                        "2. Gaya Belajar Auditori\n" +
                        "Anak dengan gaya belajar auditori lebih cenderung untuk menyerap informasi melalui pendengaran. Mereka mudah memahami materi yang dibacakan atau didengarkan, serta lebih tertarik pada diskusi, percakapan, atau mendengarkan cerita. Anak dengan gaya belajar ini lebih mudah mengingat informasi melalui lagu atau ritme. Untuk mendukung anak auditori, orang tua atau pendidik bisa membacakan materi dengan jelas dan berulang-ulang, mengajak mereka untuk berdiskusi tentang topik yang dipelajari, atau menggunakan rekaman suara atau podcast edukasi. Menggunakan lagu-lagu edukatif atau cerita lisan yang berkaitan dengan materi yang sedang dipelajari juga akan membantu anak auditori untuk memahami dan mengingat materi dengan lebih baik.\n" +
                        "\n" +
                        "3. Gaya Belajar Kinestetik  \n" +
                        "Anak dengan gaya belajar kinestetik lebih suka belajar melalui gerakan fisik dan pengalaman langsung. Mereka cenderung merasa bosan jika harus duduk diam dalam waktu lama dan lebih mudah memahami konsep melalui aktivitas fisik atau praktik langsung. Untuk mendukung anak kinestetik, orang tua atau pendidik bisa melibatkan mereka dalam permainan edukatif yang memerlukan gerakan, eksperimen, atau aktivitas luar ruangan yang terkait dengan topik yang diajarkan. Menggunakan alat peraga yang bisa disentuh atau melakukan role play juga dapat membantu anak kinestetik lebih memahami materi. Selain itu, memberikan kesempatan bagi mereka untuk mencoba hal baru atau membuat sesuatu dengan tangan mereka akan sangat mendukung pembelajaran mereka.\n" +
                        "\n" +
                        "Mengapa Menyesuaikan Metode Belajar Itu Penting?\n" +
                        "Menyesuaikan metode belajar dengan gaya anak akan membuat proses belajar menjadi lebih mudah dan menyenangkan. Anak yang belajar dengan cara yang sesuai dengan gaya belajarnya akan merasa lebih percaya diri dan tidak merasa tertekan. Mereka akan lebih mudah memahami materi dan termotivasi untuk terus belajar. Selain itu, dengan mengoptimalkan gaya belajar anak, kita dapat membantu mereka memaksimalkan potensi mereka dan menciptakan pengalaman belajar yang lebih produktif.\n" +
                        "\n" +
                        "Dengan demikian, orang tua dan pendidik perlu memahami gaya belajar anak agar dapat memberikan dukungan yang tepat. Ini akan membantu anak tidak hanya dalam menguasai materi yang sedang dipelajari, tetapi juga dalam mengembangkan keterampilan belajar yang akan bermanfaat sepanjang hidup mereka."
            )
            startActivity(intent)
        }
    }
}