package com.example.xmlapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xmlapi.Cafe
import com.example.xmlapi.StoreComment
import com.example.xmlapi.repository.Repository

class Viewmodel : ViewModel() {
    private val repository = Repository()



    private val _arr = MutableLiveData<ArrayList<Cafe>>(ArrayList<Cafe>())

    private val _reviews = MutableLiveData<ArrayList<StoreComment>>()
    private val _storeName = MutableLiveData<String>("반점")
    init{
        repository.observeCafeList(_arr)
        repository.observeReviewList(_reviews,storeName.value!!)


    }
    fun setStoreName(storeName:String){
        _storeName.value=storeName
        repository.observeReviewList(_reviews,storeName)
    }

    val cafeList get()= _arr
    val storeName get() = _storeName
    val reviews get()=_reviews
}