package com.example.lernapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class LoadingScreen: AppCompatActivity() {

    // 2 seconds timeout
    private val SPLASH_TIME_OUT: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen)

        Handler().postDelayed({

            startActivity(Intent(this, LoginScreen::class.java))

            finish()

        },SPLASH_TIME_OUT)
    }
}