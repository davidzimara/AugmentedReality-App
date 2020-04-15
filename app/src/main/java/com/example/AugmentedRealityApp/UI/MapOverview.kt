package com.example.AugmentedRealityApp.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.AugmentedRealityApp.Adapter.QuestionAdapter
import com.example.AugmentedRealityApp.DataClasses.Questions
import com.example.AugmentedRealityApp.R
import com.google.firebase.database.*

class MapOverview : AppCompatActivity() {

    lateinit var kategorieName: TextView
    lateinit var questionList: MutableList<Questions>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_overview)

        //Handed over from CategoryAdapter
        val locationId = intent.getStringExtra("extra_location_id")
        val locationName = intent.getStringExtra("extra_location_name")

        kategorieName = findViewById<TextView>(R.id.categoryName)

        kategorieName.setText(locationName)

        //val database = FirebaseDatabase.getInstance().getReference("Categorys").child(category).child("questions")

        questionList = mutableListOf()
        listView = findViewById(R.id.listViewQuestion)

        /*database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                questionList.clear()
                if (p0!!.exists()) {
                    for(h in p0.children) {
                        val question = h.getValue(Questions::class.java)
                        questionList.add(question!!)
                    }
                }
                val adapter = QuestionAdapter(this@MapOverview, R.layout.questions, questionList)
                listView.adapter = adapter
            }
        })*/
    }

    fun back_to_home(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
