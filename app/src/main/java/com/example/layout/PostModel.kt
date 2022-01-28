package com.example.layout

import java.util.*

data class PostModel(
    var ID: Int = getAutoId(),
    var title: String = "",
    var description: String = "",
    var latitude: Double,
    var longtitude: Double,
    var image: ByteArray
//    var user_id: Int = -1

    ){
    companion object{
    fun getAutoId():Int {
        val random = Random()
        return random.nextInt(10000)
    }
    }
}