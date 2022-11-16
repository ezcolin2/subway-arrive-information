package com.example.xmlapi

import retrofit2.Call
import retrofit2.http.GET

interface ApiService2 {
    @GET("api/subway/"+BuildConfig.API_KEY+ "/json/realtimePosition/0/50/경의중앙선")
    fun getInfo(): Call<Ticker2>
}