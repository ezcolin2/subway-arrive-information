package com.example.xmlapi

data class User(
    val name:String,
    val gender: String,
    val email:String,
    val uId:String,

    ){
    constructor():this("","","", "")
}
