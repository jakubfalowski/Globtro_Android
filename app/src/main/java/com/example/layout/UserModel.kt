package com.example.layout

import java.util.*

data class UserModel (
    var ID: Int = getAutoId(),
    var username: String = "",
    var password: String = ""
){
    companion object{
    fun getAutoId():Int {
        val random = Random()
        return random.nextInt(100)
    }
    }
}