package com.example.AugmentedRealityApp.DataClasses

class UserReport (val id: String, val comment: String, val visited: Boolean) {
    constructor() : this("",  "Fügen Sie eine Notiz hinzu.", false){

    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "comment" to comment
        )
    }
}