package com.example.xmlapi

data class User(
    val name:String,
    val email:String,
    val uId:String,

    ){
    constructor():this("","","")
}
