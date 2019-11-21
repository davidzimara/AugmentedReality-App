package com.example.lernapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class QuestionAdapter (val mCtx: Context, val layoutResId: Int, val questionList: List<Questions> )
    : ArrayAdapter<Questions>(mCtx, layoutResId, questionList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewQuestion1)
        val textViewAnswer1 = view.findViewById<TextView>(R.id.textViewName1)
        val textViewAnswer2 = view.findViewById<TextView>(R.id.textViewName2)
        val textViewAnswer3 = view.findViewById<TextView>(R.id.textViewName3)
        val textViewAnswer4 = view.findViewById<TextView>(R.id.textViewName4)

        val question = questionList[position]

        textViewName.text = question.question
        textViewAnswer1.text = question.answer1
        textViewAnswer2.text = question.answer2
        textViewAnswer3.text = question.answer3
        textViewAnswer4.text = question.answer4

        return view
    }
}