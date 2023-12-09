package com.example.recetario.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recetario.R
import com.example.recetario.controllers.RecipeApi
import com.example.recetario.models.Recipe
import com.example.recetario.models.RecipeListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeListActivity : AppCompatActivity() {
    private lateinit var api: RecipeApi
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipes: List<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        api = RecipeApi()
        recyclerView = findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val btnCreate = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.buttonCreate)
        val searchBar = findViewById<com.google.android.material.search.SearchBar>(R.id.search_bar)
        val searchView = findViewById<com.google.android.material.search.SearchView>(R.id.mySearchView)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = api.fetchRecipes()
                withContext(Dispatchers.Main) {

                    recipes = result.data!!
                    val adapter = recipes?.let { RecipeListAdapter(it) }!!
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                Log.e("Recipe error: ", e.message.toString())

                withContext(Dispatchers.Main) {
                    onLoginError(this@RecipeListActivity, "Recipe error")
                }
            } finally {
                progressBar.visibility = View.INVISIBLE
            }
        }
        if (intent.hasExtra("name")) {

            val name = intent.getStringExtra("name")
            val toast = Toast.makeText(this, "Bienvenido ${name}!", Toast.LENGTH_LONG)
            toast.show()
        }
        btnCreate?.setOnClickListener {
            val recipes = Intent(this, RecipeNewActivity::class.java)
            startActivity(recipes)
        }
        searchView.editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {

                // Perform filtering based on the search query
                val query = searchView.text.toString()
                filterRecyclerView(query)

                Log.i("Query", query)
                searchBar.setText(query)
                searchView.hide()

                return@setOnEditorActionListener true
            }
            false
        }
    }
    private fun onLoginError(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    private fun filterRecyclerView(query: String) {
        // Implement your filtering logic here and update the RecyclerView adapter
        val filteredRecipes = recipes.filter { recipe ->
            recipe.title.contains(query, ignoreCase = true) ||
                    recipe.category.contains(query, ignoreCase = true)
        }

        val adapter = RecipeListAdapter(filteredRecipes)
        recyclerView.adapter = adapter
    }
}