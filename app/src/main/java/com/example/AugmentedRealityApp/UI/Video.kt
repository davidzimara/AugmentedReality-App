package com.example.AugmentedRealityApp.UI

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import com.example.AugmentedRealityApp.R
import kotlinx.android.synthetic.main.activity_video.*

class Video : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        //Handed over from LocationAdapter
        val locationNameFromAdapter = intent.getStringExtra("extra_location_name")
        val locationIdFromAdapter = intent.getStringExtra("extra_location_id")

        locationName.text = locationNameFromAdapter

        val videoView = findViewById<VideoView>(R.id.videoView)

        if (locationIdFromAdapter == "l1") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.saal
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l2") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.kuecheflv
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l3") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.torturm_final
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l4") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.roetteln_1300_final
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l5") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.roetteln_1500_final
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l6") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.blide_final
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l7") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.bombarde_final
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l8") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.riesenbombarde_final
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l9") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.roetteln_heli
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l10") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.schutzwand_final
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        if (locationIdFromAdapter == "l11") {
            val videoPath = "android.resource://" + packageName + "/" + R.raw.rammbock_final
            val uri = Uri.parse(videoPath)
            videoView.setVideoURI(uri)
        }

        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)

        //autostart
        videoView.start()

        back_to_main.setOnClickListener{
            onBackPressed()
        }
    }
}
