package com.example.xmlapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xmlapi.databinding.ActivityLoginBinding
import com.example.xmlapi.databinding.ActivityRealBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRealLogin.setOnClickListener{
            val intent = Intent(this, RealActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}