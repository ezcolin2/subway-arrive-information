package com.example.xmlapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xmlapi.Cafe
import com.example.xmlapi.SubwayData
import com.example.xmlapi.StoreComment
import com.example.xmlapi.User
import com.example.xmlapi.repository.Repository

class DataViewModel : ViewModel() {
    private val repository = Repository()



    private val _arr = MutableLiveData<ArrayList<Cafe>>(ArrayList<Cafe>())
    private val _reviews = MutableLiveData<ArrayList<StoreComment>>()
    private val _storeName = MutableLiveData<String>("반점")
    private val _subwayList = MutableLiveData<Array<SubwayData>>()
    private val _email = MutableLiveData<String>()
    private val _userInfo = MutableLiveData<User>()
    private val _myComments = MutableLiveData<ArrayList<StoreComment>>()

    init{
        repository.observeCafeList(_arr)

        //repository.observeReviewList(_reviews,_storeName.value!!)
        //repository.observeSubwayList(_subwayList)

    }
    fun setStoreName(storeName:String){
        _storeName.value=storeName
        repository.observeReviewList(_reviews,storeName)
    }
    fun setEmail(email:String){
        Log.d("email",email)
        _email.value = email
        Log.d("email","!")
        repository.observeUser(_userInfo,email)
        Log.d("email","@")

    }
    fun setComment(storeComment : StoreComment){
        repository.postComment(storeComment,_storeName.value!!)
    }
    fun getSubway(){
        repository.observeSubwayList(_subwayList)
    }
    fun setMyComment(){
        repository.observeMyCommentList(_myComments,_userInfo.value?.uid?:"none")
        Log.d("hello",_userInfo.value?.uid?:"none")
    }

    val cafeList get()= _arr
    val storeName get() = _storeName
    val reviews get()=_reviews
    val subways get() = _subwayList
    val user get() = _userInfo
    val myComments get() = _myComments
}