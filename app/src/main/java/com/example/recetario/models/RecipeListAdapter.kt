package com.example.recetario.models

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recetario.R
import com.example.recetario.views.RecipeDetailActivity

class RecipeListAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(recipe.imageUrl)
            .into(holder.image)

        // Bind other data to views
        holder.title.text = recipe.title
        holder.ratingBar.rating = recipe.stars.toFloat()

        // Set click listener on the item
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra("data", Serialization.serializeRecipe(recipe))

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.recipeImageView)
        val title: TextView = itemView.findViewById(R.id.recipeTitleTextView)
        val ratingBar: RatingBar = itemView.findViewById(R.id.recipeStarsRatingBar)
    }
}