package com.example.lernapp


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass.
 */
class ChooseCategory : Fragment() {

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

        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    categoryList.clear() // have to be, otherwise it will duplicate the list item and attach them below
                    for (h in p0.children) {
                        val category = h.getValue(Categories::class.java)
                        categoryList.add(category!!)
                    }
                    val adapter = ChooseCategoryAdapter(ctx, R.layout.choose_category,  categoryList)
                    listView.adapter = adapter
                }
            }
        })
    }
}
