package com.example.xmlapi

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlapi.databinding.ListCafeBinding

class CafeAdapter(val context:CafeFragment, val cafe : ArrayList<Cafe>)
    : RecyclerView.Adapter<CafeAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListCafeBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(context,binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(cafe[position])
    }

    override fun getItemCount() = cafe.size

    class Holder(val context:CafeFragment,private val binding: ListCafeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cafe: Cafe) {
            binding.txtCafe.text = cafe.cafeName
            binding.txtRatingNum.text = String.format("%.2f",cafe.ratings)
            binding.txtRating.rating = cafe.ratings
            binding.txtReviewNum.text = "리뷰 수 : ${cafe.nums}"
            binding.root.setOnClickListener {
                context.clickEvent(cafe.cafeName)
                Log.d("cafe",cafe.cafeName)
            }
        }
    }
}
