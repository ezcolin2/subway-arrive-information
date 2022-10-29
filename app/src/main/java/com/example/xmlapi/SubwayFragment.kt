package com.example.xmlapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.databinding.DataBindingUtil.setContentView
import com.example.xmlapi.databinding.FragmentSubwayBinding
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentSubwayBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSubwayBinding.inflate(layoutInflater)
        val view:View = inflater.inflate(R.layout.fragment_subway,container,false)

        val call = Api().apiRequest()
        lateinit var arr:Array<Data>

        call.enqueue(object :Callback<Ticker>{
            override fun onResponse(call: Call<Ticker>, response: Response<Ticker>) {
                val info = response.body()
                arr = info?.realtimeArrivalList!!


                val adapter2 = SubwayFragAdapter(requireContext(), arr)
                val list = view.findViewById<ListView>(R.id.list_view)
                list.adapter = adapter2




            }

            override fun onFailure(call: Call<Ticker>, t: Throwable) {
                Log.d("TTT",t.message!!)
                call.cancel()

            }
        })



        return view
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubwayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubwayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}