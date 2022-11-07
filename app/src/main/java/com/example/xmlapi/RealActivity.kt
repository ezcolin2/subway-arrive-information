package com.example.xmlapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
        setFragment("cafe_fragment", CafeFragment())
        database= Firebase.database.reference
        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.cafeFragment -> setFragment("cafe_fragment", CafeFragment())
                R.id.subwayFragment -> setFragment("subway_fragment", SubwayFragment())
                R.id.myPageFragment-> setFragment("mypage_fragment", MyPageFragment())
            }
            true
        }





    }



    fun activateCafeInformActivity(storeName:String){

        val intent: Intent = Intent(this@RealActivity,StoreActivity::class.java)
        val comment:StoreComment = StoreComment("not","not","not",0F,"not")
        database.child("cafe").child(storeName).child("comment").child("not").setValue(comment)
        database.child("cafe").child(storeName).child("name").setValue(storeName)
        intent.putExtra("storeName",storeName)
        startActivity(intent)

    }
    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()
        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val cafe = manager.findFragmentByTag("cafe_fragment")
        val subway = manager.findFragmentByTag("subway_fragment")
        val myPage = manager.findFragmentByTag("mypage_fragment")

        if (cafe != null){
            fragTransaction.hide(cafe)
        }

        if (subway != null){
            fragTransaction.hide(subway)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }

        if (tag == "cafe_fragment") {
            if (cafe!=null){
                fragTransaction.show(cafe)
            }
        }
        else if (tag == "subway_fragment") {
            if (subway != null) {
                fragTransaction.show(subway)
            }
        }

        else if (tag == "mypage_fragment"){
            if (myPage != null){
                fragTransaction.show(myPage)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}