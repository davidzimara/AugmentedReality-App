package com.example.lernapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.update_categories.*

class QuestionOverview : AppCompatActivity() {

    lateinit var kategorieId : TextView
    lateinit var kategorieName: TextView
    private lateinit var database: DatabaseReference
    lateinit var questionList: MutableList<Questions>
    lateinit var listView: ListView
    lateinit var delete: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_overview)

        //TODO: EDIT BUTTON
        //TODO: Delete Button

        val category = intent.getStringExtra("extra_category_id")
        val categoryName = intent.getStringExtra("extra_category_name")

        kategorieName = findViewById<TextView>(R.id.categoryName)

        kategorieName.setText(categoryName)

        val database = FirebaseDatabase.getInstance().getReference("Categorys").child(category).child("questions")

        questionList = mutableListOf()
        listView = findViewById(R.id.listViewQuestion)

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    questionList.clear()
                    for(h in p0.children) {
                        val question = h.getValue(Questions::class.java)
                        questionList.add(question!!)
                    }
                    val adapter = QuestionAdapter(applicationContext, R.layout.questions, questionList)
                    listView.adapter = adapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    fun back_to_home(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
