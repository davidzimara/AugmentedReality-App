package com.example.AugmentedRealityApp.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.AugmentedRealityApp.Adapter.LocationAdapter
import com.example.AugmentedRealityApp.DataClasses.Locations
import com.example.AugmentedRealityApp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_layout.view.*


class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var locationFragment: LocationFragment
    lateinit var chooseCategory: ChooseCategoryFragment
    lateinit var settingsFragment: SettingsFragment
    private lateinit var database: DatabaseReference
    lateinit var categoryList: MutableList<Locations>
    lateinit var listView: ListView
    lateinit var dialog: BottomSheetDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.main_nav)

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

    fun show_dialog(view: View) {

        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_layout, null)
        dialog.setContentView(view)
        dialog.show()

        view.openScanner.setOnClickListener {

            val intent = Intent(this, QrCodeScanner::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        view.iv_close.setOnClickListener {
            dialog.dismiss()
        }

        database = FirebaseDatabase.getInstance().getReference("Categorys")

        categoryList = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("Categorys")
        //listView = view.findViewById(R.id.listViewDialog)


        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    categoryList.clear()
                    for (h in p0.children) {
                        val category = h.getValue(Locations::class.java)
                        categoryList.add(category!!)
                    }
                    val adapter = LocationAdapter(this@MainActivity, R.layout.categories, categoryList)
                    listView.adapter = adapter
                }
            }
        })
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
}
