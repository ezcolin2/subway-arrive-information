package com.example.xmlapi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.xmlapi.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = Firebase.auth
        mDbRef = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.btnSignup.setOnClickListener {
            val name = binding.nameEdit.text.toString().trim()
            val gender = binding.genderEdit.text.toString().trim()
            val email = binding.emailEdit.text.toString().trim()
            val password = binding.passwordEdit.text.toString().trim()
            signUp(name, gender, email, password)
        }
        binding.btnToLogin.setOnClickListener {
            val fragmentLogin = LoginFragment()
            (activity as MainActivity).replaceFragment(fragmentLogin)
        }
        return binding.root
    }

    private fun signUp(name: String, gender: String, email: String, password: String) {
        if ((name.isNotEmpty()) || gender.isNotEmpty() || (email.isNotEmpty()) || password.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity as MainActivity) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity as MainActivity, "회원가입 성공", Toast.LENGTH_SHORT)
                            .show()
                        val intent =
                            Intent(activity as MainActivity, MainActivity::class.java)
                        startActivity(intent)
                        addUserToDatabase(name, gender, email, mAuth.currentUser?.uid!!)
                    } else {
                        Toast.makeText(activity as MainActivity, "회원가입 실패", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        else {
            Toast.makeText(activity as MainActivity, "모든 칸이 입력되었는지 확인해주세요", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun addUserToDatabase(name:String, gender:String, email:String, uId: String) {
        mDbRef.child("user").child(uId).setValue(User(name, gender, email, uId))
    }
}