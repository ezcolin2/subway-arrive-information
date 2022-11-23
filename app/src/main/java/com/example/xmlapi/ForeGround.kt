package com.example.xmlapi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class ForeGround : Service() {
    val CHANNEL_ID="123"
    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(CHANNEL_ID,"FOREGROUND", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java) //매니저 생성
            manager.createNotificationChannel(serviceChannel) //서비스 채널 등록
            //onStartCommand 오버라이드
        }

    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()//채널 생성
        var notification = NotificationCompat.Builder(this, CHANNEL_ID)//호한성을 위해 낮은 버전 사용하고 빌더
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("경의중앙선 : ")
            .setContentText("2전역")
            .build() //빌드를 하면 notification 생성하고 변수에 저장
        startForeground(99, notification)//id와 notification을 날려서 포어그라운드라는 것을알려줌
        thread(start=true){
            for(i in 0..9){

                val call = Api().apiRequest2()
                lateinit var arr:Array<Data2>
                var nowPosition:Data2?=null

                call.enqueue(object: Callback<SubwayApiData2> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(call: Call<SubwayApiData2>, response: Response<SubwayApiData2>) {
                        val info = response.body()
                        Log.d("hello", info?.errorMessage?.message?:"no Error")
                        if(info?.realtimePositionList==null){
                            return

                        }
                        arr=info?.realtimePositionList

                        for(i in arr.indices){
                            if(arr[i].trainNo==intent?.getStringExtra("trainNum")){
                                nowPosition = arr[i]
                                break
                            }
                        }
                        var status=""
                        if(nowPosition?.trainSttus=="0"){
                            status="진입"
                        }
                        else if(nowPosition?.trainSttus=="1"){
                            status="도착"
                        }
                        else{
                            status="출발"
                        }
                        if(nowPosition?.statnNm=="화전"){
                            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                            val vibrationEffect = VibrationEffect.createOneShot(3000,100)
                            //vibrator.vibrate(vibrationEffect)



                        }

                        val noti = NotificationCompat.Builder(this@ForeGround,CHANNEL_ID)//호한성을 위해 낮은 버전 사용하고 빌더
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("경의중앙선 현재 위치 ")
                            .setContentText(nowPosition?.statnNm!! + status)
                            .build()
                        startForeground(99,noti)
                        Log.d("호출",nowPosition?.statnNm?:"none")
                    }

                    override fun onFailure(call: Call<SubwayApiData2>, t: Throwable) {
                        Log.d("TTT",t.message!!)
                        call.cancel()

                    }
                })

                Thread.sleep(60000)


            }
        }


        //이제 여기에 백그라운드 실행할 함수 만들면 됨
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }
}