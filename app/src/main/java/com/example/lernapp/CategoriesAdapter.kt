package com.example.lernapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CategoriesAdapter(val mCtx: Context,val layoutResId: Int,val categoriesList: List<Categories>)
 : ArrayAdapter<Categories>(mCtx, layoutResId, categoriesList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName)

        val categories = categoriesList[position]

        textViewName.text = categories.name

        return view
    }
}
