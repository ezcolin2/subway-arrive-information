package com.example.xmlapi

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.xmlapi.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init((activity as MainActivity).applicationContext, "ee89ef027765bd90ca0248eb9fe9cf6d")
        mAuth = Firebase.auth
        mDbRef = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.btnKakaologin.setOnClickListener {
            kakaoLogIn()
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()

            logIn(email, password)
        }
        binding.btnSignup.setOnClickListener {
            val fragmentSignup = SignupFragment()
            (activity as MainActivity).replaceFragment(fragmentSignup)
        }
        return binding.root
    }

    private fun kakaoLogIn() {
        val context = (activity as MainActivity).applicationContext
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(ContentValues.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                addKuserToDatabase()
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(ContentValues.TAG, "카카오톡으로 로그인 실패", error)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(ContentValues.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    addKuserToDatabase()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun logIn(email: String, password:String) {
        if ((email.isNotEmpty()) || password.isNotEmpty()) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity as MainActivity) { task ->
                if (task.isSuccessful) {
                    val intent: Intent = Intent(activity as MainActivity, RealActivity::class.java)
                    intent.putExtra("email",email)
                    startActivity(intent)
                    Toast.makeText(activity as MainActivity,"로그인 성공", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).finish()
                } else {
                    Toast.makeText(activity as MainActivity,"로그인 실패", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Error: ${task.exception}")
                }
            }
        }
        else {
            Toast.makeText(activity as MainActivity, "이메일이나 비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addKuserToDatabase() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(ContentValues.TAG, "사용자 정보 요청 성공" +
                      "\n회원번호: ${user.id}" +
                      "\n이메일: ${user.kakaoAccount?.email}" +
                      "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                      "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                val kname = "${user.kakaoAccount?.profile?.nickname}"
                val kgender = "${user.kakaoAccount?.gender}"
                val kemail = "${user.kakaoAccount?.email}"
                val kid = "${user.id}"
                signUp(kname, kgender, kemail, kid)
            }
        }
    }

    private fun signUp(name: String, gender: String, email: String, id: String) {
        mAuth.createUserWithEmailAndPassword(email, id)
            .addOnCompleteListener(activity as MainActivity) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, gender, email, id)
                }
                else {
                    val intent: Intent = Intent(activity as MainActivity, RealActivity::class.java)
                    intent.putExtra("email",email)
                    startActivity(intent)
                    Toast.makeText(activity as MainActivity,"카카오 로그인 성공", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name:String, gender:String, email:String, uId: String) {
        mDbRef.child("user").child(uId).setValue(User(name, gender, email, uId))
        val intent: Intent = Intent(activity as MainActivity, RealActivity::class.java)
        startActivity(intent)
        Toast.makeText(context,"카카오 로그인 성공", Toast.LENGTH_SHORT).show()
    }
}