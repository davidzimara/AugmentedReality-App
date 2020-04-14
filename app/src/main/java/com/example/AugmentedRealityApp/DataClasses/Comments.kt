package com.example.AugmentedRealityApp.DataClasses

class Comments (val id: String, val comment: String) {
    constructor() : this("",  ""){

    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "comment" to comment
        )
    }
}