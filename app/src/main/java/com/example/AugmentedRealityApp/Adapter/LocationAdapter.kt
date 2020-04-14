package com.example.AugmentedRealityApp.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.AugmentedRealityApp.DataClasses.*
import com.example.AugmentedRealityApp.R
import com.example.AugmentedRealityApp.UI.MapOverview
import com.example.AugmentedRealityApp.UI.Video
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_layout_info.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class LocationAdapter(val mCtx: Context, val layoutResId: Int, val locationList: List<Locations>) :
    ArrayAdapter<Locations>(mCtx, layoutResId, locationList) {

    lateinit var ctx: Context
    lateinit var dialog: BottomSheetDialog
    lateinit var commentList: MutableList<Comments>


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName_name)
        val textViewQuestions = view.findViewById<ImageView>(R.id.textViewQuestion)

        val locations = locationList[position]


        commentList = mutableListOf()

        textViewName.text = locations.name
        ctx = this.context!!

        textViewName.setOnClickListener {
            show_dialog(view, locations)
        }

        textViewQuestions.setOnClickListener {
            //showQuestions(locations)
        }


        return view
    }

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
        //val CardView = view.findViewById<CardView>(R.id.CardView)
        val startVideo = view.findViewById<ImageView>(R.id.playButton)


        textViewName1.setText(locations.name)
        textViewName2.setText(locations.year.toString())
        textViewName3.setText(locations.info)
        //textViewName4.setText(locations.comment)
        //TODO: ADD Image view for Preview of location

        val url = locations.image

        // Download directly from StorageReference using Glide
        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.burg_rotteln)
            .into(imageView)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {

            val userId = user.uid
        }

        val databaseComment = FirebaseDatabase.getInstance().getReference().child("user").child(user!!.uid).child(locations.id)

        databaseComment.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                commentList.clear()
                for (h in p0.children) {
                    val comment = h.getValue(Comments::class.java)
                    commentList.add(comment!!)
                }

                textViewName4.setText(commentList[0].comment)
            }
        })

            view.startVideo.setOnClickListener() {
                    val locationId = locations.id
                    val locationName = locations.name

                    val intent = Intent(context, Video::class.java)

                    //To pass the name and id of the chosen category to activity Game.kt
                    intent.putExtra("extra_location_id", locationId)
                    intent.putExtra("extra_location_name", locationName)

                    context.startActivity(intent)
                }

                view.changeComment.setOnClickListener() {
                    val builder = AlertDialog.Builder(mCtx)

                    val inflater = LayoutInflater.from(mCtx)

                    val view = inflater.inflate(R.layout.update_comment, null)

                    val EditText = view.findViewById<EditText>(R.id.contentComment)

                    EditText.setText(textViewName4.text.toString())

                    builder.setView(view)

                    builder.setPositiveButton("Ändern") { p0, p1 ->
                        val databaseComment = FirebaseDatabase.getInstance().getReference().child("user").child(user!!.uid).child(locations.id)

                        val comment = EditText.text.toString().trim()

                        val comObj = Comments(locations.id, comment)

                        if (comment == "") {
                            Toast.makeText(
                                this.context,
                                "Bitte geben Sie einen Kommentar ein.",
                                Toast.LENGTH_LONG
                            ).show()
                            return@setPositiveButton
                        } else {
                            databaseComment.child("comments").setValue(comObj)

                            //Toast.makeText( mCtx, "Wurde zu " + comment + " geändert.", Toast.LENGTH_LONG).show()

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


            /* private fun createQuestions(category: Locations) {

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
    }*/

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