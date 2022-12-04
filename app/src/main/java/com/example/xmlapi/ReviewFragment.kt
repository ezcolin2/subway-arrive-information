package com.example.xmlapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlapi.databinding.FragmentReviewBinding
import com.example.xmlapi.viewmodel.DataViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ReviewFragment : Fragment() {
    lateinit var binding:FragmentReviewBinding
    private lateinit var database: DatabaseReference
    private lateinit var commentList:ArrayList<StoreComment>
    private val model : DataViewModel by activityViewModels()
    private lateinit var title:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model.storeName.observe(viewLifecycleOwner){
            binding.txtTitle.text=title
        }
        model.reviews.observe(viewLifecycleOwner){
            commentList=model.reviews.value?:ArrayList<StoreComment>()

            val adapter = CommentAdapter(commentList)
            binding.recView.layoutManager = LinearLayoutManager(requireContext())
            binding.recView.adapter = adapter

        }
        title = model.storeName.value.toString()
        Log.d("name",title)

        (activity as RealActivity).hideBottom()
        binding=FragmentReviewBinding.inflate(inflater)
        database= Firebase.database.reference

        binding.btnRegisterReview.setOnClickListener{
            ReviewDialog().show(parentFragmentManager,"dialog")
        }
        return binding.root



    }





}