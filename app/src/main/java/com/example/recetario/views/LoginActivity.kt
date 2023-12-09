package com.example.recetario.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recetario.R
import com.example.recetario.controllers.AuthApi
import com.example.recetario.models.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity:AppCompatActivity() {

    private lateinit var api: AuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        api = AuthApi()

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val txtUsu: EditText = findViewById(R.id.userNameEditText)
        val txtCon: EditText = findViewById(R.id.passwordEditText)
        val btnLogin: Button = findViewById(R.id.loginButton)
        txtUsu.setText("fabian.munoz@congresogto.gob.mx")
        txtCon.setText("123456")

        btnLogin.setOnClickListener {

            val auth = Auth(txtUsu.text.toString(), txtCon.text.toString())
            if (isValid(auth)) {
                progressBar.visibility = View.VISIBLE

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val result = api.fetchUser(auth)

                        withContext(Dispatchers.Main) {

                            val intent = Intent(this@LoginActivity, RecipeListActivity::class.java)
                            intent.putExtra("name", result.data?.get(0)?.name)
                            startActivity(intent)
                            finish()
                        }
                    } catch (e: Exception) {
                        Log.e("Auth error: ", e.message.toString())

                        withContext(Dispatchers.Main) {
                            progressBar.visibility = View.INVISIBLE
                            onLoginError(this@LoginActivity, "Login error")
                        }
                    }
                }
            }
        }
    }

    private fun isValid(auth: Auth): Boolean {
        var flag: Boolean = false
        if (TextUtils.isEmpty(auth.userName))
            onLoginError(this, "Please enter Email");
        else if (!Patterns.EMAIL_ADDRESS.matcher(auth.userName).matches())
            onLoginError(this, "Please enter A valid Email");
        else if (TextUtils.isEmpty(auth.password))
            onLoginError(this, "Please enter Password");
        else if (auth.password?.length!! < 6)
            onLoginError(this, "Please enter Password greater the 6 char");
        else
            flag = true
        return flag
    }

    private fun onLoginError(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}