package com.example.xmlapi

data class User(
    val name:String,
    val gender: String,
    val email:String,
    val uid:String,
    ){
    constructor():this("","","", "")
}
