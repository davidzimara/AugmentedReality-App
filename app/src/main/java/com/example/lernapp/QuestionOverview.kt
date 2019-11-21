package com.example.lernapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.update_categories.*

class QuestionOverview : AppCompatActivity() {

    lateinit var kategorieId : TextView
    lateinit var kategorieName: TextView
    private lateinit var database: DatabaseReference
    lateinit var questionList: MutableList<Questions>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_overview)

        //TODO: Tutorial anschauen
        //TODO: Category id importieren
        //TODO: Liste Anzeigen mit Fragen
        //TODO: 1. Db Referenz
        //TODO: 2. mutableListOf<Questions>
        //TODO: 2.1 Postionsliste
        //TODO: 3. Die ListView id angeben in der xml und hier einbinden mit find
        //TODO: 4. in der XML questions.xml eine textViewName (5mal) erstellen  und hier einbinden mit find (5mal) - vielleicht frage bissel anders formatieren
        //TODO: 5. val question = questionList[position]
        //TODO: 6. (id nicht - da diese nicht angezeigt werden soll) textViewName_question= question.question, textViewName_answer1 = answer1.question ... => id: String, val question: String, val answer1: String, val answer2: String, val answer3: String, val answer4:
        //TODO: 7. Adapter erstellen mit Liste
        //TODO: der path muss folgt lauten getReference("Categorys").child(category.id).child(questions)

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
