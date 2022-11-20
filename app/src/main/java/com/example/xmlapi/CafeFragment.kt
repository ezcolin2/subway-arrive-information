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
        viewModel.cafeList.observe(viewLifecycleOwner){
            arr=viewModel.cafeList.value!!
        }
        binding = FragmentCafeBinding.inflate(inflater,container,false)
        (activity as RealActivity).visibleBottom()

//        database.child("cafe").addValueEventListener(
//            object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if(snapshot==null){
//                        return
//                    }
//                    for(snap in snapshot.children){
//
//                        val reviewNums:Int = snap.child("totalCount").getValue<Int>()?:0
//                        val reviewStars:Float = snap.child("totalScore").getValue<Float>()?:0F
//                        val name:String = snap.child("name").getValue<String>()?:"없음"
//
//
//                        arr.add(Cafe(name,reviewNums,reviewStars))
//                    }
//                    adapter.notifyDataSetChanged()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    arr.add(Cafe("정보 없음",0,0F))
//                }
//            }
//        )



        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val arr=viewModel.cafeList.value!!


        val adapter = CafeAdapter(requireContext(),arr)
        adapter.notifyDataSetChanged()
        binding.listView.adapter=adapter
        val listener = ListClickListener()
        binding.listView.onItemClickListener=listener

    }
    inner class ListClickListener: AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            viewModel.setStoreName(arr[position].cafeName)
            val bundle = bundleOf("storeName" to arr[position].cafeName)
            findNavController().navigate(R.id.action_cafeFragment_to_reviewFragment,bundle)

        }

    }







}