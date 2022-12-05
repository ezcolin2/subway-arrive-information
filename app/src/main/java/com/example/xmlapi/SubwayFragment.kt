package com.example.xmlapi

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlapi.databinding.FragmentSubwayBinding
import com.example.xmlapi.viewmodel.DataViewModel

class SubwayFragment : Fragment() {
    lateinit var binding: FragmentSubwayBinding
    lateinit var realActivity: RealActivity
    var arr: Array<SubwayData>? = null
    val model: DataViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        realActivity = context as RealActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model.subways.observe(viewLifecycleOwner) {
            model.getSubway()
            arr = model.subways.value
        }
        binding = FragmentSubwayBinding.inflate(layoutInflater, container, false)
        binding.btnGang.setOnClickListener {
            model.getSubway()
            arr = model.subways.value
            arr?.let {
                val adapter = SubwayAdapter(it)
                binding.recView.layoutManager = LinearLayoutManager(requireContext())
                binding.recView.adapter = adapter
            }
        }

        return binding.root
    }
}