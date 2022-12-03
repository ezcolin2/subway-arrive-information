package com.example.xmlapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.xmlapi.databinding.FragmentReviewBinding
import com.example.xmlapi.viewmodel.DataViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding:FragmentReviewBinding
    private lateinit var database: DatabaseReference
    private lateinit var commentList:ArrayList<StoreComment>
    private val model : DataViewModel by activityViewModels()
    private lateinit var title:String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model.storeName.observe(viewLifecycleOwner){
            binding.txtTitle.text=title
        }
        model.reviews.observe(viewLifecycleOwner){
            commentList=model.reviews.value?:ArrayList<StoreComment>()

            val commentAdapter = CommentAdapter(requireContext(), commentList)
            binding.listView.adapter=commentAdapter
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

    private fun setScoreAndCount(storeName:String,score: Float, count:Int) {
        database.child("cafe").child(storeName).child("totalScore").setValue(score)
        database.child("cafe").child(storeName).child("totalCount").setValue(count)
    }




}