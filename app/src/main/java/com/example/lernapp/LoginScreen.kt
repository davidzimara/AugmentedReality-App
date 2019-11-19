package com.example.lernapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_settings.*

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
        if(stv_username.text.toString().isEmpty()) {
            stv_username.error = "Bitte geben Sie eine Email Adresse an."
            stv_username.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(stv_username.text.toString()).matches()) {
            stv_username.error = "Bitte geben Sie eine gültige Email Adresse an."
            stv_username.requestFocus()
            return
        }

        if(stv_password.text.toString().isEmpty()) {
            stv_password.error = "Bitte Passwort angeben."
            stv_password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(stv_username.text.toString(), stv_password.text.toString())
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

    //Single SIGN-ON current problem: after registration the app is running onStart
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
            Toast.makeText(
                baseContext, "Anmeldung fehlgeschlagen.",
                Toast.LENGTH_SHORT)
                .show()
        }

    }

}


