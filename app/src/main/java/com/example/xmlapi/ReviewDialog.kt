package com.example.xmlapi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.xmlapi.databinding.ReviewsDialogBinding
import com.example.xmlapi.viewmodel.DataViewModel
import java.text.SimpleDateFormat

class ReviewDialog : DialogFragment(){
    lateinit var binding:ReviewsDialogBinding
    private val model : DataViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=ReviewsDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        var stars:Float = 0F

        binding.ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, formUser ->
                stars = rating
            }
        binding.btnRegister.setOnClickListener {
            val uId:String = model.user.value?.uid!!
            val name:String = model.user.value?.name!!
            val time:String = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
            val editComment:String = binding.dialogComment.text.toString()
            val comment = StoreComment(uId,name,editComment,stars,time)


            model.setComment(comment)
            dismiss()
        }


        super.onViewCreated(view, savedInstanceState)
    }
}