package com.example.xmlapi

import android.provider.SyncStateContract
import com.example.xmlapi.BuildConfig.BASE_URL
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    fun apiRequest2():Call<Ticker2>{
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("http://swopenapi.seoul.go.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //service 객체
        val apiService:ApiService2 = retrofit.create(ApiService2::class.java)
        //call 객체
        val call = apiService.getInfo()
        return call
    }


}
