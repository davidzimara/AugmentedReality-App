package com.example.lernapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lernapp.Common.questionList

class GridAnswerAdapter(internal var context: Context, internal var answerSheetList: MutableList<Questions>)
    : RecyclerView.Adapter<GridAnswerAdapter.MyViewHolder>() {


    //TODO: 25:59

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_grid_answer_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return answerSheetList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val question = questionList[position]

        if(answerSheetList[position].toString() == question.answer1)
            holder.question_item.setBackgroundResource(R.drawable.grid_item_right_answer)
        else if(answerSheetList[position].toString() != question.answer1)
            holder.question_item.setBackgroundResource(R.drawable.grid_item_wrong_answer)
        else
            holder.question_item.setBackgroundResource(R.drawable.grid_item_no_answer)
    }

    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        internal var question_item: View
        init {
            question_item = itemView.findViewById(R.id.question_item) as View
        }
    }
}