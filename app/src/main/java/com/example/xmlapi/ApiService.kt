package com.example.xmlapi

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/subway/"+BuildConfig.API_KEY+ "/json/realtimeStationArrival/0/10/화전")
    fun getInfo(): Call<SubwayApiData>
}
