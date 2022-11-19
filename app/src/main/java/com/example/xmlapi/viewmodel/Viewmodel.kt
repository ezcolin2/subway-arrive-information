package com.example.xmlapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xmlapi.Cafe
import com.example.xmlapi.repository.Repository

class Viewmodel : ViewModel() {
    private val repository = Repository()
    var arr= emptyArray<Cafe>()



    private val _arr = MutableLiveData<Array<Cafe>>(arrayOf(Cafe("hihi",1,1F)))
    init{
        repository.observeCafeList(_arr)


    }

    val cafeList get()= _arr
}