package com.example.xmlapi

import android.content.Intent
import android.net.rtp.AudioGroup
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        binding=ActivityStoreBinding.inflate(layoutInflater)
        database= Firebase.database.reference
        setContentView(binding.root)
        commentList = ArrayList()
        val comment = "서비스도 많이 주고 맛있어요. 짜장면 보다는 짬뽕이 더 맛있는 것 같네요"
        val c1:StoreComment = StoreComment("chulsoo",comment,5, SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()))
        val commentAdapter = CommentAdapter(this@StoreActivity, commentList)
        binding.listView.adapter=commentAdapter

        WriteFirebaseStore("1",c1,"반점").uploadComment()
        WriteFirebaseStore("2",c1,"반점").uploadComment()
        WriteFirebaseStore("3",c1,"반점").uploadComment()
        WriteFirebaseStore("4",c1,"반점").uploadComment()
        WriteFirebaseStore("5",c1,"반점").uploadComment()
        database.child("cafe").child("반점").child("comment").addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children){
                    val comment:StoreComment = snap.getValue<StoreComment>()!!
                    commentList.add(0,comment)
                }
                commentAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        binding.btnReview.setOnClickListener{
            val reviewDialog = LayoutInflater.from(this).inflate(R.layout.reviews_dialog,null)
            val reviewBuilder=AlertDialog.Builder(this)
                .setView(reviewDialog)
                .setTitle("평가")
            val review = reviewBuilder.show()
            val btnRegister = reviewDialog.findViewById<Button>(R.id.btn_register)
            val btnCancel = reviewDialog.findViewById<Button>(R.id.btn_cancel)
            val radioGroup = reviewDialog.findViewById<RadioGroup>(R.id.start_adio)

            var stars:Int = 0
            radioGroup.setOnCheckedChangeListener{group, checkId ->
                when(checkId){
                    R.id.one_star -> stars=1
                    R.id.two_star -> stars=2
                    R.id.three_star -> stars=3
                    R.id.four_star -> stars=4
                    R.id.five_star -> stars=5
                }
                btnCancel.text=stars.toString()
            }

            btnRegister.setOnClickListener {
                val uId:String = "8"
                val time:String = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
                val editComment:String = reviewDialog.findViewById<EditText>(R.id.dialog_comment).text.toString()

                val comment = StoreComment("seoha",editComment,stars,time)
                WriteFirebaseStore(uId,comment,"반점").uploadComment()
                review.dismiss()
                val intent: Intent = Intent(this@StoreActivity,StoreActivity::class.java)
                startActivity(intent)
                finish()
            }
            btnCancel.setOnClickListener{
                review.dismiss()
            }
        }



    }
}