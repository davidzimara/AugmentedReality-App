package com.example.AugmentedRealityApp.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.AugmentedRealityApp.Adapter.LocationAdapter
import com.example.AugmentedRealityApp.DataClasses.Locations
import com.example.AugmentedRealityApp.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_subject.*

class LocationFragment : Fragment() {

    private lateinit var database: DatabaseReference
    lateinit var locationList: MutableList<Locations>
    lateinit var listView: ListView
    lateinit var ctx: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity)
            .setActionBarTitle("Kategorien")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("location")

        locationList = mutableListOf()
        listView = view.findViewById(R.id.listViewSubject)
        ctx = this.context!!

        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                locationList.clear()
                if (p0!!.exists()) {
                    for (h in p0.children) {
                        val category = h.getValue(Locations::class.java)
                        locationList.add(category!!)
                    }
                }
                val adapter = LocationAdapter(
                    ctx,
                    R.layout.categories,
                    locationList
                )
                listView.adapter = adapter
            }
        })

        create_category.setOnClickListener {
            openCreateCategory()
        }
    }

    private fun openCreateCategory() {
        startActivity(Intent(this.context, CreateCategory::class.java))
    }
}
