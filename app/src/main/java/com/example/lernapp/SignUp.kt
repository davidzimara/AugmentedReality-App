package com.example.lernapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener {
            signUpUser()
        }

        btn_back_login.setOnClickListener {
            backLogin()
        }
    }

    private fun backLogin() {
        startActivity(Intent(this, LoginScreen::class.java))
        finish()
    }

    private fun signUpUser() {
        if(tv_username.text.toString().isEmpty()) {
            tv_username.error = "Bitte geben Sie eine Email Adresse an."
            tv_username.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {
            tv_username.error = "Bitte geben Sie eine gültige Email Adresse an."
            tv_username.requestFocus()
            return
        }

        if(tv_password.text.toString().isEmpty()) {
            tv_password.error = "Bitte Passwort angeben."
            tv_password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, LoginScreen::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Registrierung fehlgeschlagen. Verwenden Sie mindestens 6 Zeichen als Passwort.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

}
