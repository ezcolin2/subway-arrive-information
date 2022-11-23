package com.example.xmlapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.activityViewModels
import com.example.xmlapi.databinding.FragmentSubwayBinding
import com.example.xmlapi.viewmodel.Viewmodel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubwayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubwayFragment : Fragment() {
    lateinit var binding:FragmentSubwayBinding
    lateinit var realActivity: RealActivity
    lateinit var arr:Array<Data>
    val model: Viewmodel by activityViewModels()
    var foregroundIsRunning = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        realActivity = context as RealActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model.subways.observe(viewLifecycleOwner){
            arr=model.subways.value!!
        }
        binding=FragmentSubwayBinding.inflate(layoutInflater,container,false)
        binding.btnGang.setOnClickListener {
            val adapter = SubwayAdapter(requireContext(),arr)
            binding.listView.adapter=adapter
        }
        binding.btnCanceled.setOnClickListener {
            foregroundIsRunning=false
            (activity as RealActivity).serviceStop()
        }

        return binding.root
    }


    private fun apiRequest(){

        val call = Api().apiRequest()
        lateinit var arr:Array<Data>


        call.enqueue(object: Callback<SubwayApiData> {
            override fun onResponse(call: Call<SubwayApiData>, response: Response<SubwayApiData>) {
                val info = response.body()
                arr = info?.realtimeArrivalList!!


                val Adapter = SubwayAdapter(requireContext(), arr)
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
                    if(foregroundIsRunning){
                        return
                    }

                    foregroundIsRunning=true
                    val trainNo = arr[position].btrainNo
                    Log.d("train no",trainNo)
                    setPosition(trainNo)
                    (activity as RealActivity).serviceStart(trainNo)


                }

            }
        }
        )
    }

    private fun setPosition(num:String){

        val call = Api().apiRequest2()
        lateinit var arr:Array<Data2>
        var nowPosition:Data2?=null

        call.enqueue(object: Callback<SubwayApiData2> {
            override fun onResponse(call: Call<SubwayApiData2>, response: Response<SubwayApiData2>) {
                binding.btnGang.text="성공"
                val info = response.body()
                binding.btnGang.text="성공2"
                Log.d("hello", info?.errorMessage?.message?:"no Error")
                if(info?.realtimePositionList==null){
                    binding.txtNow.text="없음"
                    binding.txtTrainName.text="선택하세요"
                    binding.txtLast.text=""
                    binding.txtTime.text=""
                    binding.txtNum.text=""
                    return

                }

                binding.btnGang.text="성공3"
                arr=info?.realtimePositionList

                for(i in arr.indices){
                    binding.btnGang.text = arr[i].statnNm
                    if(arr[i].trainNo==num){
                        nowPosition = arr[i]
                        binding.txtNow.text = nowPosition!!.statnNm
                        binding.txtTrainName.text = "경의중앙선"
                        binding.txtLast.text = nowPosition!!.statnTnm
                        binding.txtTime.text = nowPosition!!.recptnDt
                        binding.txtNum.text = nowPosition!!.trainNo
                        break
                    }
                }
            }

            override fun onFailure(call: Call<SubwayApiData2>, t: Throwable) {
                binding.btnGang.text="실페"
                Log.d("TTT",t.message!!)
                call.cancel()

            }
        })
    }
}