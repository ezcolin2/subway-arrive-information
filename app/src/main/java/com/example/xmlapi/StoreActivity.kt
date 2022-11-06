package com.example.xmlapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.xmlapi.databinding.ActivityStoreBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class StoreActivity : AppCompatActivity() {
    lateinit var binding :ActivityStoreBinding

    private lateinit var database: DatabaseReference
    private lateinit var commentList:ArrayList<StoreComment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent:Intent = getIntent()
        val title:String= intent.getStringExtra("storeName")?:"정보없음"
        binding=ActivityStoreBinding.inflate(layoutInflater)
        database= Firebase.database.reference
        setContentView(binding.root)
        commentList = ArrayList()
        binding.txtTitle.text=title

        val commentAdapter = CommentAdapter(this@StoreActivity, commentList)
        binding.listView.adapter=commentAdapter


        database.child("cafe").child(title).child("comment").addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var totalScore:Float = 0F;
                var totalCount:Int = 0;

                for(snap in snapshot.children){
                    val comment:StoreComment = snap.getValue<StoreComment>()!!

                    if(comment.time=="not"){
                        continue;
                    }
                    commentList.add(0,comment)
                    totalScore+=snap.child("score").getValue<Float>()?:0F
                    totalCount++
                }
                if(totalCount!=0) {
                    setScoreAndCount(title, totalScore / totalCount, totalCount)
                }


                commentAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.btnExit.setOnClickListener{
            finish()
        }
        binding.btnReview.setOnClickListener{
            val reviewDialog = LayoutInflater.from(this).inflate(R.layout.reviews_dialog,null)
            val reviewBuilder=AlertDialog.Builder(this)
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
                WriteFirebaseStore(comment,"반점").uploadComment()
                review.dismiss()
                val intent: Intent = Intent(this@StoreActivity,StoreActivity::class.java)
                intent.putExtra("storeName",title)
                startActivity(intent)
                finish()
            }
            btnCancel.setOnClickListener{
                review.dismiss()
            }
        }



    }

    private fun setScoreAndCount(storeName:String,score: Float, count:Int) {
        database.child("cafe").child(storeName).child("totalScore").setValue(score)
        database.child("cafe").child(storeName).child("totalCount").setValue(count)
    }
}