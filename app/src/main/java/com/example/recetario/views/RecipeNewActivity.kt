package com.example.recetario.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recetario.R
import com.example.recetario.controllers.RecipeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeNewActivity : AppCompatActivity() {
    private lateinit var api: RecipeApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_new)

        api = RecipeApi()

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val txtIngredients: EditText = findViewById(R.id.ingredientsEditText)

        val btnBack = findViewById<Button>(R.id.buttonBack)
        btnBack?.setOnClickListener {

            val recipes = Intent(this, RecipeListActivity::class.java)
            startActivity(recipes)
            finish()
        }
        val btnCreate = findViewById<Button>(R.id.buttonCreate)
        btnCreate.setOnClickListener {
            if (isValid(txtIngredients?.text.toString())) {
                progressBar.visibility = View.VISIBLE

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val result = api.createRecipe(txtIngredients.text.toString())

                        withContext(Dispatchers.Main) {
                            btnBack.performClick()
                        }
                    } catch (e: Exception) {
                        Log.e("Recipe creation error: ", e.message.toString())

                        withContext(Dispatchers.Main) {
                            progressBar.visibility = View.INVISIBLE
                            onLoginError(this@RecipeNewActivity, "Error creating recipe")
                        }
                    }
                }
            }
        }
    }
    private fun isValid(input: String): Boolean {
        var flag: Boolean = false
        if (TextUtils.isEmpty(input))
            onLoginError(this, "Please provide ingredients");
        else if (input?.length!! < 6)
            onLoginError(this, "Please enter text greater the 6 char");
        else
            flag = true
        return flag
    }

    private fun onLoginError(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}