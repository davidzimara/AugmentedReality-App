package com.example.AugmentedRealityApp.DataClasses

import android.widget.ImageView
import com.example.AugmentedRealityApp.R

data class Locations(val id: String, val name: String, val info: String, val year: Long, val comment: String, val image: String) {
    constructor() : this("", "","",0, "Fügen Sie einen Kommentar hinzu", "Logo") {

    }
    //for the childUpdate function within the CategoryAdapter
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "year" to year,
            "info" to info,
            "comment" to comment
        )
    }
}