package com.example.recetario.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recetario.R
import com.example.recetario.controllers.AuthApi
import com.example.recetario.controllers.RecipeApi
import com.example.recetario.models.Recipe
import com.example.recetario.models.RecipeListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeListActivity : AppCompatActivity() {

    private lateinit var api: RecipeApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        api = RecipeApi()

        val recyclerView: RecyclerView = findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        Thread.sleep(2000)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = api.fetchRecipes()

                withContext(Dispatchers.Main) {

                    val adapter = result.data?.let { RecipeListAdapter(it) }
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
    }
    private fun onLoginError(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}