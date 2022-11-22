package com.example.xmlapi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import com.example.xmlapi.databinding.FragmentCafeBinding
import com.example.xmlapi.repository.Repository
import com.example.xmlapi.viewmodel.Viewmodel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

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
    val viewModel:Viewmodel by activityViewModels()
    private lateinit var arr:ArrayList<Cafe>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCafeBinding.inflate(inflater,container,false)
        (activity as RealActivity).visibleBottom()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cafeList.observe(viewLifecycleOwner){
            arr=viewModel.cafeList.value!!
            val adapter = CafeAdapter(requireContext(),arr)
            adapter.notifyDataSetChanged()
            binding.listView.adapter=adapter
            val listener = ListClickListener()
            binding.listView.onItemClickListener=listener
        }



    }
    inner class ListClickListener: AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            viewModel.setStoreName(arr[position].cafeName)
            findNavController().navigate(R.id.action_cafeFragment_to_reviewFragment)

        }

    }







}