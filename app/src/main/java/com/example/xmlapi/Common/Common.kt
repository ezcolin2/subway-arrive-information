package com.example.xmlapi.Common

import com.example.xmlapi.Remote.IGoogleAPIService
import com.example.xmlapi.Remote.RetrofitClient

object Common {

    private val GOOGLE_API_URL = "https://maps.googleapis.com/"

    val googleAPIService: IGoogleAPIService
        get() = RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}