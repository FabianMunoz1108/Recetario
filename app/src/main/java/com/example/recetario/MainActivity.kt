package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.recetario.views.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splash.setKeepOnScreenCondition { true }
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}