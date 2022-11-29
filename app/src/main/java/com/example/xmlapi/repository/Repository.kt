package com.example.xmlapi.repository

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import com.example.xmlapi.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    val database = Firebase.database
    val cafeRef = database.getReference("cafe")
    lateinit var arr:ArrayList<Cafe>
    lateinit var commentList:ArrayList<StoreComment>
    fun observeCafeList(arrCafe: MutableLiveData<ArrayList<Cafe>>){

        cafeRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    arr=ArrayList<Cafe>()

                    if(snapshot==null){
                        arr.add(Cafe("name",0,0F))
                        arrCafe.value=arr
                        return
                    }
                    for(snap in snapshot.children){
                        val reviewNums:Int = snap.child("totalCount").getValue<Int>()?:0
                        val reviewStars:Float = snap.child("totalScore").getValue<Float>()?:0F
                        val name:String = snap.child("name").getValue<String>()?:"없음"
                        arr.add(Cafe(name,reviewNums,reviewStars))

                    }
                    arrCafe.postValue(arr)



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
                        val comment =StoreComment(snap.child("uid").getValue<String>()!!,snap.child("user_name").getValue<String>()!!,snap.child("comment").getValue<String>()!!,snap.child("score").getValue<Float>()!!,snap.child("time").getValue<String>()!!)

                        if(comment.time=="not"){
                            continue;
                        }
                        commentList.add(0,comment)
                        totalScore+=snap.child("score").getValue<Float>()?:0F
                        totalCount++
                    }
                    if(totalCount!=0) {
                        setScoreAndCount(storeName, (totalScore / totalCount), totalCount)
                    }
                    arrReview.postValue(commentList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
                private fun setScoreAndCount(storeName:String,score: Float, count:Int) {
                    database.getReference("cafe").child(storeName).child("totalScore").setValue(score)
                    database.getReference("cafe").child(storeName).child("totalCount").setValue(count)
                }

            })
    }
    fun observeSubwayList(subway:MutableLiveData<Array<Data>>){
        val call = Api().apiRequest()
        lateinit var arr:Array<Data>

        call.enqueue(object: Callback<SubwayApiData> {
            override fun onResponse(call: Call<SubwayApiData>, response: Response<SubwayApiData>) {
                val info = response.body()
                subway.value= info?.realtimeArrivalList!!

            }

            override fun onFailure(call: Call<SubwayApiData>, t: Throwable) {
                Log.d("TTT",t.message!!)
                call.cancel()

            }
    })
    }
    fun postComment(storeComment : StoreComment, storeName : String){
        var commentCount : Int = 0;
        var score : Float = storeComment.score;




        database.getReference("cafe").child(storeName).child("comment").push().setValue(storeComment)
        database.getReference("cafe").child(storeName).child("totalScore").setValue(score)
        database.getReference("user").child(storeComment.uId).child("comment").push().setValue(storeComment)
        //나중에 총 댓글 수도 확인하게끔 하기
    }

    fun postReview(newValue:String){

    }
}