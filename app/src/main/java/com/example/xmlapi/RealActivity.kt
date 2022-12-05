package com.example.xmlapi

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.xmlapi.databinding.ActivityRealBinding
import com.example.xmlapi.viewmodel.DataViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class RealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRealBinding
    private lateinit var database: DatabaseReference
    private val model : DataViewModel by viewModels()
    var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email = intent.getStringExtra("email")!!
        model.setEmail(email)
        database= Firebase.database.reference
        binding.bottomNav.setupWithNavController(binding.container.getFragment<NavHostFragment>().navController)
    }

    fun hideBottom(){
        binding.bottomNav.visibility=View.GONE
    }
    fun visibleBottom(){
        binding.bottomNav.visibility=View.VISIBLE
    }

    private fun getForegroundFragment(): Fragment? {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.container)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentfragment = getForegroundFragment().toString().substring(0 until 12)
        if (currentfragment == "CafeFragment") {
            if (System.currentTimeMillis() > waitTime + 2500) {
                waitTime = System.currentTimeMillis()
                Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                return
            }
            if (System.currentTimeMillis() <= waitTime + 2500) {
                finishAffinity()
            }
        }
        else {
            super.onBackPressed()
        }
    }
}