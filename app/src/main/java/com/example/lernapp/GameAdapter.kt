package com.example.lernapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class GameAdapter(internal var context: Context): RecyclerView.Adapter<GameAdapter.MyViewHolder>()
{
    internal var questionList: MutableList<CurrentQuestion>


    val lastItemId:String?
        get() = questionList[questionList.size - 1].id

    fun addAll(newQuestion:List<CurrentQuestion>)
    {
        val init = questionList.size
        questionList.addAll(newQuestion)
        notifyItemRangeChanged(init, newQuestion.size)
    }

    fun removeLastItem()
    {
        questionList.removeAt(questionList.size - 1)
    }


    init {
        this.questionList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.current_question, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_question.text = questionList[position].question
        holder.txt_answer1.text = questionList[position].answer1
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        internal var txt_question: TextView
        internal var txt_answer1: TextView

        init {
            txt_question = itemView.findViewById<TextView>(R.id.question)
            txt_answer1 = itemView.findViewById<TextView>(R.id.answer1)
        }
    }
}