package com.example.reproductormusica

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BibliotecaActivity : AppCompatActivity(), CancionAdapter.OnItemClickListener {
    private val playlist = listOf(
        // ... (Tu lista de canciones completa va aquí) ...
        Cancion("fuerza.mp3", "Modo Maldito", "Fuerza Regida", R.drawable.fuerzare),
        Cancion("julieta.mp3", "De que me sirve", "Julieta Venegas", R.drawable.julietals),
        Cancion("never.mp3", "Never Getting Late", "Sabrina Carpenter", R.drawable.mans),
        Cancion("sangree.mp3", "Sangre", "C.R.O", R.drawable.crock),
        Cancion("siennaa.mp3", "Sienna", "The Marias", R.drawable.maria),
        Cancion("outlander.mp3", "Outlander", "Westside Gunn", R.drawable.westside),
        Cancion("pac.mp3", "I Ain't Mad at Cha", "2pac", R.drawable.paceyez)
    )

    private lateinit var btnMiniPlay: ImageButton
    private lateinit var btnMiniSiguiente: ImageButton
    private lateinit var tvMiniTitulo: TextView
    private lateinit var tvMiniArtista: TextView
    private lateinit var ivMiniAlbum: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biblioteca)


        ServicioMusica.setPlaylist(playlist)


        val rvCanciones: RecyclerView = findViewById(R.id.rvCanciones)
        val miniReproductor: ConstraintLayout = findViewById(R.id.miniReproductor)
        btnMiniPlay = findViewById(R.id.btnMiniPlay)
        btnMiniSiguiente = findViewById(R.id.btnMiniSiguiente)
        tvMiniTitulo = findViewById(R.id.tvMiniTitulo)
        // No tienes un TextView para el artista en el mini-reproductor, lo omitimos por ahora
        ivMiniAlbum = findViewById(R.id.ivMiniAlbum)


        rvCanciones.layoutManager = LinearLayoutManager(this)
        rvCanciones.adapter = CancionAdapter(playlist, this)


        miniReproductor.setOnClickListener {
            // Solo navega si hay una canción seleccionada
            if (ServicioMusica.cancionActualIndex != -1) {
                val intent = Intent(this, repertorio::class.java)
                startActivity(intent)
            }
        }

        btnMiniPlay.setOnClickListener { ServicioMusica.togglePlayPause() }
        btnMiniSiguiente.setOnClickListener { ServicioMusica.reproducirSiguiente(this) }


        observarCambiosDelReproductor()
    }

    private fun observarCambiosDelReproductor() {

        ServicioMusica.cancionActual.observe(this, Observer { cancion ->
            if (cancion != null) {
                tvMiniTitulo.text = cancion.titulo
                ivMiniAlbum.setImageResource(cancion.imagenAlbum)
            } else {

                tvMiniTitulo.text = "Nombre Canción"
                ivMiniAlbum.setImageResource(R.drawable.mp3port)
            }
        })


        ServicioMusica.isPlaying.observe(this, Observer { isPlaying ->
            if (isPlaying) {
                btnMiniPlay.setImageResource(R.drawable.pausa)
            } else {
                btnMiniPlay.setImageResource(R.drawable.play)
            }
        })
    }


    override fun onItemClick(cancion: Cancion, position: Int) {

        ServicioMusica.iniciarCancion(this, position)


        val intent = Intent(this, repertorio::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        ServicioMusica.release()
    }
}
