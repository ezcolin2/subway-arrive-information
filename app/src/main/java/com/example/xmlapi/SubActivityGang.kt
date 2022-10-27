package com.example.xmlapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer

import com.example.xmlapi.databinding.ActivitySubGangBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SubActivityGang : AppCompatActivity() {
    private lateinit var binding:ActivitySubGangBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubGangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGang.setOnClickListener{
            apiRequest()

        }
    }
    private fun apiRequest(){

        val call = Api().apiRequest()
        lateinit var arr:Array<Data>

        call.enqueue(object: Callback<Ticker> {
            override fun onResponse(call: Call<Ticker>, response: Response<Ticker>) {
                val info = response.body()
                arr = info?.realtimeArrivalList!!


                val Adapter = SubwayAdapter(this@SubActivityGang, arr)
                binding.listView.adapter=Adapter
                var listener = ListClickListener()
                binding.listView.onItemClickListener = listener

            }

            override fun onFailure(call: Call<Ticker>, t: Throwable) {
                Log.d("TTT",t.message!!)
                call.cancel()

            }
            inner class ListClickListener: AdapterView.OnItemClickListener{
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    binding.btnGang.text = arr[position].btrainNo
                }

            }
        }
        )
    }

    private fun apiRequest2(){

        val call = Api().apiRequest2()


        call.enqueue(object: Callback<Ticker2> {
            override fun onResponse(call: Call<Ticker2>, response: Response<Ticker2>) {
                val info = response.body()
                val arr = info?.realtimePositionList!!
                binding.btnGang.text=arr[0].subwayNm

            }

            override fun onFailure(call: Call<Ticker2>, t: Throwable) {

                call.cancel()

            }
        }
        )
    }
}