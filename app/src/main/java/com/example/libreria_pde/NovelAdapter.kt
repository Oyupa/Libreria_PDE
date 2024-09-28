package com.example.libreria_pde

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NovelAdapter(
    private var novelList: MutableList<Novel>,
    private val onNovelClick: (Novel) -> Unit
) : RecyclerView.Adapter<NovelAdapter.NovelViewHolder>() {

    inner class NovelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewNovelTitle)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewNovelAuthor)
        val textViewYear: TextView = itemView.findViewById(R.id.textViewNovelYear)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.imageViewFavorite)

        fun bind(novel: Novel) {
            textViewTitle.text = novel.title
            textViewAuthor.text = "Autor: ${novel.author}"
            textViewYear.text = "Año: ${novel.year}"

            // Cambiar el ícono de favorito
            favoriteIcon.setImageResource(if (novel.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_border)

            // Establecer un clic para mostrar detalles de la novela
            itemView.setOnClickListener { onNovelClick(novel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.novel_item, parent, false)
        return NovelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val novel = novelList[position]
        holder.bind(novel)

        // Cambiar el color de fondo para diferenciar novelas
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(0xFFE0F7FA.toInt()) // Azul claro
        } else {
            holder.itemView.setBackgroundColor(0xFFE8F5E9.toInt()) // Verde claro
        }
    }

    override fun getItemCount(): Int {
        return novelList.size
    }

    // Método para actualizar la lista de novelas
    fun updateNovels(novels: MutableList<Novel>) {
        this.novelList = novels
        notifyDataSetChanged()
    }
}