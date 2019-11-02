package com.example.lernapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_create_category.view.*

class CreateCategory : AppCompatActivity() {

    lateinit var subjectFragment: SubjectFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)

    }


    fun back_to_subject(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        // To pass any data to next activity
        intent.putExtra("keyIdentifier", 2)
        // start your next activity
        startActivity(intent)

        //TODO: Aufruf Kategorien Fragment "fragment_subject" (momentan Home)

    }


}
