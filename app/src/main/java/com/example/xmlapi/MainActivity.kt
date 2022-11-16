package com.example.xmlapi

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import com.example.xmlapi.databinding.ActivityLoginBinding
import com.example.xmlapi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubway.setOnClickListener{
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val vibrationEffect = VibrationEffect.createOneShot(1000,100)
            vibrator.vibrate(vibrationEffect)
        }
        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}