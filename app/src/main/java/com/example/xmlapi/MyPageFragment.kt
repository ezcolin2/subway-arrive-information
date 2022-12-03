package com.example.xmlapi

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xmlapi.databinding.FragmentMyPageBinding
import com.example.xmlapi.viewmodel.DataViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient


class MyPageFragment : Fragment() ,View.OnClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentMyPageBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private val model : DataViewModel by activityViewModels()
    private lateinit var userInfo : User
    val user = Firebase.auth.currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = Firebase.auth
        mDbRef = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPageBinding.inflate(inflater,container,false)
        model.user.observe(viewLifecycleOwner){
            userInfo=model.user.value!!
            Log.d("hello",userInfo.uid)
            binding.txtEmail.text=userInfo.email
            binding.txtName.text=userInfo.name
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(
                    ContentValues.TAG, "사용자 정보 요청 성공" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}")

                val kname = "${user.kakaoAccount?.profile?.nickname}"
                val kemail = "${user.kakaoAccount?.email}"

                binding.txtName.text = kname
                binding.txtEmail.text = kemail
            }
        }

//        binding.txtName.text=userInfo.name
//        binding.txtEmail.text=userInfo.email





//        user?.let {
//        mDbRef.child("user").addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(postSnapshot in snapshot.children) {
//
//
//                    val currentUser = postSnapshot.getValue(User::class.java)
//                    if(currentUser?.email==model.email.value) {
//                        //binding.txtName.text = currentUser?.name
//                        binding.txtName.text = postSnapshot.key
//                        binding.txtEmail.text = currentUser?.email
//                    }
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//        }
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.btnComment.setOnClickListener(this)

        binding.btnLogout.setOnClickListener {
            //firebase auth에서 sign out 기능 호출
            FirebaseAuth.getInstance().signOut()

            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                }
            }

            val intent= Intent(activity,MainActivity::class.java) //로그인 페이지 이동
            startActivity(intent)
            (activity as RealActivity).finish()
        }
    }

    override fun onClick(v:View){
        //(activity as RealActivity).activateCafeInformActivity("반점")
    }

}