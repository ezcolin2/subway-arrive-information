package com.example.xmlapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xmlapi.databinding.ActivityMainBinding
import org.w3c.dom.Element
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubway.setOnClickListener{
            val intent = Intent(this, SubActivityGang::class.java)
            startActivity(intent)
        }
        binding.btnBus.setOnClickListener{
            val intent = Intent(this, SubActivityBus::class.java)
            startActivity(intent)
        }

    }
//    private fun apiRequest(){
//        //retrofit 객체 생성
//        val retrofit : Retrofit=Retrofit.Builder()
//            .baseUrl("http://swopenapi.seoul.go.kr/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        //service 객체
//        val apiService:ApiService = retrofit.create(ApiService::class.java)
//        //call 객체
//        val call = apiService.getInfo()
//        //network
//        call.enqueue(object:Callback<Ticker>{
//            override fun onResponse(call: Call<Ticker>, response: Response<Ticker>) {
//                val info = response.body()
//                val arr = info?.realtimeArrivalList
//                for(i in 0 until arr!!.size) {
//                    binding.textView.append("${i+1} 방향 : ${info?.realtimeArrivalList?.get(i)?.trainLineNm}")
//                    binding.textView.append("${i+1} 현재위치 : ${info?.realtimeArrivalList?.get(i)?.arvlMsg2}")
//                    binding.textView.append("\n")
//                }
//            }
//
//            override fun onFailure(call: Call<Ticker>, t: Throwable) {
//                binding.textView.append(t.message)
//                call.cancel()
//
//            }
//        }
//        )
//    }
//
}