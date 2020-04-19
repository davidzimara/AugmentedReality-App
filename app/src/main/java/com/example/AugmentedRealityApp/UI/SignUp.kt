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
        val commentInitl1 = Comments("l1","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl1 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l1").child("comments")
        databaseCommentsl1.setValue(commentInitl1)

        val commentInitl2 = Comments("l2","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl2 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l2").child("comments")
        databaseCommentsl2.setValue(commentInitl2)

        val commentInitl3 = Comments("l3","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl3 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l3").child("comments")
        databaseCommentsl3.setValue(commentInitl3)

        val commentInitl4 = Comments("l4","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl4 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l4").child("comments")
        databaseCommentsl4.setValue(commentInitl4)

        val commentInitl5 = Comments("l5","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl5 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l5").child("comments")
        databaseCommentsl5.setValue(commentInitl5)

        val commentInitl6 = Comments("l6","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl6 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l6").child("comments")
        databaseCommentsl6.setValue(commentInitl6)

        val commentInitl7 = Comments("l7","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl7 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l7").child("comments")
        databaseCommentsl7.setValue(commentInitl7)

        val commentInitl8 = Comments("l8","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl8 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l8").child("comments")
        databaseCommentsl8.setValue(commentInitl8)

        val commentInitl9 = Comments("l9","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl9 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l9").child("comments")
        databaseCommentsl9.setValue(commentInitl9)

        val commentInitl10 = Comments("l10","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl10 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l10").child("comments")
        databaseCommentsl10.setValue(commentInitl10)

        val commentInitl11 = Comments("l11","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl11 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l11").child("comments")
        databaseCommentsl11.setValue(commentInitl11)

        val commentInitl12 = Comments("l12","Fügen Sie eine Notiz hinzu")

        val databaseCommentsl12 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l12").child("comments")
        databaseCommentsl12.setValue(commentInitl12)
    }

}
