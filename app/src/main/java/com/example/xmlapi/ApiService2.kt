package com.example.xmlapi

import retrofit2.Call
import retrofit2.http.GET

interface ApiService2 {
    @GET("api/subway/7a58737a65676f6e36336e45434172/json/realtimePosition/0/10/경의중앙선")
    fun getInfo(): Call<Ticker2>
}