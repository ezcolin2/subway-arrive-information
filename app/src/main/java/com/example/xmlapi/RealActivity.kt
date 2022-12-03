package com.example.xmlapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.xmlapi.databinding.ActivityRealBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealActivity : AppCompatActivity() {
    lateinit var binding:ActivityRealBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= Firebase.database.reference
        binding.bottomNav.setupWithNavController(binding.container.getFragment<NavHostFragment>().navController)
    }


    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(binding.container.id, fragment)
            commit()
        }
    }

    fun serviceStart(num:String){
        val intent = Intent(this, ForeGround::class.java) //서비스를 할 인텐트 생성
        intent.putExtra("trainNum",num)
        ContextCompat.startForegroundService(this, intent)//인텐트를 담음
    }
    fun serviceStop(){
        val intent = Intent(this, ForeGround::class.java)
        stopService(intent)
    }
    fun hideBottom(){
        binding.bottomNav.visibility=View.GONE
    }
    fun visibleBottom(){
        binding.bottomNav.visibility=View.VISIBLE
    }


}