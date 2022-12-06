package com.example.xmlapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlapi.databinding.FragmentMyCommentBinding
import com.example.xmlapi.viewmodel.DataViewModel


class MyCommentFragment : Fragment() {
    private val model : DataViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMyCommentBinding.inflate(inflater)


        model.getMyComment()
        model.myComments.observe(viewLifecycleOwner){
            val commentList = model.myComments.value

            commentList?.let{
                val adapter = CommentAdapter(commentList)
                binding.myCommentRec.layoutManager = LinearLayoutManager(requireContext())
                binding.myCommentRec.adapter = adapter
            }

        }


        return binding.root
    }


}