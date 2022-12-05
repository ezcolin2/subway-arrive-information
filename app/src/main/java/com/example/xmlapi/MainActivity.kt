package com.example.xmlapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.xmlapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    val fragmentLogin = LoginFragment()
    var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(fragmentLogin)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (System.currentTimeMillis() > waitTime + 2500) {
            waitTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (System.currentTimeMillis() <= waitTime + 2500) {
            finishAffinity()
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(binding.frmFragment.id, fragment)
            commit()
        }
    }
}