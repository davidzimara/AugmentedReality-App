package com.example.AugmentedRealityApp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.AugmentedRealityApp.DataClasses.Categories
import com.example.AugmentedRealityApp.UI.Game
import com.example.AugmentedRealityApp.R

class ChooseCategoryAdapter(val mCtx: Context, val layoutResId: Int, val categoryList: List<Categories>)
    : ArrayAdapter<Categories>(mCtx, layoutResId, categoryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val categoryName = view.findViewById<TextView>(R.id.chooseCategoryName)

        val category = categoryList[position]

        categoryName.text = category.name

        categoryName.setOnClickListener() {

            val kategorieId = category.id
            val kategorieName = category.name

            val intent = Intent(context, Game::class.java)

            //To pass the name and id of the chosen category to activity Game.kt
            intent.putExtra("extra_category_id", kategorieId)
            intent.putExtra("extra_category_name", kategorieName)

            context.startActivity(intent)
        }

        return view
    }
}