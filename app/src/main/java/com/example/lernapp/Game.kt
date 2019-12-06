package com.example.lernapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {

    //Varaibles
    val ITEM_COUNT = 21
    var total_item = 0
    var last_visible_item = 0

    lateinit var adapter: GameAdapter

    var isLoading=false
    var isMaxData=false

    var last_node:String?=""
    var last_key:String?=""

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        if(id == R.id.answer1)
        {
            isMaxData = false
            last_node = adapter.lastItemId
            adapter.removeLastItem()
            adapter.notifyDataSetChanged()
            getLastKey()
            getQuestion()

        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val category = intent.getStringExtra("extra_category_id")

        Toast.makeText(baseContext, category, Toast.LENGTH_LONG).show()

        getLastKey()

        val layoutManager = LinearLayoutManager(this)
        grid_answer.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(grid_answer.context, layoutManager.orientation)
        grid_answer.addItemDecoration(dividerItemDecoration)

        adapter = GameAdapter(this)
        grid_answer.adapter = adapter

        getQuestion()

        grid_answer.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                total_item = layoutManager.itemCount
                last_visible_item = layoutManager.findLastVisibleItemPosition()

                if(!isLoading && total_item <= last_visible_item+ITEM_COUNT)
                {
                    getQuestion()
                    isLoading=true
                }
            }
        })

    }

    private fun getQuestion() {

        val category = intent.getStringExtra("extra_category_id")

        if(!isMaxData)
        {
            val query: Query
            if(TextUtils.isEmpty(last_node))
                query = FirebaseDatabase.getInstance().reference
                    .child("Categorys")
                    .child(category).child("questions")
                    .orderByKey()
                    .limitToFirst(ITEM_COUNT)
            else
                query = FirebaseDatabase.getInstance().reference
                    .child("Categorys")
                    .child(category).child("questions")
                    .orderByKey()
                    .startAt(last_node)
                    .limitToFirst(ITEM_COUNT)

            query.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.hasChildren())
                    {
                        val questionList = ArrayList<CurrentQuestion>()
                        for (snapshot in p0.children)
                            questionList.add(snapshot.getValue(CurrentQuestion::class.java)!!)
                        last_node = questionList[questionList.size - 1 ].id

                        if (!last_node.equals(last_key))
                            questionList.removeAt(questionList.size - 1)
                        else
                            last_node = "end" // Fix error infinity add last item

                        adapter.addAll(questionList)
                        isLoading = false
                    }

                    else {
                        isLoading=false
                        isMaxData=true
                    }
                }
            })
        }
    }

    private fun getLastKey() {

        val category = intent.getStringExtra("extra_category_id")

        val get_last_key = FirebaseDatabase.getInstance().getReference("Categorys")
            .child(category).child("questions")
            .orderByKey()
            .limitToLast(1)
        get_last_key.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(dataSnapshot in p0.children) {
                    last_key = dataSnapshot.key
                }
            }
        })
    }


}
