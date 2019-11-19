package com.example.lernapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.icu.util.ULocale
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.inputmethod.EditorInfoCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class CategoryAdapter(val mCtx: Context, val layoutResId: Int, val categoryList: List<Categories>)
    : ArrayAdapter<Categories>(mCtx, layoutResId, categoryList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val imageViewUpdate = view.findViewById<ImageView>(R.id.textViewUpdate)
        val imageViewDelete = view.findViewById<ImageView>(R.id.textViewDelete)

        val category = categoryList[position]

        textViewName.text = category.name

        imageViewUpdate.setOnClickListener {
            showUpdateDialog(category)
        }

        imageViewDelete.setOnClickListener {
            deleteCategory(category)
        }

        textViewName.setOnClickListener {
            showQuestions()
        }

        return view
    }

    private fun showUpdateDialog(category: Categories) {
        val builder = AlertDialog.Builder(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update_categories, null)

        val editText = view.findViewById<EditText>(R.id.nameCategory)

        editText.setText(category.name)

        builder.setView(view)

        builder.setPositiveButton("Ändern") {p0, p1 ->
            val dbCategories = FirebaseDatabase.getInstance().getReference("Categorys")

            val kategorieName = editText.text.toString().trim()

            if(kategorieName.isEmpty()) {
                editText.error = "Bitte gebe einen Namen an."
                editText.requestFocus()
                return@setPositiveButton
            }

            val category = Categories(category.id, kategorieName)

            dbCategories.child(category.id).setValue(category)

            Toast.makeText(mCtx, "Wurde zu " + kategorieName + " geändert.", Toast.LENGTH_LONG).show()

        }

        builder.setNegativeButton("Zurück") { p0, p1 ->

        }

        val alert = builder.create()
        alert.show()

    }


    private fun deleteCategory(category: Categories) {

        val dbCategories = FirebaseDatabase.getInstance().getReference("Categorys").child(category.id)

        val kategorieName = category.name.toString().trim()

        dbCategories.removeValue()

        Toast.makeText(mCtx, kategorieName + " wurde gelöscht.", Toast.LENGTH_LONG).show()

    }

    private fun showQuestions() {
        //TODO: open acitivity questions
    }
}