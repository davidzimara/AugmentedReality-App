package com.example.AugmentedRealityApp.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.AugmentedRealityApp.DataClasses.*
import com.example.AugmentedRealityApp.R
import com.example.AugmentedRealityApp.UI.MapOverview
import com.example.AugmentedRealityApp.UI.Video
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_layout_info.view.*
import kotlinx.android.synthetic.main.dialog_layout_info.view.iv_close
import org.w3c.dom.Text


class LocationAdapter(val mCtx: Context, val layoutResId: Int, val locationList: List<Locations>) :
    ArrayAdapter<Locations>(mCtx, layoutResId, locationList) {

    lateinit var ctx: Context
    lateinit var dialog: BottomSheetDialog
    lateinit var commentList: MutableList<UserReport>
    var visited: Boolean = false

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName_name)
        val imageViewLocation = view.findViewById<ImageView>(R.id.showLocationIcon)

        val locations = locationList[position]

        commentList = mutableListOf()

        textViewName.text = locations.name
        ctx = this.context!!

        textViewName.setOnClickListener {
            show_dialog(view, locations)
        }

        imageViewLocation.setOnClickListener {
            showMap(locations)
        }
        return view
    }

    private fun displayLastVisited(view: View) {

        val textViewName6 = view.findViewById<TextView>(R.id.textViewVisited)
        val imageView2 = view.findViewById<ImageView>(R.id.iconVisited)

        if (visited == true) {
            textViewName6.setText("Bereits besucht!")
            imageView2.setImageResource(R.drawable.ic_check_black_24dp)
        } else {
            textViewName6.setText("Noch nicht besucht!")
            imageView2.setImageResource(R.drawable.ic_close_black_24dp)
        }
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

        textViewName1.setText(locations.name)
        textViewName2.setText(locations.year.toString())
        textViewName3.setText(locations.info)

        //ADD ImageView for Preview of location
        val url = locations.imageThumbnail

        // Download directly from StorageReference using Glide
        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.burg_rotteln_oben)
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
                    val comment = h.getValue(UserReport::class.java)
                    commentList.add(comment!!)
                }
                textViewName4.setText(commentList[0].comment)

                visited =  commentList[0].visited

                displayLastVisited(view)
            }
        })


        view.startVideo.setOnClickListener() {
            val locationId = locations.id
            val locationName = locations.name
            val locationImageMap = locations.imageMap
            startVideo(locationId, locationName)
        }

        view.changeComment.setOnClickListener() {
            changeComment(view, locations, textViewName4)
        }

        view.iv_close.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun changeComment(view: View, locations: Locations, textViewName4: TextView) {

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {

            val userId = user.uid
        }

        val builder = AlertDialog.Builder(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update_comment, null)

        val EditText = view.findViewById<EditText>(R.id.contentComment)

        EditText.setText(textViewName4.text.toString())

        builder.setView(view)

        builder.setPositiveButton("Ändern") { p0, p1 ->
            val databaseComment = FirebaseDatabase.getInstance().getReference().child("user").child(user!!.uid).child(locations.id)

            val comment = EditText.text.toString().trim()

            val comObj = UserReport(locations.id, comment, visited)

            if (comment == "") {
                Toast.makeText(
                    this.context,
                    "Bitte geben Sie einen Kommentar ein.",
                    Toast.LENGTH_LONG
                ).show()
                return@setPositiveButton
            } else {
                databaseComment.child("report").setValue(comObj)

                //To Update the comment within the bottom sheet
                textViewName4.setText(comment)
            }
        }

        builder.setNegativeButton("Zurück") { p0, p1 ->

        }

        val alert = builder.create()
        alert.show()
    }

    private fun startVideo(locationId: String, locationName: String) {

                 val intent = Intent(context, Video::class.java)

                //To pass the name and id of the chosen category to activity Game.kt
                intent.putExtra("extra_location_id", locationId)
                intent.putExtra("extra_location_name", locationName)

                context.startActivity(intent)
    }

    private fun showMap(locations: Locations) {

                val intent = Intent(context, MapOverview::class.java)

                val locationId = locations.id
                val locationName = locations.name
                val locationImageMap = locations.imageMap

                //To pass the name and id to the activity Question Overview.kt
                intent.putExtra("extra_location_id", locationId)
                intent.putExtra("extra_location_name", locationName)
                intent.putExtra("extra_location_ImageMap", locationImageMap)

                context.startActivity(intent)
    }
}