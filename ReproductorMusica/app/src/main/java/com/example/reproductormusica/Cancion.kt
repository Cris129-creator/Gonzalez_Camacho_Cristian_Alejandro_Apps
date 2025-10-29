package com.example.reproductormusica
import androidx.annotation.DrawableRes
data class Cancion(
    val nombreArchivo: String,
    val titulo: String,
    val artista: String,
    @DrawableRes val imagenAlbum: Int
)