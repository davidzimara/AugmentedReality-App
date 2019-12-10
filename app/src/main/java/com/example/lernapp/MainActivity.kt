package com.example.lernapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_layout.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*


class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var subjectFragment: SubjectFragment
    lateinit var chooseCategory: ChooseCategory
    lateinit var settingsFragment: SettingsFragment
    lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var categoryList: MutableList<Categories>
    lateinit var listView: ListView
    lateinit var dialog: BottomSheetDialog
    lateinit var statistics: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

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

                    statisticFragment = ChooseCategory()
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

    }

    fun setActionBarTitle(title: String) {
        getSupportActionBar()?.setTitle(title)
    }

    //show bottom sheet (layout) "dialog_layout"
    fun show_dialog(view: View) {

        dialog = BottomSheetDialog(this)
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

        database = FirebaseDatabase.getInstance().getReference("Categorys")

        categoryList = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("Categorys")
        listView = view.findViewById(R.id.listViewDialog)


        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    categoryList.clear() // have to be, otherwise it will duplicate the list item and attach them below
                    for (h in p0.children) {
                        val category = h.getValue(Categories::class.java)
                        categoryList.add(category!!)
                    }
                    val adapter = CategoryAdapter(this@MainActivity, R.layout.categories,  categoryList)
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
        chooseCategory = ChooseCategory()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, chooseCategory)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    //Logout User Method is called by onClick Button in SettingsFragment
    fun doLogout(view: View) {
        Toast.makeText(baseContext,"Sie wurden abgemeldet.",Toast.LENGTH_LONG).show()
        logout()

        auth.addAuthStateListener {
            if(auth.currentUser==null) {
                startActivity(Intent(this, LoginScreen::class.java))
                finish()
            }
        }
    }

    fun logout() {
        auth.signOut()
    }

}
