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

        //TODO: Liste vervollständigen


        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
    }
}