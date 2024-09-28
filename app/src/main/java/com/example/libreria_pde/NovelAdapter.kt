package com.example.libreria_pde

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NovelAdapter(
    private val novels: List<Novel>,
    private val onNovelClick: (Novel) -> Unit
) : RecyclerView.Adapter<NovelAdapter.NovelViewHolder>() {

    class NovelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewNovelTitle)
        val detailsTextView: TextView = itemView.findViewById(R.id.textViewNovelDetails)
        val favoriteStar: ImageView = itemView.findViewById(R.id.imageFavoriteStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.novel_item, parent, false)
        return NovelViewHolder(view)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val novel = novels[position]
        holder.titleTextView.text = novel.title
        holder.detailsTextView.text = novel.details

        // Colores alternos
        val backgroundColor = if (position % 2 == 0)
            Color.parseColor("#CCE5FF")  // Azul clarito
        else
            Color.parseColor("#CCFFCC")  // Verde clarito

        holder.itemView.setBackgroundColor(backgroundColor)

        // Mostrar la estrella según si es favorito
        holder.favoriteStar.setImageResource(
            if (novel.isFavorite) R.drawable.ic_star_filled
            else R.drawable.ic_star_border
        )

        // Evento de clic para mostrar detalles
        holder.itemView.setOnClickListener {
            onNovelClick(novel)
        }
    }

    override fun getItemCount(): Int = novels.size
}
