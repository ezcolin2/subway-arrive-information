package com.example.xmlapi.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.xmlapi.Cafe
import com.example.xmlapi.StoreComment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Repository {
    val database = Firebase.database
    val userRef = database.getReference("user")
    lateinit var arr:ArrayList<Cafe>
    lateinit var commentList:ArrayList<StoreComment>
    fun observeCafeList(arrCafe: MutableLiveData<Array<Cafe>>){

        database.getReference("cafe").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    arr=ArrayList<Cafe>()

                    if(snapshot==null){
                        arr.add(Cafe("name",0,0F))
                        arrCafe.value=arr.toTypedArray()
                        return
                    }
                    for(snap in snapshot.children){
                        Log.d("hello",snap.child("name").toString())

                        val reviewNums:Int = snap.child("totalCount").getValue<Int>()?:0
                        val reviewStars:Float = snap.child("totalScore").getValue<Float>()?:0F
                        val name:String = snap.child("name").getValue<String>()?:"없음"
                        Log.d("hello",name)
                        arr.add(Cafe(name,reviewNums,reviewStars))

                    }
                    arrCafe.postValue(arr.toTypedArray())



                }

                override fun onCancelled(error: DatabaseError) {
                    //arr.add(Cafe("정보 없음",0,0F))
                }
            }
        )
    }
    fun observeReviewList(arrReview : MutableLiveData<ArrayList<StoreComment>>,storeName:String){
        database.getReference("cafe").child(storeName).child("comment")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    commentList=ArrayList<StoreComment>()
                    var totalScore:Float = 0F;
                    var totalCount:Int = 0;

                    for(snap in snapshot.children){
                        val comment:StoreComment = snap.getValue<StoreComment>()!!

                        if(comment.time=="not"){
                            continue;
                        }
                        commentList.add(0,comment)
                        totalScore+=snap.child("score").getValue<Float>()?:0F
                        totalCount++
                    }
                    if(totalCount!=0) {
                        setScoreAndCount(storeName, totalScore / totalCount, totalCount)
                    }
                    arrReview.postValue(commentList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
    private fun setScoreAndCount(storeName:String,score: Float, count:Int) {
        database.getReference("cafe").child(storeName).child("totalScore").setValue(score)
        database.getReference("cafe").child(storeName).child("totalCount").setValue(count)
    }
    fun postReview(newValue:String){
        userRef.setValue(newValue)
    }
}