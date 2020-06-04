package com.example.AugmentedRealityApp.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.AugmentedRealityApp.DataClasses.UserReport
import com.example.AugmentedRealityApp.DataClasses.Users
import com.example.AugmentedRealityApp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

        //Initialize Database Preferences for UserReports DataClass
        val commentInitl1 = UserReport("l1","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl1 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l1").child("report")
        databaseCommentsl1.setValue(commentInitl1)

        val commentInitl2 = UserReport("l2","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl2 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l2").child("report")
        databaseCommentsl2.setValue(commentInitl2)

        val commentInitl3 = UserReport("l3","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl3 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l3").child("report")
        databaseCommentsl3.setValue(commentInitl3)

        val commentInitl4 = UserReport("l4","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl4 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l4").child("report")
        databaseCommentsl4.setValue(commentInitl4)

        val commentInitl5 = UserReport("l5","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl5 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l5").child("report")
        databaseCommentsl5.setValue(commentInitl5)

        val commentInitl6 = UserReport("l6","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl6 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l6").child("report")
        databaseCommentsl6.setValue(commentInitl6)

        val commentInitl7 = UserReport("l7","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl7 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l7").child("report")
        databaseCommentsl7.setValue(commentInitl7)

        val commentInitl8 = UserReport("l8","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl8 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l8").child("report")
        databaseCommentsl8.setValue(commentInitl8)

        val commentInitl9 = UserReport("l9","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl9 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l9").child("report")
        databaseCommentsl9.setValue(commentInitl9)

        val commentInitl10 = UserReport("l10","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl10 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l10").child("report")
        databaseCommentsl10.setValue(commentInitl10)

        val commentInitl11 = UserReport("l11","Fügen Sie eine Notiz hinzu", false)

        val databaseCommentsl11 = FirebaseDatabase.getInstance().getReference("user").child(userId).child("l11").child("report")
        databaseCommentsl11.setValue(commentInitl11)
    }

}
