package com.example.lernapp

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class CreateCategory : AppCompatActivity() {

    lateinit var subjectFragment: SubjectFragment
    lateinit var nameCategory: EditText
    lateinit var saveCategory: Button
    private lateinit var database: DatabaseReference
    //lateinit var categoriesList: MutableList<Categories>
    //lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)

        database = FirebaseDatabase.getInstance().getReference("Categorys")
        //categoriesList = mutableListOf()

        saveCategory = findViewById(R.id.saveCategory)
        nameCategory = findViewById(R.id.nameCategory)
        //TODO: Fehler da R.id. sagt suche in dem setContentVIEW XML, das ist das falsche! categories.xml wäre richtig
        //listView = findViewById(R.id.listView)

        saveCategory.setOnClickListener {
            savesCategory()
        }

        /*database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                //If there is some data it will loop all the data
                if (p0!!.exists()) {
                    for (h in p0.children) {
                        val categories = h.getValue(Categories::class.java)
                        categoriesList.add(categories!!)
                    }

                    val adapter = CategoriesAdapter(applicationContext, R.layout.categories, categoriesList)
                    listView.adapter = adapter

                }
            }

        })*/

    }

    fun savesCategory() {

        val kategorie = nameCategory.text.toString().trim()

        if(kategorie.isEmpty()) {
            nameCategory.error = "Geben Sie einen Name an."
            return
        }

        val id = database.push().key.toString()
        val kat = Categories(id, kategorie)

        database.child(id).setValue(kat)
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
