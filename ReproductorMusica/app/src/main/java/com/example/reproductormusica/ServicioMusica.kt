package com.example.reproductormusica

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ServicioMusica {
    private var mediaPlayer: MediaPlayer? = null
    var playlist: List<Cancion> = emptyList()
        private set

    var cancionActualIndex: Int = -1
        private set

    private val _cancionActual = MutableLiveData<Cancion?>()
    val cancionActual: LiveData<Cancion?> get() = _cancionActual

    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    private val _posicionActual = MutableLiveData<Int>()
    val posicionActual: LiveData<Int> get() = _posicionActual

    private val _duracionCancion = MutableLiveData<Int>()
    val duracionCancion: LiveData<Int> get() = _duracionCancion

    private val handler = Handler(Looper.getMainLooper())
    private val actualizadorSeekBar = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    _posicionActual.value = it.currentPosition
                    handler.postDelayed(this, 1000)
                }
            }
        }
    }

    fun setPlaylist(nuevaPlaylist: List<Cancion>) {
        playlist = nuevaPlaylist
    }

    fun iniciarCancion(context: Context, index: Int) {
        if (index < 0 || index >= playlist.size) {
            _isPlaying.value = false
            return
        }
        handler.removeCallbacks(actualizadorSeekBar)

        cancionActualIndex = index
        val cancion = playlist[index]
        _cancionActual.value = cancion

        mediaPlayer?.release()
        mediaPlayer = null

        try {
            val assetFileDescriptor = context.assets.openFd(cancion.nombreArchivo)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
                prepareAsync()
                setOnPreparedListener { mp ->
                    mp.start()
                    _isPlaying.value = true
                    _duracionCancion.value = mp.duration
                    // --- CORRECCIÓN 1: Usa handler.post() para iniciar la actualización ---
                    handler.post(actualizadorSeekBar)
                }
                setOnCompletionListener {
                    reproducirSiguiente(context)
                }
            }
            assetFileDescriptor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun reproducirSiguiente(context: Context) {
        var siguienteIndex = if (cancionActualIndex == -1) 0 else cancionActualIndex + 1
        if (siguienteIndex >= playlist.size) {
            siguienteIndex = 0
        }
        iniciarCancion(context, siguienteIndex)
    }

    fun reproducirAnterior(context: Context) {
        var anteriorIndex = if (cancionActualIndex == -1) 0 else cancionActualIndex - 1
        if (anteriorIndex < 0) {
            anteriorIndex = playlist.size - 1
        }
        iniciarCancion(context, anteriorIndex)
    }

    fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _isPlaying.value = false
                handler.removeCallbacks(actualizadorSeekBar)
            } else {
                it.start()
                _isPlaying.value = true
                handler.post(actualizadorSeekBar)
            }
        }
    }

    fun seekTo(posicion: Int) {
        mediaPlayer?.seekTo(posicion)
        _posicionActual.value = posicion
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        cancionActualIndex = -1
        _cancionActual.value = null
        _isPlaying.value = false
        handler.removeCallbacks(actualizadorSeekBar)
    }
}
