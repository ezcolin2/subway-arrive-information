package com.example.xmlapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlapi.databinding.FragmentCafeBinding
import com.example.xmlapi.viewmodel.DataViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CafeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CafeFragment : Fragment() {
    private lateinit var binding: FragmentCafeBinding
    val model:DataViewModel by activityViewModels()
    private lateinit var arr:ArrayList<Cafe>
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
        model.cafeList.observe(viewLifecycleOwner){
            arr=model.cafeList.value!!
            val adapter = CafeAdapter2(this,arr)
            binding.recView.layoutManager = LinearLayoutManager(requireContext())
            binding.recView.adapter=adapter


//            adapter.notifyDataSetChanged()
//            binding.listView.adapter=adapter
//            val listener = ListClickListener()
//            binding.listView.onItemClickListener=listener
        }



    }
    inner class ListClickListener: AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            model.setStoreName(arr[position].cafeName)
            findNavController().navigate(R.id.action_cafeFragment_to_reviewFragment)

        }

    }



    fun clickEvent(cafeName:String){
        model.setStoreName(cafeName)
        findNavController().navigate(R.id.action_cafeFragment_to_reviewFragment)
    }







}