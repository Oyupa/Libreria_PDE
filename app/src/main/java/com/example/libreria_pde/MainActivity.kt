package com.example.libreria_pde

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libreria_pde.ui.theme.Libreria_PDETheme

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var novelAdapter: NovelAdapter
    private lateinit var novelList: MutableList<Novel> // Lista de novelas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Inicializamos la lista de novelas
        novelList = mutableListOf()

        // Configuramos el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewNovels)
        novelAdapter = NovelAdapter(novelList) { novel -> showNovelDetails(novel) }
        recyclerView.adapter = novelAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Botón para agregar una nueva novela
        val addNovelButton: Button = findViewById(R.id.buttonAddNovel)
        addNovelButton.setOnClickListener {
            addNewNovel() // Método para agregar una novela
        }
    }

    // Método para agregar una nueva novela
    private fun addNewNovel() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Agregar Nueva Novela")

        // Crear un layout para el formulario
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 20, 50, 20)

        // Crear campos para el título y detalles de la novela
        val titleInput = EditText(this)
        titleInput.hint = "Título de la novela"
        layout.addView(titleInput)

        val detailsInput = EditText(this)
        detailsInput.hint = "Detalles de la novela"
        layout.addView(detailsInput)

        dialogBuilder.setView(layout)

        // Botones del diálogo
        dialogBuilder.setPositiveButton("Agregar") { _, _ ->
            val title = titleInput.text.toString().trim()
            val details = detailsInput.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val newNovel = Novel(title, details)
            novelList.add(newNovel)
            novelAdapter.notifyDataSetChanged()
        }
        dialogBuilder.setNegativeButton("Cancelar", null)

        // Mostrar el diálogo
        dialogBuilder.show()
    }

    // Método para mostrar los detalles de la novela seleccionada
    private fun showNovelDetails(novel: Novel) {
        // Mostrar el layout de detalles
        val novelDetailView: LinearLayout = findViewById(R.id.novelDetailView)
        novelDetailView.visibility = View.VISIBLE

        // Asignar la información de la novela a los TextView correspondientes
        findViewById<TextView>(R.id.textViewNovelTitle).text = novel.title
        findViewById<TextView>(R.id.textViewNovelDetails).text = novel.details
        findViewById<TextView>(R.id.textViewReviews).text = novel.reviews.joinToString("\n")

        // Botón para marcar como favorita
        val favoriteButton = findViewById<Button>(R.id.buttonFavorite)
        favoriteButton.text = if (novel.isFavorite) "Desmarcar Favorita" else "Marcar como Favorita"
        favoriteButton.setOnClickListener {
            novel.isFavorite = !novel.isFavorite
            // Cambiar el texto del botón según el estado
            favoriteButton.text = if (novel.isFavorite) "Desmarcar Favorita" else "Marcar como Favorita"
            novelAdapter.notifyDataSetChanged()
        }

        // Botón para agregar reseñas
        findViewById<Button>(R.id.buttonAddReview).setOnClickListener {
            addReview(novel)
        }
    }

    // Método para agregar reseñas a la novela seleccionada
    private fun addReview(novel: Novel) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Agregar Reseña")

        // Crear campo para la reseña
        val reviewInput = EditText(this)
        reviewInput.hint = "Escribe tu reseña aquí"
        dialogBuilder.setView(reviewInput)

        // Botones del diálogo
        dialogBuilder.setPositiveButton("Agregar") { _, _ ->
            val review = reviewInput.text.toString().trim()
            if (review.isEmpty()) {
                Toast.makeText(this, "La reseña no puede estar vacía", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            novel.reviews.add(review)
            findViewById<TextView>(R.id.textViewReviews).text = novel.reviews.joinToString("\n")
        }
        dialogBuilder.setNegativeButton("Cancelar", null)

        // Mostrar el diálogo
        dialogBuilder.show()
    }
}
