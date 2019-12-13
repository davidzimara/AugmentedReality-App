package com.example.lernapp.DataClasses

data class Categories(val id: String, val name: String) {
    constructor() : this("", "") {

    }
    //for the childUpdate function within the CategoryAdapter
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name
        )
    }
}