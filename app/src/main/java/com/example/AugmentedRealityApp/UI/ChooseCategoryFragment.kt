package com.example.AugmentedRealityApp.UI


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.AugmentedRealityApp.Adapter.ChooseCategoryAdapter
import com.example.AugmentedRealityApp.DataClasses.Categories
import com.example.AugmentedRealityApp.R
import com.google.firebase.database.*

class ChooseCategoryFragment : Fragment() {

    private lateinit var database: DatabaseReference
    lateinit var categoryList: MutableList<Categories>
    lateinit var listView: ListView
    lateinit var ctx: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_choose_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("Categorys")

        categoryList = mutableListOf()
        listView = view.findViewById(R.id.listViewCategory)
        ctx = this.context!!

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    categoryList.clear()
                    for (h in p0.children) {
                        val category = h.getValue(Categories::class.java)
                        categoryList.add(category!!)
                    }
                    val adapter = ChooseCategoryAdapter(ctx, R.layout.choose_category, categoryList)
                    listView.adapter = adapter
                }
            }
        })
    }
}
