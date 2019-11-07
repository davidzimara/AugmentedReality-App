package com.example.lernapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_create_category.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.dialog_layout.view.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var subjectFragment: SubjectFragment
    lateinit var createFragment: CreateFragment
    lateinit var statisticFragment: StatisticFragment
    lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "Firebase connected succesfully",Toast.LENGTH_LONG).show()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.main_nav)

        //to show always the home screen

        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        //Navigation between Fragments

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                //now create fragments

                R.id.nav_home -> {

                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_subjects -> {

                    subjectFragment = SubjectFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame, subjectFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_create -> {

                    // val dialog = BottomSheetDialog(this)
                    // val view = layoutInflater.inflate(R.layout.dialog_layout, null)
                    // dialog.setContentView(view)
                    // dialog.show()

                    //createFragment = CreateFragment()
                    //supportFragmentManager
                    //.beginTransaction()
                    //.replace(R.id.main_frame, createFragment)
                    //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    //.commit()
                }

                /** R.id.nav_statistics -> {

                    statisticFragment = StatisticFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame, statisticFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_settings -> {


                    settingsFragment = SettingsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame, settingsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                } */
            }
            true
        }


        // TODO: set Bottom Nav selected on the selected fragment
        /**val intentFragment =
            if (intent.extras != null) intent.extras!!.getString("frgToLoad") else ""
        if (intentFragment!!.isNotEmpty()) {
            when (intentFragment) {
                "subjectFragment" -> {
                    subjectFragment = SubjectFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame, subjectFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
        }*/
    }

    fun setActionBarTitle(title: String) {
        getSupportActionBar()?.setTitle(title)
    }

    //show bottom sheet (layout) "dialog_layout"
    fun show_dialog(view: View) {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_layout, null)
        dialog.setContentView(view)
        dialog.show()


        view.newCategory.setOnClickListener {

            val intent = Intent(this, CreateCategory::class.java)
            intent.putExtra("keyIdentifier", 2)
            startActivity(intent)
            //close the bottom sheet (dialog) per click on "Neue Kategorie" Button
            dialog.dismiss()
        }

        view.iv_close.setOnClickListener {
            //close the bottom sheet (dialog) per click on "iv_close" Button
            dialog.dismiss()
        }

    }


    //open the Fragment "fragment_create" not necessary anymore, can be deleted
    fun show_create_category(view: View) {

        createFragment = CreateFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, createFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

    }

    fun alert(view: View) {}
}
