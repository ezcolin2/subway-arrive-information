package com.example.xmlapi


data class StoreComment(
    val user_name : String,
    val comment : String,
    val score : Int,
    val time:String
){

    constructor():this("","",0,"")
}