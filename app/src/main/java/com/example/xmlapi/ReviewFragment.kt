package com.example.xmlapi

import android.content.Intent
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.xmlapi.databinding.FragmentReviewBinding
import com.example.xmlapi.viewmodel.Viewmodel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
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
    private val model : Viewmodel by activityViewModels()
    private lateinit var title:String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        arguments?.let {
//            title = it.getString("storeName")?:"정보 없음"
//        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model.storeName.observe(viewLifecycleOwner){

        }
        model.reviews.observe(viewLifecycleOwner){

        }
        title = model.storeName.value.toString()
        Log.d("name",title)
        // Inflate the layout for this fragment
        (activity as RealActivity).hideBottom()
        binding=FragmentReviewBinding.inflate(inflater)
        database= Firebase.database.reference
        //commentList = ArrayList()
        binding.txtTitle.text=title
        commentList=model.reviews.value?:ArrayList<StoreComment>()

        val commentAdapter = CommentAdapter(requireContext(), commentList)
        binding.listView.adapter=commentAdapter

        Log.d("name",title)

//        database.child("cafe").child(title).child("comment").addValueEventListener(object:
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var totalScore:Float = 0F;
//                var totalCount:Int = 0;
//
//                for(snap in snapshot.children){
//                    val comment:StoreComment = snap.getValue<StoreComment>()!!
//
//                    if(comment.time=="not"){
//                        continue;
//                    }
//                    commentList.add(0,comment)
//                    totalScore+=snap.child("score").getValue<Float>()?:0F
//                    totalCount++
//                }
//                if(totalCount!=0) {
//                    setScoreAndCount(title, totalScore / totalCount, totalCount)
//                }
//
//
//                commentAdapter.notifyDataSetChanged()
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
        binding.btnRegisterReview.setOnClickListener{
            val reviewDialog = LayoutInflater.from(requireContext()).inflate(R.layout.reviews_dialog,null)
            val reviewBuilder= AlertDialog.Builder(requireContext())
                .setView(reviewDialog)
                .setTitle("평가")
            val review = reviewBuilder.show()
            val btnRegister = reviewDialog.findViewById<Button>(R.id.btn_register)
            val btnCancel = reviewDialog.findViewById<Button>(R.id.btn_cancel)
            val ratingBar = reviewDialog.findViewById<RatingBar>(R.id.ratingBar)

            var stars:Float = 0F

            ratingBar.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { ratingBar, rating, formUser ->
                    stars = rating
                    btnCancel.text=rating.toString()
                }
            btnRegister.setOnClickListener {
                val uId:String = "8"
                val time:String = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
                val editComment:String = reviewDialog.findViewById<EditText>(R.id.dialog_comment).text.toString()

                val comment = StoreComment(uId,"seoha",editComment,stars,time)
                findNavController().navigate(R.id.action_reviewFragment2_to_cafeFragment)

                WriteFirebaseStore(comment,title).uploadComment()
            }
            btnCancel.setOnClickListener{
                review.dismiss()
            }
        }
        return binding.root



    }

    private fun setScoreAndCount(storeName:String,score: Float, count:Int) {
        database.child("cafe").child(storeName).child("totalScore").setValue(score)
        database.child("cafe").child(storeName).child("totalCount").setValue(count)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }


}