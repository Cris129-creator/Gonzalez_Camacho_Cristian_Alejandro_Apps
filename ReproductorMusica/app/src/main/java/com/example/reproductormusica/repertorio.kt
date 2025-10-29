package com.example.reproductormusica

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

// Esta Activity ahora solo MUESTRA el estado del ServicioMusica
class repertorio : AppCompatActivity() {

    private lateinit var btnPlayPausa: ImageButton
    private lateinit var btnSiguiente: ImageButton
    private lateinit var btnAnterior: ImageButton
    private lateinit var btnDetener: ImageButton
    private lateinit var btnMinimizar: ImageButton
    private lateinit var ivAlbumPort: ImageView
    private lateinit var tvTituloCancion: TextView
    private lateinit var tvNombreArtista: TextView
    private lateinit var seekBar: SeekBar
    // --- CORRECCIÓN 1: Declarar las variables de tiempo que faltaban ---
    private lateinit var tvTiempoActual: TextView
    private lateinit var tvTiempoTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repertorio)

        // --- Encontrar Vistas ---
        btnPlayPausa = findViewById(R.id.btnPlayPausa)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        btnAnterior = findViewById(R.id.btnAnterior)
        btnDetener = findViewById(R.id.btnDetener)
        btnMinimizar = findViewById(R.id.btnMinimizar)
        ivAlbumPort = findViewById(R.id.ivAlbumPort)
        tvTituloCancion = findViewById(R.id.tvTituloCancion)
        tvNombreArtista = findViewById(R.id.tvNombreArtista)
        seekBar = findViewById(R.id.seekBar)
        tvTiempoActual = findViewById(R.id.tvTiempoActual)
        tvTiempoTotal = findViewById(R.id.tvTiempoTotal)
        btnPlayPausa.setOnClickListener { ServicioMusica.togglePlayPause() }
        btnSiguiente.setOnClickListener { ServicioMusica.reproducirSiguiente(this) }
        btnAnterior.setOnClickListener { ServicioMusica.reproducirAnterior(this) }
        btnDetener.setOnClickListener {
            ServicioMusica.release()
            finish()

        }

        btnMinimizar.setOnClickListener {
            finish()
        }


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    tvTiempoActual.text = formatearTiempo(progress) // Actualiza el tiempo mientras se arrastra
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No necesitamos hacer nada aquí
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    ServicioMusica.seekTo(it.progress)
                }
            }
        })

        observarCambiosDelReproductor()
    }

    private fun observarCambiosDelReproductor() {

        ServicioMusica.cancionActual.observe(this, Observer { cancion ->
            if (cancion != null) {
                ivAlbumPort.setImageResource(cancion.imagenAlbum)
                tvTituloCancion.text = cancion.titulo
                tvNombreArtista.text = cancion.artista
            } else {
                finish()
            }
        })

        ServicioMusica.isPlaying.observe(this, Observer { isPlaying ->
            btnPlayPausa.setImageResource(if (isPlaying) R.drawable.pausa else R.drawable.play)
        })

        ServicioMusica.duracionCancion.observe(this, Observer { duracion ->
            seekBar.max = duracion
            tvTiempoTotal.text = formatearTiempo(duracion)
        })

        ServicioMusica.posicionActual.observe(this, Observer { posicion ->

            if (!seekBar.isPressed) {
                seekBar.progress = posicion
                tvTiempoActual.text = formatearTiempo(posicion)
            }
        })
    }


    private fun formatearTiempo(milisegundos: Int): String {
        val minutos = (milisegundos / 1000) / 60
        val segundos = (milisegundos / 1000) % 60
        return String.format("%d:%02d", minutos, segundos)
    }
}
