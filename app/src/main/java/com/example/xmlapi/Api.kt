package com.example.xmlapi


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    fun apiRequest():Call<Ticker>{
        //retrofit 객체 생성
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("http://swopenapi.seoul.go.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //service 객체
        val apiService:ApiService = retrofit.create(ApiService::class.java)
        //call 객체
        val call = apiService.getInfo()
        return call
    }


}
