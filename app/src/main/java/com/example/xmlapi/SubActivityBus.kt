package com.example.xmlapi

import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.xmlapi.databinding.ActivitySubBusBinding
import com.example.xmlapi.databinding.ActivitySubGangBinding
import okhttp3.OkHttpClient
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.*

import javax.xml.parsers.DocumentBuilderFactory
var text = "default"
class SubActivityBus : AppCompatActivity() {
    lateinit var binding:ActivitySubBusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sub_bus)
//
//        val button = findViewById<Button>(R.id.button2)
//        val textView = findViewById<TextView>(R.id.textView2)
//        textView.text = ""
//
//        val url = "https://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList?serviceKey=ML%2BQoHVuM8i9KQcPT%2FEHMPy3T48DBRAFjXEVIBQfV4whNHxGdrGzeRH7HMnzyN7uos4nL6K%2FpsYQexZaWVamZw%3D%3D&stationId=218000142"
//        button.setOnClickListener {
//            // 쓰레드 생성
//            val thread = Thread(NetworkThread(url))
//            thread.start() // 쓰레드 시작
//            thread.join() // 멀티 작업 안되게 하려면 start 후 join 입력
//
//            // 쓰레드에서 가져온 api 정보 텍스트에 뿌려주기
//            textView.text = text
//        }
        binding = ActivitySubBusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button2.setOnClickListener{
            apiRequest2()
        }
    }
    private fun apiRequest() {

        val retrofit = RetrofitClient.getXMLInstance()
        val retrofitService = retrofit.create(ApiServiceBus::class.java)
        val call: Call<BusData> = retrofitService.getInfo(
            "ML%2BQoHVuM8i9KQcPT%2FEHMPy3T48DBRAFjXEVIBQfV4whNHxGdrGzeRH7HMnzyN7uos4nL6K%2FpsYQexZaWVamZw%3D%3D",
            "200000078"
        )
        Runnable {
            call?.enqueue(object : Callback<BusData> {
                // 서버에서 정상적으로 결과 받은 경우
                override fun onResponse(
                    call: Call<BusData>,
                    response: Response<BusData>
                ) {
                    // 응답 성공
                    if (response.isSuccessful) {
                        val temp = response.body()?.msgBody?.busArrivalList?.size
                        Log.d(TAG, "response - successful ${temp}")
                    } else {
                        // 에러가 발생 한 경우
                        Log.d(TAG, "response - ${response.errorBody()}")
                        Log.d(TAG, "response - ${response.code()}")
                        Log.d(TAG, response.errorBody().toString())
                    }
                }

                // 서버 연동에 실패한 경우
                override fun onFailure(call: Call<BusData>, t: Throwable) {
                    Log.d(TAG, "failure..")
                }

            })
        }.run()
    }
        private fun apiRequest2(){

            val retrofit = RetrofitClient2.getXMLInstance()
            val retrofitService = retrofit.create(ApiServiceBus2::class.java)
            val call:Call<BusData2> = retrofitService.getInfo()
            Runnable {
                call?.enqueue(object : Callback<BusData2> {
                    // 서버에서 정상적으로 결과 받은 경우
                    override fun onResponse(
                        call: Call<BusData2>,
                        response: Response<BusData2>
                    ) {
                        // 응답 성공
                        if(response.isSuccessful) {
                            val temp=response.body()?.msgBody?.size
                            Log.d(TAG, "response - successful ${temp}")
                        } else {
                            // 에러가 발생 한 경우
                            Log.d(TAG, "response - ${response.errorBody()}")
                            Log.d(TAG, "response - ${response.code()}")
                            Log.d(TAG,response.errorBody().toString())
                        }
                    }

                    // 서버 연동에 실패한 경우
                    override fun onFailure(call: Call<BusData2>, t: Throwable) {
                        Log.d(TAG, "failure..")
                    }

                })
            }.run()

//        call.enqueue(object: Callback<BusData> {
//            override fun onResponse(call: Call<BusData>, response: Response<BusData>) {
//                val info = response.body()
//                val arr = info?.msgBody?.busArrivalList
//
//                binding.textView2.text=arr?.size.toString()
////                val arr = info?.msgBody?.busArrivalList!!
////                binding.textView2.text=arr[0].plateNo1
//
//            }
//
//            override fun onFailure(call: Call<BusData>, t: Throwable) {
//                Log.d("TTT",t.message!!)
//                binding.textView2.text="failed"
//                call.cancel()
//
//            }
//        }
 //       )
    }


    }
//class NetworkThread(
//    var url: String): Runnable {
//
//    override fun run() {
//
//        try {
//
//            val xml : Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)
//
//
//            xml.documentElement.normalize()
//
//            //찾고자 하는 데이터가 어느 노드 아래에 있는지 확인
//            val list:NodeList = xml.getElementsByTagName("item")
//
//            //list.length-1 만큼 얻고자 하는 태그의 정보를 가져온다
//            for(i in 0 until list.length){
//
//                val n:Node = list.item(i)
//
//                if(n.getNodeType() == Node.ELEMENT_NODE){
//
//                    val elem = n as Element
//
//                    val map = mutableMapOf<String,String>()
//
//
//                    // 이부분은 어디에 쓰이는지 잘 모르겠다.
//                    for(j in 0 until elem.attributes.length ) {
//
//                        map.putIfAbsent(elem.attributes.item(j).nodeName, elem.attributes.item(j).nodeValue)
//
//                    }
//
//
//                    println("=========${i+1}=========")
//                    text += "${i + 1}번 캠핑장 \n"
//
//                    println("1. 주소 : ${elem.getElementsByTagName("plateNo1").item(0).textContent}")
//                    text += "1. 주소 : ${elem.getElementsByTagName("plateNo1").item(0).textContent} \n"
//
//
//
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("TTT", "오픈API"+e.toString())
//        }
//    }
//}