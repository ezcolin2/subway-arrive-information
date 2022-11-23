package com.example.xmlapi


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView

import com.example.xmlapi.databinding.ActivitySubGangBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        call.enqueue(object: Callback<SubwayApiData> {
            override fun onResponse(call: Call<SubwayApiData>, response: Response<SubwayApiData>) {
                val info = response.body()
                arr = info?.realtimeArrivalList!!


                val Adapter = SubwayAdapter(this@SubActivityGang, arr)
                binding.listView.adapter=Adapter
                var listener = ListClickListener()
                binding.listView.onItemClickListener = listener

            }

            override fun onFailure(call: Call<SubwayApiData>, t: Throwable) {
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
}