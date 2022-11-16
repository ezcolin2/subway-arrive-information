package com.example.xmlapi

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlapi.databinding.ListMyreviewBinding

class ReviewAdapter(val review: Array<Review>)
    : RecyclerView.Adapter<ReviewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListMyreviewBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(review[position])
    }

    override fun getItemCount() = review.size

    class Holder(private val binding: ListMyreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review){
            binding.txtStorename =
        }
    }

}
