package com.example.reproductormusica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CancionAdapter(
    private val canciones: List<Cancion>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CancionAdapter.CancionViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(cancion: Cancion, position: Int)
    }

    class CancionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.ivItemAlbum)
        val titulo: TextView = itemView.findViewById(R.id.tvItemTitulo)
        val artista: TextView = itemView.findViewById(R.id.tvItemArtista)

        fun bind(cancion: Cancion, position: Int, listener: OnItemClickListener) {
            itemView.setOnClickListener {
                listener.onItemClick(cancion, position)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cancion_biblioteca, parent, false)
        return CancionViewHolder(view)
    }


    override fun getItemCount(): Int {
        return canciones.size
    }


    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val cancion = canciones[position]
        holder.imagen.setImageResource(cancion.imagenAlbum)
        holder.titulo.text = cancion.titulo
        holder.artista.text = cancion.artista


        holder.bind(cancion, position, listener)
    }
}

