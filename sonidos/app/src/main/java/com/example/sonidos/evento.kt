package com.example.sonidos

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
class evento : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_evento)

        val mainView = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imagenes = listOf(
            R.id.img1, R.id.img2, R.id.img3, R.id.img4,
            R.id.img5, R.id.img6, R.id.img7, R.id.img8,
            R.id.img9, R.id.img10, R.id.img11, R.id.img12
        )
        // Recursos de sonido correspondientes
        val sonidos = listOf(
            R.raw.hidanlaugh, R.raw.orochilaugh, R.raw.tobivoice, R.raw.deidaravoice,
            R.raw.shinra, R.raw.kisamejutsu, R.raw.konanvoice, R.raw.kakuzuvoice,
            R.raw.sasorivoice, R.raw.amaterasu, R.raw.nagatopain, R.raw.zetsuvoice
        )

        imagenes.forEachIndexed { index, i ->
            findViewById<ImageView>(i).setOnClickListener {
                mediaPlayer?.stop()
                mediaPlayer?.release()

                mediaPlayer = MediaPlayer.create(this, sonidos[index])
                mediaPlayer?.start()

                mediaPlayer?.setOnCompletionListener { mp ->
                    mp.release()
                }
            }
        }


        }

}
