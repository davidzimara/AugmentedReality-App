package com.example.lernapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CreateFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        (activity as MainActivity)
            .setActionBarTitle("Erstellen")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false)
    }



}
