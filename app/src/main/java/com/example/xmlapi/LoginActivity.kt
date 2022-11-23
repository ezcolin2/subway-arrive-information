package com.example.xmlapi

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.xmlapi.databinding.ActivityLoginBinding
import com.example.xmlapi.databinding.ActivityRealBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, "ee89ef027765bd90ca0248eb9fe9cf6d")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth

        mDbRef = Firebase.database.reference

        //카카오 로그인 버튼 이벤트
        binding.btnKakaologin.setOnClickListener {
            kakaoLogin()
        }

        //로그인 버튼 이벤트
        binding.btnLogin.setOnClickListener {
            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()

            logIn(email, password)
        }
        //회원가입 버튼 이벤트
        binding.btnSignup.setOnClickListener {
            val intent: Intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun kakaoLogin() {
        // 로그인 조합 예제

        val context = application.applicationContext
        //카카오 계정 로그인
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(ContentValues.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                addKuserToDatabase()
            }
        }

        // 카카오톡 로그인
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


    // 로그인 함수
    private fun logIn(email: String, password:String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent: Intent = Intent(this@LoginActivity, RealActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"로그인 성공", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this,"로그인 실패", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Error: ${task.exception}")
                }
            }
    }

    private fun addKuserToDatabase() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(
                    ContentValues.TAG, "사용자 정보 요청 성공" +
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
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, gender, email, id)
                }
                else {
                    val intent: Intent = Intent(this@LoginActivity, RealActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"카카오 로그인 성공", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name:String, gender:String, email:String, uId: String) {
        mDbRef.child("user").child(uId).setValue(User(name, gender, email, uId))
        val intent: Intent = Intent(this@LoginActivity, RealActivity::class.java)
        startActivity(intent)
        Toast.makeText(this,"카카오 로그인 성공", Toast.LENGTH_SHORT).show()
    }

}