package com.example.AugmentedRealityApp.DataClasses

data class Locations(val id: String, val name: String, val info: String, val year: Long,  val imageThumbnail: String, val imageMap: String) {
    constructor() : this("", "","",0,  "Logo", "Logo") {

    }
}