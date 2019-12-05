package com.example.lernapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lernapp.Common.questionList
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_game.*
import java.util.concurrent.TimeUnit

class Game : AppCompatActivity() {

    lateinit var database: DatabaseReference

    lateinit var countDownTimer: CountDownTimer

    var time_play = Common.total_Time

    lateinit var adapter: GridAnswerAdapter

    //TODO: 32:27

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val category = intent.getStringExtra("extra_category_id")

        Toast.makeText(baseContext, "Id der Kat: " + category, Toast.LENGTH_LONG).show()

        database = FirebaseDatabase.getInstance().getReference("Categorys").child(category).child("questions")

        //Get Question based on Category
        genQuestion()

        if(Common.questionList.size > 0) {
            txt_timer.visibility = View.VISIBLE
            txt_right_answer.visibility = View.VISIBLE

            countTimer()

            //Gen item for grid_answer
            genItems()
            grid_answer.setHasFixedSize(true)
            if(Common.questionList.size > 0)
                grid_answer.layoutManager = GridLayoutManager(this, if(Common.questionList.size > 10) Common.questionList.size/2 else Common.questionList.size)

            adapter = GridAnswerAdapter(this, Common.answerSheetList)
            grid_answer.adapter = adapter

            //Gen fragment List
            genFragmentList()

            val fragmentAdapter = MyFragmentAdapter(supportFragmentManager, this, Common.fragmenList)
            view_pager.offscreenPageLimit = Common.questionList.size
            view_pager.adapter = fragmentAdapter // Bind question to View Pager

            sliding_tabs.setupWithViewPager(view_pager)

        }

    }

    private fun genFragmentList() {
        for (i in Common.questionList.indices)
        {
            val bundle = Bundle()
            bundle.putInt("index", 1)
            val fragment = QuestionFragment()
            fragment.arguments = bundle

            Common.fragmenList.add(fragment)
        }
    }

    private fun genItems() {
        val question = questionList[position]

        for(i in Common.questionList.indices)
            Common.answerSheetList.add(Questions(question.id, question.question, answer1, answer2, answer3, answer4, question.categoryId)
    }

    private fun countTimer() {
        countDownTimer = object:CountDownTimer(Common.total_Time.toLong(), 1000) {
            override fun onFinish() {
                finishGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                txt_timer.text = (java.lang.String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))))

                time_play-=1000
            }

        }
    }

    private fun finishGame() {
        //TODO: code late (17:06)
    }

    private fun genQuestion() {

        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    Common.questionList.clear()
                    for(h in p0.children) {
                        val question = h.getValue(Questions::class.java)
                        Common.questionList.add(question!!)
                    }

                }
            }

        })

    }


}
