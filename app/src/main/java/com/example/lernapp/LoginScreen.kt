package com.example.lernapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        auth = FirebaseAuth.getInstance()

        Registrieren.setOnClickListener {
         startActivity(Intent(this, SignUp::class.java))
         finish()
        }

        Anmelden.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
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

        auth.signInWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Anmeldung fehlgeschlagen.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(baseContext, "Anmeldung fehlgeschlagen.",
                Toast.LENGTH_SHORT).show()
        }

    }
}
