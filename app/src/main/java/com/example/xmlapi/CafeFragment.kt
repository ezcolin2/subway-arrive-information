package com.example.xmlapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlapi.databinding.FragmentCafeBinding
import com.example.xmlapi.viewmodel.DataViewModel


class CafeFragment : Fragment() {
    private lateinit var binding: FragmentCafeBinding
    val model:DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCafeBinding.inflate(inflater)
        (activity as RealActivity).visibleBottom()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var arr=ArrayList<Cafe>()
        model.cafeList.observe(viewLifecycleOwner){
            arr=model.cafeList.value?:ArrayList<Cafe>()

            val adapter = CafeAdapter(this,arr)
            binding.recView.layoutManager = LinearLayoutManager(requireContext())
            binding.recView.adapter=adapter

        }
    }
    fun clickEvent(cafeName:String){
        model.setStoreName(cafeName)
        findNavController().navigate(R.id.action_cafeFragment_to_reviewFragment)
    }
}