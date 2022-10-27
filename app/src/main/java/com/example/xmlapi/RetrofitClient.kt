package com.example.xmlapi

import android.provider.SyncStateContract
import com.example.xmlapi.BuildConfig.BASE_URL
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Call
import retrofit2.Retrofit


object RetrofitClient {
    fun getXMLInstance() : Retrofit{
         val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
         return Retrofit.Builder()
                .baseUrl("https://www.data.go.kr/")
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()
    }
}
