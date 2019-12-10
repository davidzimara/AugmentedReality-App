package com.example.lernapp


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.lernapp.R.color
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.collection.LLRBNode
import kotlinx.android.synthetic.main.activity_game.*
import java.lang.StrictMath.random
import kotlin.math.nextTowards
import kotlin.math.nextUp
import kotlin.random.Random

class Game : AppCompatActivity() {

    lateinit var questionList: MutableList<Questions>
    var index: Int = 0
    var correctAnswer: Int = 0

    //TODO: https://www.youtube.com/watch?v=KRDJXKt4bTk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //handed over from ChooseCategoryAdapter
        val categoryNameFromAdapter = intent.getStringExtra("extra_category_name")
        val kategorieName = findViewById<TextView>(R.id.categoryName)

        kategorieName.setText(categoryNameFromAdapter)

        val buttonNext = findViewById<Button>(R.id.nextQuestion)
        val buttonEnd = findViewById<Button>(R.id.endGame)
        val buttonBack = findViewById<Button>(R.id.lastQuestion)
        val answer1text = findViewById<TextView>(R.id.answer1)
        val answer2text = findViewById<TextView>(R.id.answer2)
        val answer3text = findViewById<TextView>(R.id.answer3)
        val answer4text = findViewById<TextView>(R.id.answer4)

        back_to_main.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

        answer1text.setOnClickListener() {
            setAnswerColor()
            correctAnswer += 1
        }

        answer2text.setOnClickListener() {
            setAnswerColor()
        }

        answer3text.setOnClickListener() {
            setAnswerColor()
        }

        answer4text.setOnClickListener() {
            setAnswerColor()
        }

        buttonNext.setOnClickListener(){
            index += 1
            addQuestion()
            showQuestionIndex()
        }

        buttonBack.setOnClickListener(){
            index -= 1
            addQuestion()
            showQuestionIndex()
        }

        buttonEnd.setOnClickListener(){

            val intent = Intent(this, MainActivity::class.java)

            val correctAnswer = correctAnswer.toString()
            val listSize = questionList.size.toString()
            val correctAnswerOfTotal = correctAnswer.plus("/").plus(listSize)

            intent.putExtra("extra_correct_answer", correctAnswerOfTotal)
            this.startActivity(intent)
        }

        questionList = mutableListOf()

        addQuestion()
    }

    private fun showQuestionIndex() {
        val questionIndex = findViewById<TextView>(R.id.questionIndex)

        if (questionList.size > index) {
            questionIndex.visibility = View.VISIBLE
        }

        val listSize = questionList.size.toString()
        val currentIndex = (index + 1).toString()
        val completeStatus = currentIndex.plus("/").plus(listSize)

        Toast.makeText(applicationContext, currentIndex + "index" + index, Toast.LENGTH_LONG)

        questionIndex.setText(completeStatus)

    }

    private fun setAnswerColor() {
        val answer1text = findViewById<TextView>(R.id.answer1)
        val answer2text = findViewById<TextView>(R.id.answer2)
        val answer3text = findViewById<TextView>(R.id.answer3)
        val answer4text = findViewById<TextView>(R.id.answer4)
        answer1text.setBackgroundColor(Color.parseColor("#4caf50"))
        answer2text.setBackgroundColor(Color.parseColor("#cb2426"))
        answer3text.setBackgroundColor(Color.parseColor("#cb2426"))
        answer4text.setBackgroundColor(Color.parseColor("#cb2426"))
    }

    private fun addQuestion() {


        //handed over from the ChooseCategoryAdapter
        val category = intent.getStringExtra("extra_category_id")

        val database = FirebaseDatabase.getInstance().getReference("Categorys").child(category)
            .child("questions")

        val buttonNext = findViewById<Button>(R.id.nextQuestion)
        val buttonEnd = findViewById<Button>(R.id.endGame)
        val buttonBack = findViewById<Button>(R.id.lastQuestion)
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

                //have to be at this point otherwise the number of items in the list will be 0
                showQuestionIndex()

                var listSize = questionList.size
                var listSizeMax = listSize - 1

                if (listSizeMax >= index) {
                    setBackgroundColor()

                    /*val question = questionList[index]
                    val answerList = arrayListOf(question.answer1, question.answer2, question.answer3, question.answer4)

                    val random = java.util.Random()

                    val randomAnswers = random.nextInt(answerList.count())
                    val incrementRandomAnswer = randomAnswers+1

                    val answer = "answer".plus(incrementRandomAnswer)

                    Toast.makeText(applicationContext, answer, Toast.LENGTH_LONG).show()*/

                    questiontext.setText(questionList[index].question)
                    answer1text.setText(questionList[index].answer1)
                    answer2text.setText(questionList[index].answer2)
                    answer3text.setText(questionList[index].answer3)
                    answer4text.setText(questionList[index].answer4)

                    buttonEnd.visibility = View.INVISIBLE
                    buttonNext.visibility = View.VISIBLE
                    buttonBack.visibility = View.INVISIBLE

                    if (index > 0) {
                        buttonBack.visibility = View.VISIBLE
                    }

                    if (index == listSizeMax) {
                        buttonNext.visibility = View.INVISIBLE
                        buttonEnd.visibility = View.VISIBLE
                    }
                }
                else {
                    buttonNext.visibility = View.INVISIBLE
                    buttonEnd.visibility = View.VISIBLE
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
