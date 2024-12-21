import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.cr.AdapterIngredientes
import com.example.cr.CarouselAdapter
import com.example.cr.R
import com.example.cr.Recipe

class RecipeAdapter : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ctrl_publicacion, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tituloReceta)
        private val chefTextView: TextView = itemView.findViewById(R.id.nombreChef)
        private val chefImageView: ImageView = itemView.findViewById(R.id.imagenChef)
        private val ingredientsRecyclerView: RecyclerView = itemView.findViewById(R.id.listaIngredientes)
        private val stepsRecyclerView: RecyclerView = itemView.findViewById(R.id.listaElaboracion)
        private val imageViewPager: ViewPager2 = itemView.findViewById(R.id.viewPager)

        fun bind(recipe: Recipe) {
            // Set recipe title
            titleTextView.text = recipe.Titulo_Receta

            // Set chef name
            chefTextView.text = recipe.ChefNombre ?: "Chef Desconocido"

            // Load chef image using Glide
            Glide.with(itemView.context)
                .load(recipe.ChefImagen)
                .placeholder(R.drawable.cocinero)
                .error(R.drawable.cocinero)
                .fallback(R.drawable.cocinero)  // If the URL is null
                .circleCrop()
                .into(chefImageView)

            // Set ingredients
            val ingredientsList = recipe.Ingredientes.map { "${it.key}: ${it.value}" }
            ingredientsRecyclerView.adapter = AdapterIngredientes(ingredientsList)
            ingredientsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)

            // Set elaboration steps
            stepsRecyclerView.adapter = AdapterIngredientes(recipe.Pasos_Elaboracion)
            stepsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)

            // Set up image carousel
            val carouselAdapter = CarouselAdapter(recipe.Imagenes)
            imageViewPager.adapter = carouselAdapter
            imageViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.ID_Receta == newItem.ID_Receta
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}