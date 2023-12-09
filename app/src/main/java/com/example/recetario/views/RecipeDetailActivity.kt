package com.example.recetario.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recetario.R
import com.example.recetario.models.Recipe
import com.example.recetario.models.Serialization

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val btnBack = findViewById<com.google.android.material.button.MaterialButton>(R.id.buttonBack)
        btnBack?.setOnClickListener {

            val recipes = Intent(this, RecipeListActivity::class.java)
            startActivity(recipes)
            finish()
        }

        try {
            val serialized = intent.getByteArrayExtra("data")
            val recipe: Recipe? = serialized?.let { Serialization.deserializeRecipe(it) }
            showRecipe(recipe)
        } catch (e: Exception) {
            onLoginError(this, e.message)
        }
    }

    private fun showRecipe(recipe: Recipe?) {
        if (recipe != null) {
            val imageView: ImageView = findViewById(R.id.recipeImageView)

            val title: TextView = findViewById(R.id.recipeTitleTextView)
            val category: TextView = findViewById(R.id.recipeCategoryTextView)
            val time: TextView = findViewById(R.id.recipeTimeTextView)

            val ingredients: TextView = findViewById(R.id.recipeIngredientsListTextView)
            val instructions: TextView = findViewById(R.id.recipeInstructionsListTextView)

            val ratingBar: RatingBar = findViewById(R.id.recipeStarsRatingBar)

            // Load image using Glide
            Glide.with(this)
                .load(recipe.imageUrl)
                .into(imageView)

            title.text = recipe.title
            category.text = recipe.category
            time.text = recipe.minutes.toString() + " minutes"

            ingredients.text = recipe.ingredients.joinToString(", ")
            instructions.text = recipe.instructions.joinToString(", ")

            ratingBar.rating = recipe.stars.toFloat()
        }
    }

    private fun onLoginError(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}