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
            val kategorieName = category.name

            val intent = Intent(context, Game::class.java)

            //To pass the name and if of the choosed category to activity Game.kt
            intent.putExtra("extra_category_id", kategorieId)
            intent.putExtra("extra_category_name", kategorieName)

            context.startActivity(intent)
        }

        return view
    }
}