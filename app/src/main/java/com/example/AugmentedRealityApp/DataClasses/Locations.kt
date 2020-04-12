package com.example.AugmentedRealityApp.DataClasses

data class Locations(val id: String, val name: String, val info: String, val year: Long, val comment: String) {
    constructor() : this("", "","",0, "Fügen sie einen Kommentar hinzu") {

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