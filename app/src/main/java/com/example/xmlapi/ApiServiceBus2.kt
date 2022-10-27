package com.example.xmlapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceBus2 {
    @GET("api/subway/7a58737a65676f6e36336e45434172/xml/realtimePosition/0/30/경의중앙선")
    fun getInfo(
    ): Call<BusData2>
}