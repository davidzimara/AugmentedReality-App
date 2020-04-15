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
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.AugmentedRealityApp.Adapter.LocationAdapter
import com.example.AugmentedRealityApp.DataClasses.Comments
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
    lateinit var chooseCategory: ChooseCategoryFragment
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

    fun showChooseCategory(view: View) {
        chooseCategory = ChooseCategoryFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, chooseCategory)
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
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
                    val locationId = result.contents.toString()

                    showLocationDialog(locationId)
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

        val textViewName1 = view.findViewById<TextView>(R.id.textViewName1)
        val textViewName2 = view.findViewById<TextView>(R.id.textViewYear)
        val textViewName3 = view.findViewById<TextView>(R.id.textViewInfo)
        val imageView = view.findViewById<ImageView>(R.id.img_location)
        val startVideo = view.findViewById<ImageView>(R.id.playButton)

        textViewName1.text = locationId

        val database = FirebaseDatabase.getInstance().getReference("location")
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

                textViewName1.setText(locationList[0].name)
                textViewName2.setText(locationList[0].year.toString())
                textViewName3.setText(locationList[0].info)
                //TODO: ADD Image view for Preview of location

                val url = locationList[0].image

                // Download directly from StorageReference using Glide
                Glide.with(ctx)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.burg_rotteln)
                    .into(imageView)
            }
        })

        view.iv_close.setOnClickListener {
            dialog.dismiss()
        }
    }
}
