package com.example.xmlapi

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects

data class Ticker (
    val errorMessage:Objects,
    val realtimeArrivalList:Array<Data>
    )
