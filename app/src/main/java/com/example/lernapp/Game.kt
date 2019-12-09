package com.example.lernapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Game : AppCompatActivity() {

    lateinit var questionList: MutableList<Questions>
    lateinit var listView: ListView
    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val category = intent.getStringExtra("extra_category_id")

        Toast.makeText(baseContext, category, Toast.LENGTH_LONG).show()

        val database = FirebaseDatabase.getInstance().getReference("Categorys").child(category)
            .child("questions")

        val questionView = findViewById<TextView>(R.id.question)

        questionList = mutableListOf()
        listView = findViewById(R.id.listViewGame)

        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {

                    questionList.clear()
                    for(h in p0.children){
                        val question = h.getValue(Questions::class.java)
                        questionList.add(question!!)
                    }

                    val adapter = GameAdapter(this@Game, R.layout.current_question, questionList)
                    listView.adapter = adapter
            }
        })

    }
}
