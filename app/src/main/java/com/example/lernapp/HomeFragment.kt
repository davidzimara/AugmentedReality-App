package com.example.lernapp


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity)
            .setActionBarTitle("Home")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: aus der Main den code für home rauskopieren
        //from the Game.kt activity (last run)
        val correctAnswer = activity!!.intent.getStringExtra("extra_correct_answer")

        val statistics = view.findViewById<TextView>(R.id.statistic)

        if (correctAnswer!=null) {

            statistics.text = "Sie haben " + correctAnswer + " Fragen richtig beantwortet."

        }

    }
}


