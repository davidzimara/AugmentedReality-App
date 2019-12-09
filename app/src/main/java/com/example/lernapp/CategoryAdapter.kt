package com.example.lernapp

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.ULocale
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.IntentCompat
import androidx.core.view.inputmethod.EditorInfoCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap

class CategoryAdapter(val mCtx: Context, val layoutResId: Int, val categoryList: List<Categories>)
    : ArrayAdapter<Categories>(mCtx, layoutResId, categoryList){

    lateinit var ctx: Context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val imageViewUpdate = view.findViewById<ImageView>(R.id.textViewUpdate)
        val imageViewDelete = view.findViewById<ImageView>(R.id.textViewDelete)
        val textViewQuestions = view.findViewById<ImageView>(R.id.textViewQuestion)

        val category = categoryList[position]

        textViewName.text = category.name
        ctx = this.context!!

        imageViewUpdate.setOnClickListener {
            showUpdateDialog(category)
        }

        imageViewDelete.setOnClickListener {
            deleteCategory(category)
        }

        textViewName.setOnClickListener {
            createQuestions(category)
        }

        textViewQuestions.setOnClickListener {
            showQuestions(category)
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
            val key = dbCategories.child("Categorys").push().key
            val kategorieName = editText.text.toString().trim()

            if(kategorieName.isEmpty()) {
                editText.error = "Bitte gebe einen Namen an."
                editText.requestFocus()
                return@setPositiveButton
            }

            val category = Categories(category.id, kategorieName)
            val categoryValues = category.toMap()

            val childUpdates = HashMap<String, Any>()

            childUpdates["/$key"] = categoryValues

            dbCategories.updateChildren(childUpdates)

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

    private fun createQuestions(category: Categories) {

        val builder = AlertDialog.Builder(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.activity_create_question, null)

        builder.setView(view)

        // startActivity(Intent(, CreateQuestion::class.java))

        builder.setPositiveButton("Speichern") {p0, p1 ->

            val dbCategories = FirebaseDatabase.getInstance().getReference("Categorys").child(category.id)

            val editTextQuestion = view.findViewById<EditText>(R.id.nameQuestion)
            val editTextAnswer1 = view.findViewById<EditText>(R.id.answer1)
            val editTextAnswer2 = view.findViewById<EditText>(R.id.answer2)
            val editTextAnswer3 = view.findViewById<EditText>(R.id.answer3)
            val editTextAnswer4 = view.findViewById<EditText>(R.id.answer4)

            val id = dbCategories.push().key.toString()
            val ques = editTextQuestion.text.toString().trim()
            val answ1 = editTextAnswer1.text.toString().trim()
            val answ2 = editTextAnswer2.text.toString().trim()
            val answ3 = editTextAnswer3.text.toString().trim()
            val answ4 = editTextAnswer4.text.toString().trim()
            val categoryId = category.id

            val question = Questions(id, ques, answ1, answ2, answ3, answ4, categoryId)

            if (ques == "" || answ1 == "" || answ2 =="" || answ3 == "" || answ4 == "") {
                Toast.makeText(this.context, "Bitte füllen Sie alle Felder aus.", Toast.LENGTH_LONG).show()
                return@setPositiveButton
            } else {

                dbCategories.child("questions").child(id).setValue(question)
                    .addOnCompleteListener {
                        Toast.makeText(
                            mCtx,
                            " Ihre Frage wurde in der Kategorie " + category.name + " abgelegt.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .addOnCanceledListener {
                        Toast.makeText(
                            mCtx,
                            "Ihre Frage konnte nicht in die Datenbank abgelegt werden.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }

        builder.setNegativeButton("Abbrechen") { p0, p1 ->
            Toast.makeText(mCtx, "Ihre Frage wurde verworfen.", Toast.LENGTH_LONG).show()

        }

        val alert = builder.create()
        alert.show()
    }

    private fun showQuestions (category: Categories) {

        val intent = Intent(context, QuestionOverview::class.java)

        val kategorieId = category.id
        val kategorieName = category.name

        //Toast.makeText(mCtx, kategorie, Toast.LENGTH_LONG).show()

        //To pass any data to the activity Question Overview.kt
        intent.putExtra("extra_category_id", kategorieId)
        intent.putExtra("extra_category_name", kategorieName)

        context.startActivity(intent)
    }
}