package com.example.lernapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class LoadingScreen: AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 2000 // 2 seconds timeout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen)

        Handler().postDelayed({

            startActivity(Intent(this, MainActivity::class.java))

            finish()

        },SPLASH_TIME_OUT)
    }
}