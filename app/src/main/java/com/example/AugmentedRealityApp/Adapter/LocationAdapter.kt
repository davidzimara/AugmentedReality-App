package com.example.AugmentedRealityApp.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.AugmentedRealityApp.DataClasses.Locations
import com.example.AugmentedRealityApp.DataClasses.Questions
import com.example.AugmentedRealityApp.R
import com.example.AugmentedRealityApp.UI.MapOverview
import com.example.AugmentedRealityApp.UI.Video
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_layout_info.view.*


class LocationAdapter(val mCtx: Context, val layoutResId: Int, val locationList: List<Locations>) :
    ArrayAdapter<Locations>(mCtx, layoutResId, locationList) {

    lateinit var ctx: Context
    lateinit var dialog: BottomSheetDialog

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName_name)
        /*val imageViewUpdate = view.findViewById<ImageView>(R.id.textViewUpdate)
        val imageViewDelete = view.findViewById<ImageView>(R.id.textViewDelete)*/
        val textViewQuestions = view.findViewById<ImageView>(R.id.textViewQuestion)

        val locations = locationList[position]

        textViewName.text = locations.name
        ctx = this.context!!

        /*imageViewUpdate.setOnClickListener {
            showUpdateDialog(category)
        }

        imageViewDelete.setOnClickListener {
            deleteCategory(category)
        }*/

        textViewName.setOnClickListener {
            //createQuestions(category)
            show_dialog(view, locations)
        }

        textViewQuestions.setOnClickListener {
            showQuestions(locations)
        }

        return view
    }
    /*
    private fun showUpdateDialog(category: Locations) {
        val builder = AlertDialog.Builder(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update_categories, null)

        val editText = view.findViewById<EditText>(R.id.nameCategory)

        editText.setText(category.name)

        builder.setView(view)

        builder.setPositiveButton("Ändern") { p0, p1 ->
            val dbCategories = FirebaseDatabase.getInstance().getReference("Categorys")

            val kategorieName = editText.text.toString().trim()

            if (kategorieName.isEmpty()) {
                editText.error = "Bitte gebe einen Namen an."
                editText.requestFocus()
                return@setPositiveButton
            }

            val category = Locations(category.id, kategorieName)

            val categoryValues = category.toMap()

            val childUpdates = HashMap<String, Any>()

            childUpdates["/${category.id}"] = categoryValues

            dbCategories.updateChildren(childUpdates)

            Toast.makeText(mCtx, "Wurde zu " + kategorieName + " geändert.", Toast.LENGTH_LONG).show()

        }

        builder.setNegativeButton("Zurück") { p0, p1 ->

        }

        val alert = builder.create()
        alert.show()

    }


    private fun deleteCategory(category: Locations) {

        val builder = AlertDialog.Builder(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.delete_layout, null)

        val kategorieName = category.name.toString().trim()

        builder.setView(view)

        val deleteText = view.findViewById<TextView>(R.id.deleteCategory)
        val title = view.findViewById<TextView>(R.id.title)

        title.text = "Frage löschen"

        deleteText.text = "Möchten Sie die Kategorie " + kategorieName + " wirklick Löschen?"

        builder.setPositiveButton("Löschen") { p0, p1 ->

            val dbCategories =
                FirebaseDatabase.getInstance().getReference("Categorys").child(category.id)

            dbCategories.removeValue()

            Toast.makeText(mCtx, kategorieName + " wurde gelöscht.", Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton("Zurück") { p0, p1 ->

            Toast.makeText(mCtx, "Löschen wurde abgebrochen", Toast.LENGTH_LONG).show()
        }

        val alert = builder.create()
        alert.show()

    }
    */

    fun show_dialog(view: View, locations: Locations) {

        dialog = BottomSheetDialog(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.dialog_layout_info, null)

        dialog.setContentView(view)
        dialog.show()

        val textViewName1 = view.findViewById<TextView>(R.id.textViewName1)
        val textViewName2 = view.findViewById<TextView>(R.id.textViewYear)
        val textViewName3 = view.findViewById<TextView>(R.id.textViewInfo)
        val textViewName4 = view.findViewById<TextView>(R.id.textViewComment)
        val imageView = view.findViewById<ImageView>(R.id.img_location)
        val CardView = view.findViewById<CardView>(R.id.CardView)

        textViewName1.setText(locations.name)
        textViewName2.setText(locations.year.toString())
        textViewName3.setText(locations.info)
        textViewName4.setText(locations.comment)
        //TODO: ADD Image view for Preview of location

        val url = locations.image


        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.burg_rotteln)
            .into(imageView)

        //TODO: 1. Video Start Button Find view by ID
        //TODO: 2. Click listener to intend new Activity Video.kt
        //TODO: 3. Hand over Extra String with the current ID
        //TODO: 4. In Video.kt make if statements where you request the ID
        //TODO: 5. Start Video Player: https://www.youtube.com/watch?v=IEXSGmX8YJo

        view.CardView.setOnClickListener(){
            val locationId = locations.id
            val locationName = locations.name

            val intent = Intent(context, Video::class.java)

            //To pass the name and id of the chosen category to activity Game.kt
            intent.putExtra("extra_location_id", locationId)
            intent.putExtra("extra_location_name", locationName)

            context.startActivity(intent)
        }

        view.changeComment.setOnClickListener(){
            val builder = AlertDialog.Builder(mCtx)

            val inflater = LayoutInflater.from(mCtx)

            val view = inflater.inflate(R.layout.update_comment, null)

            val EditText = view.findViewById<EditText>(R.id.contentComment)

            EditText.setText(locations.comment)

            builder.setView(view)

            builder.setPositiveButton("Ändern") {p0, p1 ->
                val dbLocations = FirebaseDatabase.getInstance().getReference("location").child(locations.id)

                val comment = EditText.text.toString().trim()

                if (comment == "") {
                    Toast.makeText(this.context, "Bitte geben Sie einen Kommentar ein.", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                } else {
                    dbLocations.child("comment").setValue(comment)

                    Toast.makeText(mCtx, "Wurde zu " + comment + " geändert.", Toast.LENGTH_LONG).show()

                    //To Update the comment within the bottom sheet
                    textViewName4.setText(comment)
                }
            }

            builder.setNegativeButton("Zurück") { p0, p1 ->

            }

            val alert = builder.create()
            alert.show()

        }
    }


    private fun createQuestions(category: Locations) {

        val builder = AlertDialog.Builder(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update_question_layout, null)

        builder.setView(view)

        builder.setPositiveButton("Speichern") { p0, p1 ->

            val dbCategories =
                FirebaseDatabase.getInstance().getReference("Categorys").child(category.id)

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

            //To avoid that there is a question that doesn`t contain four different Answers
            if (ques == "" || answ1 == "" || answ2 == "" || answ3 == "" || answ4 == "") {
                Toast.makeText(this.context, "Bitte füllen Sie alle Felder aus.", Toast.LENGTH_LONG)
                    .show()
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

    private fun showQuestions(category: Locations) {

        val intent = Intent(context, MapOverview::class.java)

        val kategorieId = category.id
        val kategorieName = category.name

        //To pass the name and id to the activity Question Overview.kt
        intent.putExtra("extra_category_id", kategorieId)
        intent.putExtra("extra_category_name", kategorieName)

        context.startActivity(intent)
    }
}