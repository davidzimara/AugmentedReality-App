package com.example.lernapp

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateCategory : AppCompatActivity() {

    lateinit var subjectFragment: SubjectFragment
    lateinit var nameCategory: EditText
    lateinit var saveCategory: Button
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)

        saveCategory = findViewById(R.id.saveCategory)
        nameCategory = findViewById(R.id.nameCategory)

        saveCategory.setOnClickListener {
            savesCategory()
        }

    }

    fun savesCategory() {

        val kategorie = nameCategory.text.toString().trim()

        if(kategorie.isEmpty()) {
            nameCategory.error = "Geben Sie einen Name an."
            return
        }

        database = FirebaseDatabase.getInstance().getReference("Categorys")
        val id = database.push().key.toString()

        database.child(id).child("name").setValue(kategorie)
            .addOnCompleteListener {
                Toast.makeText(baseContext, kategorie + " wurde gespeichert.", Toast.LENGTH_LONG).show()
            }
            .addOnCanceledListener {
                Toast.makeText(baseContext, kategorie + " wurde nicht gespeichert.", Toast.LENGTH_LONG).show()
            }
    }


    fun back_to_subject(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        // To pass any data to next activity
        intent.putExtra("keyIdentifier", 2)
        // set which fragment should be loaded
        intent.putExtra("FrgToLoad", "SubjectFr")

        // start your next activity
        startActivity(intent)
    }


}
