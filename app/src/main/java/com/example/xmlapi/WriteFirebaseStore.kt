package com.example.xmlapi

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WriteFirebaseStore(val storeComment : StoreComment, val storeName : String) {
    lateinit var database : DatabaseReference
    lateinit var h : StoreComment
    fun uploadComment(){
        var commentCount : Int = 0;
        var score : Float = storeComment.score;
        database= Firebase.database.reference




        database.child("cafe").child(storeName).child("comment").push().setValue(storeComment)
        database.child("cafe").child(storeName).child("totalScore").push().setValue(score)
        
        database.child("user").child(storeComment.uId).child("comment").push().setValue(storeComment)
        //나중에 총 댓글 수도 확인하게끔 하기
    }
}