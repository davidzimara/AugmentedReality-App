package com.example.AugmentedRealityApp.DataClasses

import android.widget.ImageView
import com.example.AugmentedRealityApp.R

data class Locations(val id: String, val name: String, val info: String, val year: Long,  val image: String) {
    constructor() : this("", "","",0,  "Logo") {

    }
    //for the childUpdate function within the CategoryAdapter
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "year" to year,
            "info" to info
        )
    }
}