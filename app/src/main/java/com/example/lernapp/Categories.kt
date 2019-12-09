package com.example.lernapp

data class Categories (val id: String, val name: String) {
    constructor(): this("","") {

    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name
        )
    }
}