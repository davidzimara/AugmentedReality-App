package com.example.AugmentedRealityApp.UI

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.AugmentedRealityApp.DataClasses.Locations
import com.example.AugmentedRealityApp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.dialog_layout.view.*
import kotlinx.android.synthetic.main.dialog_layout.view.iv_close
import kotlinx.android.synthetic.main.dialog_layout_info.view.*


class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var locationFragment: LocationFragment
    lateinit var settingsFragment: SettingsFragment
    private lateinit var database: DatabaseReference
    lateinit var listView: ListView
    lateinit var dialog: BottomSheetDialog
    lateinit var ctx: Context
    lateinit var locationList: MutableList<Locations>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ctx = this

        val bottomNavigation: BottomNavigationView = findViewById(R.id.main_nav)

        locationList = mutableListOf()

        //to display always the home screen at start and return to Main
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        //Navigation between Fragments
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {

                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_subjects -> {

                    locationFragment = LocationFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame, locationFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_create -> {
                    //Floating Action Button replaces nav_create item but have to be there otherwise it would occur a bug that no item could be selected
                }
            }
            true
        }

    }

    fun setActionBarTitle(title: String) {
        getSupportActionBar()?.setTitle(title)
    }

    fun close_dialog(view: View) {
        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_layout_info, null)

        view.iv_close.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun show_dialog(view: View) {

        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_layout, null)
        dialog.setContentView(view)
        dialog.show()

        view.openScanner.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }

        view.iv_close.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun show_settings(view: View) {
        settingsFragment = SettingsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, settingsFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {

                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    val locationId = result.contents.toString()
                    //If to avoid that the Database reference in the function showLocationDialog gets an unknow value passed
                    if(locationId == "l1" || locationId == "l2" || locationId == "l3" || locationId == "l4" || locationId == "l5" || locationId == "l6" || locationId == "l7" || locationId == "l8" || locationId == "l9" || locationId == "l10" || locationId == "l11" || locationId == "l12"){
                        showLocationDialog(locationId)
                    } else {
                        Toast.makeText(this, "Der QR-Code " + locationId + " ist nicht bekannt.", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private fun showLocationDialog(locationId: String) {

        dialog = BottomSheetDialog(ctx)

        val inflater = LayoutInflater.from(ctx)

        val view = inflater.inflate(R.layout.dialog_layout_info, null)

        dialog.setContentView(view)
        dialog.show()

        val database = FirebaseDatabase.getInstance().getReference("location").child(locationId).child("QR")
//.child(locationId)
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                locationList.clear()
                for (h in p0.children) {
                    val location = h.getValue(Locations::class.java)
                    locationList.add(location!!)
                }

                val textViewName1 = view.findViewById<TextView>(R.id.textViewName1)
                val textViewName2 = view.findViewById<TextView>(R.id.textViewYear)
                val textViewName3 = view.findViewById<TextView>(R.id.textViewInfo)
                val imageView = view.findViewById<ImageView>(R.id.img_location)
                val cardView = view.findViewById<CardView>(R.id.CardView)

                //Hide comment section
                cardView.visibility = View.GONE

                    textViewName1.setText(locationList[0].name)
                    textViewName2.setText(locationList[0].year.toString())
                    textViewName3.setText(locationList[0].info)
                    //TODO: ADD Image view for Preview of location

                    val url = locationList[0].imageThumbnail

                    // Download directly from StorageReference using Glide
                    Glide.with(ctx)
                        .load(url)
                        .centerCrop()
                        .placeholder(R.drawable.burg_rotteln_oben)
                        .into(imageView)
            }
        })

        view.iv_close.setOnClickListener {
            dialog.dismiss()
        }

        view.startVideo.setOnClickListener(){
            val locationId = locationList[0].id
            val locationName = locationList[0].name
            startVideo(locationId, locationName)
        }
    }

    private fun startVideo(locationId: String, locationName: String) {

        val intent = Intent(ctx, Video::class.java)

        //To pass the name and id of the chosen category to activity Game.kt
        intent.putExtra("extra_location_id", locationId)
        intent.putExtra("extra_location_name", locationName)

        ctx.startActivity(intent)
    }
}
