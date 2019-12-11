package com.example.lernapp


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class Game : AppCompatActivity() {

    lateinit var questionList: MutableList<Questions>
    lateinit var answerList: ArrayList<Answers>
    var index: Int = 0
    var correctAnswer: Int = 0
    var answer1: String = ""


    //TODO: https://www.youtube.com/watch?v=KRDJXKt4bTk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //handed over from ChooseCategoryAdapter
        val categoryNameFromAdapter = intent.getStringExtra("extra_category_name")
        val amountFromAdapter = intent.getStringExtra("extra_list_size")

        //Toast.makeText(applicationContext, amountFromAdapter, Toast.LENGTH_LONG).show()
        val kategorieName = findViewById<TextView>(R.id.categoryName)

        kategorieName.setText(categoryNameFromAdapter)

        val buttonNext = findViewById<Button>(R.id.nextQuestion)
        val buttonEnd = findViewById<Button>(R.id.endGame)
        val buttonBack = findViewById<Button>(R.id.lastQuestion)
        val answer1text = findViewById<TextView>(R.id.answer1)
        val answer2text = findViewById<TextView>(R.id.answer2)
        val answer3text = findViewById<TextView>(R.id.answer3)
        val answer4text = findViewById<TextView>(R.id.answer4)
        val yourAnswer = findViewById<TextView>(R.id.yourAnswer)

        back_to_main.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

        answer1text.setOnClickListener() {
            val answer: String = answer1text.text.toString()

            if (answer == questionList[index].answer1){
                correctAnswer += 1
            }

            setAnswerColor()
            showAnswer(answer)
        }

        answer2text.setOnClickListener() {
            setAnswerColor()
            val answer: String = answer2text.text.toString()

            if (answer == questionList[index].answer1){
                correctAnswer += 1
            }

            showAnswer(answer)
        }

        answer3text.setOnClickListener() {
            val answer: String = answer3text.text.toString()

            if (answer == questionList[index].answer1){
                correctAnswer += 1
            }

            setAnswerColor()

            showAnswer(answer)
        }

        answer4text.setOnClickListener() {
            val answer: String = answer4text.text.toString()

            if (answer == questionList[index].answer1){
                correctAnswer += 1
            }

            setAnswerColor()

            showAnswer(answer)
        }

        buttonNext.setOnClickListener(){
            index += 1
            addQuestion()
            showQuestionIndex()

            buttonNext.visibility = View.INVISIBLE
            yourAnswer.visibility = View.INVISIBLE
        }

        buttonBack.setOnClickListener(){
            index -= 1
            addQuestion()
            showQuestionIndex()

            yourAnswer.visibility = View.INVISIBLE
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

        setBackgroundColor()
    }

    private fun showAnswer(answer: String) {
        val yourAnswer = findViewById<TextView>(R.id.yourAnswer)

        yourAnswer.visibility = View.VISIBLE

        yourAnswer.text = "Ihre Antwort: " + answer

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

        answer1 = answer1text.text.toString()
        val answer2 = answer2text.text.toString()
        val answer3 = answer3text.text.toString()
        val answer4 = answer4text.text.toString()

        if (answer1 == questionList[index].answer1) {
            answer1text.setBackgroundColor(Color.parseColor("#4caf50"))
            answer2text.setBackgroundColor(Color.parseColor("#cb2426"))
            answer3text.setBackgroundColor(Color.parseColor("#cb2426"))
            answer4text.setBackgroundColor(Color.parseColor("#cb2426"))
        } else if (answer2 == questionList[index].answer1) {
            answer1text.setBackgroundColor(Color.parseColor("#cb2426"))
            answer2text.setBackgroundColor(Color.parseColor("#4caf50"))
            answer3text.setBackgroundColor(Color.parseColor("#cb2426"))
            answer4text.setBackgroundColor(Color.parseColor("#cb2426"))
        } else if (answer3 == questionList[index].answer1) {
            answer1text.setBackgroundColor(Color.parseColor("#cb2426"))
            answer2text.setBackgroundColor(Color.parseColor("#cb2426"))
            answer3text.setBackgroundColor(Color.parseColor("#4caf50"))
            answer4text.setBackgroundColor(Color.parseColor("#cb2426"))
        } else if (answer4 == questionList[index].answer1) {
            answer1text.setBackgroundColor(Color.parseColor("#cb2426"))
            answer2text.setBackgroundColor(Color.parseColor("#cb2426"))
            answer3text.setBackgroundColor(Color.parseColor("#cb2426"))
            answer4text.setBackgroundColor(Color.parseColor("#4caf50"))
        }

        val buttonNext = findViewById<Button>(R.id.nextQuestion)

        var listSize = questionList.size
        var listSizeMax = listSize - 1

        if(listSizeMax == index) {
            buttonNext.visibility = View.INVISIBLE
        } else {
            buttonNext.visibility = View.VISIBLE
        }
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

                    val question = questionList[index]

                    val answer1 = Answers(question.answer1, true)
                    val answer2 = Answers(question.answer2, false)
                    val answer3 = Answers(question.answer3, false)
                    val answer4 = Answers(question.answer4, false)

                    answerList = arrayListOf(answer1, answer2, answer3, answer4)

                    answerList.shuffle()

                    questiontext.setText(questionList[index].question)
                    answer1text.setText(answerList[0].answer)
                    answer2text.setText(answerList[1].answer)
                    answer3text.setText(answerList[2].answer)
                    answer4text.setText(answerList[3].answer)

                    buttonEnd.visibility = View.INVISIBLE

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

        //to hide the answer textviews when inside the questionList are no elements otherwise it`ll crash
        if (questionList.size != 0) {
            answer1text.visibility = View.VISIBLE
            answer2text.visibility = View.VISIBLE
            answer3text.visibility = View.VISIBLE
            answer4text.visibility = View.VISIBLE
        }

        answer1text.setBackgroundColor(Color.parseColor("#ff8a50"))
        answer2text.setBackgroundColor(Color.parseColor("#ff8a50"))
        answer3text.setBackgroundColor(Color.parseColor("#ff8a50"))
        answer4text.setBackgroundColor(Color.parseColor("#ff8a50"))
        }
    }
