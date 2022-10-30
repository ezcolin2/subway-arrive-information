package com.example.xmlapi


data class StoreComment(
    val uId:String,
    val user_name : String,
    val comment : String,
    val score : Float,
    val time:String
){

    constructor():this("","","",0F,"")
}