package com.example.lernapp

object Common {

    val total_Time = 30*60*1000 // 30Min.

    var answerSheetList:MutableList<Questions> = ArrayList()
    var questionList:MutableList<Questions> = ArrayList()
    var selectedCategory: Categories?=null
    var fragmenList:MutableList<QuestionFragment> = ArrayList()


    enum class ANSWER_TYPE {
        NO_ANSWER,
        RIGHT_ANSWER,
        WRONG_ANSWER,
    }
}