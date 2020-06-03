package com.example.AugmentedRealityApp.UI

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.AugmentedRealityApp.R
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_map_overview.*

class MapOverview : AppCompatActivity() {

    lateinit var locName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_overview)

        //Handed over from LocationAdapter
        val locationName = intent.getStringExtra("extra_location_name")
        val locationImageMap = intent.getStringExtra("extra_location_ImageMap")

        locName = findViewById<TextView>(R.id.locationName)

        locName.setText("Kartenübersicht - " + locationName)

        val photoView = findViewById<PhotoView>(R.id.photo_view)

        val url = locationImageMap

        // Download directly from StorageReference using Glide
        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(photoView)

        back_to_location.setOnClickListener {
            onBackPressed()
        }
    }
}

