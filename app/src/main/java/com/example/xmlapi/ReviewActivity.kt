package com.example.xmlapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlapi.databinding.ActivityMainBinding

class ReviewActivity: AppCompatActivity() {

    val review = arrayOf(
        Review(storename = "카페명1", contents = "맛있어요!" ),
        Review(storename = "카페명2", contents = "맛있어요!" ),
        Review(storename = "카페명3", contents = "맛있어요!" ),
        Review(storename = "카페명4", contents = "맛있어요!" ),
        Review(storename = "카페명5", contents = "맛있어요!" ),
    )

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recMyreview.layoutManager = LinearLayoutManager(this)
        binding.recMyreview.adapter = ReviewAdapter(review)
    }
}