package com.example.xmlapi

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WriteFirebaseStore(val uId: String, val storeComment : StoreComment, val storeName : String) {
    lateinit var database : DatabaseReference
    lateinit var h : StoreComment
    fun uploadComment(){
        var commentCount : Int = 0;
        var score : Int = storeComment.score;
        database= Firebase.database.reference
//        database.child(storeName).addValueEventListener(object:ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                for (i in snapshot.child("comment").children) {
//                    commentCount += 1
//                }
//                if(commentCount==0){
//                    commentCount=1
//                }
//                score=snapshot.child("totalScore").getValue<Int>()?:0
//                score+=storeComment.score
//                //이름이 totalScore면 문제가됨
////                database.child(storeName).child(
///                 .setValue((score+storeComment.score)/(commentCount))
//                h = snapshot.child("comment").child("3").getValue<StoreComment>()!!
//                /                    "totalScore")
////
//            }
//            override fun onCancelled(error:DatabaseError){
//                Log.d("put failed",error.message)
//            }
//        })



        database.child("cafe").child(storeName).child("comment").child(uId).setValue(storeComment)
        database.child("cafe").child(storeName).child("totalScore").setValue(score)

    }
}