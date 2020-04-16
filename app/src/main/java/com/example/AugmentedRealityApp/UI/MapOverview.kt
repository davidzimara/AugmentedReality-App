package com.example.AugmentedRealityApp.UI

import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.AugmentedRealityApp.R
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_question_overview.*


class MapOverview : AppCompatActivity() {

    lateinit var locName: TextView
    lateinit var locPosition: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_overview)

        //Handed over from LocationAdapter
        val locationId = intent.getStringExtra("extra_location_id")
        val locationName = intent.getStringExtra("extra_location_name")

        locName = findViewById<TextView>(R.id.locationName)

        locName.setText("Kartenübersicht - " + locationName)

       /* locPosition = findViewById<ImageView>(R.id.icon_location)
        val lp: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(locPosition.getLayoutParams())
        if (locationId == "l1") {
            locPosition.getResources().getDisplayMetrics().density
            lp.setMargins(400, 600, 0, 0)
            locPosition.setLayoutParams(lp)
        } else {
            locPosition.getResources().getDisplayMetrics().density
            lp.setMargins(480, 470,0,0)
            locPosition.setLayoutParams(lp) }*/


            val photoView = findViewById(R.id.photo_view) as PhotoView
            photoView.setImageResource(R.drawable.karte_burg_rotteln_lorrach)

        back_to_location.setOnClickListener {
            onBackPressed()
        }
    }
}

        //val database = FirebaseDatabase.getInstance().getReference("Categorys").child(category).child("questions")


        /*database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                questionList.clear()
                if (p0!!.exists()) {
                    for(h in p0.children) {
                        val question = h.getValue(Questions::class.java)
                        questionList.add(question!!)
                    }
                }
                val adapter = QuestionAdapter(this@MapOverview, R.layout.questions, questionList)
                listView.adapter = adapter
            }
        })*/


