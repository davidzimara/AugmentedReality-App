package com.example.AugmentedRealityApp.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.AugmentedRealityApp.DataClasses.Comments
import com.example.AugmentedRealityApp.DataClasses.Users
import com.example.AugmentedRealityApp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

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

                    val email = tv_username.text.toString()
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {

                        val userId = user.uid
                        writeNewUser(userId, email)
                    }
                } else {
                    Toast.makeText(baseContext, "Registrierung fehlgeschlagen. Verwenden Sie mindestens 6 Zeichen als Passwort.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun writeNewUser(userId: String, email: String) {

        //Initialize Database Preferences for new User
        val user= Users(userId, email)
        val database = FirebaseDatabase.getInstance().getReference("user").child(userId)

        database.setValue(user)

        //Initialize Database Preferences for Comments
        val commentInitl1 = Comments("l1","Fügen sie einen Kommentar hinzu")

        val databaseCommentsl1 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l1").child("comments")
        databaseCommentsl1.setValue(commentInitl1)

        val commentInitl2 = Comments("l2","Fügen sie einen Kommentar hinzu")

        val databaseCommentsl2 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l2").child("comments")
        databaseCommentsl2.setValue(commentInitl1)
    }

}
