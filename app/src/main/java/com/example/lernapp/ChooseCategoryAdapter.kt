package com.example.lernapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ChooseCategoryAdapter (val mCtx: Context, val layoutResId: Int, val categoryList: List<Categories>)
    : ArrayAdapter<Categories>(mCtx, layoutResId, categoryList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val categoryName = view.findViewById<TextView>(R.id.chooseCategoryName)

        val category = categoryList[position]

        categoryName.text = category.name

        categoryName.setOnClickListener(){

            val kategorieId = category.id

            //Toast.makeText(mCtx, kategorie, Toast.LENGTH_LONG).show()

            val intent = Intent(context, Game::class.java)

            //To pass any data to activity Game.kt
            intent.putExtra("extra_category_id", kategorieId)

            context.startActivity(intent)
        }

        return view
    }
}