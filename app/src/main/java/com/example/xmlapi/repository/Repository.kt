//package com.example.xmlapi.repository
//
//import android.content.Intent
//import android.widget.Toast
//import com.example.xmlapi.MainActivity
//import com.example.xmlapi.SignupActivity
//import com.example.xmlapi.User
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//
//class Repository {
//    val database = Firebase.database
//    // 인증 초기화
//    val auth = Firebase.auth
//    //db 초기화
//    val databaseRefrence = Firebase.database.reference
//    fun signUp(name: String, gender: String, email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
//                    val intent: Intent = Intent(SignupActivity, MainActivity::class.java)
//                    startActivity(intent)
//                    addUserToDatabase(name, gender, email, mAuth.currentUser?.uid!!)
//                } else {
//                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    fun addUserToDatabase(name:String, gender:String, email:String, uId: String) {
//        databaseReference.child("user").child(uId).setValue(User(name, gender, email, uId))
//    }
//}