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
    val model: DataViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        realActivity = context as RealActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSubwayBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var subways = emptyArray<SubwayData>()
        model.subways.observe(viewLifecycleOwner) {
            subways = model.subways.value?: emptyArray()

            binding.recView.layoutManager = LinearLayoutManager(requireContext())
            binding.recView.adapter = SubwayAdapter(subways)
        }
        binding.btnGang.setOnClickListener {
            model.getSubway()


        }
    }
}