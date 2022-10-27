package com.example.xmlapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceBus {
    @GET("6410000/busarrivalservice/getBusArrivalList")
    fun getInfo(
        @Query("serviceKey") serviceKey:String,
        @Query("stationId") stationId:String
    ): Call<BusData>
}