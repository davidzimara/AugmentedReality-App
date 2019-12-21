package com.example.lernapp.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.lernapp.Adapter.CategoryAdapter
import com.example.lernapp.DataClasses.Categories
import com.example.lernapp.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_subject.*

class SubjectFragment : Fragment() {

    private lateinit var database: DatabaseReference
    lateinit var categoryList: MutableList<Categories>
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

        database = FirebaseDatabase.getInstance().getReference("Categorys")

        categoryList = mutableListOf()
        listView = view.findViewById(R.id.listViewSubject)
        ctx = this.context!!

        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                categoryList.clear()
                if (p0!!.exists()) {
                    for (h in p0.children) {
                        val category = h.getValue(Categories::class.java)
                        categoryList.add(category!!)
                    }
                }
                val adapter = CategoryAdapter(
                    ctx,
                    R.layout.categories,
                    categoryList
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
