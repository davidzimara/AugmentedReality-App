package com.example.lernapp


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.lernapp.R.color
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.collection.LLRBNode
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {

    lateinit var questionList: MutableList<Questions>
    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //handed over from ChooseCategoryAdapter
        val categoryNameFromAdapter = intent.getStringExtra("extra_category_name")
        val kategorieName = findViewById<TextView>(R.id.categoryName)

        kategorieName.setText(categoryNameFromAdapter)

        val buttonNext = findViewById<Button>(R.id.nextQuestion)
        val buttonEnd = findViewById<Button>(R.id.endGame)
        val questiontext = findViewById<TextView>(R.id.question)
        val answer1text = findViewById<TextView>(R.id.answer1)
        val answer2text = findViewById<TextView>(R.id.answer2)
        val answer3text = findViewById<TextView>(R.id.answer3)
        val answer4text = findViewById<TextView>(R.id.answer4)

        answer1text.setOnClickListener() {
            answer1text.setBackgroundColor(Color.GREEN)
            answer2text.setBackgroundColor(Color.RED)
            answer3text.setBackgroundColor(Color.RED)
            answer4text.setBackgroundColor(Color.RED)
        }

        buttonNext.setOnClickListener(){
            index = index + 1
            addQuestion()
        }

        buttonEnd.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

        questionList = mutableListOf()

        addQuestion()

    }

    private fun addQuestion() {

        //handed over from the ChooseCategoryAdapter
        val category = intent.getStringExtra("extra_category_id")

        val database = FirebaseDatabase.getInstance().getReference("Categorys").child(category)
            .child("questions")

        val buttonNext = findViewById<Button>(R.id.nextQuestion)
        val buttonEnd = findViewById<Button>(R.id.endGame)
        val questiontext = findViewById<TextView>(R.id.question)
        val answer1text = findViewById<TextView>(R.id.answer1)
        val answer2text = findViewById<TextView>(R.id.answer2)
        val answer3text = findViewById<TextView>(R.id.answer3)
        val answer4text = findViewById<TextView>(R.id.answer4)

        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {

                questionList.clear()
                for(h in p0.children){
                    val question = h.getValue(Questions::class.java)
                    questionList.add(question!!)
                }

                var listSize = questionList.size
                var listSizeMax = listSize - 1


                if (listSizeMax >= index) {
                    setBackgroundColor()

                    questiontext.setText(questionList[index].question)
                    answer1text.setText(questionList[index].answer1)
                    answer2text.setText(questionList[index].answer2)
                    answer3text.setText(questionList[index].answer3)
                    answer4text.setText(questionList[index].answer4)

                    buttonEnd.visibility = View.INVISIBLE
                    buttonNext.visibility = View.VISIBLE
                }
                else {
                    buttonNext.visibility = View.INVISIBLE
                    buttonEnd.visibility = View.VISIBLE
                    //TODO: HIDE NEXT BUTTON AND SHOW END BUTTON
                    Toast.makeText(applicationContext, "Es gibt keine weiteren Fragen.", Toast.LENGTH_LONG).show()
                }

            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun setBackgroundColor() {

        val answer1text = findViewById<TextView>(R.id.answer1)
        val answer2text = findViewById<TextView>(R.id.answer2)
        val answer3text = findViewById<TextView>(R.id.answer3)
        val answer4text = findViewById<TextView>(R.id.answer4)

        answer1text.setBackgroundColor(Color.parseColor("#ff8a50"))
        answer2text.setBackgroundColor(Color.parseColor("#ff8a50"))
        answer3text.setBackgroundColor(Color.parseColor("#ff8a50"))
        answer4text.setBackgroundColor(Color.parseColor("#ff8a50"))

    }
}
